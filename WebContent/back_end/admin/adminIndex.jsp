<%@page import="java.util.stream.Collector"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="java.util.stream.*"%>
<%@ page import="com.camp.model.CampVO"%>
<%@ page import="com.camp.model.CampService"%>
<%@ page import="com.productReport.model.ProductReportVO"%>
<%@ page import="com.productReport.model.ProductReportService"%>

<%
	CampService campSvc = new CampService();
	List<CampVO> campVOList = campSvc.getAllCamp(1);
	campVOList = campVOList.stream()
						   .filter(c -> c.getCampStatus().intValue() == 2)
						   .collect(Collectors.toList());
	pageContext.setAttribute("campVOListSize", campVOList.size());
	
	
	ProductReportService productReportSvc = new ProductReportService();
	List<ProductReportVO> productReportVOList = productReportSvc.getAll();
	productReportVOList = productReportVOList.stream()
											 .filter(p -> p.getReportStatus().intValue() == 0)
											 .collect(Collectors.toList());
	pageContext.setAttribute("productReportVOListSize", productReportVOList.size());
%>

<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <title>管理員首頁</title>
</head>
<style>
    *{
        box-sizing: border-box;
    }
    body{
        /* border: 1px solid red;  */
        margin: 0;
        font-family:"Oswald', sans-serif";
        background-color: rgb(252, 248, 248);
    }
    img{
        max-width: 100%;
    }
    html{
        /* font-size: 62.5%; */
        --header-height: 80px;
        --aside-width: 240px;
    }
   

    /* =================  header區域   =====================*/
    header.header{        
        width: 100%;
        height: var(--header-height);
        background-color: gray;
        /* position: fixed;
        top: 0;s
        left: 0; */
        position: sticky;
        top: 0;
        display: flex;
        justify-content: space-between;
        color: #FFFFFF; 
        font-weight: bold;         
    }
    .header-inner { 
            /* Make it stick */ 
            height: var(--header-inner-height); 
            position: sticky; 
            top: 1; 
            /* Other */ 
            display: flex; 
            align-items: center; 
            justify-content: space-between;
            padding-left: 60px; 
        } 
        /* Styling of other elements */ 
         
        .header-logo img { 
            display: block; 
            /* height: calc(var(--header-inner-height) - 30px); */ 
            /* height: 100px; */ 
            width: 80px; 
            /* text-align: center; */ 
        } 
         
        .header-logo { 
            display: block; 
            font-size: 32px; 
        }
        header ul{
            margin-right: 40px;
        }        
        .header-navigation li{
            font-size: 1.125rem;             
            margin-right: 1.75rem;            
            position: relative;
            text-decoration: none;
            vertical-align: middle;
            display: inline-block;
            margin-top: 5px;                                              
        }
        .header-navigation li > a{
            text-decoration: none;
            color: #FFFFFF; 
            font-weight: bold;
        }
        .header-navigation li:hover:after{ 
            transform: scalex(1); 
        }
        .header-navigation li:after{ 
            transition: 0.25s ease; 
            content: ""; 
            display: block; 
            width: 100%; 
            height: 2px; 
            background-color: currentcolor; 
            transform: scalex(0); 
            position: absolute; 
            bottom: -2px; 
            left: 0; 
        }         
    /* =================   aside區域   =====================*/
    aside.aside{            
        position: fixed;
        left: 0;
        top: var(--header-height);
        width: var(--aside-width);
        height: calc(100% - var(--header-height));
        overflow-x: visible;
        padding: 20px 0;

        transition: all 1s;
    }
    .clearfix:before,
    .clearfix:after {
        content: " ";
        display: table;
    }

    .clearfix:after {
        clear: both;
    }
    .clearfix {
        *zoom: 1;
    }

    .container {
        position: relative;
        margin: 0px auto;
        padding: 50px 0;
        clear: both;
    }

    .mcd-menu {
    list-style: none;
    padding-left: 30px;
    margin: 0;
    background: #FFF;
    /*height: 100px;*/
    border-radius: 2px;
    -moz-border-radius: 2px;
    -webkit-border-radius: 2px;    
    /* == */
    width: 200px;
    /* == */
    }
    .mcd-menu li {
    position: relative;
    list-style: none;
    /*float:left;*/
    }
    .mcd-menu li a {
    display: block;
    text-decoration: none;
    padding: 12px 20px;
    color: #777;
    /*text-align: center;
    border-right: 1px solid #E7E7E7;*/
    
    /* == */
    text-align: left;
    height: 55px;
    position: relative;
    border-bottom: 1px solid #EEE;
    /* == */
    }
    .mcd-menu li a i {
    /*display: block;
    font-size: 30px;
    margin-bottom: 10px;*/
    
    /* == */
    float: left;
    font-size: 20px;
    margin: 0 10px 0 0;
    /* == */
    
    }
    /* == */
    .mcd-menu li a p {
    float: left;
    margin: 0 ;
    }
    /* == */

    .mcd-menu li a strong {
    display: block;
    text-transform: uppercase;
    }
    .mcd-menu li a small {
    display: block;
    font-size: 10px;
    }

    .mcd-menu li a i, .mcd-menu li a strong, .mcd-menu li a small {
    position: relative;
    
    transition: all 300ms linear;
    -o-transition: all 300ms linear;
    -ms-transition: all 300ms linear;
    -moz-transition: all 300ms linear;
    -webkit-transition: all 300ms linear;
    }
    .mcd-menu li:hover > a i {
        opacity: 1;
        -webkit-animation: moveFromTop 300ms ease-in-out;
        -moz-animation: moveFromTop 300ms ease-in-out;
        -ms-animation: moveFromTop 300ms ease-in-out;
        -o-animation: moveFromTop 300ms ease-in-out;
        animation: moveFromTop 300ms ease-in-out;
    }
    .mcd-menu li:hover a strong {
        opacity: 1;
        -webkit-animation: moveFromLeft 300ms ease-in-out;
        -moz-animation: moveFromLeft 300ms ease-in-out;
        -ms-animation: moveFromLeft 300ms ease-in-out;
        -o-animation: moveFromLeft 300ms ease-in-out;
        animation: moveFromLeft 300ms ease-in-out;
    }
    .mcd-menu li:hover a small {
        opacity: 1;
        -webkit-animation: moveFromRight 300ms ease-in-out;
        -moz-animation: moveFromRight 300ms ease-in-out;
        -ms-animation: moveFromRight 300ms ease-in-out;
        -o-animation: moveFromRight 300ms ease-in-out;
        animation: moveFromRight 300ms ease-in-out;
    }

    .mcd-menu li:hover > a {
    color: #e67e22;
    }
    .mcd-menu li a.light {
    position: relative;
    color: #e67e22;
    border:0;
    /*border-top: 4px solid #e67e22;
    border-bottom: 4px solid #e67e22;
    margin-top: -4px;*/
    box-shadow: 0 0 5px #DDD;
    -moz-box-shadow: 0 0 5px #DDD;
    -webkit-box-shadow: 0 0 5px #DDD;
    
    /* == */
    border-left: 4px solid #e67e22;
    border-right: 4px solid #e67e22;
    margin: 0 -4px;
    /* == */
    }
    .mcd-menu li a.light:before {
    content: "";
    position: absolute;
    /*top: 0;
    left: 45%;
    border-top: 5px solid #e67e22;
    border-left: 5px solid transparent;
    border-right: 5px solid transparent;*/
    
    /* == */
    top: 42%;
    left: 0;
    border-left: 5px solid #e67e22;
    border-top: 5px solid transparent;
    border-bottom: 5px solid transparent;
    /* == */
    }

    /* == */
    .mcd-menu li a.light:after {
    content: "";
    position: absolute;
    top: 42%;
    right: 0;
    border-right: 5px solid #e67e22;
    border-top: 5px solid transparent;
    border-bottom: 5px solid transparent;
    }
    /* == */

    @-webkit-keyframes moveFromTop {
        from {
            opacity: 0;
            -webkit-transform: translateY(200%);
            -moz-transform: translateY(200%);
            -ms-transform: translateY(200%);
            -o-transform: translateY(200%);
            transform: translateY(200%);
        }
        to {
            opacity: 1;
            -webkit-transform: translateY(0%);
            -moz-transform: translateY(0%);
            -ms-transform: translateY(0%);
            -o-transform: translateY(0%);
            transform: translateY(0%);
        }
    }
    @-webkit-keyframes moveFromLeft {
        from {
            opacity: 0;
            -webkit-transform: translateX(200%);
            -moz-transform: translateX(200%);
            -ms-transform: translateX(200%);
            -o-transform: translateX(200%);
            transform: translateX(200%);
        }
        to {
            opacity: 1;
            -webkit-transform: translateX(0%);
            -moz-transform: translateX(0%);
            -ms-transform: translateX(0%);
            -o-transform: translateX(0%);
            transform: translateX(0%);
        }
    }
    @-webkit-keyframes moveFromRight {
        from {
            opacity: 0;
            -webkit-transform: translateX(-200%);
            -moz-transform: translateX(-200%);
            -ms-transform: translateX(-200%);
            -o-transform: translateX(-200%);
            transform: translateX(-200%);
        }
        to {
            opacity: 1;
            -webkit-transform: translateX(0%);
            -moz-transform: translateX(0%);
            -ms-transform: translateX(0%);
            -o-transform: translateX(0%);
            transform: translateX(0%);
        }
    }



    .mcd-menu li ul,
    .mcd-menu li ul li ul {
    position: absolute;
    height: auto;
    min-width: 200px;
    padding: 0;
    margin: 0;
    background: #FFF;
    /*border-top: 4px solid #e67e22;*/
    opacity: 0;
    visibility: hidden;
    transition: all 300ms linear;
    -o-transition: all 300ms linear;
    -ms-transition: all 300ms linear;
    -moz-transition: all 300ms linear;
    -webkit-transition: all 300ms linear;
    /*top: 130px;*/
    z-index: 1000;
    
    /* == */
    left:280px;
    top: 0px;
    border-left: 4px solid #e67e22;
    /* == */
    /* by Lee */
    margin-left: -21px;
    /* by Lee */
    }
    .mcd-menu li ul:before {
    content: "";
    position: absolute;
    /*top: -8px;
    left: 23%;
    border-bottom: 5px solid #e67e22;
    border-left: 5px solid transparent;
    border-right: 5px solid transparent;*/
    
    /* == */
    top: 25px;
    left: -9px;
    border-right: 5px solid #e67e22;
    border-bottom: 5px solid transparent;
    border-top: 5px solid transparent;
    /* == */
    }
    .mcd-menu li:hover > ul,
    .mcd-menu li ul li:hover > ul {
    display: block;
    opacity: 1;
    visibility: visible;
    /*top: 100px;*/
    
    /* == */
    left:200px;
    /* == */
    }
    /*.mcd-menu li ul li {
    float: none;
    }*/
    .mcd-menu li ul li a {
    padding: 10px;
    text-align: left;
    border: 0;
    border-bottom: 1px solid #EEE;
    
    /* == */
    height: auto;
    /* == */
    }
    .mcd-menu li ul li a i {
    font-size: 16px;
    display: inline-block;
    margin: 0 10px 0 0;
    }
    .mcd-menu li ul li ul {
    left: 230px;
    top: 0;
    border: 0;
    border-left: 4px solid #e67e22;
    }  
    .mcd-menu li ul li ul:before {
    content: "";
    position: absolute;
    top: 15px;
    /*left: -14px;*/
    /* == */
    left: -9px;
    /* == */
    border-right: 5px solid #e67e22;
    border-bottom: 5px solid transparent;
    border-top: 5px solid transparent;
    }
    .mcd-menu li ul li:hover > ul {
    top: 0px;
    left: 200px;
    }
    /*.mcd-menu li.float {
    float: right;
    }*/
    .mcd-menu li a.search {
    /*padding: 29px 20px 30px 10px;*/
    padding: 10px 10px 15px 10px;
    clear: both;
    }
    .mcd-menu li a.search i {
    margin: 0;
    display: inline-block;
    font-size: 18px;
    }
        /* =====================main區域========================*/
        main.main{
            /* border: 1px solid blue; */
            /* height: 1000px; */
            width: calc(100% - var(--aside-width));
            margin-left: var(--aside-width);            
            min-height: calc(100vh - var(--header-height));
            padding: 20px;
        }
        table#miyazaki { 
        margin: 30px 0;        
        font-family: Agenda-Light, sans-serif;
        font-weight: 50; 
        background: gray; color: #fff;
        text-rendering: optimizeLegibility;
        border-radius: 5px; 
        }
        
        table#miyazaki thead th { font-weight: 600; }
        table#miyazaki thead th, table#miyazaki tbody td { 
        padding: 3px; font-size: 16px;
        }
        table#miyazaki tbody td { 
        padding: 3px; font-size: 16px;
        color: #444; background: rgb(248, 246, 246);
        font-weight:500; 
        }
        table#miyazaki tbody tr { 
        border-top: 5px solid #ddd;
        border-bottom: 5px solid #ddd;
        border-left: 5px solid #ddd;
        border-right: 5px solid #ddd;        
        }
        /* 燈箱區域 */
        button{
        cursor: pointer;
        }
        div.overlay{
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100vh;
        background-color: hsla(0, 0%, 0%, .5);        
        display: none;
        }

        /* -------元素 article 置中及基本樣式-------- */
        div.overlay > article{
        background-color: white;
        color: rgb(68, 66, 66);
        width: 90%;
        max-width: 800px;
/*         height: 500px; */
        height: auto;
        border-radius: 5px;        
        padding: 10px;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        overflow:scroll;
        }
        
        div.article-group{
            padding-top:10px;
        }
        div.text-frame{
            border: 1px solid rgb(82, 79, 79); 
            width: 400px;
            min-height: 200px; 
            
        }
        div.time{
            padding-top:10px;
            text-align: right;
        }
        div.img{
            /* border: 1px solid rgb(82, 79, 79);  */
            width: 200px;
            height: 200px;
        }
        img {
        max-width: 100%;
        }

        
