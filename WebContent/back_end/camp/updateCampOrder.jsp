<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.campOrder.model.*"%>

<%
	CampOrderVO campOrderVO = (CampOrderVO) request.getAttribute("campOrderVO"); //EmpServlet.java (Concroller) 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
	pageContext.setAttribute("campOrderVO", campOrderVO);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Insert title here</title>
</head>
<body>
<!--傳送修改資料到後台  -->
<form method ="POST" action="<%=request.getContextPath()%>/camp/campOrder.do">
	<div style="padding: 10px; width: 95%;" id="workArea">
		<div style="clear: both;"></div>
		<table id="datatable" width="100%">
			<thead>
				<tr height="40" id="even">
					<th class="confirmTh" colspan="2">修改訂單</th>
				</tr>
			</thead>

			
			<tbody>
				<tr class="confirmTr">

					<td id="confirmTd" colspan="1" style="text-align: left; padding: 10px;"><font color="red">*</font>營地訂單流水號:<br></td>
					<td style="text-align: left; padding: 10px;"><input type="hidden" name="campOrderId" value="${campOrderVO.campOrderId}">${campOrderVO.campOrderId}</td>
				</tr>

				<tr class="confirmTr">

					<td id="confirmTd" colspan="1"
						style="text-align: left; padding: 10px;"><font color="red">*</font>會員編號:<br></td>
					<td style="text-align: left; padding: 10px;">${campOrderVO.campOrderId}</td>
				</tr>
				<tr class="confirmTr">
					<td id="confirmTd" colspan="1" style="text-align: left; padding: 10px;"><font color="red">*</font>付款人姓名:<br></td>
					<td style="text-align: left; padding: 10px;">	<input name="payerName" type="text" value="${campOrderVO.payerName}"></td>
				</tr>
				<tr class="confirmTr">
					<td id="confirmTd" colspan="1" style="text-align: left; padding: 10px;"><font color="red">*</font>付款人電話:<br></td>
					<td style="text-align: left; padding: 10px;"> <input name="payerPhone" type="text" value="${campOrderVO.payerPhone}"></td>
				</tr>
				<tr class="confirmTr">
					<td id="confirmTd" colspan="1"
						style="text-align: left; padding: 10px;"><font color="red">*</font>訂單狀態:<br></td>
					<td style="text-align: left; padding: 10px;">	<select name="campStatus">
							<option value="0"
								${campOrderVO.campOrderStatus == 0 ? 'selected="selected"' : ''}>處理中</option>
							<option value="1"
								${campOrderVO.campOrderStatus == 1 ? 'selected="selected"' : ''}>已確認</option>
							<option value="2"
								${campOrderVO.campOrderStatus == 2 ? 'selected="selected"' : ''}>已完成</option>
					</select></td>
				</tr>

				<tr class="confirmTr">
					<td id="confirmTd" colspan="1"
						style="text-align: left; padding: 10px;"><font color="red">*</font>會員編號:<br></td>
					<td style="text-align: left; padding: 10px;"><input type="hidden" name="memberId" value="${campOrderVO.memberId}"> ${campOrderVO.memberId}</td>
				</tr>


				<tr class="confirmTr">
					<td id="confirmTd" colspan="1"
						style="text-align: left; padding: 10px;"><font color="red">*</font>入住日期:<br></td>
					<td style="text-align: left; padding: 10px;"><fmt:formatDate value="${campOrderVO.campOrderConfirmedTime}"
							pattern="yyyy-MM-dd" /></td>
				</tr>




			</tbody>
		</table>
		<div style="text-align: center; margin-top: 10px;">
			&nbsp;
			<input type="hidden" name="action"	value="UPDATE">
			<button type="submit">送出</button>
			&nbsp;
			<button id="updateCommand" name="${campOrderVO.campOrderId}">取消</button>
		</div>
	</div>
	</form>
</body>
</html>