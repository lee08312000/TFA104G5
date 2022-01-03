<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.campArea.model.*"%>
<%@ page import="com.campTagDetail.model.*"%>
<!DOCTYPE html>
<%@ page import="java.util.*"%>

<%
	List<CampTagDetailVO> campTagDetailList = new ArrayList<CampTagDetailVO>();
	if (request.getAttribute("campTagDetailList") != null) {
		campTagDetailList = (List<CampTagDetailVO>) request.getAttribute("campTagDetailList");
	}
	pageContext.setAttribute("campTagDetailList", campTagDetailList);
	
	Object campayId = session.getAttribute("vendorId"); 
	if(campayId != null){
		pageContext.setAttribute("companyId", (int)campayId);
	}
	
%>

<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="<%=request.getContextPath()%>/back_end/camp/js/addCamp.js"></script>
<title>campshelves</title>
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"
	integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/back_end/css/insertcamp.css">
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

	<h1>營地上架 ${errorMsgs}</h1>
	<form method="post"
		ACTION="<%=request.getContextPath()%>/camp/shelves.do" enctype="multipart/form-data">
		<table class="camp_table">
			<tr>
				<td><label>選擇上架日期:</label></td>
				<td><input type="date" id="calendar" name="calendar"></td>
			</tr>
			<tr>
				<td><label>營地標籤:</label></td>

				<td><c:forEach var="campDetailVO" items="${campTagDetailList}"
						begin="<%=0%>" end="<%=campTagDetailList.size()-1%>">
						<input type="checkbox" id="campTag" name="campTag"
							value="${campDetailVO.campTagId}"
							${checkedIntList.contains(campDetailVO.campTagId)?"checked":""}>
						<label for="campTag"> ${campDetailVO.campTagName}</label>
					</c:forEach></td>
			</tr>

			<tr>
				<td><label>營地名稱:</label></td>
				<td><input type="text" id="campany_id" name="campany_id" value="${companyId}">
					<input type="text" id="camp_name" name="camp_name" value="">
				</td>

			</tr>



			<tr>
				<td><label>營地電話:</label></td>
				<td><input type="text" id="camp_phone" name="camp_phone"></td>
			</tr>

			<tr>
				<td><label>經度:</label></td>
				<td><input type="text" id="longitude" name="longitude"></td>
			</tr>

			<tr>
				<td><label>緯度:</label></td>
				<td><input type="text" id="lattitude" name="lattitude"></td>
			</tr>


			<tr>
				<td><label>營地地址:</label></td>
				<td><input type="text" id="camp_address" name="campAddress"></td>
			</tr>


			<tr>
				<td><label> 營地敘述:</label></td>
				<td><textarea name="campDiscription" cols="50" rows="10"> </textarea></td>
			</tr>

			<tr>
				<td><label>營地租借規則:</label></td>
				<td><textarea name="campRule" cols="50" rows="10"> </textarea></td>

			</tr>


			<tr>
					<td><label for="fname">營地美照1:</label></td>
					<td><input type="file" name="camp_pic1" id="fileInput1" value=""/> 
					<div id="fileDisplayArea1">
					<img style="width: 30%;" src="<%=request.getContextPath()%>/PicWithCampServlet?campid=${campVO.campId}&pic=1" />
					</div>
					</td>

				</tr>
				<tr>
					<td><label for="fname">營地美照2:</label></td>
					<td><input type="file" name="camp_pic2" id="fileInput2" value=""/> 
					<div id="fileDisplayArea2">
					<img style="width: 30%;" src="<%=request.getContextPath()%>/PicWithCampServlet?campid=${campVO.campId}&pic=2" />
					</div>
					</td>
					

				</tr>
				<tr>
					<td><label for="fname">營地美照3:</label></td>
					<td><input type="file" name="camp_pic3" id="fileInput3" value=""/> 
					<div id="fileDisplayArea3">
					<img style="width: 30%;" src="<%=request.getContextPath()%>/PicWithCampServlet?campid=${campVO.campId}&pic=3" />
					</div>
					</td>

				</tr>
				<tr>
					<td><label for="fname">營地美照4:</label></td>
					<td><input type="file" name="camp_pic4" id="fileInput4" value=""/> 
					<div id="fileDisplayArea4">
					<img style="width: 30%;" src="<%=request.getContextPath()%>/PicWithCampServlet?campid=${campVO.campId}&pic=4" />
					</div>
					</td>

				</tr>
				<tr>
					<td><label for="fname">營地美照5:</label></td>
					<td><input type="file" name="camp_pic5" id="fileInput5" value=""/> 
					<div id="fileDisplayArea5">
					<img style="width: 30%;" src="<%=request.getContextPath()%>/PicWithCampServlet?campid=${campVO.campId}&pic=5" />
					</div>
					</td>

				</tr>
						
			<tr>
				<td><label for="fname">營地狀態:</label></td>


				<td>
					<div>
						<input type="radio" name="camp_status" id="option1" value="1"
							checked> <label for="option1">上架</label> <input
							type="radio" name="camp_status" id="option2" value="2"> <label
							for="option2">下架</label>
					</div>
				</td>
			</tr>
			<tr>

				<td colspan="2"><input type="hidden" name="action"
					value="INSERT" /> <input type="submit" value="確認新增"
					style="margin-left: 350px;"> <input type="submit"
					value="取消"></td>
			</tr>

		</table>
	</form>
	
	
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

















