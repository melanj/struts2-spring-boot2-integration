<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>Backup fail</title>
    <sj:head jqueryui="true" jquerytheme="cupertino"/>
    <style>
    #localtabs>ul {
        display: none;
    }
    </style>
</head>
<body>
<sj:tabbedpanel id="localtabs">
    <p style="border: 1px solid silver; padding: 5px; background: #ee1111; text-align: center;">
        We cannot processed your request. Backup fail.
    </p>
    <s:if test="%{#execLog != null && #execLog != ''}">
    <p>Error:<br/>
    <pre><s:property value="execLog"/>
    </p>
    </s:if>
</sj:tabbedpanel>
</body>
</html>
