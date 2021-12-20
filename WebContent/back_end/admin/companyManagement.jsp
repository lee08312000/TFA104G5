<%@page import="java.util.stream.Collector"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.company.model.CompanyVO"%>
<%@ page import="com.company.model.CompanyService"%>

<% 
	List<CompanyVO> list = new ArrayList<CompanyVO>();
	if (request.getAttribute("companyVOList") != null) {
		list = (List<CompanyVO>) request.getAttribute("companyVOList");
		pageContext.setAttribute("list", list);
	} else {
		CompanyService companySvc = new CompanyService();
		list = companySvc.getAllCompany();
		pageContext.setAttribute("list", list);
	}
	


%>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm" crossorigin="anonymous">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <title>廠商查詢</title>
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
        height: 500px;
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
    
    	<h2>廠商查詢</h2>
    	<form method="post" action="<%=request.getContextPath()%>/company/CompanyManagementServlet">
    		廠商編號：
    		<input type="number" name="companyId" value="1" min="1" style="width: 60px;">
    		<input type="hidden" name="action" value="searchByCompanyId">
    		<input type="submit" value="查詢">
    		<a style="margin-left: 20px;" href="<%=request.getContextPath()%>/back_end/admin/companyManagement.jsp">所有廠商</a>
    	</form>
    	<%-- 錯誤表列 --%>
		<c:if test="${not empty errorMsgs}">
			<font style="color:red">請修正以下錯誤:</font>
			<ul>
				<c:forEach var="message" items="${errorMsgs}">
					<li style="color:red">${message}</li>
				</c:forEach>
			</ul>
		</c:if>
    	
        <table id="miyazaki" style="margin: 0 auto">
            <thead>
            <tr><th>編號</th><th>名稱</th><th>帳號</th><th>帳號狀態</th><th>詳細資料</th><th>操作</th>
            <tbody>
            <%@ include file="page1.file" %> 
				<c:forEach var="companyVO" items="${ list }" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">

						<tr data-companyId="${ companyVO.companyId }" data-companyName="${ companyVO.companyName }" data-headName="${ companyVO.headName }" data-companyAccount="${ companyVO.companyAccount }" data-companyStatus='${ companyVO.companyStatus.intValue() == 0 ? "停用中" : companyVO.companyStatus.intValue() == 1 ? "啟用中" : "異常" }' data-companyBankAccount="${ companyVO.companyBankAccount }" data-companyEmail="${ companyVO.companyEmail }" data-companyAddress="${ companyVO.companyAddress }" data-companyTel="${ companyVO.companyTel }" data-companyRegisterTime="<fmt:formatDate value="${ companyVO.companyRegisterTime }" pattern="yyyy-MM-dd HH:mm:ss"/>">
							<td>${ companyVO.companyId }</td>
							<td>${ companyVO.companyName }</td>
							<td>${ companyVO.companyAccount }</td>
							<td>${ companyVO.companyStatus.intValue() == 0 ? "停用中" : companyVO.companyStatus.intValue() == 1 ? "啟用中" : "異常" }</td>
							<td><button type="button" class="btn_open">詳細資料</button></td>
							<td>
								<form method="post" action="<%=request.getContextPath()%>/company/CompanyManagementServlet" style="display:inline-block;">
									<input type="hidden" name="action" value='${ companyVO.companyStatus.intValue() == 0 ? "on" : companyVO.companyStatus.intValue() == 1 ? "off" : "異常" }'>
									<input type="hidden" name="companyId" value="${ companyVO.companyId }">
									<input type="submit" value='${ companyVO.companyStatus.intValue() == 0 ? "啟用" : companyVO.companyStatus.intValue() == 1 ? "停用" : "異常" }'>
								</form>
							</td>
						</tr>

				</c:forEach>
                              
        </table>
        <%@ include file="page2.file" %>        
    </main>
    <div class="overlay" style="border: 1px solid red;">
        <article>
            <div class="article-group">
                <label class="control-label">廠商編號:&nbsp;<span id="companyId" >s</span></label>                                  
            </div>
            <div class="article-group">
                <label class="control-label">廠商名稱:&nbsp;<span id="companyName" >s</span></label>                                  
            </div>
            <div class="article-group">
                <label class="control-label">負責人姓名:&nbsp;<span id="headName" >s</span></label>                                  
            </div>
            <div class="article-group">
                <label class="control-label">廠商帳號:&nbsp;<span id="companyAccount" >s</span></label>                                  
            </div>
            <div class="article-group">
                <label class="control-label">廠商帳號狀態:&nbsp;<span id="companyStatus" >s</span></label>                                  
            </div>
            <div class="article-group">
                <label class="control-label">廠商email:&nbsp;<span id="companyEmail" >s</span></label>                                  
            </div>
            <div class="article-group">
                <label class="control-label">廠商電話:&nbsp;<span id="companyTel" >s</span></label>                                  
            </div>
            <div class="article-group">
                <label class="control-label">廠商地址:&nbsp;<span id="companyAddress" >s</span></label>                                  
            </div>                 
            <div class="article-group">
                <label class="control-label">廠商銀行帳號:&nbsp;<span id="companyBankAccount" >s</span></label>                                  
            </div>                 
            <div class="article-group">
                <label class="control-label">廠商註冊時間:&nbsp;<span id="companyRegisterTime" >s</span></label>                                  
            </div>                 
            <br>            
          <button type="button" class="btn_modal_close">關閉</button>
        </article>
    </div>
       
    <script>
        $(function(){
  
        // 開啟 Modal 彈跳視窗
        $(document).on("click", "button.btn_open", function(){
            $("span#companyId").text($(this).closest("tr").attr("data-companyId"));
            $("span#companyName").text($(this).closest("tr").attr("data-companyName"));
            $("span#headName").text($(this).closest("tr").attr("data-headName"));
            $("span#companyAccount").text($(this).closest("tr").attr("data-companyAccount"));
            $("span#companyStatus").text($(this).closest("tr").attr("data-companyStatus"));
            $("span#companyEmail").text($(this).closest("tr").attr("data-companyEmail"));
            $("span#companyTel").text($(this).closest("tr").attr("data-companyTel"));
            $("span#companyAddress").text($(this).closest("tr").attr("data-companyAddress"));
            $("span#companyBankAccount").text($(this).closest("tr").attr("data-companyBankAccount"));
            $("span#companyRegisterTime").text($(this).closest("tr").attr("data-companyRegisterTime"));
            
            $("div.overlay").fadeIn();
        });
        
        // 關閉 Modal
        $("button.btn_modal_close").on("click", function(){
            $("div.overlay").fadeOut();
        });
        
        });
    </script>
</body>

</html>