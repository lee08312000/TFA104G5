<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.campArea.model.*"%>
<%@ page import="com.campTagDetail.model.*"%>
<!DOCTYPE html>
<%@ page import="java.util.*"%>

<%
	List<CampTagDetailVO> campTagDetailList = new ArrayList<CampTagDetailVO>();
	if (request.getAttribute("campTagDetailList") != null) {
		campTagDetailList = (List<CampTagDetailVO>) request.getAttribute("campTagDetailList");
	}
	pageContext.setAttribute("campTagDetailList", campTagDetailList);
%>

<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="<%=request.getContextPath()%>/back_end/camp/js/addCamp.js"></script>
<title>campshelves</title>
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"
	integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/back_end/css/insertCampShelves.css?v=008">
</head>
<body>

	<!-- --------main區域------- -->

	<h1>營地上架 ${errorMsgs}</h1>
	<form method="post"
		ACTION="<%=request.getContextPath()%>/camp/shelves.do" enctype="multipart/form-data">
		<table class="camp_shelves">
			<tr>
				<td><label>選擇上架日期:</label></td>
				<td><input type="date" id="calendar" name="calendar"></td>
			</tr>
			<tr>
				<td><label>營地標籤:</label></td>

				<td><c:forEach var="campDetailVO" items="${campTagDetailList}"
						begin="<%=0%>" end="<%=campTagDetailList.size()-1%>">
						<input type="checkbox" id="campTag" name="campTag"
							value="${campDetailVO.campTagId}"
							${checkedIntList.contains(campDetailVO.campTagId)?"checked":""}>
						<label for="campTag"> ${campDetailVO.campTagName}</label>
					</c:forEach></td>
			</tr>

			<tr>
				<td><label>營地名稱:</label></td>
				<td><input type="text" id="campany_id" name="campany_id">
					<input type="text" id="camp_name" name="camp_name" value="">
				</td>

			</tr>



			<tr>
				<td><label>營地電話:</label></td>
				<td><input type="text" id="camp_phone" name="camp_phone"></td>
			</tr>

			<tr>
				<td><label>經度:</label></td>
				<td><input type="text" id="longitude" name="longitude"></td>
			</tr>

			<tr>
				<td><label>緯度:</label></td>
				<td><input type="text" id="lattitude" name="lattitude"></td>
			</tr>


			<tr>
				<td><label>營地地址:</label></td>
				<td><input type="text" id="camp_address" name="campAddress"></td>
			</tr>


			<tr>
				<td><label> 營地敘述:</label></td>
				<td><textarea name="campDiscription" cols="50" rows="10"> </textarea></td>
			</tr>

			<tr>
				<td><label>營地租借規則:</label></td>
				<td><textarea name="campRule" cols="50" rows="10"> </textarea></td>

			</tr>


			<tr>
					<td><label for="fname">營地美照1:</label></td>
					<td><input type="file" name="camp_pic1" id="fileInput1" value=""/> 
					<div id="fileDisplayArea1">
					<img style="width: 30%;" src="<%=request.getContextPath()%>/PicWithCampServlet?campid=${campVO.campId}&pic=1" />
					</div>
					</td>

				</tr>
				<tr>
					<td><label for="fname">營地美照2:</label></td>
					<td><input type="file" name="camp_pic2" id="fileInput2" value=""/> 
					<div id="fileDisplayArea2">
					<img style="width: 30%;" src="<%=request.getContextPath()%>/PicWithCampServlet?campid=${campVO.campId}&pic=2" />
					</div>
					</td>
					

				</tr>
				<tr>
					<td><label for="fname">營地美照3:</label></td>
					<td><input type="file" name="camp_pic3" id="fileInput3" value=""/> 
					<div id="fileDisplayArea3">
					<img style="width: 30%;" src="<%=request.getContextPath()%>/PicWithCampServlet?campid=${campVO.campId}&pic=3" />
					</div>
					</td>

				</tr>
				<tr>
					<td><label for="fname">營地美照4:</label></td>
					<td><input type="file" name="camp_pic4" id="fileInput4" value=""/> 
					<div id="fileDisplayArea4">
					<img style="width: 30%;" src="<%=request.getContextPath()%>/PicWithCampServlet?campid=${campVO.campId}&pic=4" />
					</div>
					</td>

				</tr>
				<tr>
					<td><label for="fname">營地美照5:</label></td>
					<td><input type="file" name="camp_pic5" id="fileInput5" value=""/> 
					<div id="fileDisplayArea5">
					<img style="width: 30%;" src="<%=request.getContextPath()%>/PicWithCampServlet?campid=${campVO.campId}&pic=5" />
					</div>
					</td>

				</tr>
						
			<tr>
				<td><label for="fname">營地狀態:</label></td>


				<td>
					<div>
						<input type="radio" name="camp_status" id="option1" value="1"
							checked> <label for="option1">上架</label> <input
							type="radio" name="camp_status" id="option2" value="2"> <label
							for="option2">下架</label>
					</div>
				</td>
			</tr>
			<tr>

				<td colspan="2"><input type="hidden" name="action"
					value="INSERT" /> <input type="submit" value="確認新增"
					style="margin-left: 250px;"> <input type="submit"
					value="取消"></td>
			</tr>

		</table>
	</form>


	
</body>

</html>

















