

 <%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.campOrder.model.*"%>
<!DOCTYPE html>
<%@ page import="java.util.*"%>

<%
	List<CampOrderVO> list = new ArrayList<CampOrderVO>();
	if(request.getAttribute("list")!=null){
		list = (ArrayList<CampOrderVO>)request.getAttribute("list");
	}
    pageContext.setAttribute("list",list);
    
    Calendar startimeCalendar = Calendar.getInstance();
	startimeCalendar.add(Calendar.DATE, -90);
	pageContext.setAttribute("startime", startimeCalendar.getTime());
	
	
	Calendar endtimeCalendar = Calendar.getInstance();
	
	pageContext.setAttribute("endtime", endtimeCalendar.getTime());
%>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="<%=request.getContextPath()%>/back_end/css/colorbox.css" />

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/back_end/js/jquery.colorbox.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	var ctx = "<%=request.getContextPath()%>";
	//到新增指令集Page
	$( ".update" ).click(function() {
		var campOrderId = $(this).attr("name");
		param = {
			data : {
				"action":"GETONECAMP",
				"campOrderId" : campOrderId
			},
			title : "營地訂單",
			href : '<c:url value="/camp/campOrder.do" />',
			innerHeight : 350,
			innerWidth : 650,
			opacity : 0.5,
			top : 100
		};
		$.colorbox(param);
	});
});
</script>

<title>order</title>
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"
	integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm"
	crossorigin="anonymous">
<link rel="stylesheet" href="<%=request.getContextPath()%>/back_end/css/campOrder.css?v=006">
</head>
<body>

	<header class="header-outer">
		<div class="header-inner responsive-wrapper">
			<div class="header-logo">
				<a style="display: inline-block; vertical-align: middle;"
					href="首頁URL"> <img src="<%=request.getContextPath()%>/back_end/images/camp_paradise_logo.png" />
				</a> <span style="display: inline-block; vertical-align: middle;">Camping
					Paradise</span>
			</div>
			<nav class="header-navigation">
				<a href="#">Home</a> <a href="#">線上商城</a> <a href="#"><img
					src="<%=request.getContextPath()%>/back_end/images/heart.png"></a> <a href="#">註冊</a> <a href="#">登入</a>
				<a href="#"> <i class="fas fa-user"></i></a>
				<button>Menu</button>
			</nav>
		</div>
	</header>

<div id="bodyCenter">
	<!-- --------main區域------- -->
	<h1>營地訂單查詢</h1>
<table>
	<div class="divSearchForm">
		<form class="searchForm"  method="post" ACTION="<%=request.getContextPath()%>/camp/campOrder.do"
			style="margin: auto; max-width: 300px">
			<div class="">
			<label>訂單狀態</label>
				<select>
					<option value="-1">全部</option>
					<option value="1">處理中</option>
					<option value="2">已確認</option>
				</select>
			</div>
			<div class="campDate">
				<label>訂單日期區間</label>
				<input type="date" id="startDate" name="startDate" value="<fmt:formatDate value='${startime}' pattern='yyyy-MM-dd'/>"/> -
			    <input type="date" id="endDate" name="endDate" value="<fmt:formatDate value='${endtime}' pattern='yyyy-MM-dd'/>">
			</div>
			
			<div class="list">							
			    <input type="text" placeholder="請輸入關鍵字" name="campOrderId">		
             <div class="submit_button">			
			 <input type="hidden" name="action"	value="SEARCHALL">
			<button type="submit">
				<i class="fa fa-search"></i>
			</button>
			</div>
			
		</form>
	</table>	

    
	</div>
	<div class="pagination">
	<%@ include file="pages/page1.jsp" %> 
	</div>
	<table id="listAllCampTable">
		<thead>
			<tr>
				<th>營地訂單<br>成立時間</th>
				<th>營地訂單<br>流水號</th>
				<th>營位名稱</th>
				<th>會員帳號</th>
				<th>預計入<br>住日期</th>
				<th>預計退<br>房日期</th>
				<th>訂帳數量</th>
				<th>營地訂單<br>總金額</th>		
				<th>付款人</th>
				<th>營地訂<br>單狀態</th>
				<th>編輯</th>
			</tr>
		</thead>

      
		<tbody>
		<c:forEach var="campOrderVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
				
				<tr>
				    <td><fmt:formatDate value="${campOrderVO.campOrderConfirmedTime}"
							pattern="yyyy-MM-dd" /></td>
					<td>${campOrderVO.campOrderId}</td>
			        <td>${campOrderVO.campName}</td>
			        <td>${campOrderVO.memberAccount}</td>		        
					<td>${campOrderVO.campCheckInDate}</td>
					<td>${campOrderVO.campCheckOutDate}</td>
					<td>${campOrderVO.bookingQuantity}帳</td>  
	                <td>$${campOrderVO.campOrderTotalAmount}</td>    	    
					<td>${campOrderVO.payerName}</td>
					<td>
					<c:choose>
					<c:when test="${campOrderVO.campOrderStatus eq 1}" >處理中</c:when>
					
					<c:when test="${campOrderVO.campOrderStatus eq 2}" >已確認</c:when>
					
					</c:choose>

				
					

					<td>
					       <input  type="button" value="修改" name="${campOrderVO.campOrderId}" class="update"  />
					      
					</td>
					
				</tr>
			</c:forEach>

		
		</tbody>
	</table>
		
	<div class="pagination">
		<%@ include file="pages/page2.jsp" %>
	</div>
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
								<li><a href="#"><i class="fas fa-cannabis"></i>基本資料瀏覽及修改</a></li>
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