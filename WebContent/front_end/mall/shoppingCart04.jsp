<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.cart.model.CartVO"%>
<%@ page import="com.mallOrder.model.MallOrderVO"%>
<%@ page import="com.mallOrder.model.MallOrderService"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
	if (request.getAttribute("mallOrderIdList") != null) {

		MallOrderService mallOrderSvc = new MallOrderService();

		List<Integer> mallOrderIdList = (List<Integer>) request.getAttribute("mallOrderIdList");
		List<MallOrderVO> mallOrderVOList = new ArrayList<MallOrderVO>();

		for (Integer mallOrderId : mallOrderIdList) {
			mallOrderVOList.add(mallOrderSvc.getOneMallOrder(mallOrderId));
		}
		request.setAttribute("mallOrderVOList", mallOrderVOList);
	} else {
		/***************************測試前端用假資料******************************/
// 		MallOrderService mallOrderSvc = new MallOrderService();
// 		List<MallOrderVO> mallOrderVOList = new ArrayList<MallOrderVO>();
// 		mallOrderVOList.add(mallOrderSvc.getOneMallOrder(1));
// 		mallOrderVOList.add(mallOrderSvc.getOneMallOrder(2));
// 		request.setAttribute("mallOrderVOList", mallOrderVOList);
		/***************************測試前端用假資料******************************/
	}
%>






<!DOCTYPE html>
<html lang="zh-Hant">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>購物車step4</title>
<!-- header&footer CSS -->
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"
	integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm"
	crossorigin="anonymous">

<!-- 我自己加的CSS -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front_end/mall/css/shoppingCart.css">
<style>
table, tr, th, td {
	border: 1px solid black;
	padding: 3px 5px;
	border-collapse: collapse;
}
th {
	border: none;
	color: white;
	background-color: #d7ab75;
}

div.overlay{
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100vh;
  background-color: hsla(0, 0%, 0%, .5);
  
  display: none;
  z-index: 999999999999;
}

/* 元素 article 置中及基本樣式 */
div.overlay > article{
  background-color: white;
  width: 80%;
  max-width: 100%;
  border-radius: 10px;
  /* box-shadow: 0 0 10px #ddd; */
  padding: 10px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  height: auto;
  max-height: 90vh;
  overflow: scroll;
}
</style>
</head>

