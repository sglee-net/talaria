<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 --%>
 <!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="<spring:url value="/resources/js/dygraph.js" />"></script>
<script type="text/javascript" src="<spring:url value="/resources/js/genArrayData.js" />"></script>
<link rel="stylesheet" href="<spring:url value="/resources/css/dygraph.css" />" />
</head>
<body>
<div id="graphdiv"></div>
<script type="text/javascript">
	var json = '[ {"ts" : 1517223851, "x":0, "y" : 100}, {"ts" : 1517223852, "x":1, "y" : 300}, {"ts" : 1517223853, "x":2, "y" : 150} ]';
	var data = genArrayData(json);
//	document.getElementById("graphdiv").innerHTML = data;
 	g = new Dygraph(
		        document.getElementById("graphdiv"),  // containing div
		        data,
		        {
	                labels: [ "ts", "X", "Y" ]
	            }                                  // the options
		      );

</script> 
</body>
</html>