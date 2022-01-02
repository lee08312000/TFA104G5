

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
	href="<%=request.getContextPath()%>/back_end/css/selectCampCertificatenum.css?v=009">
</head>
<body>

	<!-- --------main�ϰ�------- -->
	<h1  style="margin-right:300px">��a�W�[�f�֬d�ߦC�� </h1>
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




</body>
</html>

















