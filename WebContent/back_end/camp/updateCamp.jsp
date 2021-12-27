
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.camp.model.*"%>
<%@ page import="com.campTagDetail.model.*"%>
<!DOCTYPE html>
<%@ page import="java.util.*"%>

<%
CampVO cv = new CampVO();
if (request.getAttribute("campVO") != null) {
	cv = (CampVO) request.getAttribute("campVO");
}
pageContext.setAttribute("campVO", cv);


List<CampTagDetailVO> campTagDetailList = new ArrayList<CampTagDetailVO>();
if (request.getAttribute("campTagDetailList") != null) {
	campTagDetailList = (List<CampTagDetailVO>)request.getAttribute("campTagDetailList");
}
pageContext.setAttribute("campTagDetailList", campTagDetailList);
%>


<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="https://code.jquery.com/jquery-3.4.1.js"></script>
<title>campshelves</title>
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"
	integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/back_end/css/insertCampShelves.css?v=003">
	
<script >
$(function(){
	  $('#camp_pic1').change(function(){
	    var input = this;
	    var url = $(this).val();
	    var ext = url.substring(url.lastIndexOf('.') + 1).toLowerCase();
	    if (input.files && input.files[0]&& (ext == "gif" || ext == "png" || ext == "jpeg" || ext == "jpg")) 
	     {
	        var reader = new FileReader();

	        reader.onload = function (e) {
	           $('#smallPic1').attr('src', e.target.result);
	        }
	       reader.readAsDataURL(input.files[0]);
	    }
	  });
	  
	  $('#camp_pic2').change(function(){
	    var input = this;
	    var url = $(this).val();
	    var ext = url.substring(url.lastIndexOf('.') + 1).toLowerCase();
	    if (input.files && input.files[0]&& (ext == "gif" || ext == "png" || ext == "jpeg" || ext == "jpg")) 
	     {
	        var reader = new FileReader();

	        reader.onload = function (e) {
	           $('#smallPic2').attr('src', e.target.result);
	        }
	       reader.readAsDataURL(input.files[0]);
	    }
	  });

	  $('#camp_pic3').change(function(){
	    var input = this;
	    var url = $(this).val();
	    var ext = url.substring(url.lastIndexOf('.') + 1).toLowerCase();
	    if (input.files && input.files[0]&& (ext == "gif" || ext == "png" || ext == "jpeg" || ext == "jpg")) 
	     {
	        var reader = new FileReader();

	        reader.onload = function (e) {
	           $('#smallPic3').attr('src', e.target.result);
	        }
	       reader.readAsDataURL(input.files[0]);
	    }
	  });

	  $('#camp_pic4').change(function(){
	    var input = this;
	    var url = $(this).val();
	    var ext = url.substring(url.lastIndexOf('.') + 1).toLowerCase();
	    if (input.files && input.files[0]&& (ext == "gif" || ext == "png" || ext == "jpeg" || ext == "jpg")) 
	     {
	        var reader = new FileReader();

	        reader.onload = function (e) {
	           $('#smallPic4').attr('src', e.target.result);
	        }
	       reader.readAsDataURL(input.files[0]);
	    }
	  });

	  $('#camp_pic5').change(function(){
	    var input = this;
	    var url = $(this).val();
	    var ext = url.substring(url.lastIndexOf('.') + 1).toLowerCase();
	    if (input.files && input.files[0]&& (ext == "gif" || ext == "png" || ext == "jpeg" || ext == "jpg")) 
	     {
	        var reader = new FileReader();

	        reader.onload = function (e) {
	           $('#smallPic5').attr('src', e.target.result);
	        }
	       reader.readAsDataURL(input.files[0]);
	    }
	  });


	});
</script>
</head>
<body>
	

		<!-- --------main�ϰ�------- -->
		
		<h1>��a�W�[ ${errorMsgs}</h1>
		<form method="post"ACTION="<%=request.getContextPath()%>/camp/shelves.do">
			<table class="camp_shelves">
				<tr>
						<td><label>��ܤW�[���</label></td>
						
						<td>
						<input type="hidden" name="campAppliedLaunchTime" value="<fmt:formatDate value='${campVO.campAppliedLaunchTime}' pattern='yyyy-MM-dd'/>">
						<input type="date" name="campLaunchedTime" value="<fmt:formatDate value='${campVO.campLaunchedTime}' pattern='yyyy-MM-dd'/>">
						</td>
				</tr>
				<tr>
					<td><label>��a����:</label></td>

					<td>
					<c:forEach var="campDetailVO" items="${campTagDetailList}" begin="<%=0%>"  end="<%=campTagDetailList.size()-1%>">
						 <input type="checkbox" id="campTag" name="campTag" value="${campDetailVO.campTagId}" ${checkedIntList.contains(campDetailVO.campTagId)?"checked":""}>
                         <label for="campTag"> ${campDetailVO.campTagName}</label>
				  </c:forEach></td>
				</tr>

				<tr>
					<td><label>��a�W��:</label></td>
						<td>
						<input type="hidden" id="campany_id" name=campany_id value="${campVO.companyId}">
						<input type="hidden" id="camp_id" name="camp_id" value="${campVO.campId}">
						<input type="text" id="camp_name" name="camp_name" value="${campVO.campName}">
						</td>

				</tr>



				<tr>
					<td><label>��a�q��:</label></td>
					<td><input type="text" id="camp_phone" name="camp_phone" value="${campVO.campPhone}"></td>
				</tr>

				<tr>
					<td><label>�g��:</label></td>
					<td><input type="text" id="longitude" name="longitude" value="${campVO.longitude}"></td>
				</tr>

				<tr>
					<td><label>�n��:</label></td>
					<td><input type="text" id="lattitude" name="lattitude" value="${campVO.lattitude}"></td>
				</tr>


				<tr>
					<td><label>��a�a�}:</label></td>
					<td><input type="text" id="camp_address" name="campAddress" value="${campVO.campAddress}"></td>
				</tr>


				<tr>
					<td><label> ��a�ԭz:</label></td>
					<td><textarea name="campDiscription" cols="78" rows="14" >${campVO.campDiscription} </textarea></td>
				</tr>

				<tr>
					<td><label>��a���ɳW�h:</label></td>
					<td><textarea name="campRule" cols="78" rows="14"> ${campVO.campRule}</textarea></td>

				</tr>


				<tr>
					<td><label for="fname">��a����1:</label></td>
					<td><input type="file" name="camp_pic1" id="camp_pic1" value=""/> 
					<img id="smallPic1" style="width: 30%;" src="<%=request.getContextPath()%>/PicWithCampServlet?campid=${campVO.campId}&pic=1" />
					</td>

				</tr>
				
				<tr>
					<td><label for="fname">��a���A:</label></td>
					<td>
						<div>
							<input type="radio" name="camp_status" id="option1" value="1"  ${campVO.campStatus==1?"checked":""}>
							<label for="option1">�W�[</label> <input type="radio" name="camp_status"
								id="option2" value="0" ${campVO.campStatus==0?"checked":""}> <label for="option2">�U�[</label>
						</div>
					</td>
				</tr>
				<tr>
					
					<td colspan="2"><input type="hidden" name="action" value="UPDATE" />		
					<input type="submit" value="�T�{�ק�"style="margin-left: 280px;"> 
					<input type="button" onclick="location.href='<%=request.getContextPath()%>/back_end/camp/selectCamp.jsp'" value="����" />
				</tr>
				
				
					
			</table>
		</form>
		
		


</body>
</html>

















