package com.amdexa.example.structs2;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTest {

    @LocalServerPort
    int randomServerPort;

    @Test
    void bootstrapTest() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = new URI("http://localhost:" + randomServerPort + "/actuator");
        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        //Verify request succeed
        assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    void rootRedirectToIndexPageTest() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        final HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory();
        CloseableHttpClient build =
                HttpClientBuilder.create().disableRedirectHandling().build();
        factory.setHttpClient(build);
        restTemplate.setRequestFactory(factory);
        URI uri = new URI("http://localhost:" + randomServerPort + "/");
        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        //Verify request succeed
        assertEquals(302, result.getStatusCodeValue());
        assertTrue(result.getHeaders().containsKey("location"));
        assertEquals("http://localhost:" + randomServerPort + "/index.html",
                result.getHeaders().getFirst("location"));
    }

    @Test
    void indexPageHtmlRenderTest() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = new URI("http://localhost:" + randomServerPort + "/index.html");
        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        //Verify request succeed
        assertEquals(200, result.getStatusCodeValue());
        String body = result.getBody();
        assertNotNull(body);
        assertTrue(body.startsWith("<!DOCTYPE html>"));
        assertTrue(body.contains("/struts/js/struts2/jquery.struts2.min.js"));
        assertTrue(body.endsWith("</html>"));
    }
}
