 <%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.camp.model.*"%>
<!DOCTYPE html>
<%@ page import="java.util.*"%>



<%
	List<CampVO> list = new ArrayList<CampVO>();
	
	Calendar startimeCalendar = Calendar.getInstance();
	startimeCalendar.add(Calendar.DATE, -90);
	
	Date startDate = startimeCalendar.getTime();
	if(request.getAttribute("startime") != null){
		startDate =  (Date)request.getAttribute("startime");
		pageContext.setAttribute("startime", startDate);
	} else {
		pageContext.setAttribute("startime", startimeCalendar.getTime());
	}
	
	
	Calendar endtimeCalendar = Calendar.getInstance();
	
	Date endDate = endtimeCalendar.getTime();
	if(request.getAttribute("endtime") != null){
		endDate =  (Date)request.getAttribute("endtime");
		System.out.print(endDate);
		pageContext.setAttribute("endtime", endDate);
	} else {
		pageContext.setAttribute("endtime", endtimeCalendar.getTime());
	}
	
	
	Integer campstatus = 3;
	if(request.getAttribute("campstatus")!= null){
		 campstatus =  (Integer)request.getAttribute("campstatus");
		 System.out.print(campstatus);
		pageContext.setAttribute("campstatus", campstatus);
	} 
 
	if (request.getAttribute("list") != null) {
		list = (ArrayList<CampVO>) request.getAttribute("list");
	} else {
		CampService campService = new CampService();
		list = campService.camplist(campstatus, startDate, endDate, "");
	}
	pageContext.setAttribute("list", list);
	
	
	

	
	
	
	
%>

<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>campAreaShelves</title>
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"
	integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm"
	crossorigin="anonymous">
