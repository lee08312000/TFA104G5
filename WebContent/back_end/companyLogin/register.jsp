<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.company.model.*"%>

<%
	CompanyVO companyVO = (CompanyVO) request.getAttribute("companyVO");
%>

<html>
<head>
<title></title>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/back_end/companyProduct/css/register.css" />
</head>

<body>
    <header class="header">       
        <div class="header-inner responsive-wrapper">
            <div class="header-logo">
                <a style="display:inline-block; vertical-align: middle;" href="首頁URL">
                    <img src="<%=request.getContextPath()%>/back_end/companyProduct/img/chuba_logo.png" />
                </a>
                <span style="display:inline-block; vertical-align: middle;">Camping Paradise</span>
            </div>
        </div>
        <ul>          
            <nav class="header-navigation">
                <li><a href="#">Home</a></li>
                <li><a href="#">線上商城</a></li>
                <li><a href="#"><img src="<%=request.getContextPath()%>/back_end/companyProduct/img/heart.png"></a></li>
                <li><a href="#">註冊</a></li>
                <li><a href="#">登入</a></li>
                <li><a href="#"><i class="fas fa-user"></i></a></li>                
            </nav>
        </ul>
     </header>
     <!-- =================  login區域   ===================== -->
     <div class="login-wrap">        
        <div class="login-html">
            <h1 class="title1">廠商專區</h1>            
            <input id="tab-1" type="radio" name="tab" class="sign-in" onclick="location.href='<%=request.getContextPath()%>/back_end/companyLogin/vendorLogin.jsp'"><label for="tab-1" class="tab">登入</label>
            <input id="tab-2" type="radio" name="tab" class="sign-up" onclick="location.href='<%=request.getContextPath()%>/back_end/companyLogin/register.jsp'" checked><label for="tab-2" class="tab">註冊</label>
            <div class="login-form">                
                <div class="sign-up-htm">
                <FORM METHOD="post" ACTION="/TFA104G5/Company/VendorLoginServlet" name="form1">
                    <div class="group">
                        <label for="user" class="label">負責人姓名</label>
                        <c:if test="${not empty errorMsgs}">								
							<span style="color:black;font-size:6px">${errorMsgs['errorHeadName']}</span>															
						</c:if>
                        <input id="user" type="text" class="input" name="headName" value="<%= (companyVO==null)? "" : companyVO.getHeadName()%>">
                    </div>
                    <div class="group">
                        <label for="user" class="label">廠商名稱</label>
                        <c:if test="${not empty errorMsgs}">								
							<span style="color:black;font-size:6px">${errorMsgs['errorCompanyName']}</span>															
						</c:if>
                        <input id="user" type="text" class="input" name="companyName" value="<%= (companyVO==null)? "" : companyVO.getCompanyName()%>">
                    </div>
                    <div class="group">
                        <label for="pass" class="label">廠商帳號</label>
                        <c:if test="${not empty errorMsgs}">								
							<span style="color:black;font-size:6px">${errorMsgs['errorAccount']}</span>															
						</c:if>
                        <input id="pass" type="text" class="input" name="account" value="<%= (companyVO==null)? "" : companyVO.getCompanyAccount()%>">
                    </div>
                    <div class="group">
                        <label for="pass" class="label">廠商密碼</label>
                        <c:if test="${not empty errorMsgs}">								
							<span style="color:black;font-size:6px">${errorMsgs['errorPassword']}</span>															
						</c:if>
                        <input id="pass" type="password" class="input" data-type="password" name="password" value="<%= (companyVO==null)? "" : companyVO.getCompanyPassword()%>">
                    </div>
                    <div class="group">
                        <label for="pass" class="label">再次輸入密碼</label>
                        <c:if test="${not empty errorMsgs}">								
							<span style="color:black;font-size:6px">${errorMsgs['errorRePassword']}</span>															
						</c:if>
                        <input id="pass" type="password" class="input" data-type="password" name="rePassword">
                    </div>
                    <div class="group">
                        <label for="user" class="label">Email 信箱</label>
                        <c:if test="${not empty errorMsgs}">								
							<span style="color:black;font-size:6px">${errorMsgs['errorEmail']}</span>															
						</c:if>
                        <input id="user" type="text" class="input" name="email" value="<%= (companyVO==null)? "" : companyVO.getCompanyEmail()%>">
                    </div>
                    <div class="group">
                        <label for="user" class="label">廠商電話</label>
                        <c:if test="${not empty errorMsgs}">								
							<span style="color:black;font-size:6px">${errorMsgs['errorpPhone']}</span>															
						</c:if>
                        <input id="user" type="text" class="input" name="phone" value="<%= (companyVO==null)? "" : companyVO.getCompanyTel()%>">
                    </div>
                    <div class="group">
                        <label for="user" class="label">廠商銀行帳號</label>
                        <c:if test="${not empty errorMsgs}">								
							<span style="color:black;font-size:6px">${errorMsgs['errorBankAccount']}</span>															
						</c:if>
                        <input id="user" type="text" class="input" name="bankAccount" value="<%= (companyVO==null)? "" : companyVO.getCompanyBankAccount()%>">
                    </div>
                    <div class="group">
                        <label for="user" class="label">廠商地址</label>
                        <c:if test="${not empty errorMsgs}">								
							<span style="color:black;font-size:6px">${errorMsgs['errorAddress']}</span>															
						</c:if>
                        <input id="user" type="text" class="input" name="address" value="<%= (companyVO==null)? "" : companyVO.getCompanyAddress()%>">
                    </div>
                    <div class="group">
                        <input type="submit" class="button" value="Sign Up">
                    </div>
                    <input type="hidden" name="action" value="register">
                    </FORM>
                    <div class="hr"></div>                    
                </div>
            </div>
        </div>
    </div>    
</body>
</html>