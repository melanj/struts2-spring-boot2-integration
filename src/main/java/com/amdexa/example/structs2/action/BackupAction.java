package com.amdexa.example.structs2.action;

import com.opensymphony.xwork2.ActionSupport;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Getter
@Setter
@Log
@InterceptorRefs({
        @InterceptorRef(value = "defaultStack"),
        @InterceptorRef(value = "execAndWait")}
)
public class BackupAction extends ActionSupport {
    private static final long serialVersionUID = 3919456810740031020L;

    private String hostname;
    private String user;
    private String password;
    private String database;
    private String timestamp;

    public BackupAction() {

    }

    @Override
    @Action(value = "/backup", results = {
            @Result(location = "complete.jsp", name = SUCCESS),
            @Result(location = "wait.jsp", name = "wait"),
            @Result(location = "fail.jsp", name = ERROR)})
    public String execute() {
        String baseName = String.format("%s_%s", database, timestamp);
        String tempDir = System.getProperty("java.io.tmpdir");
        String sqlFileName = String.format("%s.sql", baseName);
        String sqlFilePath = String.format("%s/%s", tempDir, sqlFileName);
        String gZipFilePath = String.format("%s/%s.sql.gz", tempDir, baseName);
        String zipFileNPath = String.format("%s/%s.zip", tempDir, baseName);
        String mySqlDumpCmd = String.format("mysqldump -h%s -u%s -p%s %s", hostname, user, password, database);

        try (FileWriter fw = new FileWriter(new File(sqlFilePath));
             GZIPOutputStream gZipOutputStream = new GZIPOutputStream(new FileOutputStream(gZipFilePath));
             ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFileNPath))
        ) {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(mySqlDumpCmd);
            ZipEntry zipEntry = new ZipEntry(sqlFileName);
            zipOutputStream.putNextEntry(zipEntry);
            InputStream stream = process.getInputStream();
            InputStreamReader streamReader = new InputStreamReader(stream, StandardCharsets.UTF_8);
            char[] chars = new char[1];
            int readByte = 0;
            while ((readByte = streamReader.read(chars)) > 0) {
                fw.write(chars);
                gZipOutputStream.write(chars[0]);
                zipOutputStream.write(chars[0]);
            }
            zipOutputStream.closeEntry();
            zipOutputStream.finish();
            gZipOutputStream.finish();

        } catch (Exception e) {
            return ERROR;
        }
        return SUCCESS;
    }


}
