

<!DOCTYPE HTML>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<title>CMS-MERGE</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery-1.11.0.min.js"></script>
<!-- Custom Theme files -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<!--web-fonts-->
<style type="text/css">
.mail-form {
	background: #fff none repeat scroll 0 0;
	border: 5px solid #696f6f;
	border-radius: 2px;
	float: none;
	margin: auto;
	padding: 2em 3em 2em 2em;
	width: 80%;
	height: 800px;
}

newsletter-form {
	padding-top: 1.5em;
}

.mail-form input[type="text"] {
	background: #eee;
	border: 1px solid #696f6f;
	border-radius: 4px;
	font-family: "Raleway", sans-serif;
	font-size: 1em;
	margin: 10px;
	outline: medium none;
	padding: 0.8em 0.5em;
	width: 32%;
}

.mail-form input[type="password"] {
	padding: .8em .5em;
	width: 100%;
	font-size: 1em;
	outline: none;
	border: 1px solid #696F6F;
	border-radius: 4px;
	-webkit-border-radius: 4px;
	-moz-border-radius: 4px;
	background: url(../images/lock.png) no-repeat 347px 13px #eee;
	font-family: 'Raleway', sans-serif;
}

.mail-form input[type="submit"], .mail-form input[type="button"] {
	font-size: 1.3em;
	padding: .5em .3em;
	width: 35%;
	border: none;
	text-transform: capitalize;
	outline: none;
	border-radius: 4px;
	-webkit-border-radius: 4px;
	-moz-border-radius: 4px;
	background: #696F6F;
	color: #fff;
	cursor: pointer;
	margin: 0 auto;
	font-family: 'Raleway', sans-serif;
}

.heading {
	font-family: "Raleway", sans-serif;
	font-size: 25px;
	color: #06F;
	text-align: center;
}

.select {
	background: #eee none repeat scroll 0 0;
	border: 1px solid #696f6f;
	border-radius: 4px;
	color: #666;
	cursor: pointer;
	font-family: "Raleway", sans-serif;
	font-size: 12px;
	margin: 0 auto;
	outline: medium none;
	padding: 0.9em 0.3em;
	text-transform: capitalize;
	width: 32%;
}

.mail-form a p {
	font-size: .95em;
	text-transform: capitalize;
	color: #9C9EA0;
	margin-top: 1em;
}

.footer {
	text-align: center;
	padding: 3em 0;
}

.footer p {
	font-size: .9em;
	color: #fff;
	margin: 0px;
}

.footer p a:hover {
	color: #fff;
}

.footer p a {
	color: #fff;
}
/**responsive**/
@media ( max-width :1440px) {
	.mail-section {
		width: 44%;
	}
}

@media ( max-width : 1366px) {
	.mail-section {
		width: 100%;
	}
}

@media ( max-width : 1280px) {
	.mail-section {
		width: 50%;
	}
	.mail-form input[type="text"] {
		background: url(../images/user.png) no-repeat 344px 13px #eee;
	}
	.mail-form input[type="password"] {
		background: url(../images/lock.png) no-repeat 344px 13px #eee;
	}
}

@media ( max-width : 1080px) {
	.mail-section {
		width: 62%;
	}
	.header {
		margin-top: 5em;
	}
	.header h1 {
		font-size: 2.2em;
	}
	.mail-form input[type="submit"], .mail-form input[type="button"] {
		font-size: 1.2em;
	}
}

@media ( max-width : 991px) {
	.header {
		margin-top: 4em;
	}
	.mail-section {
		width: 82%;
		margin: 4em auto 0;
	}
	.mail-form input[type="text"], .mail-form input[type="password"] {
		font-size: .95em;
	}
}

@media ( max-width : 640px) {
	.mail-image {
		float: none;
		width: 100%;
		margin-right: 0;
		text-align: center;
	}
	.mail-form {
		float: none;
		width: 91%;
		margin-top: 1em;
	}
	.mail-form input[type="text"] {
		background: url(../images/user.png) no-repeat 455px 13px #eee;
	}
	.mail-form input[type="password"] {
		background: url(../images/lock.png) no-repeat 455px 13px #eee;
	}
	.mail-image h2 {
		font-size: 1.3em;
	}
	.mail-form a p {
		font-size: .9em;
	}
	.header {
		margin-top: 3em;
	}
}

