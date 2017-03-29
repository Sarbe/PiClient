<!DOCTYPE html>
<%@page import="java.net.InetAddress"%>
<html lang="en">

<head>

<title>Welcome to pivision</title>
<jsp:include page="headerInfo.jsp"></jsp:include>

<link href="./../css/i2.css" rel="stylesheet">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->



<style type="text/css">
#login_sec {
	margin-top: 150px;
	min-height: 350px;
}


</style>
<script type="text/javascript">
	$(document).ready(function() {
		if ("${MSG}" != "") {
			bootbox.alert("${MSG}");
		}
		
		
		
	});
</script>
</head>
<%
InetAddress IP=InetAddress.getLocalHost();
String ip = IP.getHostAddress();
%>
<body>
<%=ip %>
	<!-- Navigation -->
	<nav
		class="navbar navbar-default navbar-custom navbar-custom-dark bg-dark">
		<div class="container">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
					aria-expanded="false">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a href="/PiVisionWeb/user/accountDetails"
					style="margin-left: 20px; text-decoration: none; font-variant: small-caps; color: white;">
					<span class="flaticon-home"
					style="padding-right: 25px; font-size: 25px; color: #fed136;">Pi
						Client</span>
				</a>
			</div>


		</div>
		<!-- /.container-fluid -->
	</nav>

	<div class="container">
		<div class="col-md-12" id="login_sec">
			<div class="row">
				<div class="col-md-3"></div>
				<div class="col-md-6">
					<form class="form-horizontal" role="form" action="/PiClient/ClientServlet" method="post">
						<input type="hidden" name="method" value="login">
						<div class="form-group">

							<label for="inputEmail3" class="col-sm-2 control-label">
								Email </label>
							<div class="col-sm-5">
								<input type="text" class="form-control" name="userName"/>
							</div>
						</div>
						<div class="form-group">

							<label for="inputPassword3" class="col-sm-2 control-label">
								Password </label>
							<div class="col-sm-5">
								<input type="password" class="form-control" name="password" />
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">

								<button type="submit" class="btn btn-info">Sign in</button>
							</div>
						</div>
					</form>
				</div>
				<div class="col-md-3"></div>
			</div>
		</div>
		<hr>

		<!-- Footer -->
		<footer>
			<div class="row">
				<div class="col-lg-12">
					<p>Copyright &copy; <a class="_Gs" href="http://www.tinyad.in" target="_blank ">tinyAD</a></p>
				</div>
			</div>
		</footer>

	</div>

</body>

</html>
