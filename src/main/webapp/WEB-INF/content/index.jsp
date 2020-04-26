<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html lang="en-US">
<head>
    <meta charset="UTF-8"/>
    <title>Hello</title>
    <sj:head jqueryui="true" jquerytheme="cupertino"/></head>
<body>
<sj:tabbedpanel id="localtabs">
  <sj:tab id="tab1" target="location" label="Location"/>
  <sj:tab id="tab2" target="ip" label="IP"/>
  <sj:tab id="tab3" target="browser" label="Web browser"/>
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
</sj:tabbedpanel>
</body>
</html>