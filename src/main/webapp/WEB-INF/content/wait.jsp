<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>We are processing your request</title>
    <sj:head jqueryui="true" jquerytheme="cupertino"/>
    <meta http-equiv="refresh" content="5;url=<s:url includeParams="none"/>"/>
    <style>
    #localtabs>ul {
        display: none;
    }
    </style>
</head>

<body>
<sj:tabbedpanel id="localtabs">
    <div id="tab1">
    <p style="border: 1px solid silver; padding: 5px; background: #ffd; text-align: center;">
        We are processing your request. Please wait.
    </p>
    <p/>
    You can click this link to <a href="<s:url includeParams="none"/>">refresh</a>.
  </div>
</sj:tabbedpanel>
</body>
</html>
