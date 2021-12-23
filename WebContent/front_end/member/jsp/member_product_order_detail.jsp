<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.member.model.*"%> 
<!DOCTYPE html>
<html lang="zh-Hant">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"
        integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm" crossorigin="anonymous">
    <link href="<%=request.getContextPath()%>/front_end/member/css/member_order.css" rel="stylesheet" type="text/css">
    <title>商品訂單明細</title>
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
					href="<%=request.getContextPath()%>/front_end/member/member_favorite_camp.jsp"><img
						src="<%=request.getContextPath()%>/front_end/mall/images/heart.png"></a></li>
				<li><a
					href="<%=request.getContextPath()%>/front_end/member/register/register.jsp"
					value="">註冊</a></li>
				<li><a
					href="<%=request.getContextPath()%>/front_end/member/login/login.jsp"
					value="">登入</a></li>
				<li><a
					href="<%=request.getContextPath()%>/front_end/member/jsp/member_main.jsp"
					value=""><i class="fas fa-user"></i></a></li>
            </nav>
        </ul>

    </header>
	<%-- =================  header區域   ===================== --%>
	
	<%-- =================  sidebar   ===================== --%>
	<form class="form-horizontal" method="post"
			action="<%=request.getContextPath()%>/member/MemberServlet">
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
					</ul></li>
				<li><a href=""><i class="fas fa-sign-out-alt"></i>
				<span><input class="fas fa-sign-out-alt logout_button" type="submit" value="&nbsp;登出" /></span>
				</a>
				<input type="hidden" value="logout" name="action" />
				</li>
				
		</div>
	</aside>
	</form>
    <%-- =================  sidebar   ===================== --%>
    
	<%-- =================  商品訂單明細   ===================== --%>
    <div class="table-title">
        <h3>商品訂單明細</h3>
    </div>
    <table class="table-fill">
    	<!-- <form class="form-horizontal" method="post"
			action="<%=request.getContextPath()%>/member/????"> 再做一個servlet(?)-->
        <thead>
            <tr>
                <th>訂單編號</th> <%-- ${ mallOrderVO.mallOrderId } --%>
                <th>訂單日期</th> <%-- ${ mallOrderVO.mallOrderCompletedTime } --%>
            </tr>
            <tr>
                <th class="text-left">商品圖片</th>
                <th class="text-left">商品名稱</th>
                <th class="text-left">價格</th>
                <th class="text-left">數量</th>
                <th class="text-left">總價</th>
                <th class="text-left">商品訂單狀態</th>
                <th class="text-left">物流狀態</th>
            </tr>
        </thead>
        <tbody class="table-hover">
        
        <%-- <c:forEach var="" varStatus="" items=""> --%>
            <tr>
                <td class="text-center"><img class="product_pic" src="<%=request.getContextPath()%>/product/PicServlet?productId=${ productVO.productId }&pic=1" alt="商品圖片"></td>
                <td class="text-left"></td> <%-- ${ productVO.productName } --%>
                <td class="text-left"></td> <%-- ${ mallOrderDetailVO.productPurchasePrice } --%>
                <td class="text-left"></td> <%-- ${ mallOrderDetailVO.productPurchaseQuantity } --%>
                <td class="text-left"></td> <%-- ${ productPurchasePrice X productPurchaseQuantity } --%>
                <td class="text-left"></td> <%-- ${ mallOrderVO.mallOrderStatus } --%>
                <td class="text-left"></td> <%-- ${ mallOrderVO.mall_order_status } --%>
            </tr>
         <%-- </c:forEach> --%>
         
            <tr>
                <td class="text-left" colspan="6">
			                    訂單總金額<br> <%-- ${ mallOrderVO.mailOrderTotalAmount } --%>
			                    收件人姓名<br> <%-- ${ mallOrderVO.receiverName } --%>
			                    收件人電話<br> <%-- ${ mallOrderVO.receiverPhone } --%>
			                    送貨地址<br> <%-- ${ mallOrderVO.receiverAddress } --%>
			                    送貨廠商<br> <%-- ${ companyVO.companyName } --%>
                </td>
                <td class="text-center">
                    <button class="button" type="button" onclick="location.href = '<%=request.getContextPath()%>/front_end/member/jsp/member_product_order_list.jsp';">返回列表</button>
                	<input class="button" type="submit" value="取消訂單"/>
					<input type="hidden" value="delete" name="action" /> 
                </td>
            </tr>
        </tbody>
	<!-- </form>-->  
    </table>
    <%-- =================  商品訂單明細   ===================== --%>
    
	<%-- =================  sidebar javascript   ===================== --%>
    <script src="<%=request.getContextPath()%>/front_end/member/vendor/jQuery/jquery-3.6.0.min.js"></script>
    <script>
        $("#leftside-navigation .sub-menu > a").click(function (e) {
            $("#leftside-navigation ul ul").slideUp(), $(this).next().is(":visible") || $(this).next().slideDown(),
                e.stopPropagation()
        })
    </script>
    <%-- =================  sidebar javascript   ===================== --%>
    
</body>