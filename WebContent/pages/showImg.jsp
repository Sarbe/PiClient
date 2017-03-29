<!DOCTYPE html>
<%@page import="java.net.InetAddress"%>
<%@page import="com.pi.util.PiStatic"%>
<%@page import="com.pi.util.Config"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Show Advertisement</title>
<style type="text/css">
.mediaClass {
	height: 100%;
	width: 100%;
	position: absolute;
	margin: auto;
	left: 0;
	right: 0;
	top: 0;
	bottom: 0;
}
#watermark{
	position: absolute;
	z-index: 1000;
    width: 120px;
    height: 60px;
    padding: 5px 10px;
    bottom: 0;
    right: 0;
    opacity:0.4
}

#mediaInformation {
	position: absolute;
    z-index: 1000;
    border: 2px solid #4837ec;
    padding: 5px 10px;
    background: #f9fd44;
    bottom: 0;
    margin: 0 auto;
    left: 0;
    height: 10px;
}

html,body {
	width: 100%;
	height: 100%;
	overflow: hidden;
}

#video_header {
	display: block;
	position: absolute;
	min-height: 100%;
	max-width: 100%;
	width: auto;
	height: auto;
	background-repeat: no-repeat;
	background-size: cover;
	z-index: 0;
	overflow: hidden;
}
</style>
<script type="text/javascript" src="./../js/jquery.js"></script>
<%
InetAddress IP=InetAddress.getLocalHost();
String ip = IP.getHostAddress();
%>
<script type="text/javascript">

$(document).ready(function() {
	//$('#fs').fullScreen(true);	

	var counter = 5000;	
	var strtTm = 2000;
	
	var mediaObj = {
		fileNm:"",
		len:0,
		fileType:"",
		filePath:""
	};
	
	
	var mediaTag = "";
	
	
	function refreshImage() {
		$.ajax({
			type : "POST",
			url : "/PiClient/ScheduleServlet",
			data : {
				method : "refreshImg"
			},
			success : function(JSONdata) {

				mediaTag ='<img class="mediaClass" src="../images/tiny.png">' ;
				if(JSONdata != "" || JSONdata!="{}" ){
					data = jQuery.parseJSON(JSONdata);
					
					var mediaSrc = "http://"+"<%=ip+Config.getEnvKey("mediaServerURL")%>" + data.fileName;

				/* 	var vdoTag = '<iframe src="'+ mediaSrc +'" width="0" height="0" name="video" id="" class="mediaClass"></iframe>';
		            
		        	var imgTag = '<img class="mediaClass" src="'+ mediaSrc +'">';
		        	
		        	var mediaType = data.fileType;//$(this).attr("mediaType");
		        	//var mediaTag = "";
		        	if(mediaType.indexOf("video")==-1){
		        		mediaTag = imgTag;
		        	}else{
		        		mediaTag = vdoTag;
		        	} */
		        	// Update duration
		        	counter = data.duration*1000;
		        	
		        	mediaObj.fileNm = data.fileName;
		        	mediaObj.filePath = mediaSrc;
		        	mediaObj.len = counter;
		        	mediaObj.fileType = data.fileType;
				}				
				
			}
		});
	}
		

	var interimFunction = function(){
		console.log("Called after "+ counter);
	    clearInterval(interval);
	    refreshImage();
	    //counter += 1000;
	    interval = setInterval(interimFunction, counter);
	    
	    $("#vdobox").attr("src",mediaObj.filePath);
	    
	    //$("#mediaBox").html(mediaTag);
	    /* if(mediaObj.fileType.indexOf("video") == -1){ //image
	    	$("#vdobox").attr("src","");
    		$("#imgbox").attr("src",mediaObj.filePath);
    		
    	}else{ //vdo
    		$("#imgbox").attr("src","");
    		$("#vdobox").attr("src",mediaObj.filePath);
    	} */
	    
	    
	    <%if(Config.getKey("env").equals(PiStatic.env_LOCAL)){%>
	    	
	    <%}%>
	    $("#mediaInformation").text("fileName :: " +mediaObj.fileNm+" / Duration :"+ mediaObj.len);
	   // console.log("interval :: " + interval);
	};
	var interval = setInterval(interimFunction, strtTm);		
	
	/* $("#btn").click(function(){
			refreshImage();
		}); */

});


</script>
</head>
<body>
<!-- <button id="btn">Click</button> -->
	<div id="watermark"> <img  src="../images/tiny.png" width="100%" height="100%"> </div>
	<div id="mediaInformation">  </div>
	<!-- <iframe src="../images/tiny.png" width="0" height="0" name="video" id="vdobox" class="mediaClass" ></iframe> -->
	
	<div id="mediaBox">
		<iframe src="../images/tiny.png" width="0" height="0" name="video" id="vdobox" class="mediaClass" ></iframe>
	</div>
	<!-- <div id="mediaBox">
		<img class="mediaClass" src="../images/tiny.png" id="imgbox">
	</div>  -->
</body>
</html>