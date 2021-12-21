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
	  var msg = "�z�u���T�w�n�R���ܡH\n\n�нT�{�I";
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
	href="<%=request.getContextPath()%>/back_end/css/selectCampArea.css?v=004">
</head>
<body>
	
	<!-- --------main�ϰ�------- -->
	
	<h1 style="margin-right:45px">���C�� ${errorMsgs}</h1>
	<form method="post" ACTION="<%=request.getContextPath()%>/camp/shelves.do">
		<div class="selectors" style="float:left">		 
		     <input type="hidden" name="campstatus" value="3">
		     <input type="hidden" name="campIdsearch" value="">
			 <input type="hidden" name="action" value="SEARCHALL">
			<button type="submit">
				<i class="fas fa-home"></i>
			</button>
		</div>
	
	</form>
   
	<form method="post" ACTION="<%=request.getContextPath()%>/camp/campareashelves.do" >
		<div>				
		 <input type="hidden" name="action" value="SEARCHALL">
		 <input type="hidden" name="campId" value="${campId}">
		<button type="submit"><i class="fa fa-search"></i></button>	
		</div>					
	</form>	
	
	 <form method="post" ACTION="<%=request.getContextPath()%>/camp/campareashelves.do">
		<div >		 
			 <input type="hidden" name="action" value="INSERTPAGE">
			  <input type="hidden" name="campId" value="${campId}">		 
			<button type="submit" style=" background-color:#4CAF50;font-size:16px;color:white;border:1px solid #4CAF50;margin-left:2px;margin-top:20px">�s�W���</button>
		</div>
	
	</form>
	
	
	
	
	

	<div class="pagination" >
		<%@ include file="pages/page1.jsp" %>
	</div>
	<table class="camp_table">
		<tbody>
			<tr>
				<th>���y����</th>
				<th>���W��</th>
				<th>������</th>
				<th>������</th>
				<th>�C�b�[��<br>�H�Y�W��</th>
				<th>�[�ʤH<br>�Y����</th>
				<th>�b�ƤW��</th>
				<th>�w�s�b��</th>
				<th>������</th>
				<th colspan="2">�s��</th>

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
					<td></td>
					<td></td>
					<td>
						<form method="post" ACTION="<%=request.getContextPath()%>/camp/campareashelves.do">
						<input type="hidden" name="campAreaId"  value="${campareaVO.campAreaId}" class="update" />
						<input type="hidden" name="action"  value="UPDATEFINDBYKEY" class="update" />
						<input  style="background-color:#008CBA;font-size:14px;color:white;border:1px solid #008CBA"  type="submit" value="�ק�"  />
						</form>
					</td>
					<td>
						<form method="post" ACTION="<%=request.getContextPath()%>/camp/campareashelves.do">
						<input type="hidden" name="campAreaId"  value="${campareaVO.campAreaId}" class="delete"/>
						<input type="hidden" name="action"  value="DELETE" class="delete" />
						<input type="hidden" name="camp_Id"  value="${campareaVO.campId}" class="delete"/>
						<input style="background-color:#FF0000;font-size:14px;color:white;border:1px solid #FF0000" type="submit" onclick="deleteCheck()" value="�R��"  />
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

















