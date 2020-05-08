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
<p>Report</p>
</div>
    <p>Hi! It looks like your location is currently: <s:property value="timeZone"/></p>
    <p>Where the local time is: <s:property value="time"/></p>
    <p>Your country: <s:property value="country"/></p>
    <p>Your region: <s:property value="regionName"/></p>
    <p>Your city: <s:property value="city"/></p>
    <p>Your public address is: <s:property value="clientIp"/></p>
    <p>Your ISP: <s:property value="isp"/></p>
    <p>Your ISP ASN: <s:property value="as"/></p>
    <p>Your web browser is: <s:property value="browser"/></p>
    <p>BGP whois information:</p>
    <p><pre><s:property value="bgpData"/></pre></p>
<br/>
<p><s:property value="notice"/></p>
</body>
</html>