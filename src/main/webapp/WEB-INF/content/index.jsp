<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html lang="en-US">
<head>
    <meta charset="UTF-8"/>
    <title>Hello</title>
    <sj:head jqueryui="true" jquerytheme="cupertino"/>
</head>
<body>
<sj:tabbedpanel id="localtabs">
  <sj:tab id="tab1" target="location" label="Location"/>
  <sj:tab id="tab2" target="ip" label="IP"/>
  <sj:tab id="tab3" target="browser" label="Web browser"/>
  <sj:tab id="tab4" target="bgp" label="BGP data"/>
  <sj:tab id="tab5" target="mysql" label="Mysql"/>
  <div id="location">
    <p>Hi! 👋 It looks like your location is currently: <s:property value="timeZone"/></p>
    <p>Where the local time is: <s:property value="time"/></p>
    <p>Your country: <s:property value="country"/></p>
    <p>Your region: <s:property value="regionName"/></p>
    <p>Your city: <s:property value="city"/></p>
  </div>
  <div id="ip">
    <p>Your public address is: <s:property value="clientIp"/></p>
    <p>Your ISP: <s:property value="isp"/></p>
    <p>Your ISP ASN: <s:property value="as"/></p>
  </div>
  <div id="browser">
      <p>Your web browser is: <s:property value="browser"/></p>
  </div>
  <div id="bgp">
        <p><pre><s:property value="bgpData"/></pre></p>
  </div>
  <div id="mysql">
    <div align="left">
        <h3>Backup your MySql database</h3>
        <s:form action="backup" method="post">
            <s:textfield name="hostname" label="Hostname" />
            <s:textfield name="user" label="User" />
            <s:password name="password" label="Password" />
            <s:textfield name="database" label="Database" />
            <s:hidden name="timestamp" value="%{timestamp}" />
            <s:submit value="Login" />
        </s:form>
    </div>
  </div>
</sj:tabbedpanel>
</body>
</html>