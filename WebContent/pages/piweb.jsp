<!DOCTYPE html>
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
	$(document).ready(function() {
						
		<%if(request.getAttribute("MSG")!=null){%>
			bootbox.alert('<%=request.getAttribute("MSG")%>');
			
		<%}%>	
		
		/* $(".showImg").click(function() {
			var curE = $(this);
			var sid = $(this).attr("schId");
			
			$.ajax({
				type : "POST",
				url : "/PiClient/ClientServlet",
				data : {
					method : "getImage",
					schdId : sid
				},
				success : function(
						JSONdata) {
					//alert("json : "+JSONdata);
					data = jQuery
							.parseJSON(JSONdata);
					var srcAtt = "data:image/"+data.mimeType 
							+ ";base64,"
							+ data.mediaByteArray;
					//alert(srcAtt);
					
					$("#imgEle_" + sid).append('<img alt="" id="showModal" width="50" height="25" src="'+srcAtt+'">');
					curE.remove();
				}
			});

		}); */

		$(".delSchd").click(function() {
			var scId = $(this).attr("id");
			alert(scId);
			$("#selectedSchedule").val(scId);
			$("#schDtlForm [name=method]").val("deleteSchedule");
			$("#schDtlForm").submit();
		});
		
		$("#oldSchedule").click(function() {
			$("#schDtlForm [name=method]").val("retrieveOldSchd");
			$("#schDtlForm").submit();
		});
		
			
		$("#dataTable").on("click",".showModal",function() {
			
			var mediaSrc = $(this).attr("mediaSrc");
			
			var vdoTag = '<video autoplay="" loop="" muted="" preload="auto" id="video__header" class="lightBoxImg">'+
            '<source src="'+ mediaSrc +'" type="video/mp4"></video>';
        	var imgTag = '<img class="lightBoxImg" src="'+ mediaSrc +'">';
        	
        	var mediaType = $(this).attr("mediaType");
        	var mediaTag = "";
        	if(mediaType.indexOf("video")==-1){
        		mediaTag = imgTag;
        	}else{
        		mediaTag = vdoTag;
        	}
			$("#lightBox .modal-body").html(mediaTag);
			
			$("#lightBox").modal({
				  keyboard: false
			});
		});	
			
		
		// Date picker
		$('#date_timepicker_start').datetimepicker({
			format:'Y-m-d',
			onShow:function( ct ){
				this.setOptions({
			    	maxDate: $('#date_timepicker_end').val()? $('#date_timepicker_end').val():false
		   		})
		  	},
		  	timepicker:false
		 });
		
		 $('#date_timepicker_end').datetimepicker({
			format: 'Y-m-d',
			 onShow:function( ct ){
			 	this.setOptions({
			    	minDate: $('#date_timepicker_start').val()? $('#date_timepicker_start').val():false
			   	})
			  },
		  timepicker:false
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
<%
InetAddress IP=InetAddress.getLocalHost();
String ip = IP.getHostAddress();
%>
<body>
	
	<!-- Navigation -->
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">PiClient<%=ip %></a>
			</div>
			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="/PiClient/ClientServlet?method=logout">Logout</a></li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container -->
	</nav>

	<!-- Page Content -->
	<div class="container">

		<!-- Display -->
		<div class="row"></div>
		<div class="row">

			<div class="col-md-5">
				<div class="col-lg-12">
					<h1 class="page-header">
						Create Schedule <small></small>
					</h1>
				</div>
				<form action="/PiClient/MediaUploadServlet" method="post" enctype="multipart/form-data">
					<input type="hidden" name="method" value="uploadImage">
					<div class="col-md-12 formbox">
						<div class="form-horizontal" role="form">
							<div class="form-group">
								<label for="" class="col-sm-4 control-label">
									MAC ID </label>
								<div class="col-sm-8">
									<div
										style="padding-top: 7px; font-weight: bold; font-size: medium; color: red;">
										<%=PiStatic.macAdd %></div>
								</div>
							</div>
							<div class="form-group">
								<label for="" class="col-sm-4 control-label">
									Select Image </label>
								<div class="col-sm-8">
									<input type="file" name="imageFile" class="form-control"
										required="required" value=""> <br>
								</div>
							</div>
							<div class="form-group">
								<label for="" class="col-sm-4 control-label">
									Start Date </label>
								<div class="col-sm-8">
									<input type="text" name="startDate" id="date_timepicker_start" autocomplete="off" 
										class="form-control hasDatepicker" required="required">
								</div>
							</div>

							<div class="form-group">

								<label for="" class="col-sm-4 control-label">
									End Date </label>
								<div class="col-sm-8">
									<input type="text" name="endDate" id="date_timepicker_end" autocomplete="off" 
										class="form-control hasDatepicker" required="required">
								</div>
							</div>
							<div class="form-group">
								<label for="" class="col-sm-4 control-label">
									Schedule Hours </label>
								<div class="col-sm-8">
									<select name="timeOfDay" class="form-control" required="required">
										<option value="MORNING">Morning Hours(9-12)</option>
										<option value="NOON">Noon Hours(12-15)</option>
										<option value="AFTERNOON">Afternoon Hours(15-18)</option>
										<option value="EVENING">Evening Hours(18-22)</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<label for="" class="col-sm-4 control-label">Duration </label>
								<div class="col-sm-8" style="display: inline-block;">
									<input type="number" name="duration" min="2" max="30" style="width: 100px;display: inline-block;"
										class="form-control" required="required">
										<span>Sec</span>
								</div>
							</div>
							<div class="form-group">
								<label for="" class="col-sm-4 control-label">Priority </label>
								<div class="col-sm-8">
									<input type="number" name="priority" min="1" max="5" style="width: 100px;"
										class="form-control" required="required">
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-12" style="text-align: center;">
									<button type="submit" class="btn btn-success">Save</button>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
			<div class="col-md-7">

				<div class="col-lg-12">
					<h1 class="page-header">
						List of Schedule <small></small>
					</h1>
				</div>
				<form action="/PiClient/ClientServlet" method="post" id="schDtlForm">
					<input type="hidden" name="method" value="deleteSchedule">
					<input type="hidden" name="selectedSchedule" id="selectedSchedule">
					<button type="button" class="btn btn-link" id="oldSchedule">Retrieve Old Content from server</button>
					<div class="col-lg-12" style="overflow-y: scroll;height: 400px;">
						<div class="col-md-12" >
							<table class="table" style="font-size: 12px;" id="dataTable">
								<thead>
									<tr>
										<th>#</th>
										<th>Schedule Id</th>
										<th>Start Date</th>
										<th>End Date</th>
										<th>Duration</th>
										<th>Type</th>
										<th>Media</th>
										<th>Action</th>
									</tr>
								</thead>
								<tbody>
									<%
										for (int i = 0; i < PiStatic.getMemory().size(); i++) {
											SchedulePayLoad s = PiStatic.getMemory().get(i);
									%>
									<tr>
										<td><%=(i + 1)%></td>
										<td><%=s.getScheduleId()%></td>
										<td><%=s.getStartDate()%></td>
										<td><%=s.getEndDate()%></td>
										<td><%=s.getDuration()%>&nbsp; Sec</td>
										<td><%=s.getMimeType()%></td>
										<td style="text-align: center;">
										<%
											String url = "http://" + ip +Config.getEnvKey("mediaServerURL") + s.getFileName();
										%>
										<span class="showModal" mediaSrc="<%=url %>" mediaType="<%=s.getFileType()%>">
											<%if(s.getFileType().contains("video")){ %>
												<i class="fa fa-video-camera" aria-hidden="true" style="font-size: 20px;color :#ff7800;"></i>
											<%} %>
											<%if(s.getFileType().contains("image")){ %>
												<!-- <span class="showModal"><i class="fa fa-picture-o" aria-hidden="true"></i></span> -->
												<img alt="" width="50" height="25" src="<%=url%>">
											<%} %>
										</span>
										</td>
										<td><span id="<%=s.getScheduleId()%>" class="delSchd" style="color: red;">
										<i class="fa fa-trash" aria-hidden="true"></i></span></td>
									</tr>
									<%
										}
									%>
								</tbody>
							</table>
						</div>
					</div>
				</form>


			</div>
		</div>



		<!-- Presentation -->

		<div class="row">
			<div class="col-md-8">
				<div class="col-md-12">
					<div class="form-horizontal" role="form"></div>

				</div>
				<div class="col-md-4"></div>
			</div>
		</div>
		
		<!-- Modal Section Start-->
			<div class="modal fade" id="lightBox" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-body" style="padding: 0"></div>
					</div>
				</div>
			</div>
		<!-- /.row -->

		<hr>

		<!-- Footer -->
		<footer>
			<div class="row">
				<div class="col-lg-12">
					<p>Copyright &copy; tinyAD</p>
				</div>
			</div>
			<!-- /.row -->
		</footer>

	</div>
	<!-- /.container -->
</body>

</html>
