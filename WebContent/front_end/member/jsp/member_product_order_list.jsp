<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>	
<%@ page import="java.util.*"%>
<%@ page import="com.member.model.*"%>
<%@ page import="com.mallOrder.model.*"%>
<%@ page import="com.company.model.*"%> 
<%@ page import="java.util.stream.Collectors"%>

<%
	MemberVO memberVO = (MemberVO) session.getAttribute("memberVO");
	
	MallOrderService mallOrderSvc = new MallOrderService();
	List<MallOrderVO> list = mallOrderSvc.getMallOrderByMember(memberVO.getMemberId());
	list = list.stream()
			   .sorted(Comparator.comparing(MallOrderVO::getMallOrderId).reversed())
			   .collect(Collectors.toList());
	pageContext.setAttribute("list",list);
%>
<jsp:useBean id="companySvc" scope="page" class="com.company.model.CompanyService" />


<!DOCTYPE html>
<html lang="zh-Hant">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"
	integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm"
	crossorigin="anonymous">
<link
	href="<%=request.getContextPath()%>/front_end/member/css/member_product_order_list.css"
	rel="stylesheet" type="text/css">
<title>商品訂單列表</title>
<!-- frontawesome把icon引入的東東 -->
<script src="https://kit.fontawesome.com/05a51b0b98.js"
	crossorigin="anonymous"></script>
</head>

