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


	
	<!-- --------main�ϰ�------- -->

	<h1>��a�W�[�f��</h1>
	<h2>${errorMsgs}</h2>
	<form method="post"ACTION="<%=request.getContextPath()%>/camp/shelves.do">
		<table class="camp_table">
		
	
	
			<tr>
				<td><label  width="30%">�t�ӦW��:</label></td>				
				<td><input type="text" id="company_name" name="company_name" value="${campVO.companyName}"></td>
			</tr>

			<tr>
				<td><label>�t�d�H�m�W:</label></td>
				<td><input type="text" id="head_name" name="head_name" value="${campVO.headName}"></td>
			</tr>
			
			<tr>
				<td><label>�t�ӹq��:</label></td>
				<td><input type="text" id="company_tel"name="company_tel"value="${campVO.companyTel}"></td>
			</tr>
			

			<tr>
				<td><label>��a�W��:</label></td>
				<td><input type="text" id="camp_name"name="camp_name"value="${campVO.companyTel}"></td>
			</tr>
			
			<tr>
				<td><label>�t�Ӧa�}:</label></td>		
				<td><input type="text" id="company_address" name="company_address"value="${campVO.companyAddress}"></td>
			</tr>
			
		
		

			<tr>
				<td><label>�{�Ҧr��:</label></td>
				<td><input type="text" id="certificate_num"name="certificate_num"value="${campVO.certificateNum}"></td>
			</tr>

			<tr>
				<td><label>�ҮѹϤ�:</label></td>
				<td><div class="upload_certificate_pic">
						<input id="upload" type="file" accept="image/*"
							multiple="multiple">
					</div>
					<div class="img-box">
						<!-- �s��w���Ϥ� -->
					</div></td>
			</tr>

			<tr>

				<td colspan="2"><input type="hidden" name="action"
					value="updateCertificate"/> <input type="submit" value="�T�{��s"
					style="margin-left: 250px;"> <input type="submit"
					value="����"></td>
			</tr>

		</table>
	</form>


	<footer class="tm-footer text-center">
		<pre>�A�ȱM�u�G(02)2252-7966�@�@ �ȪA�ɶ��G�g�@�ܶg��9:00~18:00�@�@ �ȪA�H�c�Gcamp@easycamp.com.tw</pre>
		<pre>Copyright &copy; 2021 Camping Paradise | Design: <a
				style="text-decoration: none;" rel="nofollow" href="#">TFA104�Ĥ���</a>
				</pre>
	</footer>
</body>
</html>

















