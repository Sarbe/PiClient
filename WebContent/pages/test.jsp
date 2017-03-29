<!DOCTYPE html>
<%@page import="java.util.Random"%>
<%@page import="java.net.InetAddress"%>
<%@page import="com.pi.util.Config"%>
<%@page import="com.pi.bean.SchedulePayLoad"%>
<%@page import="com.pi.util.PiStatic"%>
<html lang="en">

<head>
<title>Schedule Details</title>

<jsp:include page="headerInfo.jsp"></jsp:include>
<style type="text/css">
.page-header {
	margin-top: 80px;
}

.fa {
	cursor: pointer;
	font-size: 15px;
}

.lightBoxImg {
	width: 100%;
	height: 100%
}

.showModal {
	cursor: pointer;
}
</style>
<script type="text/javascript">
	<%Random r = new Random();%>
	$(document).ready(function() {
		var counter = 1;
		$("#btn").click(function(){
			
			$("#p").html("<div>Hello theere :: Test Number "+counter+"</div>");			
			counter++;
		});		
	});
</script>
<style type="text/css">
.formbox{
	border: 2px solid #eee;
	background: #eee;
}
</style>
</head>
<body>
<button id="btn">Click</button>
<div id="p" style="border:1px solid red;background:#eee;width:300px;height:100px">

</div>

</body>

</html>
