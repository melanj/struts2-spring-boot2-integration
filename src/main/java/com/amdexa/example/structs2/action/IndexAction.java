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
    public static final String URL_BGP_TMPL = "https://www.radb.net/query?advanced_query=" +
            "&keywords=%s&-T+option=&ip_option=&-i+option=";
    public static final String START_POS_SIG = "query-result query-result-external\"><code>";
    public static final String END_POS_SIG = "</code>";
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
        String res = restTemplate.getForEntity(String.format(URL_BGP_TMPL,"AS208755"), String.class).getBody();
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