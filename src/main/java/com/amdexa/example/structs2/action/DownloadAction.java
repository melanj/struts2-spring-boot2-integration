package com.amdexa.example.structs2.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.opensymphony.xwork2.ActionSupport;
import lombok.Getter;
import lombok.Setter;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

@Getter
@Setter
public class DownloadAction extends ActionSupport {

    public static final String JAVA_IO_TMPDIR = "java.io.tmpdir";
    private InputStream fileInputStream;
    private String type;
    private String filename;

    @Override
    @Action(value = "/download/*", params = {"type", "{1}"}, results = {
            @Result(name = SUCCESS, type = "stream", params = {
                    "contentType", "application/octet-stream",
                    "inputName", "fileInputStream",
                    "contentDisposition", "attachment;filename=\"${filename}.{1}\"",
                    "bufferSize", "1024"
            }),
            @Result(location = "fail.jsp", name = ERROR)})
    public String execute() throws Exception {
        try {
            String tempDir = System.getProperty(JAVA_IO_TMPDIR);
            setFileInputStream(new FileInputStream(new File(tempDir + "/" + filename + "." + getType())));
            return SUCCESS;
        } catch (Exception e) {
            return ERROR;
        }
    }
}