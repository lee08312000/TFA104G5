

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
	href="<%=request.getContextPath()%>/back_end/css/updateCampCertificatenum.css?v=008">
</head>
<body>

	<!-- --------main區域------- -->
	<h1>營地上架審核列表 </h1>
    <h2>${errorMsgs}</h2>
<form method="post" ACTION="<%=request.getContextPath()%>/camp/shelves.do"  >
	<div class="selector">
		 <label>日期區間</label> <input type="date" id="startDate"
			name="startDate"> <input type="date" id="endDate"
			name="endDate"> <input type="text" placeholder="請輸入關鍵字"
			name="campOrderId"> <input type="hidden" name="action"
			value="SEARCH">
		<button type="submit">
			<i class="fa fa-search"></i>
		</button>
	</div>
</form>

	<div class="pagination">
		<%@ include file="pages/page1.jsp" %>
	</div>
	<table class="camp_table">
		<tbody>
			<tr>
				<th>廠商流水號</th>
                <th>廠商名稱</th>
                <th>負責人姓名</th>
                <th>廠商電話</th>
                <th>營地名稱</th>
                <th>廠商地址</th>
                <th>認證字號</th>                           
                <th>證書圖片</th>
                <th>編輯</th>

			</tr>
		</thead>


		<tbody>
			<c:forEach var="companyVO" items="${list}" begin="<%=pageIndex%>"
				end="<%=pageIndex+rowsPerPage-1%>">

				<tr>
					<td><fmt:formatDate value="${campVO.campConfirmedTime}"
							pattern="yyyy-MM-dd" /></td>
					<td>${companyVO.companyId}</td>
					<td>${companyVO.companyName}</td>
					<td>${companyVO.headName}</td>
					<td>${campVO.campName}</td>
					<td>${companyVO.companyAddress}</td>
					<td>${campVO.certificateNum}</td>
					<td>${campVO.certificatePic}</td>
			
					<td><input type="button" value="修改" name="${companyVO.companyId}"
						class="update" /></td>
				</tr>
			</c:forEach>


		</tbody>

	</table>

	<div class="pagination">
		<%@ include file="pages/page2.jsp"%>
	</div>



	<footer class="tm-footer text-center">
		<pre>服務專線：(02)2252-7966　　 客服時間：週一至週五9:00~18:00　　 客服信箱：camp@easycamp.com.tw</pre>
		<pre>Copyright &copy; 2021 Camping Paradise | Design: <a
				style="text-decoration: none;" rel="nofollow" href="#">TFA104第五組</a>
				</pre>
	</footer>
</body>
</html>

