<body>
	<%-- =================  header區域   ===================== --%>
	<header class="header">
		<div class="header-inner responsive-wrapper">
			<div class="header-logo">
				<a style="display: inline-block; vertical-align: middle;"
					href="<%=request.getContextPath()%>/front_end/camp/camp_index.html">
					<img
					src="<%=request.getContextPath()%>/front_end/mall/images/camp_paradise_logo.png" />
				</a> <span style="display: inline-block; vertical-align: middle;">Camping
					Paradise</span>
			</div>
		</div>
		<ul>
			<nav class="header-navigation">
				<li><a
					href="<%=request.getContextPath()%>/front_end/camp/camp_index.html">Home</a></li>
				<li><a
					href="<%=request.getContextPath()%>/front_end/mall/mall_index.html">線上商城</a></li>
				<li><a
					href="<%=request.getContextPath()%>/front_end/member/jsp/member_favorite_camp.jsp"><img
						src="<%=request.getContextPath()%>/front_end/mall/images/heart.png"></a></li>

				<%-- =================  登出鈕   ===================== --%>	
				<li>
				<form method="post" action="<%=request.getContextPath()%>/member/MemberServlet">
				<a>
				<input class="fas fa-sign-out-alt logout_button" type="submit" value="登出" />
				</a>
				<input type="hidden" value="logout" name="action" />
				</form>
				</li>
				<%-- =================  登出鈕   ===================== --%>	
				
				<li><a
					href="<%=request.getContextPath()%>/front_end/member/jsp/member_main.jsp"
					value=""><i class="fas fa-user"></i></a></li>
			</nav>
		</ul>

	</header>
	<%-- =================  header區域   ===================== --%>
	
	<%-- =================  sidebar   ===================== --%>
	<aside class="sidebar">
		<div id="leftside-navigation" class="nano">
			<ul class="nano-content">
				<li class="sub-menu"><a href="javascript:void(0);"><i
						class="fas fa-heart"></i><span>&nbsp;我的最愛</span><i
						class="arrow fa fa-angle-right pull-right"></i></a>
					<ul>
						<li><a
							href="<%=request.getContextPath()%>/front_end/member/jsp/member_favorite_camp.jsp">我的最愛營地</a>
						</li>
						<li><a
							href="<%=request.getContextPath()%>/front_end/member/jsp/member_favorite_product.jsp">我的最愛商品</a>
						</li>
					</ul></li>
				<li class="sub-menu"><a href="javascript:void(0);"><i
						class="far fa-list-alt"></i><span>&nbsp;我的訂單</span><i
						class="arrow fa fa-angle-right pull-right"></i></a>
					<ul>
						<li><a
							href="<%=request.getContextPath()%>/front_end/member/jsp/member_camp_order_list.jsp">營地訂單</a>
						</li>
						<li><a
							href="<%=request.getContextPath()%>/front_end/member/jsp/member_product_order_list.jsp">商品訂單</a>
						</li>
					</ul></li>
				<li class="sub-menu"><a href="javascript:void(0);"><i
						class="fa fa-table"></i><span>&nbsp;修改資料</span><i
						class="arrow fa fa-angle-right pull-right"></i></a>
					<ul>
						<li><a
							href="<%=request.getContextPath()%>/front_end/member/jsp/member_reset_info.jsp">修改會員資訊與密碼</a>
						</li>
					</ul>
				</li>
		</div>
	</aside>
	<%-- =================  sidebar   ===================== --%>
	
	<%-- =================  商品訂單列表   ===================== --%>
	<div class="table-title">
		<h3>商品訂單列表</h3>
	</div>
	<c:forEach var="mallOrderVO" items="${list}">
	<table class="table-fill">		
		<thead>
			<tr>
				<th>訂單編號: ${mallOrderVO.mallOrderId}</th>
				<th>訂單日期:<br> <fmt:formatDate value="${list[0].mallOrderConfirmedTime}" pattern="yyyy-MM-dd "/></th>
			</tr>
			<tr>
				<th class="text-left">廠商名稱</th>
				<th class="text-left">收件人姓名</th>
				<th class="text-left">收件人電話</th>
				<th class="text-left">收件人地址</th>
				<th class="text-left">總價</th>
				<th class="text-left">商品狀態</th>
				<th class="text-left">物流狀態</th>
			</tr>
		</thead>
		<tbody class="table-hover">			
			<tr>
				<td class="text-left">${companySvc.getOneCompany(mallOrderVO.companyId).companyName}</td>
				<td class="text-left">${mallOrderVO.receiverName}</td>
				<td class="text-left">${mallOrderVO.receiverPhone}</td>
				<td class="text-left">${mallOrderVO.receiverAddress}</td>
				<td class="text-left">${mallOrderVO.mailOrderTotalAmount}</td>
				<td class="text-center">${mallOrderVO.mallOrderStatus == 0 ? "處理中" : mallOrderVO.mallOrderStatus == 1 ? "已確認" : "已完成"}</td>
				<td class="text-center">${mallOrderVO.mallOrderDeliveryStatus == 0 ? "未發貨" : mallOrderVO.mallOrderDeliveryStatus == 1 ? "已發貨" : "已收貨"}</td>
			</tr>
			<tr>
				<td class="text-left" colspan="6">訂單總金額: ${mallOrderVO.mailOrderTotalAmount}</td>
				<td class="text-center">
					<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/Member/MemberProductServlet" style="margin-bottom: 0px;">
					<input type="hidden" name="mallOrderId"  value="${mallOrderVO.mallOrderId}">
					<input type="hidden" name="action"	value="getMallOrderDetail">
					<input class="button" type="submit" value="查看明細">					
					</FORM>
				</td>
			</tr>			 
		</tbody>
	</table>
	</c:forEach>
	<%-- =================  商品訂單列表   ===================== --%>
	
	<%-- =================  sidebar javascript   ===================== --%>
	<script
		src="<%=request.getContextPath()%>/front_end/member/vendor/jQuery/jquery-3.6.0.min.js"></script>
	<script>
		$("#leftside-navigation .sub-menu > a").click(
				function(e) {
					$("#leftside-navigation ul ul").slideUp(), $(this).next()
							.is(":visible")
							|| $(this).next().slideDown(), e.stopPropagation()
				})
	</script>
	<%-- =================  sidebar javascript   ===================== --%>
	
</body>