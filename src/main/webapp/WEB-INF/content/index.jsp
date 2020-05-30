<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html lang="en-US">
<head>
    <meta charset="UTF-8"/>
    <title>Hello</title>
    <sj:head jqueryui="true" jquerytheme="cupertino"/>
    <script src="uStates.js"></script>
    <script src="http://d3js.org/d3.v3.min.js"></script>
</head>
<body>
<sj:tabbedpanel id="localtabs">
  <sj:tab id="tab1" target="location" label="Location"/>
  <sj:tab id="tab2" target="ip" label="IP"/>
  <sj:tab id="tab3" target="browser" label="Web browser"/>
  <sj:tab id="tab4" target="bgp" label="BGP data"/>
  <sj:tab id="tab5" target="mysql" label="Mysql"/>
  <sj:tab id="tab6" target="report" label="Report"/>
  <sj:tab id="tab7" target="map" label="Map"/>
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
            <s:textfield name="hostname" label="Hostname" required="true" />
            <s:textfield name="user" label="User" required="true" />
            <s:password name="password" label="Password" required="true" />
            <s:textfield name="database" label="Database" required="true" />
            <s:checkbox label="Skip column statistics (mysql 8 or later)" name="skipColStat" value="true" fieldValue="true" />
            <s:hidden name="timestamp" value="%{timestamp}" />
            <s:submit value="Backup" />
        </s:form>
    </div>
  </div>
  <div id="report">
      <h3>Download all information as a PDF</h3>
          <s:form action="pdf" method="post">
            <s:hidden name="timeZone" value="%{timeZone}" />
            <s:hidden name="time" value="%{time}" />
            <s:hidden name="clientIp" value="%{clientIp}" />
            <s:hidden name="country" value="%{country}" />
            <s:hidden name="regionName" value="%{regionName}" />
            <s:hidden name="city" value="%{city}" />
            <s:hidden name="isp" value="%{isp}" />
            <s:hidden name="as" value="%{as}" />
            <s:hidden name="browser" value="%{browser}" />
            <s:hidden name="bgpData" value="%{bgpData}" />
            <s:submit value="Download" />
          </s:form>
  </div>
    <div id="map">
          <div id="tooltip"></div>
          <svg width="960" height="600" id="statesvg"></svg>
          <script>

          	function tooltipHtml(n, d){	/* function to create html content string in tooltip div. */
          		return "<h4>"+n+"</h4><table>"+
          			"<tr><td>Low</td><td>"+(d.low)+"</td></tr>"+
          			"<tr><td>Average</td><td>"+(d.avg)+"</td></tr>"+
          			"<tr><td>High</td><td>"+(d.high)+"</td></tr>"+
          			"</table>";
          	}

          	var sampleData ={};	/* Sample random data. */
          	["HI", "AK", "FL", "SC", "GA", "AL", "NC", "TN", "RI", "CT", "MA",
          	"ME", "NH", "VT", "NY", "NJ", "PA", "DE", "MD", "WV", "KY", "OH",
          	"MI", "WY", "MT", "ID", "WA", "DC", "TX", "CA", "AZ", "NV", "UT",
          	"CO", "NM", "OR", "ND", "SD", "NE", "IA", "MS", "IN", "IL", "MN",
          	"WI", "MO", "AR", "OK", "KS", "LS", "VA"]
          		.forEach(function(d){
          			var low=Math.round(100*Math.random()),
          				mid=Math.round(100*Math.random()),
          				high=Math.round(100*Math.random());
          			sampleData[d]={low:d3.min([low,mid,high]), high:d3.max([low,mid,high]),
          					avg:Math.round((low+mid+high)/3), color:d3.interpolate("#ffffcc", "#800026")(low/100)};
          		});

          	/* draw states on id #statesvg */
          	uStates.draw("#statesvg", sampleData, tooltipHtml);

          	d3.select(self.frameElement).style("height", "600px");
          </script>
    </div>
</sj:tabbedpanel>
</body>
</html>