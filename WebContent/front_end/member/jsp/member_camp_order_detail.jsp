<%@ page import="com.campAreaOrderDetail.model.CampAreaOrderDetailVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <!-- jstl核心函式庫含for each標籤等 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> <!-- 日期格式化標籤使用 -->
<%@ page import="com.member.model.*"%>
<%@ page import="com.camp.model.*"%>
<%@ page import="com.campTag.model.*"%>
<%@ page import="com.campTagDetail.model.*"%>
<%@ page import="com.campOrder.model.*"%>
<%@ page import="com.campArea.model.*"%> 
<%@ page import="com.campAreaOrderDetail.model.*"%> 
<%@ page import="java.util.*"%> <!-- list用 -->

<%

MemberVO memberVO =  (MemberVO)session.getAttribute("memberVO");
CampOrderVO campOrderVO = (CampOrderVO)session.getAttribute("campOrderVO");
CampAreaVO campAreaVO = new CampAreaVO();
CampAreaOrderDetailDAO campAreaOrderDetailDAO = new CampAreaOrderDetailDAOImpl();
// List<CampAreaVO> campAreaList = campAreaSvc.camparealist(campOrderVO.getCampId());  // 為了要拿到campAreaName 
// List<CampAreaOrderDetailVO> campAreaOrderDetailList = (CampAreaOrderDetailVO)session.getAttribute("campAreaOrderDetailList");


// CampOrderVO campOrderVO = new CampOrderVO();
// CampOrderService campOrderSvc =  new CampOrderService();
// campOrderVO = (CampOrderVO)campOrderSvc.OrderByUserId(memberVO.getMemberId());
// pageContext.setAttribute("onecamp", campOrderVO);

// List<CampOrderVO> list = campOrderSvc.OrderByUserId(memberVO.getMemberId());

// pageContext.setAttribute("list", list);
// System.out.println(memberVO.getMemberId());

// CampAreaOrderDetailDAO campAreaOrderDetailDAO = new CampAreaOrderDetailDAOImpl();
// CampAreaOrderDetailVO campAreaOrderDetailVO = campAreaOrderDetailDAO.findByCampOrderId(campOrderVO.getCampOrderId());

%>

<jsp:useBean id="campSvc" class="com.camp.model.CampService"></jsp:useBean>
<jsp:useBean id="campAreaSvc" class="com.campArea.model.CampAreaService"></jsp:useBean>

<!DOCTYPE html>
<html lang="zh-Hant">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"
        integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm" crossorigin="anonymous">
    <link href="<%=request.getContextPath()%>/front_end/member/css/member_order.css" rel="stylesheet" type="text/css">
    <title>營地訂單明細</title>
    <!-- frontawesome把icon引入的東東 -->
    <script src="https://kit.fontawesome.com/05a51b0b98.js" crossorigin="anonymous"></script>
</head>

