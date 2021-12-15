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
<title>�q��e��</title>
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
					href="����URL"> <img src="#" />
				</a> <span style="display: inline-block; vertical-align: middle;">Camping
					Paradise</span>
			</div>
			<nav class="header-navigation">
				<a href="#">Home</a> <a href="#">�u�W�ӫ�</a> <a href="#"><img
					src="#"></a> <a href="#">���U</a> <a href="#">�n�J</a> <a href="#"><i
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
					<em>1</em>��ܤ��&�Ѽ�
				</div>
				<div class="triangle-incomplete"></div>

				<div class="triangle2-active"></div>
				<div class="step active">
					<em>2</em>������ƶq
				</div>
				<div class="triangle-active"></div>


				<div class="triangle2-incomplete"></div>
				<div class="step incomplete">
					<em>3</em>��ܥI�ڤ覡
				</div>
				<div class="triangle-incomplete"></div>


				<div class="triangle2-incomplete"></div>
				<div class="step incomplete">
					<em>4</em>���b
				</div>
				<div class="triangle-incomplete"></div>
			</section>


			<h2 style="text-align: left;">���w�q</h2>



			<hr>
			<div class="outer_block freeinfo">
				<div class="left_block" id="begin">
					�J����:${date} <br>
					<button type="button" class="btn btn-accept" id="btn-accept">
						������</button>
				</div>
				<div class="right_block" id="daynum">
					�S��Ѽ�: ${days} <br>
					<button type="button" class="btn btn-accept" id="btn-accept">
						����Ѽ�</button>
				</div>
			</div>

			<label for="weekday">���� :</label> <input type="text" id="weekday"
				value="0" disabled>�� <label for="holiday">���� :</label> <input
				type="text" id="holikday" value="0" disabled>��


			<%-- ���~��C --%>
			<c:if test="${not empty errorMsgs}">
				<font style="color: red">�Эץ��H�U���~:</font>
				<ul>
					<c:forEach var="message" items="${errorMsgs}">
						<li style="color: red">${message}</li>
					</c:forEach>
				</ul>
			</c:if>
			<h1>��a�W��:${campVO.campName}</h1>
			<table class="freeinfo">
				<thead>
					<tr style="text-align: center;">
						<td>�Ϥ�</td>
						<td>��Ϥ���</td>
						<td>����/�C�b����</td>
						<td>�b��</td>
						<td>�����/�C�b����</td>
						<td>�b��</td>
						<td>�[�ʤH�Y</td>
						<td>�H�Y�ƶq</td>
						<td>�`�p</td>
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
								<button type="button">���s���</button>
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
			  <button type="button" class="checkout" onclick="location.href = '<%=request.getContextPath()%>/front_end/camp/camp_calendar';"><span> &larr;</span>�W�@��</button>
            <div id="confirm" class="checkout">�ߧY���b<span> &rarr;</span></div>

		</div>
	</main>


	<footer class="tm-footer text-center">
		<pre>�A�ȱM�u�G(02)2252-7966�@�@ �ȪA�ɶ��G�g�@�ܶg��9:00~18:00�@�@ �ȪA�H�c�Gcamp@easycamp.com.tw</pre>
		<pre>Copyright &copy; 2020 Simple House | Design: <a
				style="text-decoration: none;" rel="nofollow" href="#">TFA104�Ĥ���</a>
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
				<span class="windowtitle">�T�{�q��H��</span> <span class="x-btn close">X</span>
			</div>

			<div id="outerbox">
				<ul class="innerbox innerbox2" id="thead">
					<li>��Ϥ���</li>
					<li>����/�C�b����</li>
					<li>�b��</li>
					<li>�����/�C�b����</li>
					<li>�b��</li>
					<li>�[�ʤH�Y</li>
					<li>�H�Y�ƶq</li>
					<li>�`�p</li>
				</ul>
				<ul class="innerbox" id="thead">
					<li></li>
					<li></li>
					<li></li>
					<li></li>
					<li></li>
					<li></li>
					<li>�`�p:</li>
					<li id="total">0</li>
				</ul>
			</div>
			<button type="submit" class="orderbtn" id="orderbtn">�T�{�e�X</button>
		</div>
	</form>







	<script type="text/javascript"
		src="front_end/camp/js/camp_booking01.js"></script>


</body>
</html>