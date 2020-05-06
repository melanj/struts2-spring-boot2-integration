package com.amdexa.example.structs2.action;

import com.opensymphony.xwork2.ActionSupport;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@EqualsAndHashCode(callSuper = false)
@Data
@ParentPackage("default")
@Action(value = "/pdf", results = {
        @Result(type = "pdfstream", params = {
                "location", "/WEB-INF/content/report.jsp",
                "contentDisposition", "attachment;filename=jsppdf.pdf"
        })})
public class PdfDownload extends ActionSupport {

    private String type;
    private String filename;
    private String browser;
    private String timestamp;

    @Autowired
    private HttpServletRequest request;

    @Override
    public String execute() throws Exception {
        setTimestamp("Generated on: " + (new Date()).toInstant().toString());
        setBrowser(request.getHeader("user-agent"));
        return SUCCESS;
    }
}
