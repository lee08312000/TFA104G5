<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>	
<%@ page import="java.util.*"%>
<%@ page import="com.member.model.*"%>
<%@ page import="com.mallOrder.model.*"%>
<%@ page import="com.mallOrderDetail.model.*"%>
<%@ page import="com.company.model.*"%>
<%@ page import="com.product.model.*"%> 

<%
	List<MallOrderDetailVO> mallOrderDetail = (List<MallOrderDetailVO>) request.getAttribute("mallOrderDetailList");
	for (MallOrderDetailVO  mallOrderDetailVO : mallOrderDetail) {
		System.out.println(mallOrderDetailVO.getMallOrderDetailId());
	}
	
	pageContext.setAttribute("mallOrderDetail", mallOrderDetail);
	
%>

<jsp:useBean id="mallOrderSvc" scope="page" class="com.mallOrder.model.MallOrderService" />
<jsp:useBean id="productOrderSvc" scope="page" class="com.product.model.ProductService" />
<jsp:useBean id="companySvc" scope="page" class="com.company.model.CompanyService" />

<!DOCTYPE html>
<html lang="zh-Hant">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"
        integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm" crossorigin="anonymous">
    <link href="<%=request.getContextPath()%>/front_end/member/css/member_product_order_list.css" rel="stylesheet" type="text/css">
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
                <li class="sub-menu">
                    <a href="javascript:void(0);"><i class="fas fa-heart"></i><span>&nbsp;我的最愛</span><i
                            class="arrow fa fa-angle-right pull-right"></i></a>
                    <ul>
                        <li><a href="<%= request.getContextPath() %>/front_end/member/jsp/member_favorite_camp.jsp">我的最愛營地</a>
                        </li>
                        <li><a href="<%= request.getContextPath() %>/front_end/member/jsp/member_favorite_product.jsp">我的最愛商品</a>
                        </li>
                    </ul>
                </li>
                <li class="sub-menu">
                    <a href="javascript:void(0);"><i class="far fa-list-alt"></i><span>&nbsp;我的訂單</span><i
                            class="arrow fa fa-angle-right pull-right"></i></a>
                    <ul>
                        <li><a href="<%= request.getContextPath() %>/front_end/member/jsp/member_camp_order_list.jsp">營地訂單</a>
                        </li>
                        <li><a href="<%= request.getContextPath() %>/front_end/member/jsp/member_product_order_list.jsp">商品訂單</a>
                        </li>
                    </ul>
                </li>
                <li class="sub-menu">
                    <a href="javascript:void(0);"><i class="fa fa-table"></i><span>&nbsp;修改資料</span><i
                            class="arrow fa fa-angle-right pull-right"></i></a>
                    <ul>
                        <li>
                        <a href="<%= request.getContextPath() %>/front_end/member/jsp/member_reset_info.jsp">修改會員資訊與密碼</a>
                        </li>
                    </ul>
                </li>
        </div>
    </aside>
    <%-- =================  sidebar   ===================== --%>
    
	<%-- =================  商品訂單明細   ===================== --%>
    <div class="table-title">
        <h3>商品訂單明細</h3>
    </div>
    <table class="table-fill">
        <thead>
            <tr>
                <th>訂單編號 : ${mallOrderDetail.get(0).mallOrderId} </th>
                <th>訂單日期 : <fmt:formatDate value="${mallOrderSvc.getOneMallOrder(mallOrderDetail.get(0).mallOrderId).mallOrderConfirmedTime}" pattern="yyyy-MM-dd "/></th>
            </tr>
            <tr>
                <th class="text-left">商品圖片</th>
                <th class="text-left">商品名稱</th>
                <th class="text-left">價格</th>
                <th class="text-left">數量</th>
                <th class="text-left">總價</th>
                <th class="text-left">商品狀態</th>
                <th class="text-left">物流狀態</th>
                <th class="text-left">評論商品</th>
            </tr>
        </thead>
        <tbody class="table-hover">
        	<c:forEach var="mallOrderDetailVO" items="${mallOrderDetail}">
            <tr data-comment="${mallOrderDetailVO.productComment}" data-star="${mallOrderDetailVO.productCommentStar}">
                <td class="text-center"><img class="product_pic" src="/TFA104G5/product/PicServlet?productId=${mallOrderDetailVO.productId}&pic=1" alt="商品圖片"></td>
                <td class="text-left"><a href="<%=request.getContextPath()%>/front_end/mall/mall_product_detail.html?productId=${ mallOrderDetailVO.productId }">${productOrderSvc.getOneProduct(mallOrderDetailVO.productId).productName}</a></td>
                <td class="text-left">${mallOrderDetailVO.productPurchasePrice}</td>
                <td class="text-left">${mallOrderDetailVO.productPurchaseQuantity}</td>
                <td class="text-left">${mallOrderDetailVO.productPurchasePrice * mallOrderDetailVO.productPurchaseQuantity }</td>
                <td class="text-center">${mallOrderSvc.getOneMallOrder(mallOrderDetailVO.mallOrderId).mallOrderStatus == 0 ? "處理中" : mallOrderSvc.getOneMallOrder(mallOrderDetailVO.mallOrderId).mallOrderStatus == 1 ? "已確認" : "已完成"}</td>
                <td class="text-center">${mallOrderSvc.getOneMallOrder(mallOrderDetailVO.mallOrderId).mallOrderDeliveryStatus == 0 ? "未發貨" : mallOrderSvc.getOneMallOrder(mallOrderDetailVO.mallOrderId).mallOrderDeliveryStatus == 1 ? "已發貨" : "已收貨"}</td>
            	<td class="text-center">
	            	<c:if test="${mallOrderSvc.getOneMallOrder(mallOrderDetail.get(0).mallOrderId).mallOrderDeliveryStatus == 2}">
	            	<input type="hidden" class="commentOrderDetail" value="${mallOrderDetailVO.mallOrderDetailId}">
	            	<button class="comment" type="button" >評論商品</button></td>
	            	</c:if>
            	</td>
            </tr>
			</c:forEach>
            <tr>
                <td class="text-left" colspan="7">
			                    訂單總金額:  ${mallOrderSvc.getOneMallOrder(mallOrderDetail.get(0).mallOrderId).mailOrderTotalAmount}<br>
			                    訂購人姓名:  ${mallOrderSvc.getOneMallOrder(mallOrderDetail.get(0).mallOrderId).receiverName}<br>
			                    訂購人電話:  ${mallOrderSvc.getOneMallOrder(mallOrderDetail.get(0).mallOrderId).receiverPhone}<br>
			                    送貨地址:  ${mallOrderSvc.getOneMallOrder(mallOrderDetail.get(0).mallOrderId).receiverAddress}<br>
			                    送貨廠商:  ${companySvc.getOneCompany(mallOrderSvc.getOneMallOrder(mallOrderDetail.get(0).mallOrderId).companyId).companyName}<br>
                </td>
                <td class="text-center">
                    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/Member/MemberProductServlet" style="margin-bottom: 0px;">
                	<c:if test="${mallOrderSvc.getOneMallOrder(mallOrderDetail.get(0).mallOrderId).mallOrderDeliveryStatus == 1}">
                    <input type="hidden" name="mallOrderId"  value="${mallOrderDetail.get(0).mallOrderId}">
					<input type="hidden" name="action"	value="updateMallOrderAllStatus">
					<input class="button" type="submit" value="確認收貨">
					</c:if>
                    </FORM>
                    <button class="button" type="button" onclick="location.href = '<%=request.getContextPath()%>/front_end/member/jsp/member_product_order_list.jsp';">返回列表</button>
                </td>
            </tr>
        </tbody>
    </table>
    <div class="commentArea -off">
        <button class="comment" id="close" type="button">X</button>
        <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/Member/MemberProductServlet" style="margin-bottom: 0px;">
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
            <div>商品評論:</div>
            <textarea class="textarea" name="commentText"></textarea>            
	            <input type="hidden" name="mallOrderDetailId" id="mallOrderDetailIdVar" value="">
				<input type="hidden" name="action"	value="updateComment">
				<input type="hidden" name="mallOrderId"  value="${mallOrderDetail.get(0).mallOrderId}">
				<input class="updateButton" id="commentButton" type="submit" value="確認送出">
            </FORM> 
        </div>    
    </div>
    
    <%-- =================  商品訂單明細   ===================== --%>
    
	<%-- =================  sidebar javascript   ===================== --%>
    <script
		src="<%=request.getContextPath()%>/front_end/member/vendor/jQuery/jquery-3.6.0.min.js"></script>
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