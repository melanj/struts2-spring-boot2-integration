package com.amdexa.example.structs2.action;

import com.opensymphony.xwork2.ActionSupport;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.JSONObject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Getter
@Setter
public class IndexAction extends ActionSupport {
    private static final long serialVersionUID = 1L;
    public static final String URL_TIME = "http://worldtimeapi.org/api/ip";
    public static final String URL_IP = "http://ip-api.com/json";
    private String timeZone;
    private String time;
    private String clientIp;
    private String country;
    private String regionName;
    private String city;
    private String isp;
    private String as;
    private String browser;

    @Autowired
    private HttpServletRequest request;


    @Override
    @Action(value = "/index", results = {@Result(location = "index.jsp", name = "success")})
    public String execute() {
        setBrowser(request.getHeader("user-agent"));
        RestTemplate restTemplate = new RestTemplate();
        JSONObject response = restTemplate.getForEntity(URL_TIME, JSONObject.class).getBody();
        if (null != response) {
            setTimeZone(response.getAsString("timezone"));
            setTime(response.getAsString("datetime"));
            setClientIp(response.getAsString("client_ip"));
        }
        response = restTemplate.getForEntity(URL_IP, JSONObject.class).getBody();
        if (null != response) {
            setCountry(response.getAsString("country"));
            setRegionName(response.getAsString("regionName"));
            setCity(response.getAsString("city"));
            setIsp(response.getAsString("isp"));
            setAs(response.getAsString("as"));
        }
        return SUCCESS;
    }


}