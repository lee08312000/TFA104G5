<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.campOrder.model.*"%>
<!DOCTYPE html>
<%@ page import="java.util.*"%>

<%
	List<CampOrderVO> list = new ArrayList<CampOrderVO>();
	if (request.getAttribute("list") != null) {
		list = (ArrayList<CampOrderVO>) request.getAttribute("list");
	}
	pageContext.setAttribute("list", list);

	Calendar startimeCalendar = Calendar.getInstance();
	startimeCalendar.add(Calendar.DATE, -90);
	pageContext.setAttribute("startime", startimeCalendar.getTime());

	Calendar endtimeCalendar = Calendar.getInstance();

	pageContext.setAttribute("endtime", endtimeCalendar.getTime());
%>

<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>campAreaShelves</title>
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"
	integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/back_end/css/selectCampComment.css?v=009">
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
					<a href="#">Home</a> <a href="#"></a>
					<c:if test ="${companyVO!=null}">
			                <li>${companyVO.getCompanyAccount()} 你好</li>
			                <li>登出</li>              
       			 </c:if>
       				<c:if test ="${companyVO==null}">
					   <a href="#">註冊</a> <a href="#">登入</a> <a href="#"> <i class="fas fa-user"></i></a>
					</c:if> 
					<button>Menu</button>
				</nav>
			 
		</div>
	</header>



	<!-- --------main區域------- -->

	<h1>營地評價查詢</h1>
	<div class="selectors" style="margin-left: 300px; margin-top: 60px">
	<form method="post"
		ACTION="<%=request.getContextPath()%>/camp/campOrder.do">
		
			<label>日期區間</label> <input type="date" id="startDate" name="startDate" value="<fmt:formatDate value='${startime}' pattern='yyyy-MM-dd'/>" />
			<input type="date" id="endDate" name="endDate"
				value="<fmt:formatDate value='${endtime}' pattern='yyyy-MM-dd'/>">
			<input type="text" placeholder="請輸入關鍵字" name="campOrderId"> <input
				type="hidden" name="action" value="SEARCHCOMMENT">
			<button type="submit">
				<i class="fa fa-search"></i>
			</button>
		
	</form>
	</div>



	<div class="pagination">
		<%@ include file="pages/page1.file"%>
	</div>

	<table class="camp_table" style="margin-left: 300px; width: 70%">
		<tbody>
			<tr>
				<th>營地訂單評論時間</th>
				<th>營地訂單流水號</th>
				<th>會員流水號</th>
				<th>營地訂單評論</th>
				<th>營地訂單評價star</th>

			</tr>
	
		<c:forEach var="camporderVO" items="${list}" begin="<%=pageIndex%>"
			end="<%=pageIndex+rowsPerPage-1%>">
            
			<tr>

				<td><fmt:formatDate value="${camporderVO.campOrderCommentTime}" pattern="yyyy-MM-dd" /></td>
				<td>${camporderVO.campOrderId}</td>
				<td>${camporderVO.memberId}</td>
				<td>${camporderVO.campComment}</td>
				<td>${camporderVO.campCommentStar}顆星</td>
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
							    <li><a  href="<%=request.getContextPath()%>/back_end/camp/campindex.jsp"  target="main"><i class="fas fa-cannabis"></i>我的營地</a></li>			
								<li><a  href="<%=request.getContextPath()%>/back_end/camp/insertCampShelves.jsp"  target="main"><i class="fas fa-cannabis"></i>營地上下架</a></li>
							    <li><a  href="<%=request.getContextPath()%>/back_end/camp/selectCampCertificatenum.jsp"  target="main"><i class="fas fa-cannabis"></i>營地審核狀況</a></li>								
							</ul>
						</li>
							
							
							
						<li><a href="" class="light"> <i class="fa fa-edit"></i>
								<strong>商品管理</strong> <small>Commodity </small>
						</a></li>
						<li><a href="" class="light"> <i class="fa fa-gift"></i>
								<strong>訂單管理</strong> <small>Order </small>
						</a>
							<ul>
							   <li><a  href="<%=request.getContextPath()%>/back_end/camp/campindex.jsp"  target="main"><i class="fas fa-cannabis"></i>日程表管理</a></li>			
							   <li><a  href="<%=request.getContextPath()%>/back_end/camp/listAllCampOrder.jsp"  target="main"><i class="fas fa-cannabis"></i>營地訂單管理</a></li>								   
							   <li><a  href="<%=request.getContextPath()%>/back_end/camp/productOrderList.html"  target="main"><i class="fas fa-cannabis"></i>商城訂單管理</a></li>
							</ul></li>
						<li><a href="" class="light"> <i
								class="fas fa-calendar-week"></i> <strong>廠商資料</strong> <small>Vendor
									Information</small>
						</a>
							<ul>
							
							     <li><a  href="<%=request.getContextPath()%>/back_end/companyProduct/jsp/companyImformation.jsp"  target="main"><i class="fas fa-cannabis"></i>基本資料瀏覽及修改</a></li>
								<li><a href="#"><i class="fas fa-cannabis"></i>更改密碼</a></li>
							</ul></li>
						<li><a href="" class="light"> <i
								class="fa fa-comment-alt"></i> <strong>我的評論</strong> <small>Comment</small>
						</a>
							<ul>
							<li><a  href="<%=request.getContextPath()%>/back_end/camp/campComment.jsp"  target="main"><i class="fas fa-cannabis"></i>營地評價</a></li>
							<li><a  href="<%=request.getContextPath()%>/back_end/companyProduct/jsp/vendorProductComment.jsp"  target="main"><i class="fas fa-cannabis"></i>商品評價</a></li>
							</ul></li>
					</ul>
				</nav>
			</div>
		</aside>
	</div>
	
	
	
	



</body>
</html>