</style>
<body>

    <header class="header">       
        <div class="header-inner responsive-wrapper">
            <div class="header-logo">
                <a style="display:inline-block; vertical-align: middle;" href="<%=request.getContextPath()%>/back_end/admin/adminIndex.jsp">
                    <img src="<%=request.getContextPath()%>/back_end/admin/images/camp_paradise_logo.png" />
                </a>
                <span style="display:inline-block; vertical-align: middle;">Camping Paradise 平台管理員</span>
            </div>
        </div>
        <nav class="header-navigation">
        	<ul>          
                <li>${ adminVO.adminId }&nbsp;號管理員,你好!</li>
                <li><a href="<%=request.getContextPath()%>/admin/AdminServlet?action=logout">登出</a></li>              
        	</ul>    
        </nav>    
                    
    </header>

    <aside class="aside">
        <div class="container">
            <nav>
                    <ul class="mcd-menu">
                        <li>
                            <a href="" class="light">
                                <strong>管理員中心</strong>
                            </a>
                            <ul>
                            	<li><a href="<%=request.getContextPath()%>/back_end/admin/adminIndex.jsp"><i class="fas fa-cannabis"></i>管理員首頁</a></li>
                                <li><a href="<%=request.getContextPath()%>/back_end/admin/adminInfo.jsp"><i class="fas fa-cannabis"></i>管理員資訊</a></li>					
                                <li><a href="<%=request.getContextPath()%>/back_end/admin/updateAdmin.jsp"><i class="fas fa-cannabis"></i>基本資料修改</a></li>					
                            </ul>
                        </li>
                        <li>
                            <a href="" class="light">
                                <strong>管理員管理</strong>
                            </a>
                            <ul>
                                <li><a href="<%=request.getContextPath()%>/back_end/admin/adminManagement.jsp"><i class="fas fa-cannabis"></i>管理員查詢</a></li>					
                            </ul>
                        </li>
                        <li>
                            <a href="" class="light">
                                <strong>廠商管理</strong>
                            </a>
                            <ul>
                            	<li><a href="<%=request.getContextPath()%>/back_end/admin/companyManagement.jsp"><i class="fas fa-cannabis"></i>廠商查詢</a></li>
                                <li><a href="<%=request.getContextPath()%>/back_end/admin/campCheck.jsp"><i class="fas fa-cannabis"></i>營地上架審核</a></li>					
                                <li><a href="<%=request.getContextPath()%>/back_end/admin/productReport.jsp"><i class="fas fa-cannabis"></i>商品檢舉管理</a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="" class="light">
                                <strong>一般會員管理</strong>
                            </a>
                    		<ul>
                                <li><a href="<%=request.getContextPath()%>/back_end/admin/memberManagement.jsp"><i class="fas fa-cannabis"></i>會員查詢</a></li>					
                            </ul>
                        </li>                 							
                    </ul>
                </nav>
            </div>                 
    </aside>

    <main class="main">
    	<h2>歡迎&nbsp;${ adminVO.adminId }&nbsp;號管理員，您須處理的工作如下:</h2>
		<div style="text-align: center;">
    		<div style="display: inline-block; margin: 0 100px;">
    			<h2>待審核的營地</h2>
            	數量:&nbsp;${ campVOListSize }<br><br>
            	<a style="margin-left: 50px" href="<%=request.getContextPath()%>/back_end/admin/campCheck.jsp">前往審核營地</a>
            </div>
    		<div style="display: inline-block; margin: 0 100px;">
    			<h2>待處理的商品檢舉</h2>
            	數量:&nbsp;${ productReportVOListSize }<br><br>
            	<a style="margin-left: 50px" href="<%=request.getContextPath()%>/back_end/admin/productReport.jsp">前往處理檢舉</a>
            </div>
    	</div>
    </main>
       
</body>

</html>