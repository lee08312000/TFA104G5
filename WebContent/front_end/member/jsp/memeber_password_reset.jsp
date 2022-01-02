
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>會員忘記密碼修改</title>
</head>
<style>
* {
	box-sizing: border-box;
}

body {
	/* border: 1px solid red;  */
	margin: 0;
	font-family: "Oswald', sans-serif";
	background-color: rgb(252, 248, 248);
}

/* =================  按鈕  =====================*/
.button {
	margin: 15px 5px 0px 5px;
	border-radius: 20px;
	background-color: #c09f45;
	border: none;
	color: white;
	padding: 8px 16px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
	/* margin: 4px 2px; */
	transition-duration: 0.4s;
	cursor: pointer;
}

.button:hover {
	background-color: #5f5e33;
}
/* =================  按鈕  =====================*/

.control-label {
	margin: 25px 0px 0px 0px;
}

img {
	max-width: 100%;
}

html {
	/* font-size: 62.5%; */ -
	-header-height: 80px; -
	-aside-width: 240px;
}

/* =================  header區域   =====================*/
header.header {
	width: 100%;
	height: var(- -header-height);
	background-color: #d7ab75;
	/* position: fixed;
        top: 0;
        left: 0; */
	position: sticky;
	top: 0;
	display: flex;
	justify-content: space-between;
	color: #FFFFFF;
	font-weight: bold;
}

.header-inner {
	/* Make it stick */
	height: var(- -header-inner-height);
	position: sticky;
	top: 1;
	/* Other */
	display: flex;
	align-items: center;
	justify-content: space-between;
	padding-left: 60px;
}

/* Styling of other elements */
.header-logo img {
	display: block;
	/* height: calc(var(--header-inner-height) - 30px); */
	/* height: 100px; */
	width: 95px;
	/* text-align: center; */
}

.header-logo {
	display: block;
	font-size: 32px;
}

header ul {
	margin-right: 40px;
}

.header-navigation li {
	font-size: 1.125rem;
	margin-right: 1.75rem;
	position: relative;
	text-decoration: none;
	vertical-align: middle;
	display: inline-block;
	margin-top: 5px;
}

.header-navigation li>a {
	text-decoration: none;
	color: #FFFFFF;
	font-weight: bold;
}

.header-navigation li:hover:after {
	transform: scalex(1);
}

.header-navigation li:after {
	transition: 0.25s ease;
	content: "";
	display: block;
	width: 100%;
	height: 2px;
	background-color: currentcolor;
	transform: scalex(0);
	position: absolute;
	bottom: -2px;
	left: 0;
}
/* =================  header區域   =====================*/

/* =================  登入區塊   =====================*/
.updateform {
	margin: 0 auto 100px;
	padding: 45px;
	margin-top: 50px;
	max-width: 480px;
	min-height: calc(65vh - var(- -header-height));
	padding: 20px;
	/* position: relative; */
	/* z-index: 1; */
	background: #FFFFFF;
	text-align: center;
	box-shadow: 0 0 20px 0 rgba(0, 0, 0, 0.2), 0 5px 5px 0
		rgba(0, 0, 0, 0.24);
}

/* .form-horizontal {
        padding-left: 40px;
    } */
.updateform label {
	color: #f0652f;
}

.form-control {
	border-color: #e97445;
	height: 28px;
	width: 300px;
}

.textarea {
	border-color: #e97445;
	width: 500px;
	height: 200px;
	resize: none;
}
/* =================  登入區塊   =====================*/

/* =================  footer  =====================*/
footer {
  position: absolute;
  text-align: center;
  bottom: 0;
  margin: 0 auto;
  left: 50%;
  transform: translateX(-50%);
  /* border: 1px black solid; */
  width: 100%;
  background-color: #dbb07c;
  /* color: #FFFFFF; */
  font-size: 16px;
  font-weight: bold;
  /* margin-top: 100px !important; */
}

pre {
  display: block;
  font-family: monospace;
  white-space: pre;
  margin: 1em 0px !important;
  font-size: 16px;
}
/* =================  footer  =====================*/

</style>

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
	
	<%-- =================  會員密碼修改   ===================== --%>
	<div class="updateform">
		<h2>會員密碼修改</h2>
		<form class="form-horizontal" method="post"
			action="<%=request.getContextPath()%>/member/MemberServlet">
			<div class="form-group">
				<!-- 新密碼 -->
				<div class="control-label">
					<input type="text" class="form-control" value="" name="password"
						placeholder="請輸入新密碼(英文大小寫有差別)" />
				</div>
			</div>
			<div class="form-group">
				<!-- 確認新密碼 -->
				<div class="control-label">
					<input type="text" class="form-control" value="" name="check"
						placeholder="請再次輸入新密碼(英文大小寫有差別)" />
				</div>
			</div>
			<div>
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
			<input type="hidden" value="reset_password" name="action" /> <input
				class="button" type="submit" value="確認送出" />

		</form>
	</div>
	<%-- =================  會員密碼修改   ===================== --%>
	
	<%-- =================  footer   ===================== --%>
	<footer class="tm-footer text-center">
		<pre>服務專線：(02)2252-7966　　 客服時間：週一至週五9:00~18:00　　 客服信箱：camp@easycamp.com.tw</pre>
		<pre>Copyright &copy; 2021 Camping Paradise | Design: <a
				style="text-decoration: none;" rel="nofollow" href="#">TFA104第五組</a>
		</pre>
	</footer>
	<%-- =================  footer   ===================== --%>
	
</body>