<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>We have processed your request</title>
    <sj:head jqueryui="true" jquerytheme="cupertino"/>
    <style>
    #localtabs>ul {
        display: none;
    }
    </style>
</head>
<body>
<sj:tabbedpanel id="localtabs">
    <b>We have processed your request.</b>
    <p/>
    <s:url var="fileDownload" namespace="/" action="download/sql.gz"></s:url>
    <s:url var="fileDownloadzip" namespace="/" action="download/zip"></s:url>
    <s:url var="fileDownloadsql" namespace="/" action="download/sql"></s:url>
    Download file
    <s:a href="%{fileDownload}?filename=%{database}_%{timestamp}">gz</s:a>&nbsp;
    <s:a href="%{fileDownloadzip}?filename=%{database}_%{timestamp}">zip</s:a>&nbsp;
    <s:a href="%{fileDownloadsql}?filename=%{database}_%{timestamp}">sql</s:a><br>
    <s:if test="%{#execLog != null && #execLog != ''}">
    <p>warnings:<br/>
    <pre><s:property value="execLog"/>
    </p>
    </s:if>
</sj:tabbedpanel>
</body>
</html>
