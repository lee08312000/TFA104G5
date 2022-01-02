<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.campArea.model.*"%>
<!DOCTYPE html>
<%@ page import="java.util.*"%>

<%
	List<CampAreaVO> list = new ArrayList<CampAreaVO>();
	if (request.getAttribute("list") != null) {
		list = (ArrayList<CampAreaVO>) request.getAttribute("list");
	}
	pageContext.setAttribute("list", list);
%>

<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script type="text/javascript">
  function deleteCheck() {
	  var msg = "您真的確定要刪除嗎？\n\n請確認！";
	  if (confirm(msg)==true){
	  return true;
	  }else{
	  return false;
	  }
  }
</script>
<title>campAreaShelves</title>
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"
	integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/back_end/css/campArea.css">
</head>
<body>
<!-- --------head區域------- -->
	<header class="header-outer">
		<div class="header-inner responsive-wrapper">
			<div class="header-logo">
				<a style="display: inline-block; vertical-align: middle;"
					href="首頁URL"> <img
					src="<%=request.getContextPath()%>/back_end/images/camp_paradise_logo.png" />
				</a> <span style="display: inline-block; vertical-align: middle;">Camping
					Paradise</span>
			</div>
			<nav class="header-navigation">
				<a href="#">Home</a> <a href="#">線上商城</a> <a href="#"><img
					src="<%=request.getContextPath()%>/back_end/images/heart.png"></a>
				<a href="#">註冊</a> <a href="#">登入</a> <a href="#"> <i
					class="fas fa-user"></i></a>
				<button>Menu</button>
			</nav>
		</div>
	</header>
	
	<!-- --------main區域------- -->
	
	<h1 style="margin-right:45px">營位查詢列表 ${errorMsgs}</h1>
	<div class="home">		 
	<form method="post" ACTION="<%=request.getContextPath()%>/camp/shelves.do">
			 
		     <input type="hidden" name="campstatus" value="3">
		     <input type="hidden" name="campIdsearch" value="">
			 <input type="hidden" name="action" value="SEARCHALL">
			<button type="submit">
				<i class="fas fa-home"></i>
			</button>
	
	
	</form>
   
	<form method="post" ACTION="<%=request.getContextPath()%>/camp/campareashelves.do" >
		 <input type="hidden" name="action" value="SEARCHALL">
		 <input type="hidden" name="campId" value="${campId}">
		<button type="submit"><i class="fa fa-search"></i></button>	
				
	</form>	
		</div>		
	
	 <form method="post" ACTION="<%=request.getContextPath()%>/camp/campareashelves.do">
		<div >		 
			 <input type="hidden" name="action" value="INSERTPAGE">
			  <input type="hidden" name="campId" value="${campId}">		 
			<button type="submit" style=" background-color:#4CAF50;font-size:16px;color:white;border:1px solid #4CAF50;margin-top:20px;margin-left:350px">新增營位</button>
		</div>
	
	</form>
	
	
	
	
	

	<div class="pagination" >
		<%@ include file="pages/page1.file" %>
	</div>
	<table class="camp_table">
		<tbody>
			<tr>
				<th>營位流水號</th>
				<th>營位名稱</th>
				<th>平日單價</th>
				<th>假日單價</th>
				<th>每帳加購<br>人頭上限</th>
				<th>加購<br>人頭價格</th>
				<th>帳數上限</th>
				<th>營位美照</th>
				
				<th colspan="2">編輯</th>

			</tr>
		</thead>


		<tbody>
			<c:forEach var="campareaVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">

				<tr>
					<td>${campareaVO.campAreaId}</td>
					<td>${campareaVO.campAreaName}</td>
					<td>${campareaVO.weekdayPrice}</td>  
					<td>${campareaVO.holidayPrice}</td>
					<td>${campareaVO.capitationMax}</td>
					<td>${campareaVO.perCapitationFee}</td>
					<td>${campareaVO.campAreaMax}</td>
					
					<td><img style="width: 30%;" src="<%=request.getContextPath()%>/PicWithCampServlet?campid=${campareaVO.campId}&areaindex=1" /></td>
					<td>
						<form method="post" ACTION="<%=request.getContextPath()%>/camp/campareashelves.do">
						<input type="hidden" name="campAreaId"  value="${campareaVO.campAreaId}" class="update" />
						<input type="hidden" name="action"  value="UPDATEFINDBYKEY" class="update" />
						<input  style="background-color:#008CBA;font-size:14px;color:white;border:1px solid #008CBA"  type="submit" value="修改"  />
						</form>
					</td>
					<td>
						<form method="post" ACTION="<%=request.getContextPath()%>/camp/campareashelves.do">
						<input type="hidden" name="campAreaId"  value="${campareaVO.campAreaId}" class="delete"/>
						<input type="hidden" name="action"  value="DELETE" class="delete" />
						<input type="hidden" name="camp_Id"  value="${campareaVO.campId}" class="delete"/>
						<input style="background-color:#FF0000;font-size:14px;color:white;border:1px solid #FF0000" type="submit" onclick="deleteCheck()" value="刪除"  />
						</form>
					</td>
				</tr>
			</c:forEach>


		</tbody>

	</table>

	<div class="pagination">
		<%@ include file="pages/page2.file"%>
	</div>
	
	<!-- --------aside區域------- -->
	<div id="sidebar">
		<aside class="aside">
			<div class="container">
				<nav>
					<ul class="mcd-menu">
						<li><a href="" class="light"> <i class="fa fa-campground"></i>
								<strong>營地管理</strong> <small>Camp Management</small>
						</a>
							<ul>
								<li><a href="#"><i class="fas fa-cannabis"></i>我的營地</a></li>
								<li><a href="#"><i class="fas fa-cannabis"></i>營地上下架</a></li>
								<li><a href="#"><i class="fas fa-cannabis"></i>審核狀況</a></li>
							</ul></li>
						<li><a href="" class="light"> <i class="fa fa-edit"></i>
								<strong>商品管理</strong> <small>Commodity </small>
						</a></li>
						<li><a href="" class="light"> <i class="fa fa-gift"></i>
								<strong>訂單管理</strong> <small>Order </small>
						</a>
							<ul>
								<li><a href="#"><i class="fas fa-cannabis"></i>日程表管理</a></li>
								<li><a href="#"><i class="fas fa-cannabis"></i>營地訂單管理</a></li>
								<li><a href="#"><i class="fas fa-cannabis"></i>商城訂單管理</a></li>
							</ul></li>
						<li><a href="" class="light"> <i
								class="fas fa-calendar-week"></i> <strong>廠商資料</strong> <small>Vendor
									Information</small>
						</a>
							<ul>
								<li><a href="#"><i class="fas fa-cannabis"></i>基本資料瀏覽,修改</a></li>
								<li><a href="#"><i class="fas fa-cannabis"></i>更改密碼</a></li>
							</ul></li>
						<li><a href="" class="light"> <i
								class="fa fa-comment-alt"></i> <strong>我的評論</strong> <small>Comment</small>
						</a>
							<ul>
								<li><a href="#"><i class="fas fa-cannabis"></i>營地評價</a></li>
								<li><a href="#"><i class="fas fa-cannabis"></i>商品評價</a></li>
							</ul></li>
					</ul>
				</nav>
			</div>
		</aside>
	</div>

</body>
</html>

















