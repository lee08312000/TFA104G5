<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.campOrder.model.*"%>

<%
	CampOrderVO campOrderVO = (CampOrderVO) request.getAttribute("campOrderVO"); //EmpServlet.java (Concroller) �s�Jreq��empVO���� (�]�A�������X��empVO, �]�]�A��J��ƿ��~�ɪ�empVO����)
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
<!--�ǰe�ק��ƨ��x  -->
<form method ="POST" action="<%=request.getContextPath()%>/camp/campOrder.do">
	<div style="padding: 10px; width: 95%;" id="workArea">
		<div style="clear: both;"></div>
		<table id="datatable" width="100%">
			<thead>
				<tr height="40" id="even">
					<th class="confirmTh" colspan="2">�ק�q��</th>
				</tr>
			</thead>

			
			<tbody>
				<tr class="confirmTr">

					<td id="confirmTd" colspan="1" style="text-align: left; padding: 10px;"><font color="red">*</font>��a�q��y����:<br></td>
					<td style="text-align: left; padding: 10px;"><input type="hidden" name="campOrderId" value="${campOrderVO.campOrderId}">${campOrderVO.campOrderId}</td>
				</tr>

				<tr class="confirmTr">

					<td id="confirmTd" colspan="1"
						style="text-align: left; padding: 10px;"><font color="red">*</font>�|���s��:<br></td>
					<td style="text-align: left; padding: 10px;">${campOrderVO.campOrderId}</td>
				</tr>
				<tr class="confirmTr">
					<td id="confirmTd" colspan="1" style="text-align: left; padding: 10px;"><font color="red">*</font>�I�ڤH�m�W:<br></td>
					<td style="text-align: left; padding: 10px;">	<input name="payerName" type="text" value="${campOrderVO.payerName}"></td>
				</tr>
				<tr class="confirmTr">
					<td id="confirmTd" colspan="1" style="text-align: left; padding: 10px;"><font color="red">*</font>�I�ڤH�q��:<br></td>
					<td style="text-align: left; padding: 10px;"> <input name="payerPhone" type="text" value="${campOrderVO.payerPhone}"></td>
				</tr>
				<tr class="confirmTr">
					<td id="confirmTd" colspan="1"
						style="text-align: left; padding: 10px;"><font color="red">*</font>�q�檬�A:<br></td>
					<td style="text-align: left; padding: 10px;">	<select name="campStatus">
							<option value="0"
								${campOrderVO.campOrderStatus == 0 ? 'selected="selected"' : ''}>�B�z��</option>
							<option value="1"
								${campOrderVO.campOrderStatus == 1 ? 'selected="selected"' : ''}>�w�T�{</option>
							<option value="2"
								${campOrderVO.campOrderStatus == 2 ? 'selected="selected"' : ''}>�w����</option>
					</select></td>
				</tr>

				<tr class="confirmTr">
					<td id="confirmTd" colspan="1"
						style="text-align: left; padding: 10px;"><font color="red">*</font>�|���s��:<br></td>
					<td style="text-align: left; padding: 10px;"><input type="hidden" name="memberId" value="${campOrderVO.memberId}"> ${campOrderVO.memberId}</td>
				</tr>


				<tr class="confirmTr">
					<td id="confirmTd" colspan="1"
						style="text-align: left; padding: 10px;"><font color="red">*</font>�J����:<br></td>
					<td style="text-align: left; padding: 10px;"><fmt:formatDate value="${campOrderVO.campOrderConfirmedTime}"
							pattern="yyyy-MM-dd" /></td>
				</tr>




			</tbody>
		</table>
		<div style="text-align: center; margin-top: 10px;">
			&nbsp;
			<input type="hidden" name="action"	value="UPDATE">
			<button type="submit">�e�X</button>
			&nbsp;
			<button id="updateCommand" name="${campOrderVO.campOrderId}">����</button>
		</div>
	</div>
	</form>
</body>
</html>