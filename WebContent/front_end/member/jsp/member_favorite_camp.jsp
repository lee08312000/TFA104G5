<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <!-- jstl核心函式庫含for each標籤等 -->
<%@ page import="com.member.model.*"%>

<%@ page import="com.camp.model.*"%>
<%@ page import="com.favoriteCamp.model.*"%>
<%@ page import="com.campTag.model.*"%>
<%@ page import="com.campTagDetail.model.*"%>
<%@ page import="com.campOrder.model.*"%>
<%@ page import="java.util.*"%> <!-- list用 -->

<%
	//得到session中memberVO的資料
	MemberVO memberVO =  (MemberVO)session.getAttribute("memberVO");
	FavoriteCampDAO favoriteCampDAO = new FavoriteCampDAOlmpl();
	List<FavoriteCampVO> list = favoriteCampDAO.getAllByMemberId(memberVO.getMemberId());
	pageContext.setAttribute("list", list);

	//我的最愛假資料
	// FavoriteCampService favoriteCampSvc = new FavoriteCampService();
	// FavoriteCampVO favoriteCampVO = favoriteCampSvc.getFavoritecamp(1);
	// List<FavoriteCampVO> camplist = favoriteCampSvc.favoriteCampList(memberVO.getMemberId(), camplist);
	// session.setAttribute("camplist", camplist);
	
	// 營地假資料
	// CampService campSvc = new CampService();
	// CampVO campVO = campSvc.findCampByCampId(favoriteCampVO.getCampId());
	// List<CampVO> camplist = campSvc.getAll();
	// request.setAttribute("CampVO", campVO);
	// request.setAttribute("Camplist", camplist);

	// 營地假資料
	// CampOrderService campOrderSvc = new CampOrderService();
	// CampOrderVO campOrderVO = campOrderSvc.findByCampOrderId(1);
	// request.setAttribute("memberVO", memberVO);
	// CampTagDetailService campTagDetailSvc = new CampTagDetailService();
	// List<String> list = campTagDetailSvc.findCampTagsByCampId(1);
	// pageContext.setAttribute("list", list);
%>

	<jsp:useBean id="campSvc" class="com.camp.model.CampService"></jsp:useBean> 
	<jsp:useBean id="campTagSvc" class="com.campTag.model.CampTagService"></jsp:useBean>
	<jsp:useBean id="campOrderSvc" class="com.campOrder.model.CampOrderService"></jsp:useBean>
	<jsp:useBean id="campOrderDAO" class="com.campOrder.model.CampOrderDAOImpl"></jsp:useBean>
	<jsp:useBean id="campTagDetailSvc" class="com.campTagDetail.model.CampTagDetailService"></jsp:useBean>

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
	href="<%=request.getContextPath()%>/front_end/member/css/member_order.css"
	rel="stylesheet" type="text/css">
<title>我的最愛營地</title>
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
	<aside class="sidebar">
		<form class="form-horizontal" method="post"
			action="<%=request.getContextPath()%>/member/MemberServlet">
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
				<span><input class="fas fa-sign-out-alt logout_button" type="submit" value="&nbsp;登出" /></span>
				</a>
				<input type="hidden" value="logout" name="action" />
				</li>
				
		</div>
		</form>
	</aside>
	</form>
	<%-- =================  sidebar   ===================== --%>
	
	<%-- =================  我的最愛營地表格   ===================== --%>
	<div class="table-title">
		<h3>我的最愛營地</h3>
	</div>
	
	<table class="table-fill">
		<thead>
			<tr>
				<th class="text-left">營地圖片</th>
				<th class="text-left">營地名稱</th>
				<th class="text-left">相關標籤</th> <!-- 這邊可能要join，還有加搜尋標籤 -->
				<th class="text-left">地址</th>
				<th class="text-left">評價</th>
				<th class="text-left"></th>
			</tr>
		</thead>
		
		<tbody class="table-hover">
			<div class="table-fill"><%@ include file="page1.file" %></div>
			<c:forEach var="favoriteCampVO" items="${ list }" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
			
				<%
				Integer campId = ((FavoriteCampVO)pageContext.getAttribute("favoriteCampVO")).getCampId(); 
				List<CampOrderVO> campOrderVOList = campOrderDAO.getAll(0);
				List<CampOrderVO> newCampOrderVOList = new ArrayList<CampOrderVO>();
				int countStar = 0;
				for (CampOrderVO campOrderVO : campOrderVOList) {
					if (campId.intValue() == campOrderVO.getCampId().intValue() && campOrderVO.getCampCommentStar().intValue() != 0) {
						newCampOrderVOList.add(campOrderVO);
						countStar += campOrderVO.getCampCommentStar();
					}
				}
				int finalCountStar = 0;
				if (newCampOrderVOList.size() != 0) {
					
				finalCountStar = Math.round((float)countStar / (float)newCampOrderVOList.size());
				}
				
				pageContext.setAttribute("finalCountStar", finalCountStar);
				
				
				
				
				%>
				<tr>
					<td class="text-center"><img class="product_pic" src="<%=request.getContextPath()%>/PicWithCampServlet?campid=${ favoriteCampVO.campId }&pic=1"
						alt="營地圖片"></td>
					<td class="text-left"><a href="<%=request.getContextPath()%>/front_end/camp/camp_detail.html?campId=${ favoriteCampVO.campId }">${ campSvc.getOneCamp(favoriteCampVO.campId).campName }</a></td> 
					<td class="text-center">
					<c:forEach var="campTagName" items="${ campTagDetailSvc.findCampTagsByCampIdNames(favoriteCampVO.campId) }">
					<a href="#">${ campTagName }</a>
					</c:forEach>
					</td> <!-- ${ campTagDetail.營地標籤中文方法列出  } 或 ${ campTagVO.campTagName } (?) 如何點了進入相同類型營地列表?健泯的網站? 或者直接列就好不要按鈕? -->
					<td class="text-left">${ campSvc.getOneCamp(favoriteCampVO.campId).campAddress }</td> <!-- ${ campVO.campAddress } -->
					<td class="text-left">${ finalCountStar } / 5</td> <!-- ${ campOrderVO.campCommentStar } -->
					<td class="text-center">
						<form method="post"
						action="<%=request.getContextPath()%>/favoriteCamp/FavoriteCampServlet">
							<input class="button" type="submit" value="刪除" />
							<input type="hidden" value="delete" name="action" /> 
							<input type="hidden" value="${ favoriteCampVO.favoriteCampId }" name="favoriteCampId" />
						</form> 
					</td>
				</tr>
			</c:forEach>			
		</tbody>
	</table>

	<div class="table-fill"><%@ include file="page2.file" %></div>
	<%-- =================  我的最愛營地表格   ===================== --%>
	
	<%-- =================  sidebar javascript   ===================== --%>
	<script
		src="<%=request.getContextPath()%>/front_end/member/vendor/jQuery/jquery-3.6.0.min.js"></script>
	<script>
		$("#leftside-navigation .sub-menu > a").click(
				function(e) {
					$("#leftside-navigation ul ul").slideUp(), $(this).next()
							.is(":visible")
							|| $(this).next().slideDown(), e.stopPropagation()
				})
	</script>
	<%-- =================  sidebar javascript   ===================== --%>
	
</body>