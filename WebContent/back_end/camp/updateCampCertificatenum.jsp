<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.campArea.model.*"%>
<%@ page import="com.camp.model.*"%>
<%@ page import="com.company.model.*"%>
<!DOCTYPE html>
<%@ page import="java.util.*"%>
<%

CampVO cv = new CampVO();
if (request.getAttribute("campVO") != null) {
	cv = (CampVO) request.getAttribute("campVO");
}
pageContext.setAttribute("campVO", cv);

%>



<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>CampCerticatenum</title>
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"
	integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/back_end/css/updateCampCertificatenum.css?v=002">
</head>
<body>


	
	<!-- --------main區域------- -->

	<h1>營地上架審核</h1>
	<h2>${errorMsgs}</h2>
	<form method="post"ACTION="<%=request.getContextPath()%>/camp/shelves.do">
		<table class="camp_table">
		
	
	
			<tr>
				<td><label  width="30%">廠商名稱:</label></td>				
				<td><input type="text" id="company_name" name="company_name" value="${campVO.companyName}"></td>
			</tr>

			<tr>
				<td><label>負責人姓名:</label></td>
				<td><input type="text" id="head_name" name="head_name" value="${campVO.headName}"></td>
			</tr>
			
			<tr>
				<td><label>廠商電話:</label></td>
				<td><input type="text" id="company_tel"name="company_tel"value="${campVO.companyTel}"></td>
			</tr>
			

			<tr>
				<td><label>營地名稱:</label></td>
				<td><input type="text" id="camp_name"name="camp_name"value="${campVO.companyTel}"></td>
			</tr>
			
			<tr>
				<td><label>廠商地址:</label></td>		
				<td><input type="text" id="company_address" name="company_address"value="${campVO.companyAddress}"></td>
			</tr>
			
		
		

			<tr>
				<td><label>認證字號:</label></td>
				<td><input type="text" id="certificate_num"name="certificate_num"value="${campVO.certificateNum}"></td>
			</tr>

			<tr>
				<td><label>證書圖片:</label></td>
				<td><div class="upload_certificate_pic">
						<input id="upload" type="file" accept="image/*"
							multiple="multiple">
					</div>
					<div class="img-box">
						<!-- 存放預覽圖片 -->
					</div></td>
			</tr>

			<tr>

				<td colspan="2"><input type="hidden" name="action"
					value="updateCertificate"/> <input type="submit" value="確認更新"
					style="margin-left: 250px;"> <input type="submit"
					value="取消"></td>
			</tr>

		</table>
	</form>


	<footer class="tm-footer text-center">
		<pre>服務專線：(02)2252-7966　　 客服時間：週一至週五9:00~18:00　　 客服信箱：camp@easycamp.com.tw</pre>
		<pre>Copyright &copy; 2021 Camping Paradise | Design: <a
				style="text-decoration: none;" rel="nofollow" href="#">TFA104第五組</a>
				</pre>
	</footer>
</body>
</html>

















