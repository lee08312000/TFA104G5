

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

<title>campAreaShelves</title>
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"
	integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/back_end/css/campCetificatenum.css?v=001">
</head>
<body>
<!-- --------head�ϰ�------- -->
	<header class="header-outer">
		<div class="header-inner responsive-wrapper">
			<div class="header-logo">
				<a style="display: inline-block; vertical-align: middle;"
					href="����URL"> <img
					src="<%=request.getContextPath()%>/back_end/images/camp_paradise_logo.png" />
				</a> <span style="display: inline-block; vertical-align: middle;">Camping
					Paradise</span>
			</div>
		
				<nav class="header-navigation">
					<a href="#">Home</a> <a href="#"></a>
					<c:if test ="${companyVO!=null}">
			                <li>${companyVO.getCompanyAccount()} �A�n</li>
			                <li>�n�X</li>              
       			 </c:if>
       				<c:if test ="${companyVO==null}">
					   <a href="#">���U</a> <a href="#">�n�J</a> <a href="#"> <i class="fas fa-user"></i></a>
					</c:if> 
					<button>Menu</button>
				</nav>
			 
		</div>
	</header>



	<!-- --------main�ϰ�------- -->
	<h1  style="margin-left:200px;margin-top:15px">��a�W�[�f�֬d�ߦC�� </h1>
    <h2>${errorMsgs}</h2>
<form method="post" ACTION="<%=request.getContextPath()%>/camp/shelves.do"  >
	<div class="selector">
		 <label>����϶�</label>
          <input type="date" id="startDate" name="startDate" value="<fmt:formatDate value='${startime}' pattern='yyyy-MM-dd'/>"/>
		  <input type="date" id="endDate"name="endDate" value="<fmt:formatDate value='${endtime}' pattern='yyyy-MM-dd'/>">
		 <input type="text" placeholder="�п�J�t�ӦW��" name="companyName" value=""> 
		 <input type="hidden" name="action"value="SEARCHCAMPANY">
		<button type="submit">
			<i class="fa fa-search"></i>
		</button>
	</div>
</form>

	<div class="pagination">
		<%@ include file="pages/page1.file" %>
	</div>
	<table class="camp_table">
		<tbody>
			<tr> 
			
				<th>�t�Ӭy����</th>
                <th>�t�ӦW��</th>
                <th>�t�d�H�m�W</th>
                <th>�t�ӹq��</th>
                <th>��a�W��</th>
                <th>�t�Ӧa�}</th>
                <th>�{�Ҧr��</th>                           
                <th>�ҮѹϤ�</th>
               

			</tr>
		</thead>


		<tbody>
			<c:forEach var="campVO" items="${list}" begin="<%=pageIndex%>"
				end="<%=pageIndex+rowsPerPage-1%>">

				<tr>
				 
					<td>${campVO.companyId}</td>
					<td>${campVO.companyName}</td>
					<td>${campVO.headName}</td>
					<td>${campVO.companyTel}</td>
					<td>${campVO.campName}</td>
					<td>${campVO.companyAddress}</td>
					<td>${campVO.certificateNum}</td>
					<td><img style="width: 30%;" src="<%=request.getContextPath()%>/PicWithCampServlet?campid=${campVO.campId}&certificate=t" /></td>
					
			        
				</tr>
			</c:forEach>


		</tbody>

	</table>

	<div class="pagination">
		<%@ include file="pages/page2.file"%>
	</div>
	
	<!-- --------aside�ϰ�------- -->
	<div id="sidebar">
		<aside class="aside">
			<div class="container">
				<nav>
					<ul class="mcd-menu">
						<li><a href="" class="light"> <i class="fa fa-campground"></i>
								<strong>��a�޲z</strong> <small>Camp Management</small>
						</a>
							<ul>
							    <li><a  href="<%=request.getContextPath()%>/back_end/camp/campindex.jsp"  target="main"><i class="fas fa-cannabis"></i>�ڪ���a</a></li>			
								<li><a  href="<%=request.getContextPath()%>/back_end/camp/selectCamp.jsp"  target="main"><i class="fas fa-cannabis"></i>��a�W�U�[</a></li>
							    <li><a  href="<%=request.getContextPath()%>/back_end/camp/selectCampCertificatenum.jsp"  target="main"><i class="fas fa-cannabis"></i>��a�f�֪��p</a></li>								
							</ul>
						</li>
							
							
							
						<li><a href="" class="light"> <i class="fa fa-edit"></i>
								<strong>�ӫ~�޲z</strong> <small>Commodity </small>
						</a></li>
						<li><a href="" class="light"> <i class="fa fa-gift"></i>
								<strong>�q��޲z</strong> <small>Order </small>
						</a>
							<ul>
							   <li><a  href="<%=request.getContextPath()%>/back_end/camp/campindex.jsp"  target="main"><i class="fas fa-cannabis"></i>��{��޲z</a></li>			
							   <li><a  href="<%=request.getContextPath()%>/back_end/camp/listAllCampOrder.jsp"  target="main"><i class="fas fa-cannabis"></i>��a�q��޲z</a></li>								   
							   <li><a  href="<%=request.getContextPath()%>/back_end/camp/productOrderList.html"  target="main"><i class="fas fa-cannabis"></i>�ӫ��q��޲z</a></li>
							</ul></li>
						<li><a href="" class="light"> <i
								class="fas fa-calendar-week"></i> <strong>�t�Ӹ��</strong> <small>Vendor
									Information</small>
						</a>
							<ul>
							
							     <li><a  href="<%=request.getContextPath()%>/back_end/companyProduct/jsp/companyImformation.jsp"  target="main"><i class="fas fa-cannabis"></i>�򥻸���s���έק�</a></li>
								<li><a href="#"><i class="fas fa-cannabis"></i>���K�X</a></li>
							</ul></li>
						<li><a href="" class="light"> <i
								class="fa fa-comment-alt"></i> <strong>�ڪ�����</strong> <small>Comment</small>
						</a>
							<ul>
							<li><a  href="<%=request.getContextPath()%>/back_end/camp/campComment.jsp"  target="main"><i class="fas fa-cannabis"></i>��a����</a></li>
							<li><a  href="<%=request.getContextPath()%>/back_end/companyProduct/jsp/vendorProductComment.jsp"  target="main"><i class="fas fa-cannabis"></i>�ӫ~����</a></li>
							</ul></li>
					</ul>
				</nav>
			</div>
		</aside>
	</div>
	
	




</body>
</html>

















