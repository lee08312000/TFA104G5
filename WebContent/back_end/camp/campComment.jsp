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
	href="<%=request.getContextPath()%>/back_end/css/camp.css?v=008">
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

	<h1>��a�����d��</h1>
	<div class="selectors" style="margin-left: 300px; margin-top: 50px">
	<form method="post"
		ACTION="<%=request.getContextPath()%>/camp/campOrder.do">
		
			<label>����϶�</label> <input type="date" id="startDate" name="startDate" value="<fmt:formatDate value='${startime}' pattern='yyyy-MM-dd'/>" />
			<input type="date" id="endDate" name="endDate"
				value="<fmt:formatDate value='${endtime}' pattern='yyyy-MM-dd'/>">
			<input type="text" placeholder="�п�J����r" name="campOrderId"> <input
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
				<th>��a�q����׮ɶ�</th>
				<th>��a�q��y����</th>
				<th>�|���y����</th>
				<th>��a�q�����</th>
				<th>��a�q�����star</th>

			</tr>
	
		<c:forEach var="camporderVO" items="${list}" begin="<%=pageIndex%>"
			end="<%=pageIndex+rowsPerPage-1%>">
            
			<tr>

				<td><fmt:formatDate value="${camporderVO.campOrderCommentTime}" pattern="yyyy-MM-dd" /></td>
				<td>${camporderVO.campOrderId}</td>
				<td>${camporderVO.memberId}</td>
				<td>${camporderVO.campComment}</td>
				<td>${camporderVO.campCommentStar}���P</td>
			</tr>
		</c:forEach>

	</tbody>
	
	</table>

	<div class="pagination">
		<%@ include file="pages/page2.file"%>

	</div>
	
	
	
	



</body>
</html>





