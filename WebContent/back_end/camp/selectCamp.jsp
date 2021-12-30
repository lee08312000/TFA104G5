 <%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.camp.model.*"%>
<!DOCTYPE html>
<%@ page import="java.util.*"%>



<%
	List<CampVO> list = new ArrayList<CampVO>();
	if (request.getAttribute("list") != null) {
		list = (ArrayList<CampVO>) request.getAttribute("list");
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
<link rel="stylesheet" href="<%=request.getContextPath()%>/back_end/css/selectCamp.css?v=008">

</head>
<body>
	<!-- --------main�ϰ�------- -->
	<h1 style="margin-right:10px">��a�d�ߦC�� </h1>
    <h2>${errorMsgs}</h2>
  
	<form method="post" ACTION="<%=request.getContextPath()%>/camp/shelves.do"  >
		<div class="selector">
		<label>��a���A</label> 
		<select name="campstatus">
			<option value="3">���� </option>
			<option value="1">�W�[</option>
			<option value="0">�U�[</option>
		</select> 
		<label>����϶�</label>
		 <input type="date" id="startDate" name="startDate" value="<fmt:formatDate value='${startime}' pattern='yyyy-MM-dd'/>"/>
		  <input type="date" id="endDate"name="endDate" value="<fmt:formatDate value='${endtime}' pattern='yyyy-MM-dd'/>">
		   <input type="text" placeholder="�п�J����r"name="campIdsearch">
		    <input type="hidden" name="action" value="SEARCHALL">
		    <button type="submit" id="searchSubmit">
			<i class="fa fa-search"></i>
		   </button>
		   
		 
		   
		</div>
	</form>
	
	<form method="post" ACTION="<%=request.getContextPath()%>/camp/shelves.do">
					  <input type="hidden" name="action" value="INSERTCAMP">
					    <button  style="background-color:#4CAF50;font-size:16px;color:white;border:1px solid #4CAF50 ;margin-left:22px" type="submit" id="insertSubmit">�s�W��a</button>
				
					</form>
					



	<div class="pagination">
		<%@ include file="pages/page1.jsp" %>
	</div>
	<table class="camp_table" style="margin-left:20px">
	
		<thead>
		
			<tr>
				<th>�W�[���</th>
                <th>��a<br>�y����</th>
                <th>��a�W��</th>
                <th>��a�q��</th>
                <th>��a�a�}</th>
                <th>��a�ԭz</th>
                <th>��a����</th>
                <th>��a���A</th>
                <th>���Խ�</th>
                <th>�s��</th>
			</tr>
			
		</thead>


		<tbody>
			<c:forEach var="campVO" items="${list}" begin="<%=pageIndex%>"
				end="<%=pageIndex+rowsPerPage-1%>">

				<tr>
					<td><fmt:formatDate value="${campVO.campLaunchedTime}"
							pattern="yyyy-MM-dd" /></td>
					<td>${campVO.campId}</td>
					<td>${campVO.campName}</td>
					<td>${campVO.campPhone}</td>
					<td>${campVO.campAddress}</td>
					<td>${campVO.campDiscription}</td>
					<td><img id="smallPic1" style="width: 30%;" src='<%=request.getContextPath()%>/PicWithCampServlet?campid=${campVO.campId}&pic=1' /></td>					
					<td>${campVO.campStatus==1?"�W�[":"�U�["}
					<c:if test="${campVO.campStatus==0}" >
					<form method="post" ACTION="<%=request.getContextPath()%>/camp/shelves.do" >
					<input type="hidden" name="action" value="certificatepage">
					<input type="hidden" name="campId" value="${campVO.campId}">
					<button class="cal" type="submit" >�f��</button>					
					</form>
					</c:if></td>
					
					
					<td>
					<form method="post" ACTION="<%=request.getContextPath()%>/camp/campareashelves.do">
					  <input type="hidden" name="action"value="SEARCHALL">
					  <input type="hidden" name="campId" value="${campVO.campId}">
					<button style="background-color:#FFFF93 "type="submit" ><i class="fas fa-file-alt"></i> </button>
				
					</form>
					
			
					
					</td>						
					
					
					<td> 
					<form method="post" ACTION="<%=request.getContextPath()%>/camp/shelves.do" >				
					 <input type="hidden" name="action" value="UPDATEFINDBYKEY">
					 <input type="hidden" name="campId" value="${campVO.campId}">
					<button  style="background-color: #008CBA ;font-size:16px;color:white;border:1px solid #4CAF50" type="submit" id="insertSubmit"type="submit">�ק�</button>
						</form>	
				</td>
			
						
						
				</tr>
			</c:forEach>
			


		</tbody>

	</table>

	<div class="pagination">
		<%@ include file="pages/page2.jsp"%>
		
	</div>
	
		
	
	 
</body>

</html>














