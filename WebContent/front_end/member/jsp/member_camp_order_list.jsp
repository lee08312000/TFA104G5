<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <!-- jstl核心函式庫含for each標籤等 -->
<%@ page import="com.member.model.*"%>
<%@ page import="com.camp.model.*"%>
<%@ page import="com.favoriteCamp.model.*"%>
<%@ page import="com.campTag.model.*"%>
<%@ page import="com.campTagDetail.model.*"%>
<%@ page import="com.campOrder.model.*"%>
<%@ page import="java.util.*"%> <!-- list用 -->

<%

MemberVO memberVO =  (MemberVO)session.getAttribute("memberVO");
CampOrderService campOrderSvc =  new CampOrderService();
List<CampOrderVO> list = campOrderSvc.OrderByUserId(memberVO.getMemberId());
pageContext.setAttribute("list", list);
System.out.println(memberVO.getMemberId());

// 	// 營地假資料
// 	CampService campSvc = new CampService();
// 	CampVO campVO = campSvc.findCampByCampId(1);
// 	request.setAttribute("CampVO", campVO);

// 	// 營地假資料
// 	CampService campSvc = new CampService();
// 	CampVO campVO = campSvc.findCampByCampId(1);
// 	request.setAttribute("CampVO", campVO);

// 	MemberService memberSvc = new MemberService();
// 	MemberVO memberVO = memberSvc.getOneMember(1);
// 	request.setAttribute("memberVO", memberVO);
// 	// 營地假資料

// 	CampTagDetailService campTagDetailSvc = new CampTagDetailService();
// 	List<String> list = campTagDetailSvc.findCampTagsByCampIdNames(1);
// 	pageContext.setAttribute("list", list);
	
%>

	<jsp:useBean id="campSvc" class="com.camp.model.CampService"></jsp:useBean> 


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
	href="<%=request.getContextPath()%>/front_end/member/css/member_order.css"
	rel="stylesheet" type="text/css">
<title>營地訂單列表</title>
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
					href="<%=request.getContextPath()%>/front_end/member/member_favorite_camp.jsp"><img
						src="<%=request.getContextPath()%>/front_end/mall/images/heart.png"></a></li>
				<li><a
					href="<%=request.getContextPath()%>/front_end/member/register/register.jsp"
					value="">註冊</a></li>
				<li><a
					href="<%=request.getContextPath()%>/front_end/member/login/login.jsp"
					value="">登入</a></li>
				<li><a
					href="<%=request.getContextPath()%>/front_end/member/jsp/member_main.jsp"
					value=""><i class="fas fa-user"></i></a></li>
			</nav>
		</ul>

	</header>
	<%-- =================  header區域   ===================== --%>
	
	
	<%-- =================  sidebar   ===================== --%>
	<form class="form-horizontal" method="post"
			action="<%=request.getContextPath()%>/member/MemberServlet">
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
					</ul></li>
				<li><a href=""><i class="fas fa-sign-out-alt"></i>
				<span><input class="fas fa-sign-out-alt logout_button" type="submit" value="&nbsp;登出" /></span>
				</a>
				<input type="hidden" value="logout" name="action" />
				</li>
				
		</div>
	</aside>
	</form>
	<%-- =================  sidebar   ===================== --%>
	
	<%
	CampOrderVO campOrderVO = (CampOrderVO)session.getAttribute("campOrderVO");
	Integer statusNum = campOrderVO.getCampOrderStatus();
	String statusMsg = "";
	switch(statusNum) {  
    case 0: 
    	statusMsg = "處理中";
        break; 
    case 1: 
        statusMsg = "已確認";
        break; 
    case 2: 
    	statusMsg = "已完成";
        break; 
    default: 
    	statusMsg = "";
	}
	%>
	
	
	<%-- =================  營地訂單列表   ===================== --%>
	<div class="table-title">
		<h3>營地訂單列表</h3>
	</div>
	<!-- 整個TABLE用FORECACH來用 -->
	<div class="table-fill"><%@ include file="page1.file" %></div>
	<c:forEach var="campOrderVO" items="${ list }" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
	<table class="table-fill">
		<thead> 
			<tr>
				<th>訂單編號 ${ campOrderVO.getCampOrderId() }</th> 
				<th>訂單日期 ${ campOrderVO.getCampOrderConfirmedTime() }</th> 
			</tr>
			<tr>
				<th class="text-left">營地圖片</th>
				<th class="text-left">營地名稱</th>
				<th class="text-left">訂位日期</th>
				<th class="text-left">天數</th>
				<th class="text-left">總價</th>
				<th class="text-left">訂單狀態</th>
			</tr>
		</thead>
		<tbody class="table-hover">
			<tr>
				<td class="text-center"><img class="product_pic" src="<%=request.getContextPath()%>/PicWithCampServlet?campid=${ campOrderVO.campId }&pic=1"
					alt="營地圖片"></td> 
				<td class="text-left"><a href="#">${ campSvc.getOneCamp(campOrderVO.campId).campName }</a></td> 
				<td class="text-left">${ campOrderVO.campCheckInDate }</td> 
				<td class="text-left"></td>	
				<td class="text-left">${ campOrderVO.campOrderTotalAmount }</td> 
				<td class="text-left">
				${(campOrderVO.campOrderStatus == 0) ? "處理中" : (campOrderVO.campOrderStatus == 1) ? "已確認" : (campOrderVO.campOrderStatus == 2) : "已完成" }			
				</td> 
			</tr>

			<tr>
				<td class="text-left" colspan="5"></td>
				<td class="text-center">
					<form method="post" action="<%=request.getContextPath()%>/favoriteCamp/FavoriteCampServlet">
					<button class="button" type="button"
						onclick="location.href = '<%=request.getContextPath()%>/front_end/member/jsp/member_camp_order_detail.jsp';">訂單明細</button>
						<input class="button" type="submit" value="取消訂單"/>
						<input type="hidden" value="delete" name="action" /> 
					</form> 
				</td>
			</tr>
			</c:forEach>	
		</tbody>
		<!-- </form> -->
	</table>


	<div class="table-fill"><%@ include file="page2.file" %></div>

	<!-- 整個TABLE用FORECACH來用 -->
	<%-- =================  營地訂單列表   ===================== --%>
	
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