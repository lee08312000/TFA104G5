<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.company.model.*"%>
<%@ page import="com.product.model.*"%>
<%@ page import="com.member.model.*"%>
<%@ page import="com.productType.model.*"%>
<%@ page import="com.mallOrder.model.*"%>
<%@ page import="com.mallOrderDetail.model.*"%>

<%
	List<MallOrderDetailVO> mallOrderDetailList = (List<MallOrderDetailVO>) request.getAttribute("mallOrderDetailList");

	pageContext.setAttribute("mallOrderDetailList",mallOrderDetailList);
%>
<jsp:useBean id="memberSvc" scope="page" class="com.member.model.MemberService" />
<jsp:useBean id="mallOrderSvc" scope="page" class="com.mallOrder.model.MallOrderService" />

<html>
<head>
<title>廠商商品評論詳細資訊 - vendorProductComment.jsp</title>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/back_end/companyProduct/css/vendorProductCommentDetail.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

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
                                <li><a href="#"><i class="fas fa-cannabis"></i>商城訂單管理</a></li>
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
    	<c:forEach var="mallOrderDetailVO" items="${mallOrderDetailList}">
         <div class="commentArea ">
            <br>
            <div>會員名稱:${memberSvc.getOneMember(mallOrderSvc.getOneMallOrder(mallOrderDetailVO.mallOrderId).memberId).memberName}</div>
            <div>
                <div class="star_block">
                	<c:if test="${mallOrderDetailVO.productCommentStar >= 1}">
                    <span class="star" data-star="1">   
                        <i class="fas fa-star"></i></span>
                    </c:if>
                    <c:if test="${mallOrderDetailVO.productCommentStar >= 2}">    
                    <span class="star" data-star="2">
                        <i class="fas fa-star"></i>
                    </span>
                    </c:if>
                    <c:if test="${mallOrderDetailVO.productCommentStar >= 3}">
                    <span class="star" data-star="3">
                        <i class="fas fa-star"></i>
                    </span>
                    </c:if>
                    <c:if test="${mallOrderDetailVO.productCommentStar >= 4}">
                    <span class="star" data-star="4">
                        <i class="fas fa-star"></i>                    
                    </span>
                    </c:if>
                    <c:if test="${mallOrderDetailVO.productCommentStar >= 5}">
                    <span class="star" data-star="5">
                        <i class="fas fa-star"></i>
                    </span>
                    </c:if>
                </div>
                <div>商品評論:</div>
                <div class="commentText">${mallOrderDetailVO.productComment}</div>  
            </div>    
        </div>
        </c:forEach>
    </main> 

</body>
</html> 