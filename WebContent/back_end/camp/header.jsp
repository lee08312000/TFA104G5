<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.company.model.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<%

Object companyVO = session.getAttribute("companyVO"); 
if(companyVO != null){
	pageContext.setAttribute("companyVO", (CompanyVO)companyVO);
}
%>
<title>campAreaShelves</title>
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"
	integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/back_end/css/selectCamp.css">
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
					<a href="#">Home</a> <a href="#"></a>
					<c:if test ="${companyVO!=null}">
			                <li>${companyVO.getCompanyAccount()} 你好</li>
			                <li>登出</li>              
       			 </c:if>
       				<c:if test ="${companyVO==null}">
					   <a href="#">註冊</a> <a href="#">登入</a> <a href="#"> <i class="fas fa-user"></i></a>
					</c:if> 
					<button>Menu</button>
				</nav>
			 
		</div>
	</header>
</body>
</html>