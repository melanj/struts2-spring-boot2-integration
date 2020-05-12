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
import java.util.Date;

@Getter
@Setter
public class IndexAction extends ActionSupport {
    private static final long serialVersionUID = 1L;
    private static final String URL_TIME = "http://worldtimeapi.org/api/ip";
    private static final String URL_IP = "http://ip-api.com/json";
    private static final String URL_BGP_TMPL = "https://www.radb.net/query?advanced_query=" +
            "&keywords=%s&-T+option=&ip_option=&-i+option=";
    private static final String START_POS_SIG = "query-result query-result-external\"><code>";
    private static final String END_POS_SIG = "</code>";
    private static final String USER_AGENT = "user-agent";
    private static final String TIMEZONE = "timezone";
    private static final String DATETIME = "datetime";
    private static final String CLIENT_IP = "client_ip";
    private static final String COUNTRY = "country";
    private static final String REGION_NAME = "regionName";
    private static final String CITY = "city";
    private static final String ISP = "isp";
    private static final String AS = "as";
    private String timeZone;
    private String time;
    private String clientIp;
    private String country;
    private String regionName;
    private String city;
    private String isp;
    private String as;
    private String browser;
    private String bgpData;
    private String timestamp;

    @Autowired
    private HttpServletRequest request;


    @Override
    @Action(value = "/index", results = {@Result(location = "index.jsp", name = "success")})
    public String execute() {
        setTimestamp(String.valueOf((new Date()).toInstant().toEpochMilli()));
        setBrowser(request.getHeader(USER_AGENT));
        RestTemplate restTemplate = new RestTemplate();
        JSONObject response = restTemplate.getForEntity(URL_TIME, JSONObject.class).getBody();
        if (null != response) {
            setTimeZone(response.getAsString(TIMEZONE));
            setTime(response.getAsString(DATETIME));
            setClientIp(response.getAsString(CLIENT_IP));
        }
        response = restTemplate.getForEntity(URL_IP, JSONObject.class).getBody();
        if (null != response) {
            setCountry(response.getAsString(COUNTRY));
            setRegionName(response.getAsString(REGION_NAME));
            setCity(response.getAsString(CITY));
            setIsp(response.getAsString(ISP));
            setAs(response.getAsString(AS));
        }
        String res = restTemplate.getForEntity(String.format(URL_BGP_TMPL,getAs().split(" ")[0]), String.class).getBody();
        if (null != res) {
            int begin = res.indexOf(START_POS_SIG);
            if(begin != -1) {
                int beginIndex = begin + START_POS_SIG.length();
                res = res.substring(beginIndex);
                int endIndex = res.indexOf(END_POS_SIG);
                if(endIndex != -1){
                    res = res.substring(0, endIndex);
                    setBgpData(res);
                }
            }
        }
        return SUCCESS;
    }


}