<body>
	<!-- header-start -->
	<header class="header-outer" style="width: 100%;">
		<div class="header-inner responsive-wrapper">
			<div class="header-logo">
				<a style="display: inline-block; vertical-align: middle;"
					href="首頁URL"> <img
					src="<%=request.getContextPath()%>/front_end/mall/images/camp_paradise_logo.png" />
				</a> <span style="display: inline-block; vertical-align: middle;">Camping
					Paradise</span>
			</div>
			<nav class="header-navigation">
				<a href="#">Home</a> <a href="#">線上商城</a> <a href="#"><img
					src="<%=request.getContextPath()%>/front_end/mall/images/heart.png"></a>
				<a href="<%=request.getContextPath()%>/front_end/mall/shoppingCart01.jsp"><i style="color: white; font-size: 23px;"
					class="fas fa-shopping-cart"><div id="cartNum" style="background-color: red; border-radius: 100%;text-align: center; width: 18px; font-size: 14px; position: absolute; top: -8px; right: -8px; padding: 3px;"></div></i></a> <a href="#">註冊</a> <a href="#">登入</a>
				<a href="#"> <i class="fas fa-user"></i></a>

				<!-- fas fa-user-circle
    
                fas fa-user-circle
                 -->
				<button>Menu</button>
			</nav>
		</div>
	</header>
	<!-- header-end -->
	<div style="text-align: center;">
		<h2>
			1.確認商品 >> 2.資料填寫 >> 3.資料確認 >> <span
				style="color: white; background-color: #dbb07c; border-radius: 20px; padding: 3px 10px;">4.訂單成立</span>
		</h2>
	</div>
	
	<div class="orders" style="padding: 50px 30px 50px 30px;">
		<table style="margin-right: auto; margin-left: auto;">
			<tr>
				<th>訂單編號</th>
				<th>廠商名稱</th>
				<th>總金額</th>
				<th>收件人姓名</th>
				<th>收件人電話</th>
				<th>收件人地址</th>
				<th>成立時間</th>
				<th>訂單狀態</th>
				<th>物流狀態</th>
				<th>操作</th>
			</tr>

			<jsp:useBean id="companySvc" class="com.company.model.CompanyService"></jsp:useBean>
			<c:forEach var="mallOrderVO" items="${ mallOrderVOList }">
				<tr data-mallOrderId="${ mallOrderVO.mallOrderId }">
					<td>${ mallOrderVO.mallOrderId }</td>
					<td>${ companySvc.getOneCompany(mallOrderVO.companyId).companyName }</td>
					<td>${ mallOrderVO.mailOrderTotalAmount }</td>
					<td>${ mallOrderVO.receiverName }</td>
					<td>${ mallOrderVO.receiverPhone }</td>
					<td>${ mallOrderVO.receiverAddress }</td>
					<td><fmt:formatDate
							value="${ mallOrderVO.mallOrderConfirmedTime }"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>${ mallOrderVO.mallOrderStatus.intValue() == 0 ? "處理中" : mallOrderVO.mallOrderStatus.intValue() == 1 ? "已確認" : mallOrderVO.mallOrderStatus.intValue() == 2 ? "已完成" : "異常狀態"}</td>
					<td>${ mallOrderVO.mallOrderDeliveryStatus.intValue() == 0 ? "未發貨" : mallOrderVO.mallOrderDeliveryStatus.intValue() == 1 ? "已發貨" : mallOrderVO.mallOrderDeliveryStatus.intValue() == 2 ? "已收貨" : "異常狀態"}</td>
					<td><button class="checkOrderDetail" type="button">查看明細</button></td>
				</tr>
			</c:forEach>
		</table>


	</div>

	<!-- footer-start -->
	<footer class="tm-footer text-center">
		<pre>服務專線：(02)2252-7966　　 客服時間：週一至週五9:00~18:00　　 客服信箱：camp@easycamp.com.tw</pre>
		<pre>Copyright &copy; 2021 Camping Paradise | Design: <a
				style="text-decoration: none;" rel="nofollow" href="#">TFA104第五組</a>
		</pre>
	</footer>
	<!-- footer-end -->

	<!-- 訂單明細燈箱 -->
	<div class="overlay" style="border: 1px solid red;">
		<article style="text-align: center;">
		  <h1 >訂單明細</h1>
			<table id="orderDetail" style="margin-right: auto; margin-left: auto;">
				<!-- 訂單明細動態新增 -->
			</table>

		  <p></p>
		  <button type="button" class="btn_modal_close">關閉</button>
		</article>
	  </div>

	<script
		src="<%=request.getContextPath()%>/front_end/mall/vendor/vendors_shoppingCart/jquery/jquery-3.6.0.min.js"></script>

	<script
		src="<%=request.getContextPath()%>/front_end/mall/js/shoppingCart.js"></script>
	<script>
		$(document).on("click", "button.checkOrderDetail", function(){
			let that = this;
			// 去資料庫查訂單明細
	$.ajax({
        url: "/TFA104G5/Cart/CartServlet",
        type: "POST",
        data: {
			"action": "showOrderDetail",
			"mallOrderId": $(that).closest("tr").attr("data-mallOrderId")
		},
        dataType: "json",
        beforeSend: function () {

        },
        success: function (orderDetailList) {
			
				// 塞入明細表格
				let tableHead = 
				`<tr>
					<th>圖片</th>
					<th>商品名稱</th>
					<th>數量</th>
					<th>單價</th>
					<th>項目金額</th>
				</tr>`;

				let tableDetail = "";
				$.each(orderDetailList, function (index, item){
					// console.log(`${"${item.productId}"}`);

				tableDetail +=
				`<tr>
					<td><img style="width: 70px;" src="/TFA104G5/product/PicServlet?productId=${"${item.productId}"}&pic=1"></td>
					<td>${"${item.productName}"}</td>
					<td>${"${item.productPurchaseQuantity}"}</td>
					<td>${"${item.productPurchasePrice}"}</td>
					<td>${"${item.productPurchaseQuantity * item.productPurchasePrice}"}</td>
				</tr>`;
				});
				let table = tableHead + tableDetail;

			$("table#orderDetail").html(table);
			$("div.overlay").fadeIn();
        },
        complete: function (xhr) {
            // console.log(xhr);
        }
    });

			
		});


		// 燈箱裡的關閉按鈕
		$("button.btn_modal_close").on("click", function(){
    		$("div.overlay").fadeOut();
  		});

		
	</script>
</body>

</html>