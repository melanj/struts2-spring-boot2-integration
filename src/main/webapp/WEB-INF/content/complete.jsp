<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>We have processed your request</title>
    <sj:head jqueryui="true" jquerytheme="cupertino"/>
</head>
<body>
    <h1>The process is complete</h1>
    <b>We have processed your request.</b>
    <p/>
    <s:url var="fileDownload" namespace="/" action="download/sql.gz"></s:url>
    <s:url var="fileDownloadzip" namespace="/" action="download/zip"></s:url>
    <s:url var="fileDownloadsql" namespace="/" action="download/sql"></s:url>
<h4>Download file - <s:a href="%{fileDownload}?filename=%{timestamp}">GZip format</s:a><br>
Download file - <s:a href="%{fileDownloadzip}?filename=%{timestamp}">Zip format</s:a><br>
Download file - <s:a href="%{fileDownloadsql}?filename=%{timestamp}">SQL format</s:a><br>
</h4>
</body>
</html>
