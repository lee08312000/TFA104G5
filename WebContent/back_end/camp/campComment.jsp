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
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/back_end/css/selectCamp.css">
</head>
<body >

	<!-- --------main區域------- -->
	
	 <h1>營地評價查詢</h1>
    <div class="selectors">
    
        <label>日期區間</label>
        <input type="date" id="startDate" name="startDate"> 
        <input type="date" id="endDate" name="endDate">
     

    <input type="text" placeholder="請輸入關鍵字" name="campOrderId"> 
    <input type="hidden" name="action"	value="SEARCHALL">
   <button type="submit">
       <i class="fa fa-search"></i>
   </button>  
</div>

    <table class="camp_table" >
        <tbody>
            <tr>
                <th>營地訂單評論時間</th>
                <th>營地訂單流水號</th>
                <th>會員帳號</th>
                <th>營地訂單評論</th>
                <th>營地訂單評價star</th>
                         
            </tr>
        </tbody>
                 
    </table>
    
    <tbody>
				<c:forEach var="campVO" items="${list}" begin="<%=pageIndex%>"
				end="<%=pageIndex+rowsPerPage1%>">
				<tr>
					<td><fmt:formatDate value="${campVO.campLaunchedTime}"
							pattern="yyyy-MM-dd" /></td>
					<td>${campVO.campId}</td>
					<td>${campVO.campName}</td>
					<td>${campVO.campPhone}</td>
					<td>${campVO.longitude}</td>
					<td>${campVO.lattitude}</td>
					<td>${campVO.campAddress}</td>
					<td>${campVO.campDiscription}</td>
					<td>${campVO.campRule}</td>
					<td>${campVO.campPic1}</td>					
					<td>${campVO.campStatus==1?"上架":"下架"}</td>
					
			         
					<td>
					<form method="post" ACTION="<%=request.getContextPath()%>/camp/campareashelves.do">
					  <input type="hidden" name="action"value="SEARCHALL">
					  <input type="hidden" name="campId" value="${campVO.campId}">
					<button type="submit" ><i class="fas fa-file-alt"></i> </button>
				
					</form>
					</td>						
					
					
					<td> 
					<form method="post" ACTION="<%=request.getContextPath()%>/camp/shelves.do" >				
					 <input type="hidden" name="action" value="UPDATEFINDBYKEY">
					 <input type="hidden" name="campId" value="${campVO.campId}">
					<button type="submit">修改</button>
						</form>	
				</td>
			
						
						
				</tr>
			</c:forEach>


		</tbody>

	</table>
    
    	<div class="pagination">
		<%@ include file="pages/page1.jsp" %>
	</div>
	
	</body>
</html>

	
	


