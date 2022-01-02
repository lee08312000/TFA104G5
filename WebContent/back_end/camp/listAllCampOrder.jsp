

 <%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.campOrder.model.*"%>
<!DOCTYPE html>
<%@ page import="java.util.*"%>

<%
	List<CampOrderVO> list = new ArrayList<CampOrderVO>();
	if(request.getAttribute("list")!=null){
		list = (ArrayList<CampOrderVO>)request.getAttribute("list");
	}
    pageContext.setAttribute("list",list);
    
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
<link rel="stylesheet" href="<%=request.getContextPath()%>/back_end/css/colorbox.css" />

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/back_end/js/jquery.colorbox.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	var ctx = "<%=request.getContextPath()%>";
	//��s�W���O��Page
	$( ".update" ).click(function() {
		var campOrderId = $(this).attr("name");
		param = {
			data : {
				"action":"GETONECAMP",
				"campOrderId" : campOrderId
			},
			title : "��a�q��",
			href : '<c:url value="/camp/campOrder.do" />',
			innerHeight : 350,
			innerWidth : 650,
			opacity : 0.5,
			top : 100
		};
		$.colorbox(param);
	});
});
</script>

<title>order</title>
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"
	integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm"
	crossorigin="anonymous">
<link rel="stylesheet" href="<%=request.getContextPath()%>/back_end/css/campOrder.css?v=006">
</head>
<body>

	<header class="header-outer">
		<div class="header-inner responsive-wrapper">
			<div class="header-logo">
				<a style="display: inline-block; vertical-align: middle;"
					href="����URL"> <img src="<%=request.getContextPath()%>/back_end/images/camp_paradise_logo.png" />
				</a> <span style="display: inline-block; vertical-align: middle;">Camping
					Paradise</span>
			</div>
			<nav class="header-navigation">
				<a href="#">Home</a> <a href="#">�u�W�ӫ�</a> <a href="#"><img
					src="<%=request.getContextPath()%>/back_end/images/heart.png"></a> <a href="#">���U</a> <a href="#">�n�J</a>
				<a href="#"> <i class="fas fa-user"></i></a>
				<button>Menu</button>
			</nav>
		</div>
	</header>

<div id="bodyCenter">
	<!-- --------main�ϰ�------- -->
	<h1>��a�q��d��</h1>
<table>
	<div class="divSearchForm">
		<form class="searchForm"  method="post" ACTION="<%=request.getContextPath()%>/camp/campOrder.do"
			style="margin: auto; max-width: 300px">
			<div class="">
			<label>�q�檬�A</label>
				<select>
					<option value="-1">����</option>
					<option value="1">�B�z��</option>
					<option value="2">�w�T�{</option>
				</select>
			</div>
			<div class="campDate">
				<label>�q�����϶�</label>
				<input type="date" id="startDate" name="startDate" value="<fmt:formatDate value='${startime}' pattern='yyyy-MM-dd'/>"/> -
			    <input type="date" id="endDate" name="endDate" value="<fmt:formatDate value='${endtime}' pattern='yyyy-MM-dd'/>">
			</div>
			
			<div class="list">							
			    <input type="text" placeholder="�п�J����r" name="campOrderId">		
             <div class="submit_button">			
			 <input type="hidden" name="action"	value="SEARCHALL">
			<button type="submit">
				<i class="fa fa-search"></i>
			</button>
			</div>
			
		</form>
	</table>	

    
	</div>
	<div class="pagination">
	<%@ include file="pages/page1.jsp" %> 
	</div>
	<table id="listAllCampTable">
		<thead>
			<tr>
				<th>��a�q��<br>���߮ɶ�</th>
				<th>��a�q��<br>�y����</th>
				<th>���W��</th>
				<th>�|���b��</th>
				<th>�w�p�J<br>����</th>
				<th>�w�p�h<br>�Ф��</th>
				<th>�q�b�ƶq</th>
				<th>��a�q��<br>�`���B</th>		
				<th>�I�ڤH</th>
				<th>��a�q<br>�檬�A</th>
				<th>�s��</th>
			</tr>
		</thead>

      
		<tbody>
		<c:forEach var="campOrderVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
				
				<tr>
				    <td><fmt:formatDate value="${campOrderVO.campOrderConfirmedTime}"
							pattern="yyyy-MM-dd" /></td>
					<td>${campOrderVO.campOrderId}</td>
			        <td>${campOrderVO.campName}</td>
			        <td>${campOrderVO.memberAccount}</td>		        
					<td>${campOrderVO.campCheckInDate}</td>
					<td>${campOrderVO.campCheckOutDate}</td>
					<td>${campOrderVO.bookingQuantity}�b</td>  
	                <td>$${campOrderVO.campOrderTotalAmount}</td>    	    
					<td>${campOrderVO.payerName}</td>
					<td>
					<c:choose>
					<c:when test="${campOrderVO.campOrderStatus eq 1}" >�B�z��</c:when>
					
					<c:when test="${campOrderVO.campOrderStatus eq 2}" >�w�T�{</c:when>
					
					</c:choose>

				
					

					<td>
					       <input  type="button" value="�ק�" name="${campOrderVO.campOrderId}" class="update"  />
					      
					</td>
					
				</tr>
			</c:forEach>

		
		</tbody>
	</table>
		
	<div class="pagination">
		<%@ include file="pages/page2.jsp" %>
	</div>
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
								<li><a href="#"><i class="fas fa-cannabis"></i>�ڪ���a</a></li>
								<li><a href="#"><i class="fas fa-cannabis"></i>��a�W�U�[</a></li>
								<li><a href="#"><i class="fas fa-cannabis"></i>�f�֪��p</a></li>
							</ul></li>
						<li><a href="" class="light"> <i class="fa fa-edit"></i>
								<strong>�ӫ~�޲z</strong> <small>Commodity </small>
						</a></li>
						<li><a href="" class="light"> <i class="fa fa-gift"></i>
								<strong>�q��޲z</strong> <small>Order </small>
						</a>
							<ul>
								<li><a href="#"><i class="fas fa-cannabis"></i>��{��޲z</a></li>
								<li><a href="#"><i class="fas fa-cannabis"></i>��a�q��޲z</a></li>
								<li><a href="#"><i class="fas fa-cannabis"></i>�ӫ��q��޲z</a></li>
							</ul></li>
						<li><a href="" class="light"> <i
								class="fas fa-calendar-week"></i> <strong>�t�Ӹ��</strong> <small>Vendor
									Information</small>
						</a>
							<ul>
								<li><a href="#"><i class="fas fa-cannabis"></i>�򥻸���s���έק�</a></li>
								<li><a href="#"><i class="fas fa-cannabis"></i>���K�X</a></li>
							</ul></li>
						<li><a href="" class="light"> <i
								class="fa fa-comment-alt"></i> <strong>�ڪ�����</strong> <small>Comment</small>
						</a>
							<ul>
								<li><a href="#"><i class="fas fa-cannabis"></i>��a����</a></li>
								<li><a href="#"><i class="fas fa-cannabis"></i>�ӫ~����</a></li>
							</ul></li>
					</ul>
				</nav>
			</div>
		</aside>
	</div>
	


</body>
</html>