<body>

	<%-- =================  header區域   ===================== --%>
    <header class="header">
        <div class="header-inner responsive-wrapper">
            <div class="header-logo">
                <a style="display:inline-block; vertical-align: middle;" href="<%=request.getContextPath() %>/front_end/camp/camp_index.html">
                    <img src="<%=request.getContextPath()%>/front_end/mall/images/camp_paradise_logo.png" />
                </a>
                <span style="display:inline-block; vertical-align: middle;">Camping Paradise</span>
            </div>
        </div>
        <ul>
            <nav class="header-navigation">
				<li><a
					href="<%=request.getContextPath()%>/front_end/camp/camp_index.html">Home</a></li>
				<li><a
					href="<%=request.getContextPath()%>/front_end/mall/mall_index.html">線上商城</a></li>
				<li><a
					href="<%=request.getContextPath()%>/front_end/member/jsp/member_favorite_camp.jsp"><img
						src="<%=request.getContextPath()%>/front_end/mall/images/heart.png"></a></li>
				
				<%-- =================  登出鈕   ===================== --%>	
				<li>
				<form method="post" action="<%=request.getContextPath()%>/member/MemberServlet">
				<a>
				<input class="fas fa-sign-out-alt logout_button" type="submit" value="登出" />
				</a>
				<input type="hidden" value="logout" name="action" />
				</form>
				</li>
				<%-- =================  登出鈕   ===================== --%>	
				
				<li><a
					href="<%=request.getContextPath()%>/front_end/member/jsp/member_main.jsp"
					value=""><i class="fas fa-user"></i></a></li>
            </nav>
        </ul>

    </header>
    <%-- =================  header區域   ===================== --%>
    
    
	<%-- =================  sidebar   ===================== --%>
	<aside class="sidebar">
		<div id="leftside-navigation" class="nano">
			<ul class="nano-content">
				<li class="sub-menu"><a href="javascript:void(0);"><i
						class="fas fa-heart"></i><span>&nbsp;我的最愛</span><i
						class="arrow fa fa-angle-right pull-right"></i></a>
					<ul>
						<li><a
							href="<%=request.getContextPath()%>/front_end/member/jsp/member_favorite_camp.jsp">我的最愛營地</a>
						</li>
						<li><a
							href="<%=request.getContextPath()%>/front_end/member/jsp/member_favorite_product.jsp">我的最愛商品</a>
						</li>
					</ul></li>
				<li class="sub-menu"><a href="javascript:void(0);"><i
						class="far fa-list-alt"></i><span>&nbsp;我的訂單</span><i
						class="arrow fa fa-angle-right pull-right"></i></a>
					<ul>
						<li><a
							href="<%=request.getContextPath()%>/front_end/member/jsp/member_camp_order_list.jsp">營地訂單</a>
						</li>
						<li><a
							href="<%=request.getContextPath()%>/front_end/member/jsp/member_product_order_list.jsp">商品訂單</a>
						</li>
					</ul></li>
				<li class="sub-menu"><a href="javascript:void(0);"><i
						class="fa fa-table"></i><span>&nbsp;修改資料</span><i
						class="arrow fa fa-angle-right pull-right"></i></a>
					<ul>
						<li><a
							href="<%=request.getContextPath()%>/front_end/member/jsp/member_reset_info.jsp">修改會員資訊與密碼</a>
						</li>
					</ul>	
		</div>
	</aside>
    <%-- =================  sidebar   ===================== --%>
    
    
    <%-- =================  營地訂單明細   ===================== --%>
    <div class="table-title">
        <h3>營地訂單明細</h3>
    </div>
    <table class="table-fill">  
        <thead>
            <tr>
                <th>訂單編號 ${ campOrderVO.getCampOrderId() }</th> 
                <th>訂單日期 <fmt:formatDate value="${campOrderVO.getCampOrderConfirmedTime()}" pattern="yyyy-MM-dd"/></th> <%-- ${ campOrderVO.campOrderCompletedTime } --%>
            </tr>
            <tr>
            	<th class="text-left">營地照片</th>
                <th class="text-left">營地名稱</th>
				<th class="text-left">入住日期</th>
				<th class="text-left" colspan="2">退房日期</th>
                <th class="text-left" colspan="2">訂單狀態</th>
            </tr>
        </thead>
        
        <tbody class="table-hover">
        
        <%-- =================  營地迴圈  ===================== --%>
            <tr>
                <td class="text-center"><img class="product_pic" src="<%=request.getContextPath()%>/PicWithCampServlet?campid=${ campOrderVO.campId }&pic=1" alt="營地圖片"></td>
                <td class="text-left">${ campSvc.getOneCamp(campOrderVO.campId).campName }</td>
                <td class="text-left">${ campOrderVO.campCheckInDate }</td> 
                <td class="text-left" colspan="2">${ campOrderVO.campCheckOutDate }</td> 
                <td class="text-left" colspan="2">
                ${ (campOrderVO.campOrderStatus == 0) ? "處理中" : (campOrderVO.campOrderStatus == 1) ? "已確認" : (campOrderVO.campOrderStatus == 2) ? "已完成" : "" }
                </td> 
            </tr> 
        <%-- =================  營地迴圈  ===================== --%>  

            <tr>
                <th class="text-left">營位名稱</th> 
                <th class="text-left">數量</th>
                <th class="text-left">營位總金額</th>
                <th class="text-left">加購人頭數</th>
                <th class="text-left">加購人頭單價</th>
                <th class="text-left" colspan="2">人頭總金額</th>
            </tr>

        <%-- =================  迴圈  ===================== --%>      
			<c:forEach var="campAreaOrderDetailVO" items="${ campAreaOrderDetailList }">
            <tr>
                <td class="text-left">${ campAreaSvc.getOneCampArea(campAreaOrderDetailVO.campAreaId).campAreaName }</td> 
                <td class="text-left">${ campAreaOrderDetailVO.bookingQuantity }</td> 
                <td class="text-left">${ campAreaOrderDetailVO.campAreaWeekdayPrice * campAreaOrderDetailVO.bookingWeekdays + campAreaOrderDetailVO.campAreaHolidayPrice * ampAreaOrderDetailVO.bookingHolidays }</td> 
                <td class="text-left">${ campAreaOrderDetailVO.capitationQuantity }</td> 
                <td class="text-left">${ campAreaOrderDetailVO.perCapitationFee }</td> 
                <td class="text-left" colspan="2">${ campAreaOrderDetailVO.capitationQuantity * campAreaOrderDetailVO.perCapitationFee }</td> 
            </tr>
			</c:forEach>
		<%-- =================  迴圈  ===================== --%>                                                           
                   
            <tr>
                <td class="text-left" colspan="6">
			                    訂單總金額 ${ campOrderVO.campOrderTotalAmount }<br> 
			                    訂購人姓名 ${ campOrderVO.payerName }<br> 
			                    訂購人電話 ${ campOrderVO.payerPhone }<br>
                </td>
                <td class="text-center">
                    <button class="button" type="button" onclick="location.href = '<%=request.getContextPath()%>/front_end/member/jsp/member_camp_order_list.jsp';">返回列表</button><br>
	            	<input type="hidden" class="commentOrderDetail" value="${ campOrderVO.getCampOrderId() }">
	            	<button class="comment" type="button" >評論營地</button></td>
                </td>
            </tr>
        </tbody>
    </table>
    <%-- =================  營地訂單明細   ===================== --%> 
    
    <div class="commentArea -off">
        <button class="comment" id="close" type="button">X</button>
        <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/member/MemberOrderServlet" style="margin-bottom: 0px;">
        <div>
            <div class="star_block">
            	
	                <span class="star -on" data-star="1">
		                <label for="radio1">
		                    <input type="radio" id="radio1" name="starNum" value="1" style="display: none" checked>    
		                    <i class="fas fa-star"></i>
		                </label>
	                </span>	            
	              
	                <span class="star" data-star="2">
	                	<label for="radio2">
		                    <input type="radio" id="radio2" name="starNum" value="2" style="display: none">
		                    <i class="fas fa-star"></i>
	                    </label>
	                </span>
	            
	            
	                <span class="star" data-star="3">
		                <label for="radio3">
		                    <input type="radio" id="radio3" name="starNum" value="3" style="display: none">
		                    <i class="fas fa-star"></i>
		                </label>
	                </span>
	            
	            
	                <span class="star" data-star="4">
		                <label for="radio4">
		                    <input type="radio" id="radio4" name="starNum" value="4" style="display: none">
		                    <i class="fas fa-star"></i>
		                </label>                    
	                </span>
	            
	            
	                <span class="star" data-star="5">
		                <label for="radio5">
		                    <input type="radio" id="radio5" name="starNum" value="5" style="display: none">
		                    <i class="fas fa-star"></i>
		                </label>
	                </span>
	            
            </div>
            <div>營地評論:</div>
            <textarea class="textarea" name="commentText"></textarea>            
				<input type="hidden" name="action"	value="updateComment">
				<input type="hidden" name="campOrderId"  value="${ campOrderVO.getCampOrderId() }">
				<input class="updateButton" id="commentButton" type="submit" value="確認送出">
            </FORM> 
        </div>  
     </div>  
    
    <%-- =================  訂單評論區   ===================== --%>
    
    

	<%-- =================  sidebar javascript   ===================== --%>
    <script src="<%=request.getContextPath()%>/front_end/member/vendor/jQuery/jquery-3.6.0.min.js"></script>
    <script>
        $("#leftside-navigation .sub-menu > a").click(function (e) {
            $("#leftside-navigation ul ul").slideUp(), $(this).next().is(":visible") || $(this).next().slideDown(),
                e.stopPropagation()
        })
        
                // 開啟 Modal 彈跳視窗
        $("button.comment").on("click", function(){
            $("div.commentArea").removeClass("-off");            
            $("#mallOrderDetailIdVar").val($(this).closest("tr").find("input.commentOrderDetail").eq(0).val()); 
            let mallOrder = $(this).closest("tr");
            let comment = mallOrder.attr("data-comment");
        	let star = mallOrder.attr("data-star");
        	$(".textarea").text(comment);
        	
        	$("div.star_block").find("span.star").each(function(i, item){

                if( parseInt($(this).attr("data-star")) == star ){
                	$(this).click();
                	$(this).children("label").click();
                	
                };
             });
        });        
        // 關閉 Modal
        $("#close").on("click", function(){
            $("div.commentArea").addClass("-off");
            $("textarea.textarea").text("");
        });

        $("div.commentArea").on("click", "span.star", function(e){

            let current_star = parseInt($(this).attr("data-star"));

            $(this).closest("div.star_block").find("span.star").each(function(i, item){

            if( parseInt($(this).attr("data-star")) <= current_star ){
                $(this).addClass("-on");
            }else{
                $(this).removeClass("-on");
            }

            });

        });   
    </script>
    <%-- =================  sidebar javascript   ===================== --%>
    
</body>