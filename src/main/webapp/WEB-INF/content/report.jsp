<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <title>We have processed your request</title>
    <style>
    .border-blue, .hover-border-blue:hover {
        border-color: #2196F3 !important;
    }
    .pale-blue, .hover-pale-blue:hover {
        color: #000 !important;
        background-color: #ddffff !important;
    }
    .leftbar {
        border-left: 6px solid #ccc !important;
    }
    .container, .panel {
        padding: 0.01em 16px;
    }
    </style>
</head>
<body>
<div class="container pale-blue leftbar border-blue">
<p><s:property value="browser"/></p>
</div>
<br/>
<p><s:property value="timestamp"/></p>
</body>
</html>