<link rel="stylesheet" href="<%=request.getContextPath()%>/back_end/css/camp.css">

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

	<!-- --------main區域------- -->
	<h1 style="margin-right:10px">營地查詢列表 </h1>
    <h2>${errorMsgs}</h2>
  
	<form method="post" ACTION="<%=request.getContextPath()%>/camp/shelves.do"  >
		<div class="selector">
		<label>營地狀態</label> 
		<select name="campstatus">
			<option value="3" ${campstatus==3?"selected":""}>全部 </option>
			<option value="1" ${campstatus==1?"selected":""}>上架</option>
			<option value="0" ${campstatus==0?"selected":""}>下架</option>
		</select> 
		<label>日期區間</label>
		 <input type="date" id="startDate" name="startDate" value="<fmt:formatDate value='${startime}' pattern='yyyy-MM-dd'/>"/>
		  <input type="date" id="endDate"name="endDate" value="<fmt:formatDate value='${endtime}' pattern='yyyy-MM-dd'/>">
		   <input type="text" placeholder="請輸入營地名稱" name="campName" value="${campName}">
		    <input type="hidden" name="action" value="SEARCHALL">
		    <button type="submit" id="searchSubmit">
			<i class="fa fa-search"></i>
		   </button>
		   
		 
		   
		</div>
	</form>
	
	<form method="post" ACTION="<%=request.getContextPath()%>/camp/shelves.do">
					  <input type="hidden" name="action" value="INSERTCAMP">
					    <button  style="background-color:#4CAF50;font-size:16px;color:white;border:1px solid #4CAF50 ;margin-left:350px" type="submit" id="insertSubmit">新增營地</button>
				
					</form>
					



	<div class="pagination">
		<%@ include file="pages/page1.file" %>
	</div>
	<table class="camp_table">
	
		<thead>
		
			<tr>
				<th>上架日期</th>
                <th>營地<br>流水號</th>
                <th>營地名稱</th>
                <th>營地電話</th>
                <th>營地地址</th>
                <th>營地敘述</th>
                <th>營地美照</th>
                <th>營地狀態</th>
                <th>營位詳請</th>
                <th>編輯</th>
			</tr>
			
		</thead>


		<tbody>
			<c:forEach var="campVO" items="${list}" begin="<%=pageIndex%>"
				end="<%=pageIndex+rowsPerPage-1%>">

				<tr>
					<td><fmt:formatDate value="${campVO.campLaunchedTime}"
							pattern="yyyy-MM-dd" /></td>
					<td>${campVO.campId}</td>
					<td>${campVO.campName}</td>
					<td>${campVO.campPhone}</td>
					<td>${campVO.campAddress}</td>
					<td>${campVO.campDiscription}</td>
					<td><img id="smallPic1" style="width: 30%;" src='<%=request.getContextPath()%>/PicWithCampServlet?campid=${campVO.campId}&pic=1' /></td>					
					<td>${campVO.campStatus==1?"上架":"下架"}
					<c:if test="${campVO.campStatus==0}" >
					<form method="post" ACTION="<%=request.getContextPath()%>/camp/shelves.do" >
					<input type="hidden" name="action" value="certificatepage">
					<input type="hidden" name="campId" value="${campVO.campId}">
					<button class="cal" type="submit" >審核</button>					
					</form>
					</c:if></td>
					
					
					<td>
					<form method="post" ACTION="<%=request.getContextPath()%>/camp/campareashelves.do">
					  <input type="hidden" name="action"value="SEARCHALL">
					  <input type="hidden" name="campId" value="${campVO.campId}">
					<button style="background-color:#FFFF93 "type="submit" ><i class="fas fa-file-alt"></i> </button>
				
					</form>
					
			
					
					</td>						
					
					
					<td> 
					<form method="post" ACTION="<%=request.getContextPath()%>/camp/shelves.do" >				
					 <input type="hidden" name="action" value="UPDATEFINDBYKEY">
					 <input type="hidden" name="campId" value="${campVO.campId}">
					<button  style="background-color: #008CBA ;font-size:16px;color:white;border:1px solid #4CAF50" type="submit" id="insertSubmit"type="submit">修改</button>
						</form>	
				</td>
			
						
						
				</tr>
			</c:forEach>
			


		</tbody>

	</table>

	<div class="pagination">
		<%@ include file="pages/page2.file"%>
		
	</div>
	
	
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
								<li><a href="#"><i class="fas fa-cannabis"></i>我的營地</a></li>
								<li><a href="#"><i class="fas fa-cannabis"></i>營地上下架</a></li>
								<li><a href="#"><i class="fas fa-cannabis"></i>審核狀況</a></li>
							</ul></li>
						<li><a href="" class="light"> <i class="fa fa-edit"></i>
								<strong>商品管理</strong> <small>Commodity </small>
						</a></li>
						<li><a href="" class="light"> <i class="fa fa-gift"></i>
								<strong>訂單管理</strong> <small>Order </small>
						</a>
							<ul>
								<li><a href="#"><i class="fas fa-cannabis"></i>日程表管理</a></li>
								<li><a href="#"><i class="fas fa-cannabis"></i>營地訂單管理</a></li>
								<li><a href="#"><i class="fas fa-cannabis"></i>商城訂單管理</a></li>
							</ul></li>
						<li><a href="" class="light"> <i
								class="fas fa-calendar-week"></i> <strong>廠商資料</strong> <small>Vendor
									Information</small>
						</a>
							<ul>
								<li><a href="#"><i class="fas fa-cannabis"></i>基本資料瀏覽,修改</a></li>
								<li><a href="#"><i class="fas fa-cannabis"></i>更改密碼</a></li>
							</ul></li>
						<li><a href="" class="light"> <i
								class="fa fa-comment-alt"></i> <strong>我的評論</strong> <small>Comment</small>
						</a>
							<ul>
								<li><a href="#"><i class="fas fa-cannabis"></i>營地評價</a></li>
								<li><a href="#"><i class="fas fa-cannabis"></i>商品評價</a></li>
							</ul></li>
					</ul>
				</nav>
			</div>
		</aside>
	</div>
	
	
		
	
	 
</body>

</html>














