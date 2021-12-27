  <%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.campOrder.model.*"%>
<!DOCTYPE html>
<%@ page import="java.util.*"%>
<%@ page import="com.campOrder.model.*"%>


<%
	List<CampOrderVO> list = new ArrayList<CampOrderVO>();
	if(request.getAttribute("list")!=null){
		list = (ArrayList<CampOrderVO>)request.getAttribute("list");
	}
    pageContext.setAttribute("list",list);
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
		param = {
			data : {
				"action":"GETONECAMP",
				"campOrderId" : campOrderId
			},
			type: 'POST',
			title : "營地訂單",
			dataType: 'json',
            contentType: 'application/json',
			href : '<c:url value="'+ctx+'/back_end/camp/campOrder.do" />',
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
<link rel="stylesheet" href="<%=request.getContextPath()%>/back_end/css/campOrder.css">
</head>
<body>
<!-- --------head區域------- -->
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
	<h2>營地訂單查詢</h2>
	
<table>
	<div class="divSearchForm">
		<form class="searchForm"  method="post" ACTION="<%=request.getContextPath()%>/camp/campOrder.do"
			style="margin: auto; max-width: 300px">
			<div class="">
			<label>狀態</label>
				<select>
					<option value="-1">全部</option>
					<option value="1">已處理</option>
					<option value="2">處理中</option>
					<option value="3">已取消</option>
				</select>
			</div>
			<div>
				<label>訂單日期區間</label>
				<input type="date" id="startDate" name="startDate"> -
			    <input type="date" id="endDate" name="endDate">
			</div>
			<div>
				<label>付款人</label>
			    <input type="text" placeholder="請輸入關鍵字" name="payerName">
			</div>
			    <div class="list">		
				<label>訂單流水號</label>							
			    <input type="text" placeholder="請輸入關鍵字" name="campOrderId">		
             <div class="submit_button">			
			 <input type="hidden" name="action"	value="SEARCHALL">
			<button type="submit">
				<i class="fa fa-search"></i>
			</button>
			</div>
			<div style="clear: both;"></div>
			</div>
		</form>
	</table>	

    
	</div>
	<div class="pagination">
	<%@ include file="pages/page1.jsp" %> 
	</div>
	<table>
		<thead>
			<tr>
				<th>日期</th>
				<th>訂單流水號</th>
				<th>付款人</th>
				<th>連絡電話</th>
				<th>訂單總金額</th>
				<th>訂單狀態</th>
				<th>編輯</th>
			</tr>
		</thead>

      
		<tbody>
		<c:forEach var="campOrderVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
				
				<tr>
					<td><fmt:formatDate value="${campOrderVO.campOrderConfirmedTime}" pattern="yyyy-MM-dd" /></td>
					<td>${campOrderVO.campOrderId}</td>
					<td>${campOrderVO.payerName}</td>
					<td>${campOrderVO.payerPhone}</td>
					<td>${campOrderVO.campOrderTotalAmount}</td>
					<td>${campOrderVO.campOrderStatus}</td> 
					<td>
					     <input type="button" value="修改" name="${campOrderVO.campOrderId}" class="update"  />
					</td>
				</tr>
			</c:forEach>

		
		</tbody>
	</table>
		
	<div class="pagination">
		<%@ include file="pages/page2.jsp" %>
	</div>
</div>
	


	<footer class="tm-footer text-center">
		<pre>服務專線：(02)2252-7966　　 客服時間：週一至週五9:00~18:00　　 客服信箱：camp@easycamp.com.tw</pre>
		<pre>Copyright &copy; 2021 Camping Paradise | Design: <a
				style="text-decoration: none;" rel="nofollow" href="#">TFA104第五組</a>
				</pre>
	</footer>
</body>
</html>