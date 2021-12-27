<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.company.model.*"%>
<%@ page import="com.product.model.*"%>
<%@ page import="com.productType.model.*"%>

<%
	Integer vendorId = (Integer) session.getAttribute("vendorId");
	
	ProductService ProductSvc = new ProductService();
	List<ProductVO> list = ProductSvc.getProductsByCompany(vendorId);
	pageContext.setAttribute("list",list);
%>
<jsp:useBean id="productTypeSvc" scope="page" class="com.productType.model.ProductTypeService" />

<html>
<head>
<title>廠商商品評論查詢 - vendorProductComment.jsp</title>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/back_end/companyProduct/css/vendorProductComment.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>

</head>
<body>
    <header class="header">       
        <div class="header-inner responsive-wrapper">
            <div class="header-logo">
                <a style="display:inline-block; vertical-align: middle;" href="首頁URL">
                    <img src="<%=request.getContextPath()%>/back_end/companyProduct/img/chuba_logo.png" />
                </a>
                <span style="display:inline-block; vertical-align: middle;">Camping Paradise</span>
            </div>
        </div>
        <ul>          
            <nav class="header-navigation">
                <li><a href="#">Home</a></li>
                <li><a href="#">線上商城</a></li>
                <li><a href="#"><img src="<%=request.getContextPath()%>/back_end/companyProduct/img/heart.png"></a></li>
                <li><a href="#">註冊</a></li>
                <li><a href="#">登入</a></li>
                <li><a href="#"><i class="fas fa-user"></i></a></li>                
            </nav>
        </ul>    
                    
    </header>

    <aside class="aside">
        <div class="container">
            <nav>
                    <ul class="mcd-menu">
                        <li>
                            <a href="" class="light">
                                <i class="fa fa-campground"></i>
                                <strong>營地管理</strong>
                                <small>Camp Management</small>
                            </a>
                    <ul>
                                <li><a href="#"><i class="fas fa-cannabis"></i>我的營地</a></li>					
                                <li><a href="#"><i class="fas fa-cannabis"></i>營地上下架</a></li>
                                <li><a href="#"><i class="fas fa-cannabis"></i>審核狀況</a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="" class="light">
                                <i class="fa fa-edit"></i>
                                <strong>商品管理</strong>
                                <small>Commodity </small>
                            </a>
                        </li>
                        <li>
                            <a href="" class="light">
                                <i class="fa fa-gift"></i>
                                <strong>訂單管理</strong>
                                <small>Order </small>
                            </a>
                    <ul>
                                <li><a href="#"><i class="fas fa-cannabis"></i>日程表管理</a></li>					
                                <li><a href="#"><i class="fas fa-cannabis"></i>營地訂單管理</a></li>
                                <li><a href="#"><i class="fas fa-cannabis"></i>商城訂單管理</a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="" class="light">
                                <i class="fas fa-calendar-week"></i>
                                <strong>廠商資料</strong>
                                <small>Vendor Information</small>
                            </a>
                    <ul>
                                <li><a href="#"><i class="fas fa-cannabis"></i>基本資料瀏覽,修改</a></li>					
                                <li><a href="#"><i class="fas fa-cannabis"></i>更改密碼</a></li>				
                            </ul>
                        </li>
                        <li>
                            <a href="" class="light">
                                <i class="fa fa-comment-alt"></i>
                                <strong>我的評論</strong>
                                <small>Comment</small>
                            </a>
                            <ul>
                                <li><a href="#"><i class="fas fa-cannabis"></i>營地評價</a></li>					
                                <li><a href="#"><i class="fas fa-cannabis"></i>商品評價</a></li>				
                            </ul>
                        </li>							
                    </ul>
                </nav>
            </div>                 
    </aside>

    <main class="main">
         <table id="miyazaki">
            <thead>
            <tr><th>商品編號<th>商品分類<th>商品名稱<th>商品圖片<th>商品品牌<th>商品狀態<th>查看商品評論
            <tbody>
            <c:forEach var="productVO" items="${list}">
            <tr>
                <td>${productVO.productId}
                <td>${productTypeSvc.getOneProductType(productVO.productTypeId).productTypeName}
                <td>${productVO.productName}
                <td><img src="<%=request.getContextPath()%>/product/PicServlet?productId=${productVO.productId}&pic=1" style="width: 100px;">
                <td>${productVO.productBrand}
                <td>${(1==productVO.productStatus)? '上架':'下架'}                                
                <td><FORM METHOD="post" ACTION="<%=request.getContextPath()%>/MallOrderDetail/productCommentServlet" style="margin-bottom: 0px;">
						 <input type="submit" value="查看商品評論">
						 <input type="hidden" name="productId"  value="${productVO.productId}">
						 <input type="hidden" name="action"	value="getProductComment">
					</FORM>                 
     		</c:forEach>
        </table> 
    </main>    
   

</body>
</html> 