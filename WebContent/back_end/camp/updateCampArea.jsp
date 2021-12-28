
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.campArea.model.*"%>
<!DOCTYPE html>
<%@ page import="java.util.*"%>

<%
CampAreaVO cv = new CampAreaVO();
if (request.getAttribute("campAreaVO") != null) {
	cv = (CampAreaVO) request.getAttribute("campAreaVO");
}
pageContext.setAttribute("campAreaVO", cv);
%>

<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>CampArea</title>
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"
	integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/back_end/css/updateCampArea.css?v=00s">
</head>
<body>
	
	<!-- --------main區域------- -->

	<h1>營位上架 ${errorMsgs}</h1>
	<form method="post" ACTION="<%=request.getContextPath()%>/camp/campareashelves.do">
		<table class="camparea_shelves">
		
			<tr>
				<td>營地流水號:</td>
				<td><input type="hidden" id="campId" name="campId" value="${campAreaVO.campId}">${campAreaVO.campId}</td>
			</tr>

			<tr>
				<td><label>營位名稱:</label></td>
				<td><input type="text" id="campAreaName" name="campAreaName" value="${campAreaVO.campAreaName}"></td>
			</tr>

			<tr>
				<td><label>平日單價:</label></td>
				<td><input type="text" id="weekdayPrice" name="weekdayPrice" value="${campAreaVO.weekdayPrice}"></td>
			</tr>



			<tr>
				<td><label>假日單價:</label></td>
				<td><input type="text" id="holidayPrice"name="holidayPrice" value="${campAreaVO.holidayPrice}"></td>
			</tr>

			<tr>
				<td><label>每帳人頭加購上限:</label></td>
				<td><input type="text" id="capitationmax"name="capitationMax"  value="${campAreaVO.capitationMax}"></td>
			</tr>
			
			<tr>
				<td><label>加購人頭價格:</label></td>
				<td><input type="text" id="perCapitationFee"name="perCapitationFee" value="${campAreaVO.perCapitationFee}"></td>
			</tr>

			<tr>
				<td><label>帳數上限:</label></td>
				<td><input type="text" id="campAreaMax" name="campAreaMax" value="${campAreaVO.campAreaMax}"></td>
			</tr>

			<tr>
				<td><label>庫存帳數:</label></td>
				<td><input type="text" id="inventory" name="inventory"></td>
			</tr>

			<tr>
				<td><label>營位美照:</label></td>
				<td>
					<div class="upload-header">
						<input id="upload" type="file" accept="image/*"
							multiple="multiple">
						<div class="img-box">
							<!-- 存放預覽圖片 -->
						</div>
					</div>
				</td>

			</tr>
			
			<tr>
				<td colspan="2"><input type="hidden" name="action" value="UPDATE" /> 
				<input type="hidden" id="campAreaId" name="campAreaId" value="${campAreaVO.campAreaId}">
				<input type="submit" value="確認修改" style="margin-left: 250px;">
				 <input type="button" onclick="location.href='<%=request.getContextPath()%>/camp/campareashelves.do?campId=${campAreaVO.campId}&action=SEARCHALL'" value="取消" />
				 
				 </td>
			</tr>

		</table>
	</form>
	

</body>
</html>

















