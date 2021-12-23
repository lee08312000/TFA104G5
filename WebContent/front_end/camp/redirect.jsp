<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>

<%
	String orderid = (String) request.getParameter("orderid");
	String tradetime = (String) request.getParameter("tradetime");

	System.out.println("orderid=" + orderid);

	System.out.println("tradetime=" + tradetime);
%>



<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>�q�榨��</title>
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"
	integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm"
	crossorigin="anonymous">
<link href="css/header_footer.css" rel="stylesheet" />
<link href="css/redirect.css" rel="stylesheet" />
 <script type="text/javascript" src="js/redirect.js"></script>

</head>

<body>
	<!-- Sticky header -->
	<header class="header-outer">
		<div class="header-inner responsive-wrapper">
			<div class="header-logo">
				<a style="display: inline-block; vertical-align: middle;"
					href="camp_index.html"> <img src="img/icon/chuba_logo.png" />
				</a> <span style="display: inline-block; vertical-align: middle;">Camping
					Paradise</span>
			</div>
			<nav class="header-navigation">
				<a href="camp_index.html">Home</a> <a href="../mall/mall_index.html">�u�W�ӫ�</a>
				<a href="#"><img src="img/icon/heart.png"></a> <a
					href="../member/login.jsp">�n�J|���U</a> <a href="#"><i
					class="fas fa-user-circle"></i></a>
				<button>Menu</button>
			</nav>
		</div>
	</header>






	<div id="master-wrap">
		<div id="logo-box" class="logo-box">

			<div class="animated fast fadeInUp">
				<div class="icon"></div>
				<h1>�P�±z���q��!!</h1>
			</div>

			<div class="notice animated fadeInUp">
				<p class="lead" id="orderid">�q��s��:</p>
				<p class="lead" id="tradetime">�q�榨�߮ɶ�:${tradetime}</p>
				<a class="btn animation" href="camp_index.html">&larr; �^����</a> <a
					class="btn animation"
					href="<%=request.getContextPath()%>/front_end/member/jsp/member_camp_order_list.jsp">
					�d�ݭq��&rarr;</a>

			</div>


			<div class="footer animated slow fadeInUp">
				<p id="timer">
					<script type="text/javascript">
						countDown();
					</script>
				</p>
				<p class="copyright">&copy; Redfrost.com</p>
			</div>

		</div>
		<!-- /#logo-box -->
	</div>
	<!-- /#master-wrap -->



	<footer class="tm-footer text-center">
		<pre>�A�ȱM�u�G(02)2252-7966�@�@ �ȪA�ɶ��G�g�@�ܶg��9:00~18:00�@�@ �ȪA�H�c�Gcamp@easycamp.com.tw</pre>
		<pre>Copyright &copy; 2020 Simple House | Design: <a
				style="text-decoration: none;" rel="nofollow" href="#">TFA104�Ĥ���</a>
		</pre>
	</footer>
<script>
var orderid = <%=orderid%>;
var tradetime = '<%=tradetime%>';
document.getElementById("orderid").innerText="�q��s�� : "+orderid;
document.getElementById("tradetime").innerText="�q�榨�߮ɶ�: "+tradetime;
</script>
</body>

</html>