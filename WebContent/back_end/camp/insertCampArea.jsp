

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.campArea.model.*"%>
<!DOCTYPE html>
<%@ page import="java.util.*"%>



<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>CampArea</title>
<script src="<%=request.getContextPath()%>/back_end/camp/js/addCampArea.js"></script>

<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"
	integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/back_end/css/camps.css?v=003">
</head>
<body>



<!-- --------head區域------- -->
	<header class="header-outer">
		<div class="header-inner responsive-wrapper">
			<div class="header-logo">
				<a style="display: inline-block; vertical-align: middle;"
					href="首頁URL"> <img
					src="<%=request.getContextPath()%>/back_end/images/camp_paradise_logo.png" />
				</a> <span style="display: inline-block; vertical-align: middle;">Camping
					Paradise</span>
			</div>
		
				<nav class="header-navigation">
					<a href="#">Home</a> 
					<c:if test ="${companyVO!=null}">
			                <li>${companyVO.getCompanyAccount()} 你好</li>
			               <li><label for="logout">登出</label>
			                <form id="logoutForm" style="display: none;" method="post" action="<%=request.getContextPath()%>/Company/VendorLogoutServlet">
									<input type="hidden" name="action" value="logout">
									<input id="logout" type="submit" value="登出">
								</form></li>              
       			 </c:if>
       				<c:if test ="${companyVO==null}" >
					    <a href="<%=request.getContextPath()%>/back_end/companyLogin/register.jsp" target="_blank">註冊</a>  <a href="<%=request.getContextPath()%>/back_end/companyLogin/vendorLogin.jsp" target="_blank">登入</a> <a href="#"> <i class="fas fa-user"></i></a>
					</c:if> 
					<button>Menu</button>
				</nav>
			 
		</div>
	</header>


	

	<!-- --------main區域------- -->

	<h1>營位上架 ${errorMsgs}</h1>
	<form method="post" ACTION="<%=request.getContextPath()%>/camp/campareashelves.do" enctype="multipart/form-data">
		<table class="campareas" margin-left:200px;>
		
			<tr>
				<td>營地流水號:</td>
				<td><input type="hidden" id="campId" name="campId" value="${campId}">${campId}</td>
			</tr>

			<tr>
				<td><label>營位名稱:</label></td>
				<td><input type="text" id="campAreaName" name="campAreaName"></td>
			</tr>

			<tr>
				<td><label>平日單價:</label></td>
				<td><input type="text" id="weekdayPrice" name="weekdayPrice"></td>
			</tr>



			<tr>
				<td><label>假日單價:</label></td>
				<td><input type="text" id="holidayPrice"name="holidayPrice"></td>
			</tr>

			<tr>
				<td><label>每帳人頭加購上限:</label></td>
				<td><input type="text" id="capitationmax"name="capitationMax"></td>
			</tr>
			
			<tr>
				<td><label>加購人頭價格:</label></td>
				<td><input type="text" id="perCapitationFee"name="perCapitationFee"></td>
			</tr>

			<tr>
				<td><label>帳數上限:</label></td>
				<td><input type="text" id="campAreaMax" name="campAreaMax"></td>
			</tr>

			
			<tr>
				<td><label for="fname">營位美照1:</label></td>
					<td><input type="file" name="campArea_pic1" id="fileInput1" value=""/> 
					<div id="fileDisplayArea1">
					</div>
					</td>
			</tr>
			
			<tr>

				<td colspan="2"><input type="hidden" name="action" value="INSERT" /> 
				<input type="submit" value="確認新增"style="margin-left: 200px;"> <input type="submit" value="取消"></td>
			</tr>

		</table>
	</form>
	
<!-- --------aside區域------- -->
	<div id="sidebar">
		<aside class="aside">
			<div class="container">
				<nav>
					<ul class="mcd-menu">
						<li><a href="" class="light"> <i class="fa fa-campground"></i>
								<strong>營地管理</strong> <small>Camp Management</small>
						</a>
							<ul>
							    <li><a  href="<%=request.getContextPath()%>/back_end/camp/campindex.jsp"  ><i class="fas fa-cannabis"></i>我的營地</a></li>			
								<li><a  href="<%=request.getContextPath()%>/back_end/camp/insertCampShelves.jsp"  ><i class="fas fa-cannabis"></i>營地上下架</a></li>
							    <li><a  href="<%=request.getContextPath()%>/back_end/camp/selectCampCertificatenum.jsp"  ><i class="fas fa-cannabis"></i>營地審核狀況</a></li>								
							</ul>
						</li>
							
							
							
						<li><a href="" class="light"> <i class="fa fa-edit"></i>
								<strong>商品管理</strong> <small>Commodity </small>
						</a></li>
						<li><a href="" class="light"> <i class="fa fa-gift"></i>
								<strong>訂單管理</strong> <small>Order </small>
						</a>
							<ul>
							   <li><a  href="<%=request.getContextPath()%>/back_end/camp/campindex.jsp"  ><i class="fas fa-cannabis"></i>日程表管理</a></li>			
							   <li><a  href="<%=request.getContextPath()%>/back_end/camp/listAllCampOrder.jsp"  ><i class="fas fa-cannabis"></i>營地訂單管理</a></li>								   
							   <li><a  href="<%=request.getContextPath()%>/back_end/camp/productOrderList.html"  ><i class="fas fa-cannabis"></i>商城訂單管理</a></li>
							</ul></li>
						<li><a href="" class="light"> <i
								class="fas fa-calendar-week"></i> <strong>廠商資料</strong> <small>Vendor
									Information</small>
						</a>
							<ul>
							
							     <li><a  href="<%=request.getContextPath()%>/back_end/companyProduct/jsp/companyImformation.jsp"  ><i class="fas fa-cannabis"></i>基本資料瀏覽及修改</a></li>
								<li><a href="#"><i class="fas fa-cannabis"></i>更改密碼</a></li>
							</ul></li>
						<li><a href="" class="light"> <i
								class="fa fa-comment-alt"></i> <strong>我的評論</strong> <small>Comment</small>
						</a>
							<ul>
							<li><a  href="<%=request.getContextPath()%>/back_end/camp/campComment.jsp"  ><i class="fas fa-cannabis"></i>營地評價</a></li>
							<li><a  href="<%=request.getContextPath()%>/back_end/companyProduct/jsp/vendorProductComment.jsp"  ><i class="fas fa-cannabis"></i>商品評價</a></li>
							</ul></li>
					</ul>
				</nav>
			</div>
		</aside>
	</div>
</body>
</html>

















