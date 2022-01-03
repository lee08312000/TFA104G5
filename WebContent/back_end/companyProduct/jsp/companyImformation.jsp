<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.company.model.*"%>

<%	
	CompanyVO companyVO = (CompanyVO) session.getAttribute("companyVO");
%>

<html>
<head>
<title>商品資訊 </title>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/back_end/companyProduct/css/companyImformation.css" />

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
                <li><a href="#">登出</a></li>
                <li><a href="#"><i class="fas fa-user"></i></a></li>                
            </nav>
        </ul>    
                    
    </header>

    <aside class="aside">
        <div class="container">
            <nav>
                    <ul class="mcd-menu">
                        <li>
                            <a href="" class="light">
                                <i class="fa fa-campground"></i>
                                <strong>營地管理</strong>
                                <small>Camp Management</small>
                            </a>
                    <ul>
                                <li><a href="#"><i class="fas fa-cannabis"></i>我的營地</a></li>					
                                <li><a href="#"><i class="fas fa-cannabis"></i>營地上下架</a></li>
                                <li><a href="#"><i class="fas fa-cannabis"></i>審核狀況</a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="" class="light">
                                <i class="fa fa-edit"></i>
                                <strong>商品管理</strong>
                                <small>Commodity </small>
                            </a>
                        </li>
                        <li>
                            <a href="" class="light">
                                <i class="fa fa-gift"></i>
                                <strong>訂單管理</strong>
                                <small>Order </small>
                            </a>
                    <ul>
                                <li><a href="#"><i class="fas fa-cannabis"></i>日程表管理</a></li>					
                                <li><a href="#"><i class="fas fa-cannabis"></i>營地訂單管理</a></li>
                                <li><a href="<%=request.getContextPath()%>/back_end/companyProduct/html/productOrderList.html"><i class="fas fa-cannabis"></i>商城訂單管理</a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="" class="light">
                                <i class="fas fa-calendar-week"></i>
                                <strong>廠商資料</strong>
                                <small>Vendor Information</small>
                            </a>
                    <ul>
                                <li><a href="#"><i class="fas fa-cannabis"></i>基本資料瀏覽,修改</a></li>					
                                <li><a href="#"><i class="fas fa-cannabis"></i>更改密碼</a></li>				
                            </ul>
                        </li>
                        <li>
                            <a href="" class="light">
                                <i class="fa fa-comment-alt"></i>
                                <strong>我的評論</strong>
                                <small>Comment</small>
                            </a>
                            <ul>
                                <li><a href="#"><i class="fas fa-cannabis"></i>營地評價</a></li>					
                                <li><a href="#"><i class="fas fa-cannabis"></i>商品評價</a></li>				
                            </ul>
                        </li>							
                    </ul>
                </nav>
            </div>                 
    </aside>
    <main class="main">  
       <div class="vendor-information">
         <div class="main-information">
            <div class="title-1">廠商基本資訊</div>
            <div class="information">
                <div class="left-main">
                    <div class="informate">
                        <div class="wrap">
                            <div class="left">廠商名稱:</div>
                            <div class="right"><%=companyVO.getCompanyName()%></div>
                        </div>
                        <div class="wrap">
                            <div class="left">負責人姓名:</div>
                            <div class="right"><%=companyVO.getHeadName()%></div>
                        </div>
                        <div class="wrap">
                            <div class="left">廠商帳號:</div>
                            <div class="right"><%=companyVO.getCompanyAccount()%></div>
                        </div>
                        <div class="wrap">
                            <div class="left">廠商Email:</div>
                            <div class="right"><%=companyVO.getCompanyEmail()%></div>
                        </div>
                        
                    </div>
                </div>                    
                <div class="right-main">
                    <div class="informate">
                        <div class="wrap">
                            <div class="left">廠商電話:</div>
                            <div class="right"><%=companyVO.getCompanyTel()%></div>
                        </div>
                        <div class="wrap">
                            <div class="left">廠商銀行帳號:</div>
                            <div class="right"><%=companyVO.getCompanyBankAccount()%></div>
                        </div>
                        <div class="wrap">
                            <div class="left">廠商地址:</div>
                            <div class="right"><%=companyVO.getCompanyAddress()%></div>
                        </div>
                        <div class="wrap">
                            <div class="left">註冊時間:</div>
                            <div class="right"><fmt:formatDate value="${companyVO.companyRegisterTime}" pattern="yyyy-MM-dd HH:mm:ss"/></div>
                        </div>
                        <input class="update" type="button" value="修改基本資訊" onclick="location.href='<%=request.getContextPath()%>/back_end/companyProduct/jsp/companyUpdate.jsp'">
                    </div>
                </div>
            </div>
         </div>
       </div>
    </main>
    
    </body>
</html> 