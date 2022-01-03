<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.company.model.*"%>

<html>
<head>
<title></title>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/back_end/companyProduct/css/vendorLogin.css" />
</head>

<body>
    <header class="header">       
        <div class="header-inner responsive-wrapper">
            <div class="header-logo">
                <a style="display:inline-block; vertical-align: middle;" href="/TFA104G5/front_end/camp/camp_index.html">
                    <img src="<%=request.getContextPath()%>/back_end/companyProduct/img/chuba_logo.png" />
                </a>
                <span style="display:inline-block; vertical-align: middle;">Camping Paradise</span>
            </div>
        </div>
        <ul>          
            <nav class="header-navigation">
                <li><a href="/TFA104G5/front_end/camp/camp_index.html">Home</a></li>
                <li><a href="/TFA104G5/front_end/mall/mall_index.html">線上商城</a></li>
                <li><a href="/TFA104G5/front_end/member/jsp/member_favorite_product.jsp"><img src="<%=request.getContextPath()%>/back_end/companyProduct/img/heart.png"></a></li>
                
                <li><a href="<%=request.getContextPath()%>/back_end/camp/campindex.jsp"><i class="fas fa-user"></i></a></li>                
            </nav>
        </ul>
     </header>
     <!-- =================  login區域   ===================== -->
     <div class="login-wrap">        
        <div class="login-html">
            <h1 class="title1">廠商專區</h1>            
            <input id="tab-1" type="radio" name="tab" class="sign-in" onclick="location.href='<%=request.getContextPath()%>/back_end/companyLogin/vendorLogin.jsp'" checked><label for="tab-1" class="tab">登入</label>
            <input id="tab-2" type="radio" name="tab" class="sign-up" onclick="location.href='<%=request.getContextPath()%>/back_end/companyLogin/register.jsp'"><label for="tab-2" class="tab">註冊</label>
            <div class="login-form">            
                <div class="sign-in-htm">                
                <FORM METHOD="post" ACTION="/TFA104G5/Company/VendorLoginServlet" name="form1">
                    <div class="group">
                        <label for="user" class="label">帳號</label>
                        <input id="user" type="text" class="input" name="account">
                    </div>
                    <div class="group">
                        <label for="pass" class="label">密碼</label>
                        <input id="pass" type="password" class="input" data-type="password" name="password">
                    </div>
                    <div class="group">
                        <input id="check" type="checkbox" class="check">
                        <label for="check"><span class="icon"></span>保持登入</label>
                    </div>
                    <div class="group">
                        <input type="submit" class="button" value="登入">
                    </div>
                    <input type="hidden" name="action" value="login">
                 </FORM>
                 <%-- 錯誤表列 --%>
				<c:if test="${not empty errorMsgs}">				
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li style="color:white; list-style-type:none">${message}</li>
						</c:forEach>
					</ul>
				</c:if>
                    <div class="hr"></div>
                    <div class="foot-lnk">
                        <a href="#forgot">Forgot Password?</a>
                    </div>
                </div>                
            </div>
        </div>
    </div>
    
</body>
</html>