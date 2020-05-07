package com.amdexa.example.structs2.action;

import com.opensymphony.xwork2.ActionSupport;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Result;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.StringTokenizer;
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
    private boolean skipColStat;
    private String execLog;

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
        String skipColumnStatFlag = (skipColStat) ? "--column-statistics=0" : "";
        String mySqlDumpCmd = String.format("mysqldump %s -h%s -u%s -p%s %s", skipColumnStatFlag, hostname, user, password, database);

        File sqlFileRef = new File(sqlFilePath);
        File gzFileRef = new File(gZipFilePath);
        File zipFileRef = new File(zipFileNPath);
        try (FileWriter fw = new FileWriter(sqlFileRef);
             GZIPOutputStream gZipOutputStream = new GZIPOutputStream(new FileOutputStream(gzFileRef));
             ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFileRef))
        ) {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(mySqlDumpCmd);
            ZipEntry zipEntry = new ZipEntry(sqlFileName);
            zipOutputStream.putNextEntry(zipEntry);
            InputStream stream = process.getInputStream();
            InputStreamReader streamReader = new InputStreamReader(stream, StandardCharsets.UTF_8);
            BufferedReader errorStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            char[] chars = new char[1];
            int readByte = 0;
            boolean hasData = false;
            while ((readByte = streamReader.read(chars)) > 0) {
                fw.write(chars);
                gZipOutputStream.write(chars[0]);
                zipOutputStream.write(chars[0]);
                hasData = true;
            }

            StringBuilder stdError = new StringBuilder();
            boolean hasError = false;
            while(errorStream.ready()) {
                String errLine = errorStream.readLine();
                stdError.append(errLine).append("\n");
                hasError = !errLine.contains("[Warning]") || hasError;
            }
            setExecLog(addLinebreaks(stdError.toString(),80));
            if (hasError || !hasData) {
                sqlFileRef.delete();
                gzFileRef.delete();
                zipFileRef.delete();
                throw new Exception("mysqldump command failed!");
            }

            zipOutputStream.closeEntry();
            zipOutputStream.finish();
            gZipOutputStream.finish();
            /* this will helps avoid fill up /tmp to some extent*/
            sqlFileRef.deleteOnExit();
            gzFileRef.deleteOnExit();
            zipFileRef.deleteOnExit();

        } catch (Exception e) {
            return ERROR;
        }
        return SUCCESS;
    }

    private String addLinebreaks(String input, int maxLineLength) {
        StringTokenizer tok = new StringTokenizer(input, " ");
        StringBuilder output = new StringBuilder(input.length());
        int lineLen = 0;
        while (tok.hasMoreTokens()) {
            String word = tok.nextToken();

            if (lineLen + word.length() > maxLineLength) {
                output.append("\n");
                lineLen = 0;
            }
            output.append(word);
            lineLen += word.length();
        }
        return output.toString();
    }


}
