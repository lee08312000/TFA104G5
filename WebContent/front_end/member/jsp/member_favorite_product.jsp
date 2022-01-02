<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><!-- jstl核心函式庫含for each標籤等 -->
<%@ page import="com.member.model.*"%>
<%@ page import="com.product.model.*"%>
<%@ page import="com.favoriteProdoct.model.*"%>
<%@ page import="java.util.*"%> <!-- list用 -->

<%
	MemberVO memberVO =  (MemberVO)session.getAttribute("memberVO");
	
	FavoriteProdoctService favoriteProdoctSvc = new FavoriteProdoctService();
	List<FavoriteProdoctVO>	productList = favoriteProdoctSvc.getAllFavoriteProdoct();
	List<FavoriteProdoctVO> list = new ArrayList<FavoriteProdoctVO>();
	
	for(FavoriteProdoctVO favoriteProdoctVO : productList) {
		if(memberVO.getMemberId().intValue() == favoriteProdoctVO.getMemberId().intValue()) {
		// intValue 變成int來比對 
			list.add(favoriteProdoctVO);
		}  
	}
	
	pageContext.setAttribute("list", list);	
%>

	<jsp:useBean id="productSvc" class="com.product.model.ProductService"></jsp:useBean> <!-- 新創一個SVC從CLASS抓 -->
	<jsp:useBean id="companySvc" class="com.company.model.CompanyService"></jsp:useBean>




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
<title>我的最愛商品</title>
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
				<input type="hidden" value="logout" name="action" />
				</a>
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
						</ul></li>
			</div>
		</aside>
	<%-- =================  sidebar   ===================== --%>

	<%-- =================  我的最愛商品表格   ===================== --%>
	<div class="table-title">
		<h3>我的最愛商品</h3>
	</div>
	<table class="table-fill">
		<thead>
			<tr>
				<th class="text-left">商品圖片</th>
				<th class="text-left">商品名稱</th>
				<th class="text-left">價格</th>
				<th class="text-left">販售廠商</th> <!-- 這邊資料庫混再一起(?) -->
				<th class="text-left">評價</th>
				<th class="text-left"></th>
			</tr>
		</thead>

		<tbody class="table-hover">
		<div class="table-fill"><%@ include file="page1.file" %></div>
		<c:forEach var="favoriteProductVO" items="${ list }" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
			<tr>
				<td class="text-center"><img class="product_pic"
					src="<%=request.getContextPath()%>/product/PicServlet?productId=${ favoriteProductVO.productId }&pic=1"
					alt="商品圖片"></td>
				<td class="text-left"><a href="<%=request.getContextPath()%>/front_end/mall/mall_product_detail.html?productId=${ favoriteProductVO.productId }">${ productSvc.getOneProduct(favoriteProductVO.productId).productName }</a></td> 	
				<td class="text-left">${ productSvc.getOneProduct(favoriteProductVO.productId).productPrice }</td> <!-- ${ productVO.productPrice } -->
				<td class="text-left">${ companySvc.getOneCompany(productSvc.getOneProduct(favoriteProductVO.productId).companyId).companyName }</td> <!-- ${ productVO.companyId } -->
				<td class="text-left">${ productSvc.getOneProduct(favoriteProductVO.productId).productCommentedAllnum.intValue() == 0 ? "0" : Math.round(productSvc.getOneProduct(favoriteProductVO.productId).productCommentAllstar.floatValue() / productSvc.getOneProduct(favoriteProductVO.productId).productCommentedAllnum.floatValue())   } / 5</td> <!-- ${ productVO.productCommentAllstar } -->
				<!-- Math.round(productSvc.getOneProduct(favoriteProductVO.productId).productCommentAllstar.floatValue() / productSvc.getOneProduct(favoriteProductVO.productId).productCommentedAllnum.floatValue()) --> 
				<td class="text-center"> 
					<form method="post"
			action="<%=request.getContextPath()%>/favoriteProduct/FavoriteProductServlet">
					<input class="button" type="submit" value="刪除" /> 
					<input type="hidden" value="delete" name="action" />
					<input type="hidden" value="${ favoriteProductVO.favoriteProductId }" name="favoriteProductId" />
					</form>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
	<div class="table-fill"><%@ include file="page2.file" %></div>
	<%-- =================  我的最愛商品表格   ===================== --%>

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