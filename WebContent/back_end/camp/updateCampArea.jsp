
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.campArea.model.*"%>
<!DOCTYPE html>
<%@ page import="java.util.*"%>

<%
CampAreaVO cv = new CampAreaVO();
if (request.getAttribute("campAreaVO") != null) {
	cv = (CampAreaVO) request.getAttribute("campAreaVO");
}
pageContext.setAttribute("campAreaVO", cv);
%>

<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="<%=request.getContextPath()%>/back_end/camp/js/updateCampArea.js"></script>
<title>CampArea</title>
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"
	integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/back_end/css/campAreaU.css?v=002">
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
			                 <li><label for="logout">�n�X</label>
			                <form id="logoutForm" style="display: none;" method="post" action="<%=request.getContextPath()%>/Company/VendorLogoutServlet">
									<input type="hidden" name="action" value="logout">
									<input id="logout" type="submit" value="�n�X">
								</form></li>                 
       			 </c:if>
       				<c:if test ="${companyVO==null}">
					    <a href="<%=request.getContextPath()%>/back_end/companyLogin/register.jsp">���U</a>  <a href="<%=request.getContextPath()%>/back_end/companyLogin/vendorLogin.jsp">�n�J</a> <a href="#"> <i class="fas fa-user"></i></a>
					</c:if> 
					<button>Menu</button>
				</nav>
			 
		</div>
	</header>


	
	<!-- --------main�ϰ�------- -->

	<h1>���W�[ ${errorMsgs}</h1>
	<form method="post" ACTION="<%=request.getContextPath()%>/camp/campareashelves.do" enctype="multipart/form-data">
		<table class="camparea_shelves">
		
			<tr>
				<td>��a�y����:</td>
				<td><input type="hidden" id="campId" name="campId" value="${campAreaVO.campId}">${campAreaVO.campId}</td>
			</tr>

			<tr>
				<td><label>���W��:</label></td>
				<td><input type="text" id="campAreaName" name="campAreaName" value="${campAreaVO.campAreaName}"></td>
			</tr>

			<tr>
				<td><label>������:</label></td>
				<td><input type="text" id="weekdayPrice" name="weekdayPrice" value="${campAreaVO.weekdayPrice}"></td>
			</tr>



			<tr>
				<td><label>������:</label></td>
				<td><input type="text" id="holidayPrice"name="holidayPrice" value="${campAreaVO.holidayPrice}"></td>
			</tr>

			<tr>
				<td><label>�C�b�H�Y�[�ʤW��:</label></td>
				<td><input type="text" id="capitationmax"name="capitationMax"  value="${campAreaVO.capitationMax}"></td>
			</tr>
			
			<tr>
				<td><label>�[�ʤH�Y����:</label></td>
				<td><input type="text" id="perCapitationFee"name="perCapitationFee" value="${campAreaVO.perCapitationFee}"></td>
			</tr>

			<tr>
				<td><label>�b�ƤW��:</label></td>
				<td><input type="text" id="campAreaMax" name="campAreaMax" value="${campAreaVO.campAreaMax}"></td>
			</tr>

		
			<tr>
				<td><label for="fname">������1:</label></td>
					<td><input type="file" name="campArea_pic1" id="fileInput1" value="${campAreaVO.campAreaPic}"/> 
					<div id="fileDisplayArea1">
					<img id="smallPic1" style="width: 30%;" src='<%=request.getContextPath()%>/PicWithCampServlet?campid=${campAreaVO.campId}&&areaindex=1' />
					</div>
				</td>

			</tr>
			
			<tr>
				<td colspan="2"><input type="hidden" name="action" value="UPDATE" /> 
				<input type="hidden" id="campAreaId" name="campAreaId" value="${campAreaVO.campAreaId}">
				<input type="submit" value="�T�{�ק�" style="margin-left: 250px;">
				 <input type="button" onclick="location.href='<%=request.getContextPath()%>/camp/campareashelves.do?campId=${campAreaVO.campId}&action=SEARCHALL'" value="����" />
				 
				 </td>
			</tr>

		</table>
	</form>
	
	
	<!-- --------aside�ϰ�------- -->
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
							    <li><a  href="<%=request.getContextPath()%>/back_end/camp/campindex.jsp"  ><i class="fas fa-cannabis"></i>�ڪ���a</a></li>			
								<li><a  href="<%=request.getContextPath()%>/back_end/camp/insertCampShelves.jsp"  ><i class="fas fa-cannabis"></i>��a�W�U�[</a></li>
							    <li><a  href="<%=request.getContextPath()%>/back_end/camp/selectCampCertificatenum.jsp"  ><i class="fas fa-cannabis"></i>��a�f�֪��p</a></li>								
							</ul>
						</li>
							
							
							
						<li><a href="" class="light"> <i class="fa fa-edit"></i>
								<strong>�ӫ~�޲z</strong> <small>Commodity </small>
						</a></li>
						<li><a href="" class="light"> <i class="fa fa-gift"></i>
								<strong>�q��޲z</strong> <small>Order </small>
						</a>
							<ul>
							   <li><a  href="<%=request.getContextPath()%>/back_end/camp/campindex.jsp"  ><i class="fas fa-cannabis"></i>��{��޲z</a></li>			
							   <li><a  href="<%=request.getContextPath()%>/back_end/camp/listAllCampOrder.jsp"  ><i class="fas fa-cannabis"></i>��a�q��޲z</a></li>								   
							   <li><a  href="<%=request.getContextPath()%>/back_end/camp/productOrderList.html"  ><i class="fas fa-cannabis"></i>�ӫ��q��޲z</a></li>
							</ul></li>
						<li><a href="" class="light"> <i
								class="fas fa-calendar-week"></i> <strong>�t�Ӹ��</strong> <small>Vendor
									Information</small>
						</a>
							<ul>
							
							     <li><a  href="<%=request.getContextPath()%>/back_end/companyProduct/jsp/companyImformation.jsp"  ><i class="fas fa-cannabis"></i>�򥻸���s���έק�</a></li>
								<li><a href="#"><i class="fas fa-cannabis"></i>���K�X</a></li>
							</ul></li>
						<li><a href="" class="light"> <i
								class="fa fa-comment-alt"></i> <strong>�ڪ�����</strong> <small>Comment</small>
						</a>
							<ul>
							<li><a  href="<%=request.getContextPath()%>/back_end/camp/campComment.jsp"  ><i class="fas fa-cannabis"></i>��a����</a></li>
							<li><a  href="<%=request.getContextPath()%>/back_end/companyProduct/jsp/vendorProductComment.jsp"  ><i class="fas fa-cannabis"></i>�ӫ~����</a></li>
							</ul></li>
					</ul>
				</nav>
			</div>
		</aside>
	</div>
	
	

</body>
</html>

















