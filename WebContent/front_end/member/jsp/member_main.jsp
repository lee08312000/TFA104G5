<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.member.model.*"%>

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
	href="<%=request.getContextPath()%>/front_end/member/css/member_main.css"
	rel="stylesheet" type="text/css">
<title>會員首頁</title>
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
				<span><input class="fas fa-sign-out-alt logout_button" type="submit" value="登出" /></span>
				</a>
				<input type="hidden" value="logout" name="action" />
				</li>
				
		</div>
	</aside>
	</form>
	<%-- =================  sidebar   ===================== --%>
	
	<%-- =================  會員資訊   ===================== --%>
	<FORM METHOD="post" ACTION="/TFA104G5/member/MemberServlet" enctype="multipart/form-data">
	<div class="main_content">
		<div id="fileDisplayArea1">
		<img class="member_pic"
			src="<%=request.getContextPath()%>/member/PicServlet?memberId=${ memberVO.memberId }">
		</div>
		<input class="button" name="member_pic_upload" id="pic_upload" type="file" /> 
		<input class="button" type="submit" value="送出頭貼"/>
		<input type="hidden" name="action" value="pic_upload"/>
		<a class="welcome">歡迎&nbsp; ${ memberVO.memberName }&nbsp;登入</a>
	</div>
	</form>


	<div class="table-title"></div>
	<table class="table-fill">
		<thead>
			<tr>
				<th class="text-left">姓名</th>
				<th class="text-left">會員帳號</th>
				<th class="text-left">地址</th>
				<th class="text-left">Email</th>
				<th class="text-left">手機號碼</th>
			</tr>
		</thead>
		<tbody class="table-hover">
			<tr>
				<td class="text-left">${ memberVO.memberName }</td>
				<td class="text-left">${ memberVO.memberAccount }</td>
				<td class="text-left">${ memberVO.memberAddress }</td>
				<td class="text-left">${ memberVO.memberEmail }</td>
				<td class="text-left">${ memberVO.memberPhone }</td>
			</tr>

		</tbody>
	</table>
	<%-- =================  會員資訊   ===================== --%>

	<%-- =================  footer  ===================== --%>
	<footer class="tm-footer text-center">
		<pre>服務專線：(02)2252-7966　　 客服時間：週一至週五9:00~18:00　　 客服信箱：camp@easycamp.com.tw</pre>
		<pre>Copyright &copy; 2021 Camping Paradise | Design: <a
				style="text-decoration: none;" rel="nofollow" href="#">TFA104第五組</a>
		</pre>
	</footer>
	<%-- =================  footer  ===================== --%>
	
	<%-- =================  sidebar javascript  ===================== --%>
	<script
		src="<%=request.getContextPath()%>/front_end/member/vendor/jQuery/jquery-3.6.0.min.js"></script>
	<script>
		$("#leftside-navigation .sub-menu > a").click(
				function(e) {
					$("#leftside-navigation ul ul").slideUp(), $(this).next()
							.is(":visible")
							|| $(this).next().slideDown(), e.stopPropagation()
				})
		
		var fileInput1 = document.getElementById('pic_upload');
        var fileDisplayArea1 = document.getElementById('fileDisplayArea1');
        
        fileInput1.addEventListener('change', function(e) {
            var file = fileInput1.files[0];
            var imageType = /image.*/;

            if (file.type.match(imageType)) {
                var reader = new FileReader();

                reader.onload = function(e) {
                    fileDisplayArea1.innerHTML = "";

                    var img = new Image();
                    img.src = reader.result;
                    fileDisplayArea1.appendChild(img);
                    $("#fileDisplayArea1 img").addClass("member_pic");
                    
                }

                reader.readAsDataURL(file);	
            } else {
                fileDisplayArea1.innerHTML = "File not supported!"
            }
        });
        
	</script>
	<%-- =================  sidebar javascript  ===================== --%>
	
</body>