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
	href="<%=request.getContextPath()%>/back_end/css/camp3.css?v=003">
	
	<script src="<%=request.getContextPath()%>/back_end/camp/js/updateCampCertificatenum.js"></script>
	
</head>
<body>
<!-- --------head�ϰ�------- -->
<header class="header-outer">
		<div class="header-inner responsive-wrapper">
			<div class="header-logo">
				<a style="display: inline-block; vertical-align: middle;"
					href="����URL"> <img
					src="<%=request.getContextPath()%>/back_end/images/camp_paradise_logo.png" />
				</a> <span style="display: inline-block; vertical-align: middle;">Camping
					Paradise</span>
			</div>			   
       	
				<nav class="header-navigation">
					<a href="#">Home</a> <a href="#"></a>
					<c:if test ="${companyVO!=null}">
			                <li>${companyVO.getCompanyAccount()} �A�n</li>
			                <li>�n�X</li>              
       			 </c:if>
       				<c:if test ="${companyVO==null}">
					   <a href="#">���U</a> <a href="#">�n�J</a> <a href="#"> <i class="fas fa-user"></i></a>
					</c:if> 
					<button>Menu</button>
				</nav>
			 
		</div>
	</header>



	
	<!-- --------main�ϰ�------- -->

	<h1 style="margin-right:100px">��a�W�[�f��</h1>
	<h2>${errorMsgs}</h2>
	<form method="post"ACTION="<%=request.getContextPath()%>/camp/shelves.do" enctype="multipart/form-data">
		<table class="camp_table">

			<tr> 
			    
				<td><label  width="30%">�t�ӦW��:</label></td>				
				<td>
				<input type="text" id="company_name" name="company_name" value="${campVO.companyName}">
				</td>
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
					<td><label for="fname">�ҮѹϤ�:</label></td>
					<td><input type="file" name="camp_pic1" id="fileInput1" value=""/> 
					<div id="fileDisplayArea1">
					<img style="width: 30%;" src="<%=request.getContextPath()%>/PicWithCampServlet?campid=${campVO.campId}&certificate=t" />
					</div>
					</td>

			</tr>

			<tr>
				<td colspan="2">
				<input type="hidden" name="action"value="updateCertificate"/> 
				<input type="hidden" name="camp_id" value="${campVO.campId}"/> 
			
				<input type="submit" value="�T�{��s"style="margin-left: 250px;">
				 <input type="submit"value="����"></td>
			</tr>

		</table>
	</form>
		<!-- --------aside�ϰ�------- -->
	<div id="sidebar">
		<aside class="aside">
			<div class="container">
				<nav>
					<ul class="mcd-menu">
						<li><a href="" class="light"> <i class="fa fa-campground"></i>
								<strong>��a�޲z</strong> <small>Camp Management</small>
						</a>
							<ul>
								<li><a href="#"><i class="fas fa-cannabis"></i>�ڪ���a</a></li>
								<li><a href="#"><i class="fas fa-cannabis"></i>��a�W�U�[</a></li>
								<li><a href="#"><i class="fas fa-cannabis"></i>�f�֪��p</a></li>
							</ul></li>
						<li><a href="" class="light"> <i class="fa fa-edit"></i>
								<strong>�ӫ~�޲z</strong> <small>Commodity </small>
						</a></li>
						<li><a href="" class="light"> <i class="fa fa-gift"></i>
								<strong>�q��޲z</strong> <small>Order </small>
						</a>
							<ul>
								<li><a href="#"><i class="fas fa-cannabis"></i>��{��޲z</a></li>
								<li><a href="#"><i class="fas fa-cannabis"></i>��a�q��޲z</a></li>
								<li><a href="#"><i class="fas fa-cannabis"></i>�ӫ��q��޲z</a></li>
							</ul></li>
						<li><a href="" class="light"> <i
								class="fas fa-calendar-week"></i> <strong>�t�Ӹ��</strong> <small>Vendor
									Information</small>
						</a>
							<ul>
								<li><a href="#"><i class="fas fa-cannabis"></i>�򥻸���s��,�ק�</a></li>
								<li><a href="#"><i class="fas fa-cannabis"></i>���K�X</a></li>
							</ul></li>
						<li><a href="" class="light"> <i
								class="fa fa-comment-alt"></i> <strong>�ڪ�����</strong> <small>Comment</small>
						</a>
							<ul>
								<li><a href="#"><i class="fas fa-cannabis"></i>��a����</a></li>
								<li><a href="#"><i class="fas fa-cannabis"></i>�ӫ~����</a></li>
							</ul></li>
					</ul>
				</nav>
			</div>
		</aside>
	</div>



</body>
</html>

