@media ( max-width : 480px) {
	.mail-form {
		float: none;
		width: 85%;
		margin-top: 1em;
		padding: 2em 3em 2em 2em;
	}
	.header {
		margin-top: 2em;
	}
	.mail-section {
		width: 68%;
		margin: 2em auto 0;
		padding: 2em 3em 3em 1em;
	}
	.mail-image h3 {
		font-size: 1em;
	}
	.header h1 {
		font-size: 2em;
	}
	.mail-form input[type="submit"], .mail-form input[type="button"] {
		font-size: 1.05em;
		width: 44%;
	}
	.mail-form input[type="text"] {
		background: url(../images/user.png) no-repeat 260px 13px #eee;
	}
	.mail-form input[type="password"] {
		background: url(../images/lock.png) no-repeat 260px 13px #eee;
	}
}

@media ( max-width : 320px) {
	.mail-section {
		width: 78%;
		margin: 2em auto 0;
		padding: 2em 2em 2em 1em;
	}
	.mail-form input[type="text"], .mail-form input[type="password"] {
		font-size: .9em;
	}
	.header h1 {
		font-size: 1.8em;
	}
	.mail-form {
		float: none;
		width: 87%;
		margin-top: 1em;
		padding: 2em 2em 2em 1em;
	}
}

.main .mail-section .mail-form div ol li {
	font-family: Arial, Helvetica, sans-serif;
	font-size: 14px;
	margin-top: 10px;
}
</style>
<!--web-fonts-->

</head>
<body>
	<div class="header"></div>
	<div class="main">
		<div class="mail-section">

			<div class="mail-form">
				<div
					style="padding: 20px; background: #696F6F; color: #FFF; font-weight: bold; font-size: 20px; width: 98.6%;"
					align="center;">CMS Database and Berkeley Db Merge</div>
				<div
					style="padding: 10px; width: 100%; height: 300px; overflow: scroll; border: solid #06F; margin-bottom: 20px;">
					<h1 class="heading">Instructions</h1>
					<ul >
						<li>Step-1 Before starting CMS Merging,take all backup of school content like (Database, ROOT, and BerkeleyDB).</li>
						<li>Step-2 Change details of application.properties file(Read comment mention in properties file).</li>
						<li>Step-3 Restart server</li>
						<li>Step-4 Select Last CMS updated version from dropdown , then click on submit button and wait until all process completes.</li>
						<li>Step-5 After database gets merged, some excel file will get downloaded into given path in properties file.</li>
						<li>Step-6 Restart server</li>
						<li>Step-7 Start upload excel with given path.</li>
						<li>Step-8 After uploading 3-4 excel file,restart tomcat server.</li>
					

					</ul>
				</div>
				<table width="100%">
					<tr>
						<td style="width: 50%"><div style="width: 100%;">
								<form action="${pageContext.request.contextPath}/mergeData.htm"
									id="formSubmitId">

									<c:if test="${not empty cmsVersionLst }">
										<select name="endDate" id="dataId">
											<option value="">Select Last CMS Version</option>
											<c:forEach items="${cmsVersionLst}" var="cmsVerlst"
												varStatus="status">
												<c:if test="${cmsVerlst.id le 2 }">
													<option
														value=" <fmt:formatDate pattern="yyyy-MM-dd hh:mm:ss" 
            value="${cmsVerlst.endDate}" />">${cmsVerlst.versionName}</option>
												</c:if>
											</c:forEach>
										</select>
									</c:if>
									<br /> <br /> <input type="button" value="Submit" id="formId">
									<!-- <a href="javascript:void(0)">submit</a> -->
								</form>
							</div></td>
						<td style="width: 50%"><div
								style="float: right; text-align: right;">


								<form method="POST"
									action="${pageContext.request.contextPath}/uploadModule.htm"
									enctype="multipart/form-data" id="excelFormId">
									File to upload: <input type="file" name="file" id="fileId"> <br />

									<input type="button" value="Upload" id="uploadExcelId">
								</form>


							</div></td>
					</tr>
				</table>



			</div>
			<%-- 					<table width="100%">
  <tr>
    <td style="width:50%"><div style=" width: 100%;">
       <form method="POST" action="${pageContext.request.contextPath}/uploadModule.htm" enctype="multipart/form-data" id="excelFormId">
		File to upload: <input type="file" name="file">
 
		Name: <input type="text" name="name">
 
 
		<input type="button" value="Upload" id="uploadExcelId"> Press here to upload the file!
	</form>	
                        </div></td>
    <td style="width:50%"><div style="float: right; text-align: right;">
                    <!--  <form>
                    <input name="" type="file" style="margin-left:20px">
					<input type="button" value="submit" style="margin-top:20px; margin-right: 92px;">
                    </form> -->
						</div></td>
  </tr>
