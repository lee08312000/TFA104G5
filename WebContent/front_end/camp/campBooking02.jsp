<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page import="java.util.*"%>
<%@ page import="com.campArea.model.*"%>
<%@ page import="com.camp.model.*"%>
<%@ page import="com.member.model.*"%>
<%
	MemberVO member = (MemberVO) session.getAttribute("memberVO");

	CampVO campVO = (CampVO) request.getAttribute("campVO");
	String beginDate = (String) request.getAttribute("beginDate");
	String endDate = (String) request.getAttribute("endDate");
	ArrayList<Map> seatlist =(ArrayList<Map>)session.getAttribute("seatlist");

%>



<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>確認資料</title>
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"
	integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm"
	crossorigin="anonymous">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

<script src="https://unpkg.com/imask"></script>


<link
	href="<%=request.getContextPath()%>/front_end/camp/css/header_footer.css"
	rel="stylesheet" />




<link
	href="<%=request.getContextPath()%>/front_end/camp/css/camp_booking02.css"
	rel="stylesheet" />

<link href="img/icon/chuba_logo.png" rel="shortcut icon">


</head>

<body>
	<!-- Sticky header -->
	<header class="header-outer">
		<div class="header-inner responsive-wrapper">
			<div class="header-logo">
				<a style="display: inline-block; vertical-align: middle;"
					href="camp_index.html"> <img
					src="img/icon/chuba_logo.png" />
				</a> <span style="display: inline-block; vertical-align: middle;">Camping
					Paradise</span>
			</div>
			<nav class="header-navigation">
				<a href="camp_index.html">Home</a> <a href="../mall/mall_index.html">線上商城</a> <a href="#"><img
					src="img/icon/heart.png"></a> <a href="../member/login.jsp">登入|註冊</a>
				<a href="#"><i class="fas fa-user-circle"></i></a>
				<button>Menu</button>
			</nav>
		</div>
	</header>




	<main>
	
	       <section class="checkout-progress">

                <div class="triangle2-incomplete"></div>
                <div class="step incomplete"><em>1</em>選擇日期&天數</div>
                <div class="triangle-incomplete"></div>

                <div class="triangle2-incomplete"></div>
                <div class="step incomplete"><em>2</em>選擇營位數量</div>
                <div class="triangle-incomplete"></div>

                <div class="triangle2-active"></div>
                <div class="step active"><em>3</em>資料確認</div>
                <div class="triangle-active"></div>



                <div class="triangle2-incomplete"></div>
                <div class="step incomplete"><em>4</em>結帳</div>
                <div class="triangle-incomplete"></div>
            </section>
		<%-- 錯誤表列 --%>
		<c:if test="${not empty errorMsgs}">
			<font style="color: red">請修正以下錯誤:</font>
			<ul>
				<c:forEach var="message" items="${errorMsgs}">
					<li style="color: red">${message}</li>
				</c:forEach>
			</ul>
		</c:if>

		<form method="post"
			action="CampBookingServlet">

			<div class="wrapper">

				<section class="traveler">


					<h2>
						<span class="traveler-number">訂位資訊</span>
					</h2>




					<div class="input">
						<label for="Name">營地名稱:</label> <input type="text" id="campname"
							 value="${campVO.getCampName()}" disabled />
					</div>

					<div class="input">
						<label for="bookday">訂位日期:</label> <input type="text"
							class="birthday-dp" id="bookday" name="BookedDate"
							value="${beginDate}~${endDate}" disabled />
					</div>


					<c:forEach items="${seatlist}" var="orderlist">
						<div class="input">
							<label for="campAreaNameNum">營位數量:</label> <input type="text"
								class="phone-number" id="campAreaNameNum" name="campAreaNameNum"
								value="${orderlist['campAreaName']}${orderlist['holidayNum']+orderlist['weekdayNum']}帳"
								disabled /> <label for="perCapitationNum">加購人頭數量:</label> <input
								type="text" class="phone-number" id="perCapitationNum"
								name="perCapitationNum"
								value="${orderlist['perCapitationNum']}人" disabled /> <label
								for="subtotal">小計:</label> <input type="text"
								class="phone-number" id="subtotal" name="subtotal"
								value="${orderlist['subtotal']}" disabled />
						</div>
					</c:forEach>


					<hr>
					<div class="input">
						<label for="totalmoney">總金額:</label> <input type="text"
							class="totalmoney" id="totalmoney" name="totalmoney" value="$0"
							disabled />
					</div>
				</section>




				<div class="input">
					<button class="btn add" onclick="history.go(-1);">修改訂購資訊</button>
				</div>



				<section class="flight-departing">
					<h2>付款人資訊</h2>
					<input type="checkbox" id="memberdata" class="memberdata" /> <label
						for="memberdata">同會員資料</label>

					<div class="input">
						<label for="DepartingDate" class="notna">付款人姓名:</label> <input
							type="text" class="datepicker" id="payername" placeholder="真實姓名"
							required />
						<div class="input radio">
							<input type="radio" class="" id="men" name="sex" value="1" /> <label
								for="men">先生</label> <input type="radio" class="" id="women"
								name="sex" value="0" /> <label for="women">小姐</label>
						</div>
					</div>

					<div class="input">
						<label for="payertel" class="notna">聯絡電話:</label> <input
							type="tel" class="timepicker" id="payertel" name="payertel"
							value="" placeholder="(09)xx-xxx-xxx" size="10" required
							pattern="[0-9]{10}">
					</div>



					<div class="input">
						<label for="email" class="notna">電子信箱:</label> <input type="email"
							id="email" name="email"
							pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$" required>

					</div>


				</section>



				<section class="payment">
					<div class="payment-title">
						<h2>信用卡資訊</h2>
					</div>
					<div class="container preload">
						<div class="creditcard">
							<div class="front">
								<div id="ccsingle"></div>
								<svg version="1.1" id="cardfront"
									xmlns="http://www.w3.org/2000/svg"
									xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
									viewBox="0 0 750 471"
									style="enable-background: new 0 0 750 471;"
									xml:space="preserve">
                            <g id="Front">
                                <g id="CardBackground">
                                    <g id="Page-1_1_">
                                        <g id="amex_1_">
                                            <path id="Rectangle-1_1_"
										class="lightcolor grey"
										d="M40,0h670c22.1,0,40,17.9,40,40v391c0,22.1-17.9,40-40,40H40c-22.1,0-40-17.9-40-40V40
                                    C0,17.9,17.9,0,40,0z" />
                                        </g>
                                    </g>
                                    <path class="darkcolor greydark"
										d="M750,431V193.2c-217.6-57.5-556.4-13.5-750,24.9V431c0,22.1,17.9,40,40,40h670C732.1,471,750,453.1,750,431z" />
                                </g>
                                <text
										transform="matrix(1 0 0 1 60.106 295.0121)" id="svgnumber"
										class="st2 st3 st4">0123 4567 8910 1112</text>
                                <text
										transform="matrix(1 0 0 1 54.1064 428.1723)" id="svgname"
										class="st2 st5 st6">JOHN DOE</text>
                                <text
										transform="matrix(1 0 0 1 54.1074 389.8793)"
										class="st7 st5 st8">cardholder name</text>
                                <text
										transform="matrix(1 0 0 1 479.7754 388.8793)"
										class="st7 st5 st8">expiration</text>
                                <text
										transform="matrix(1 0 0 1 65.1054 241.5)" class="st7 st5 st8">card number</text>
                                <g>
                                    <text
										transform="matrix(1 0 0 1 574.4219 433.8095)" id="svgexpire"
										class="st2 st5 st9">01/23</text>
                                    <text
										transform="matrix(1 0 0 1 479.3848 417.0097)"
										class="st2 st10 st11">VALID</text>
                                    <text
										transform="matrix(1 0 0 1 479.3848 435.6762)"
										class="st2 st10 st11">THRU</text>
                                    <polygon class="st2"
										points="554.5,421 540.4,414.2 540.4,427.9 		" />
                                </g>
                                <g id="cchip">
                                    <g>
                                        <path class="st2"
										d="M168.1,143.6H82.9c-10.2,0-18.5-8.3-18.5-18.5V74.9c0-10.2,8.3-18.5,18.5-18.5h85.3
                                c10.2,0,18.5,8.3,18.5,18.5v50.2C186.6,135.3,178.3,143.6,168.1,143.6z" />
                                    </g>
                                    <g>
                                        <g>
                                            <rect x="82" y="70"
										class="st12" width="1.5" height="60" />
                                        </g>
                                        <g>
                                            <rect x="167.4" y="70"
										class="st12" width="1.5" height="60" />
                                        </g>
                                        <g>
                                            <path class="st12"
										d="M125.5,130.8c-10.2,0-18.5-8.3-18.5-18.5c0-4.6,1.7-8.9,4.7-12.3c-3-3.4-4.7-7.7-4.7-12.3
                                    c0-10.2,8.3-18.5,18.5-18.5s18.5,8.3,18.5,18.5c0,4.6-1.7,8.9-4.7,12.3c3,3.4,4.7,7.7,4.7,12.3
                                    C143.9,122.5,135.7,130.8,125.5,130.8z M125.5,70.8c-9.3,0-16.9,7.6-16.9,16.9c0,4.4,1.7,8.6,4.8,11.8l0.5,0.5l-0.5,0.5
                                    c-3.1,3.2-4.8,7.4-4.8,11.8c0,9.3,7.6,16.9,16.9,16.9s16.9-7.6,16.9-16.9c0-4.4-1.7-8.6-4.8-11.8l-0.5-0.5l0.5-0.5
                                    c3.1-3.2,4.8-7.4,4.8-11.8C142.4,78.4,134.8,70.8,125.5,70.8z" />
                                        </g>
                                        <g>
                                            <rect x="82.8" y="82.1"
										class="st12" width="25.8" height="1.5" />
                                        </g>
                                        <g>
                                            <rect x="82.8" y="117.9"
										class="st12" width="26.1" height="1.5" />
                                        </g>
                                        <g>
                                            <rect x="142.4" y="82.1"
										class="st12" width="25.8" height="1.5" />
                                        </g>
                                        <g>
                                            <rect x="142" y="117.9"
										class="st12" width="26.2" height="1.5" />
                                        </g>
                                    </g>
                                </g>
                            </g>
                            <g id="Back">
                            </g>
                        </svg>
							</div>
							<div class="back">
								<svg version="1.1" id="cardback"
									xmlns="http://www.w3.org/2000/svg"
									xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
									viewBox="0 0 750 471"
									style="enable-background: new 0 0 750 471;"
									xml:space="preserve">
                            <g id="Front">
                                <line class="st0" x1="35.3" y1="10.4"
										x2="36.7" y2="11" />
                            </g>
                            <g id="Back">
                                <g id="Page-1_2_">
                                    <g id="amex_2_">
                                        <path id="Rectangle-1_2_"
										class="darkcolor greydark"
										d="M40,0h670c22.1,0,40,17.9,40,40v391c0,22.1-17.9,40-40,40H40c-22.1,0-40-17.9-40-40V40
                                C0,17.9,17.9,0,40,0z" />
                                    </g>
                                </g>
                                <rect y="61.6" class="st2" width="750"
										height="78" />
                                <g>
                                    <path class="st3"
										d="M701.1,249.1H48.9c-3.3,0-6-2.7-6-6v-52.5c0-3.3,2.7-6,6-6h652.1c3.3,0,6,2.7,6,6v52.5
                            C707.1,246.4,704.4,249.1,701.1,249.1z" />
                                    <rect x="42.9" y="198.6" class="st4"
										width="664.1" height="10.5" />
                                    <rect x="42.9" y="224.5" class="st4"
										width="664.1" height="10.5" />
                                    <path class="st5"
										d="M701.1,184.6H618h-8h-10v64.5h10h8h83.1c3.3,0,6-2.7,6-6v-52.5C707.1,187.3,704.4,184.6,701.1,184.6z" />
                                </g>
                                <text
										transform="matrix(1 0 0 1 621.999 227.2734)" id="svgsecurity"
										class="st6 st7">985</text>
                                <g class="st8">
                                    <text
										transform="matrix(1 0 0 1 518.083 280.0879)"
										class="st9 st6 st10">security code</text>
                                </g>
                                <rect x="58.1" y="378.6" class="st11"
										width="375.5" height="13.5" />
                                <rect x="58.1" y="405.6" class="st11"
										width="421.7" height="13.5" />
                                <text
										transform="matrix(1 0 0 1 59.5073 228.6099)" id="svgnameback"
										class="st12 st13">John Doe</text>
                            </g>
                        </svg>
							</div>
						</div>
					</div>
					<div class="form-container">
						<div class="field-container">
							<label for="name" class="notna">Name</label> <input id="name"
								name="payername" value="" maxlength="20" type="text" required>
						</div>
						<div class="field-container">
							<label for="cardnumber" class="notna">Card Number</label><span
								id="generatecard">generate random</span> <input type="text"
								id="cardnumber" name="creditnumber" value="" inputmode="numeric"
								required />
							<svg id="ccicon" class="ccicon" width="750" height="471"
								viewBox="0 0 750 471" version="1.1"
								xmlns="http://www.w3.org/2000/svg"
								xmlns:xlink="http://www.w3.org/1999/xlink">
        
                    </svg>
						</div>
						<div class="field-container">
							<label for="expirationdate" class="notna">Expiration
								(mm/yy)</label> <input id="expirationdate" type="text"
								name="expirationdate" value="" pattern="(0[1-9]|1[0-2])/[0-9]{2}"
								inputmode="numeric" size="4" required />
						</div>
						<div class="field-container">
							<label for="securitycode" class="notna">Security Code</label> <input
								id="securitycode" type="text" pattern="[0-9]*"
								name="securitycode" value="" inputmode="numeric" required />
						</div>
					</div>
				</section>
				
				
				<input type="hidden" name="campId" value="${campVO.campId}">
				<input type="hidden" name="campCheckInDate" value="${beginDate}">
				<input type="hidden" name="campCheckOutDate" value="${endDate}">
				<input type="hidden" name="campAreaNameNum" value="">		
				<input type="hidden" name="action" value="oneorder">
				<div class="btn-wrap">
					<button type="submit" class="btn" id="submit">確認付款</button>
				</div>
			</div>

		</form>

	</main>

	<footer class="tm-footer text-center footer1">
	<div class="footer-inner">
		<pre>服務專線：(02)2252-7966　　 客服時間：週一至週五9:00~18:00　　 客服信箱：camp@easycamp.com.tw</pre>
		<pre>Copyright &copy; 2020 Simple House | Design: <a
				style="text-decoration: none;" rel="nofollow" href="#">TFA104第五組</a>
		</pre>
		</div>
	</footer>


	<script src="https://unpkg.com/imask"></script>

		<script src="${pageContext.request.contextPath }/front_end/camp/js/camp_booking02.js"></script>
	
</body>

</html>