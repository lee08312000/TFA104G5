<%@page import="com.campAreaOrderDetail.model.CampAreaOrderDetailVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.member.model.*"%> 
<%@ page import="com.camp.model.*"%> 
<%@ page import="com.campArea.model.*"%> 
<%@ page import="com.campOrder.model.*"%> 
<%@ page import="com.campAreaOrderDetail.model.*"%> 

<%
	// 營地假資料
	MemberService memberSvc = new MemberService();
	MemberVO memberVO = memberSvc.getOneMember(1);
	request.setAttribute("memberVO", memberVO);
	
	CampService campSvc = new CampService();
	CampVO campVO = campSvc.findCampByCampId(1);
	request.setAttribute("CampVO", campVO);
	
	CampAreaService campAreaSvc = new CampAreaService();
	CampAreaVO campAreaVO = campAreaSvc.getOneCampArea(1); // campAreaVO.getCampAreaId()
	request.setAttribute("campAreaVO", campAreaVO);
	
	CampOrderService campOrderSvc = new CampOrderService();
	CampOrderVO campOrderVO = campOrderSvc.findByCampOrderId(campVO.getCampId()); // campAreaVO.getCampAreaId()
	request.setAttribute("CampOrderVO", campOrderVO);
	
	// campAreaOrderDetail沒有service????

	// 營地假資料

%>


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
                <li><a href="<%=request.getContextPath() %>/front_end/camp/camp_index.html">Home</a></li>
                <li><a href="<%=request.getContextPath() %>/front_end/mall/mall_index.html">線上商城</a></li>
                <li><a href="<%=request.getContextPath() %>/front_end/member/member_favorite_camp.jsp"><img src="<%=request.getContextPath()%>/front_end/mall/images/heart.png"></a></li>
                <li><a href="<%=request.getContextPath() %>/front_end/member/register.jsp" value="">註冊</a></li>
                <li><a href="<%=request.getContextPath() %>/front_end/member/login.jsp" value="">登入</a></li>
                <li><a href="<%=request.getContextPath() %>/front_end/member/member_main.jsp" value=""><i class="fas fa-user"></i></a></li>
            </nav>
        </ul>

    </header>
    <%-- =================  header區域   ===================== --%>
    
    
	<%-- =================  sidebar   ===================== --%>
    <aside class="sidebar">
        <div id="leftside-navigation" class="nano">
            <ul class="nano-content">
                <li class="sub-menu">
                    <a href="javascript:void(0);"><i class="fas fa-heart"></i><span>&nbsp;我的最愛</span><i
                            class="arrow fa fa-angle-right pull-right"></i></a>
                    <ul>
                        <li><a href="<%= request.getContextPath() %>/front_end/member/member_favorite_camp.jsp">我的最愛營地</a>
                        </li>
                        <li><a href="<%= request.getContextPath() %>/front_end/member/member_favorite_product.jsp">我的最愛商品</a>
                        </li>
                    </ul>
                </li>
                <li class="sub-menu">
                    <a href="javascript:void(0);"><i class="far fa-list-alt"></i><span>&nbsp;我的訂單</span><i
                            class="arrow fa fa-angle-right pull-right"></i></a>
                    <ul>
                        <li><a href="<%= request.getContextPath() %>/front_end/member/member_camp_order_list.jsp">營地訂單</a>
                        </li>
                        <li><a href="<%= request.getContextPath() %>/front_end/member/member_product_order_list.jsp">商品訂單</a>
                        </li>
                    </ul>
                </li>
                <li class="sub-menu">
                    <a href="javascript:void(0);"><i class="fa fa-table"></i><span>&nbsp;修改資料</span><i
                            class="arrow fa fa-angle-right pull-right"></i></a>
                    <ul>
                        <li>
                        <a href="<%= request.getContextPath() %>/front_end/member/member_reset_info.jsp">修改會員資訊與密碼</a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href=""><i class="fas fa-sign-out-alt"></i><span>&nbsp;登出</span></a>
                </li>
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
                <th>訂單編號 ${ campOrderVO.campOrderId }</th>
                <th>訂單日期 ${ campOrderVO.campOrderCompletedTime }</th>
            </tr>
            <tr>
            	<th class="text-left">營地照片</th>
                <th class="text-left">營地名稱</th>
                <th class="text-left">預約日期</th>
                <th class="text-left" colspan="2">天數</th>
                <th class="text-left" colspan="2">訂單狀態</th>
            </tr>
        </thead>
        
        <tbody class="table-hover">
        
        <%-- =================  營地迴圈  ===================== --%>
        <c:forEach var="" varStatus="" items="">
        
            <tr>
                <td class="text-center"><img class="product_pic" src="<%=request.getContextPath()%>/camp/PicWithCampServlet?campId=${ campVO.campId }&pic=1" alt="商品圖片"></td>
                <td class="text-left">${ campVO.campName }</td>
                <td class="text-left">${ campOrderVO.campCheckInDate }</td>
                <td class="text-left" colspan="2">天數</td>
                <td class="text-left" colspan="2">${ campOrderVO.campOrderStatus }</td>
            </tr>
            
		</c:forEach>
        <%-- =================  營地迴圈  ===================== --%>  
          
            <tr>
                <th class="text-left">營位名稱</th>
                <th class="text-left">數量</th>
                <th class="text-left">金額</th>
            </tr>
           
        <%-- =================  營位迴圈  ===================== --%>      
        <c:forEach var="" varStatus="" items="">
        
            <tr>
                <td class="text-left">${ campAreaVO.campAreaName }</td>
                <td class="text-left">${ CampOrderVO.campAreaName }</td>
                <td class="text-left">1000</td>
            </tr>
            
		</c:forEach>
		<%-- =================  營位迴圈  ===================== --%>  
           
           <tr>
                <th class="text-left">加購人頭數</th>
                <th class="text-left">加購人頭單價</th>
                <th class="text-left">金額</th>
            </tr>
           
        <%-- =================  加購迴圈  ===================== --%>      
        <c:forEach var="" varStatus="" items="">
        
            <tr>
                <td class="text-left">2</td>
                <td class="text-left">1</td>
                <td class="text-left">1000</td>
            </tr>
            
		</c:forEach>
		<%-- =================  加購迴圈  ===================== --%>                                                           
                   
            <tr>
                <td class="text-left" colspan="6">
			                    訂單總金額 ${ CampOrderVO.campOrderTotalAmount }<br>
			                    訂購人姓名 ${ CampOrderVO.payerName }<br>
			                    訂購人電話 ${ CampOrderVO.payerPhone }<br>
                </td>
                <td class="text-center">
                    <button class="button" type="button">取消訂單</button>
                    <button class="button" type="button" onclick="location.href = '<%=request.getContextPath()%>/front_end/member/member_camp_order_list.jsp';">返回訂單列表</button>
                </td>
            </tr>
        </tbody>
    </table>
    <%-- =================  營地訂單明細   ===================== --%>

	<%-- =================  sidebar javascript   ===================== --%>
    <script src="<%=request.getContextPath()%>/front_end/member/vandors/jQuery/jquery-3.6.0.min.js"></script>
    <script>
        $("#leftside-navigation .sub-menu > a").click(function (e) {
            $("#leftside-navigation ul ul").slideUp(), $(this).next().is(":visible") || $(this).next().slideDown(),
                e.stopPropagation()
        })
    </script>
    <%-- =================  sidebar javascript   ===================== --%>
    
</body>