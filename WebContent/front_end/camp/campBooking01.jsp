<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.campArea.model.*"%>
<%@ page import="com.camp.model.*"%>
<%
	List<CampAreaVO> list = (List<CampAreaVO>) request.getAttribute("list");
	String date = (String) request.getAttribute("date");
	String days = (String) request.getAttribute("days");
	CampVO campVO = (CampVO) request.getAttribute("campVO");
%>
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<title>訂位畫面</title>
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"
	integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm"
	crossorigin="anonymous">
<link href="#" rel="shortcut icon">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front_end/camp/css/header_footer.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front_end/camp/css/camp_booking01.css">


</head>

<body>
	<!-- Sticky header -->
	<header class="header-outer">
		<div class="header-inner responsive-wrapper">
			<div class="header-logo">
				<a style="display: inline-block; vertical-align: middle;"
					href="首頁URL"> <img src="#" />
				</a> <span style="display: inline-block; vertical-align: middle;">Camping
					Paradise</span>
			</div>
			<nav class="header-navigation">
				<a href="#">Home</a> <a href="#">線上商城</a> <a href="#"><img
					src="#"></a> <a href="#">註冊</a> <a href="#">登入</a> <a href="#"><i
					class="fas fa-user-circle"></i></a>
				<button>Menu</button>
			</nav>
		</div>
	</header>




	<main>

		<div id="container">
			<section class="checkout-progress">

				<div class="triangle2-incomplete"></div>
				<div class="step incomplete">
					<em>1</em>選擇日期&天數
				</div>
				<div class="triangle-incomplete"></div>

				<div class="triangle2-active"></div>
				<div class="step active">
					<em>2</em>選擇營位數量
				</div>
				<div class="triangle-active"></div>


				<div class="triangle2-incomplete"></div>
				<div class="step incomplete">
					<em>3</em>選擇付款方式
				</div>
				<div class="triangle-incomplete"></div>


				<div class="triangle2-incomplete"></div>
				<div class="step incomplete">
					<em>4</em>結帳
				</div>
				<div class="triangle-incomplete"></div>
			</section>


			<h2 style="text-align: left;">營位預訂</h2>



			<hr>
			<div class="outer_block freeinfo">
				<div class="left_block" id="begin">
					入住日期:${date} <br>
					<button type="button" class="btn btn-accept" id="btn-accept">
						重選日期</button>
				</div>
				<div class="right_block" id="daynum">
					露營天數: ${days} <br>
					<button type="button" class="btn btn-accept" id="btn-accept">
						重選天數</button>
				</div>
			</div>

			<label for="weekday">平日 :</label> <input type="text" id="weekday"
				value="0" disabled>天 <label for="holiday">假日 :</label> <input
				type="text" id="holikday" value="0" disabled>天


			<%-- 錯誤表列 --%>
			<c:if test="${not empty errorMsgs}">
				<font style="color: red">請修正以下錯誤:</font>
				<ul>
					<c:forEach var="message" items="${errorMsgs}">
						<li style="color: red">${message}</li>
					</c:forEach>
				</ul>
			</c:if>
			<h1>營地名稱:${campVO.campName}</h1>
			<table class="freeinfo">
				<thead>
					<tr style="text-align: center;">
						<td>圖片</td>
						<td>營區分位</td>
						<td>平日/每帳價格</td>
						<td>帳數</td>
						<td>假日日/每帳價格</td>
						<td>帳數</td>
						<td>加購人頭</td>
						<td>人頭數量</td>
						<td>總計</td>
						<td></td>
					</tr>
				</thead>

				<tbody>

					<c:forEach var="areaVO" varStatus="v" items="${list}">
						<tr class="p">
						
							<td class="campAreaId" style="display:none;">${areaVO.campAreaId}</td>
							<td class="image enlarge"><img
								src="<%=request.getContextPath()%>/PicWithCampServlet?campid=${areaVO.campId}&areaindex=${v.index+1}">
							</td>
							<td class="name">${areaVO.campAreaName}</td>
							<td class="wprice">${areaVO.weekdayPrice}</td>
							<td class="wamount"><input type="number" name="weeknum"
								value="0" min="0" max="${areaVO.campAreaMax}"></td>
							<td class="hprice">${areaVO.holidayPrice}</td>
							<td class="hamount"><input type="number" name="holinum"
								value="0" min="0" max="${areaVO.campAreaMax}"></td>
							<td class="pprice">${areaVO.perCapitationFee}</td>
							<td class="pamount"><input type="number" name="pernum"
								value="0" min="0" max="${areaVO.capitationMax}"></td>
							<td class="pricesubtotal"></td>
							<td class="remove">
								<button type="button">重新填選</button>
							</td>
						</tr>

					</c:forEach>
					

				</tbody>
				<tfoot>
					<tr>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>

						<td>Subtotal:</td>
						<td class="totalpricesubtotal"></td>
						<td></td>
					</tr>

				</tfoot>
			</table>
			  <button type="button" class="checkout" onclick="location.href = '<%=request.getContextPath()%>/front_end/camp/camp_calendar';"><span> &larr;</span>上一頁</button>
            <div id="confirm" class="checkout">立即結帳<span> &rarr;</span></div>

		</div>
	</main>


	<footer class="tm-footer text-center">
		<pre>服務專線：(02)2252-7966　　 客服時間：週一至週五9:00~18:00　　 客服信箱：camp@easycamp.com.tw</pre>
		<pre>Copyright &copy; 2020 Simple House | Design: <a
				style="text-decoration: none;" rel="nofollow" href="#">TFA104第五組</a>
		</pre>
	</footer>




	<form method="post" action="<%=request.getContextPath()%>/CampBookingServlet">
		<div class="cover"></div>
		<input type="hidden" name="action" value="confirmseat">
		<input type="hidden" name="campId" value="${campVO.campId}">
		
		<input type="hidden" id="chooseDate" name="chooseDate" value="">
		<input type="hidden" id="chooseDay"  name="chooseDay" value="">
		<div class="pop-box">
			<div class="close_container" id="close_container">
				<span class="windowtitle">確認訂位信息</span> <span class="x-btn close">X</span>
			</div>

			<div id="outerbox">
				<ul class="innerbox innerbox2" id="thead">
					<li>營區分位</li>
					<li>平日/每帳價格</li>
					<li>帳數</li>
					<li>假日日/每帳價格</li>
					<li>帳數</li>
					<li>加購人頭</li>
					<li>人頭數量</li>
					<li>總計</li>
				</ul>
				<ul class="innerbox" id="thead">
					<li></li>
					<li></li>
					<li></li>
					<li></li>
					<li></li>
					<li></li>
					<li>總計:</li>
					<li id="total">0</li>
				</ul>
			</div>
			<button type="submit" class="orderbtn" id="orderbtn">確認送出</button>
		</div>
	</form>







	<script type="text/javascript"
		src="front_end/camp/js/camp_booking01.js"></script>


</body>
</html>