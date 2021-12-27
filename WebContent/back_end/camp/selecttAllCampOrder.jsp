  <%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.campOrder.model.*"%>
<!DOCTYPE html>
<%@ page import="java.util.*"%>
<%@ page import="com.campOrder.model.*"%>


<%
	List<CampOrderVO> list = new ArrayList<CampOrderVO>();
	if(request.getAttribute("list")!=null){
		list = (ArrayList<CampOrderVO>)request.getAttribute("list");
	}
    pageContext.setAttribute("list",list);
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
		param = {
			data : {
				"action":"GETONECAMP",
				"campOrderId" : campOrderId
			},
			type: 'POST',
			title : "��a�q��",
			dataType: 'json',
            contentType: 'application/json',
			href : '<c:url value="'+ctx+'/back_end/camp/campOrder.do" />',
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
<link rel="stylesheet" href="<%=request.getContextPath()%>/back_end/css/campOrder.css">
</head>
<body>
<!-- --------head�ϰ�------- -->
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
	<h2>��a�q��d��</h2>
	
<table>
	<div class="divSearchForm">
		<form class="searchForm"  method="post" ACTION="<%=request.getContextPath()%>/camp/campOrder.do"
			style="margin: auto; max-width: 300px">
			<div class="">
			<label>���A</label>
				<select>
					<option value="-1">����</option>
					<option value="1">�w�B�z</option>
					<option value="2">�B�z��</option>
					<option value="3">�w����</option>
				</select>
			</div>
			<div>
				<label>�q�����϶�</label>
				<input type="date" id="startDate" name="startDate"> -
			    <input type="date" id="endDate" name="endDate">
			</div>
			<div>
				<label>�I�ڤH</label>
			    <input type="text" placeholder="�п�J����r" name="payerName">
			</div>
			    <div class="list">		
				<label>�q��y����</label>							
			    <input type="text" placeholder="�п�J����r" name="campOrderId">		
             <div class="submit_button">			
			 <input type="hidden" name="action"	value="SEARCHALL">
			<button type="submit">
				<i class="fa fa-search"></i>
			</button>
			</div>
			<div style="clear: both;"></div>
			</div>
		</form>
	</table>	

    
	</div>
	<div class="pagination">
	<%@ include file="pages/page1.jsp" %> 
	</div>
	<table>
		<thead>
			<tr>
				<th>���</th>
				<th>�q��y����</th>
				<th>�I�ڤH</th>
				<th>�s���q��</th>
				<th>�q���`���B</th>
				<th>�q�檬�A</th>
				<th>�s��</th>
			</tr>
		</thead>

      
		<tbody>
		<c:forEach var="campOrderVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
				
				<tr>
					<td><fmt:formatDate value="${campOrderVO.campOrderConfirmedTime}" pattern="yyyy-MM-dd" /></td>
					<td>${campOrderVO.campOrderId}</td>
					<td>${campOrderVO.payerName}</td>
					<td>${campOrderVO.payerPhone}</td>
					<td>${campOrderVO.campOrderTotalAmount}</td>
					<td>${campOrderVO.campOrderStatus}</td> 
					<td>
					     <input type="button" value="�ק�" name="${campOrderVO.campOrderId}" class="update"  />
					</td>
				</tr>
			</c:forEach>

		
		</tbody>
	</table>
		
	<div class="pagination">
		<%@ include file="pages/page2.jsp" %>
	</div>
</div>
	


	<footer class="tm-footer text-center">
		<pre>�A�ȱM�u�G(02)2252-7966�@�@ �ȪA�ɶ��G�g�@�ܶg��9:00~18:00�@�@ �ȪA�H�c�Gcamp@easycamp.com.tw</pre>
		<pre>Copyright &copy; 2021 Camping Paradise | Design: <a
				style="text-decoration: none;" rel="nofollow" href="#">TFA104�Ĥ���</a>
				</pre>
	</footer>
</body>
</html>