</table>
 --%>


		</div>
		<div class="clear"></div>
	</div>

	<style>
#fullPageloader {
	display: none;
	position: fixed !important;
	top: 0 !important;
	left: 0 !important;
	width: 100% !important;
	height: 100% !important;
	text-align: center !important;
	border-collapse: collapse !important;
	border-spacing: 0 !important;
	box-sizing: content-box !important;
	line-height: normal !important;
	z-index: 999999 !important;
	background: -moz-radial-gradient(center, ellipse cover, rgba(255, 255, 255, 0.9)
		0%, rgba(247, 247, 247, 0.85) 49%, rgba(193, 189, 191, 0.8) 100%);
	/* FF3.6+ */
	background: -webkit-gradient(radial, center center, 0px, center center, 100%,
		color-stop(0%, rgba(255, 255, 255, 0.9)),
		color-stop(49%, rgba(247, 247, 247, 0.85)),
		color-stop(100%, rgba(193, 189, 191, 0.8))); /* Chrome,Safari4+ */
	background: -webkit-radial-gradient(center, ellipse cover, rgba(255, 255, 255, 0.9)
		0%, rgba(247, 247, 247, 0.85) 49%, rgba(193, 189, 191, 0.8) 100%);
	/* Chrome10+,Safari5.1+ */
	background: -o-radial-gradient(center, ellipse cover, rgba(255, 255, 255, 0.9)
		0%, rgba(247, 247, 247, 0.85) 49%, rgba(193, 189, 191, 0.8) 100%);
	/* Opera 12+ */
	background: -ms-radial-gradient(center, ellipse cover, rgba(255, 255, 255, 0.9)
		0%, rgba(247, 247, 247, 0.85) 49%, rgba(193, 189, 191, 0.8) 100%);
	/* IE10+ */
	background: radial-gradient(ellipse at center, rgba(255, 255, 255, 0.9)
		0%, rgba(247, 247, 247, 0.85) 49%, rgba(193, 189, 191, 0.8) 100%);
	/* W3C */
	filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#e6ffffff',
		endColorstr='#ccc1bdbf', GradientType=1);
	/* IE6-9 fallback on horizontal gradient */
}

#fullPageloader table, #fullPageloader th, #fullPageloader td {
	font-family: 'Times New Roman', Times, serif !important;
	padding: 0 !important;
	margin: 0px auto !important;
	font-size: 14px !important;
}
</style>
	<table id="fullPageloader">
		<tr>
			<td>
				<%-- <img src="${pageContext.request.contextPath}/static/images/fullPageLoader.gif" /> --%>
				<img
				src="data:image/gif;base64,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" />
			</td>
		</tr>
	</table>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#formId").on("click", function() {
				
				var check=$("#dataId").val();
				console.log("hsdkfjkasldf"+check);
				if(check=='')
					{
					alert("Please select cms version....");
						return false;
					}
				
				
				var r = confirm("Are you sure to merge CMS !!!");
				if (r == true) {
					$("#formSubmitId").submit();
					//$("#fullPageloader").show();
				}
			});

			$("#uploadExcelId").on("click", function() {
				var check=$("#fileId").val();
				if(check=='')
					{
					alert("Please select file....");
						return false;
					}
				$("#excelFormId").submit();
				$("#fullPageloader").show();
			})
		});
	</script>
</body>
</html>
