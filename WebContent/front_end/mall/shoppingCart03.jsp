<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.cart.model.CartVO"%>

<!DOCTYPE html>
<html lang="zh-Hant">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>購物車step3</title>
<!-- header&footer CSS -->
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"
	integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm"
	crossorigin="anonymous">

<!-- 我自己加的CSS -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front_end/mall/css/shoppingCart.css">

<style>
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
				<a href="/TFA104G5/front_end/camp/camp_index.html">Home</a>
        		<a href="/TFA104G5/front_end/mall/mall_index.html">線上商城</a>
        		<a href="/TFA104G5/front_end/member/jsp/member_favorite_product.jsp"><img src="/TFA104G5/front_end/mall/images/heart.png"></a>
        		<a href="/TFA104G5/front_end/mall/shoppingCart01.jsp"><i style="color: white; font-size: 23px; position: relative;" class="fas fa-shopping-cart"><div id="cartNum" style="background-color: red; border-radius: 100%;text-align: center; width: 18px; font-size: 14px; position: absolute; top: -8px; right: -8px; padding: 3px;"></div></i></a>
        		<a href="/TFA104G5/front_end/member/login/login.jsp">登入|註冊</a>
        		<a href="/TFA104G5/front_end/member/jsp/member_main.jsp"> <i class="fas fa-user"></i></a>
			</nav>
		</div>
	</header>
	<!-- header-end -->
	<div style="text-align: center;">
		<h2>
			1.確認商品 >> 2.資料填寫 >> <span
				style="color: white; background-color: #dbb07c; border-radius: 20px; padding: 3px 10px;">3.資料確認</span>
			>> 4.訂單成立
		</h2>
	</div>

	<div class="shopping-cart" style="padding: 50px 30px 50px 30px;">

		<%
			if (session.getAttribute("buyList") != null) {

				List<CartVO> buyList = (List<CartVO>) session.getAttribute("buyList");

				Set<Integer> companySet = new TreeSet<Integer>();
				for (CartVO c : buyList) {
					companySet.add(c.getCompanyId());
				}

				request.setAttribute("companySet", companySet);
			}
		%>

		<jsp:useBean id="companyDAOImpl"
			class="com.company.model.CompanyDAOImpl"></jsp:useBean>

		<c:forEach var="oneOrder" items="${ companySet }">






			<!-- 一個訂單start -->
			<div class="oneOrder">
				<h1 style="margin-bottom: 10px;">${companyDAOImpl.findByPK(oneOrder).companyName}</h1>

				<div class="column-labels">
					<label class="product-image">Image</label> <label
						class="product-details">Product</label> <label
						class="product-price" style="color: black; font-weight: bold;">單價</label>
					<label class="product-quantity"
						style="color: black; font-weight: bold;">數量</label> <label
						class="product-removal" style="color: black; font-weight: bold;">移除</label>
					<label class="product-line-price"
						style="color: black; font-weight: bold;">總計</label>
				</div>

				<c:forEach var="oneProduct" items="${ buyList }">

					<c:if test="${oneOrder == oneProduct.companyId}">
						<!-- 一個商品項start -->
						<div class="product">
							<div class="product-image">
								<img
									src="<%=request.getContextPath()%>/product/PicServlet?productId=${oneProduct.productId}&pic=1">
							</div>
							<div class="product-details">
								<div style="width: 85%; font-weight: bold;"
									class="product-title">
									<a
										href="<%=request.getContextPath()%>/front_end/mall/mall_product_detail.html?productId=${oneProduct.productId}&productTypeId=${oneProduct.productTypeId}"
										style="text-decoration: none;">${oneProduct.productName}</a>
								</div>
								<p class="product-description"></p>
							</div>
							<div class="product-price">${oneProduct.productPrice}</div>
							<div class="product-quantity">
								<input type="number"
									value="${oneProduct.productPurchaseQuantity}" min="1" disabled>
							</div>
							<div class="product-removal">
								<button class="remove-product" style="visibility: hidden">移除</button>
							</div>
							<div class="product-line-price">${oneProduct.productPurchaseQuantity * oneProduct.productPrice}</div>
						</div>
						<!-- 一個商品項end -->

					</c:if>

				</c:forEach>

				<div style="padding-bottom: 50px;" class="totals">
					<div class="totals-item">
						<label style="font-weight: bold; color: black;">訂單金額</label>
						<div class="totals-value">0</div>
					</div>
				</div>
			</div>
			<!-- 一個訂單end -->



		</c:forEach>


		<!-- 總金額 -->

		<div class="totals">
			<div class="totals-item totals-item-total">
				<label style="font-weight: bold; color: red;">訂單總金額</label>
				<div class="totals-value" id="cart-total" style="font-weight: bold; color: red;">0</div>
			</div>
		</div>

		<!-- 填寫收件人資訊 -->
		<form id="checkout" action="<%=request.getContextPath()%>/Cart/CartServlet" method="post">
			<div style="text-align: center; margin-bottom: 50px;">
				<div class="receiverInfo"
					style="text-align: left; display: inline-block;">
					<label style="color: black; font-weight: bold;"> 收件人姓名 : ${ receiverVO.receiverName }<input
						type="hidden" maxlength="10" size="50" name="receiverName"
						id="receiverName" value="${ receiverVO.receiverName }" placeholder="請輸入姓名"
						style="margin-bottom: 10px;">
					</label> <br> <label style="color: black; font-weight: bold;">
						收件人手機 : ${ receiverVO.receiverPhone }<input type="hidden" maxlength="10"
						size="50" name="receiverPhone" id="receiverPhone"
						value="${ receiverVO.receiverPhone }" placeholder="ex:09xxxxxxxx"
						style="margin-bottom: 10px;">
					</label> <br> <label style="color: black; font-weight: bold;">
						收件人地址 : ${ receiverVO.receiverAddress }<input type="hidden" maxlength="80"
						size="50" name="receiverAddress" id="receiverAddress"
						value="${ receiverVO.receiverAddress }" placeholder="請輸入地址"
						style="margin-bottom: 10px;">
					</label> <br>

					<button type="button" id="inputReceiverInfo"
						style="visibility: hidden">同會員資料</button>
					<br> <label style="color: black; font-weight: bold;">
						信用卡號 : ${ receiverVO.creditCardNum }<input type="hidden" maxlength="16"
						size="16" name="creditCardNum" id="creditCardNum"
						value="${ receiverVO.creditCardNum }" placeholder="請輸入16碼數字"
						style="margin-bottom: 10px;">
					</label> <br> <label style="color: black; font-weight: bold;">
						安全碼 : ${ receiverVO.securityCode }<input type="hidden" maxlength="3" size="3"
						name="securityCode" id="securityCode" value="${ receiverVO.securityCode }"
						placeholder="xxx" style="margin-bottom: 10px;">
					</label> <label style="color: black; font-weight: bold;"> 有效日期 : ${ receiverVO.effectiveDate }<input
						type="hidden" maxlength="4" size="4" name="effectiveDate"
						id="effectiveDate" value="${ receiverVO.effectiveDate }" placeholder="MMYY"
						style="margin-bottom: 10px;">
					</label><br>
										<%-- 錯誤表列 --%>
					<c:if test="${not empty errorMsgs}">
						<font style="color: red">請修正以下錯誤:</font>
						<ul>
							<c:forEach var="message" items="${errorMsgs}">
								<li style="color: red">${message}</li>
							</c:forEach>
						</ul>
					</c:if>
					
				</div>
			</div>


			<input type="hidden" name="action" value="checkout">
			<input type="submit" class="checkout" value="確認結帳">
		</form>

		<form action="<%=request.getContextPath()%>/Cart/CartServlet"
			method="post">
			<input type="hidden" name="receiverName" value="${ receiverVO.receiverName }">
			<input type="hidden" name="receiverPhone" value="${ receiverVO.receiverPhone }">
			<input type="hidden" name="receiverAddress" value="${ receiverVO.receiverAddress }">
			<input type="hidden" name="creditCardNum" value="${ receiverVO.creditCardNum }">
			<input type="hidden" name="securityCode" value="${ receiverVO.securityCode }">
			<input type="hidden" name="effectiveDate" value="${ receiverVO.effectiveDate }">
			<input type="hidden" name="action" value="step3tostep2"> <input
				type="submit" class="checkout" style="margin-right: 30px;"
				value="上一步">
		</form>

	</div>


	<!-- footer-start -->
	<footer class="tm-footer text-center">
		<pre>服務專線：(02)2252-7966　　 客服時間：週一至週五9:00~18:00　　 客服信箱：camp@easycamp.com.tw</pre>
		<pre>Copyright &copy; 2021 Camping Paradise | Design: <a
				style="text-decoration: none;" rel="nofollow" href="#">TFA104第五組</a>
		</pre>
	</footer>
	<!-- footer-end -->

	<!-- 訂單成立中請稍等 -->
	<div class="overlay" style="border: 1px solid red;">
		<article style="text-align: center;">
			<h1>訂單成立中請稍等...</h1>
	
			    <p></p>
			<!-- <button type="button" class="btn_modal_close">關閉</button> -->
		</article>
	</div>

	<script
		src="<%=request.getContextPath()%>/front_end/mall/vendor/vendors_shoppingCart/jquery/jquery-3.6.0.min.js"></script>

	<script
		src="<%=request.getContextPath()%>/front_end/mall/js/shoppingCart.js"></script>
	
	<script>
		$("form#checkout").on("submit", function(){
			$("div.overlay").fadeIn();
		});
	</script>
</body>

</html>