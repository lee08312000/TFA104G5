

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.campArea.model.*"%>
<!DOCTYPE html>
<%@ page import="java.util.*"%>



<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>CampArea</title>
<script src="<%=request.getContextPath()%>/back_end/camp/js/addCampArea.js"></script>

<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"
	integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/back_end/css/insertCampArea.css?v=006">
</head>
<body>
	

	<!-- --------main�ϰ�------- -->

	<h1>���W�[ ${errorMsgs}</h1>
	<form method="post" ACTION="<%=request.getContextPath()%>/camp/campareashelves.do" enctype="multipart/form-data">
		<table class="camparea_shelves" margin-left:200px>
		
			<tr>
				<td>��a�y����:</td>
				<td><input type="hidden" id="campId" name="campId" value="${campId}">${campId}</td>
			</tr>

			<tr>
				<td><label>���W��:</label></td>
				<td><input type="text" id="campAreaName" name="campAreaName"></td>
			</tr>

			<tr>
				<td><label>������:</label></td>
				<td><input type="text" id="weekdayPrice" name="weekdayPrice"></td>
			</tr>



			<tr>
				<td><label>������:</label></td>
				<td><input type="text" id="holidayPrice"name="holidayPrice"></td>
			</tr>

			<tr>
				<td><label>�C�b�H�Y�[�ʤW��:</label></td>
				<td><input type="text" id="capitationmax"name="capitationMax"></td>
			</tr>
			
			<tr>
				<td><label>�[�ʤH�Y����:</label></td>
				<td><input type="text" id="perCapitationFee"name="perCapitationFee"></td>
			</tr>

			<tr>
				<td><label>�b�ƤW��:</label></td>
				<td><input type="text" id="campAreaMax" name="campAreaMax"></td>
			</tr>

			<tr>
				<td><label>�w�s�b��:</label></td>
				<td><input type="text" id="inventory" name="inventory"></td>
			</tr>

			<tr>
				<td><label for="fname">������1:</label></td>
					<td><input type="file" name="campArea_pic1" id="fileInput1" value=""/> 
					<div id="fileDisplayArea1">
					</div>
					</td>
			</tr>
			
			<tr>

				<td colspan="2"><input type="hidden" name="action" value="INSERT" /> 
				<input type="submit" value="�T�{�s�W"style="margin-left: 250px;"> <input type="submit" value="����"></td>
			</tr>

		</table>
	</form>
	
	
	
</body>
</html>

















