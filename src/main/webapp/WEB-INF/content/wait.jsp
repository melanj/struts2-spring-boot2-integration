<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>We are processing your request</title>
    <sj:head jqueryui="true" jquerytheme="cupertino"/>
    <meta http-equiv="refresh" content="5;url=<s:url includeParams="none"/>"/>
</head>

<body>
    <p style="border: 1px solid silver; padding: 5px; background: #ffd; text-align: center;">
        We are processing your request. Please wait.
    </p>

    <p/>
    You can click this link to <a href="<s:url includeParams="none"/>">refresh</a>.

</body>
</html>
