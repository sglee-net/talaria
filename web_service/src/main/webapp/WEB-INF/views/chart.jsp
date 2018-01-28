<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 --%>
 <!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="<spring:url value="/resources/js/dygraph.js" />"></script>
<link rel="stylesheet" href="<spring:url value="/resources/css/dygraph.css" />" />
</head>
<body>
<div id="graphdiv"></div>
<script type="text/javascript">
  g = new Dygraph(
        document.getElementById("graphdiv"),  // containing div
        "Date,Temperature\n" +                // the data series
        "2008-05-07,75\n" +
        "2008-05-08,70\n" +
        "2008-05-09,80\n",
        { }                                   // the options
      );
</script> 
</body>
</html>