<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.product.model.*"%>
<%@ page import="com.productType.model.*"%>

<%
	ProductVO productVO = (ProductVO) request.getAttribute("productVO");
%>
<jsp:useBean id="productTypeSvc" scope="page" class="com.productType.model.ProductTypeService" />

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>修改商品資料 - addProduct.jsp</title>
<script src="<%=request.getContextPath()%>/back_end/companyProduct/js/updateProduct.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/back_end/companyProduct/css/updateProduct.css" />
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm" crossorigin="anonymous">
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
                <li><a href="#">登出</a></li>
                <li><a href="#"><i class="fas fa-user"></i></a></li>                
            </nav>
        </ul>    
                    
    </header>
    <%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color:red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color:red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
    <div class="updateform">
        <h1>修改商品資訊</h1>
            <FORM METHOD="post" ACTION="/TFA104G5/Product/ProductServlet" name="form1" enctype="multipart/form-data">
                <div class="form-group">
                <label class="control-label">商品名稱</label>
                    <div class="control-label">
                        <input type="text" class="form-control" name="productName" value="<%=productVO.getProductName()%>">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label">商品分類</label><br>
                    <select size="1" name="productTypeId">
                    	<c:forEach var="productType" items="${productTypeSvc.allProductType}">
							<option value="${productType.productTypeId}" ${(productVO.productTypeId == productType.productTypeId) ? "selected" : "" }>${productType.productTypeName}</option>
						</c:forEach>             
                    </select>
                </div>
                <div class="form-group">
                    <label class="control-label">商品單價</label>
                        <div class="control-label">
                            <input type="text" class="form-control" name="productPrice" value="<%=productVO.getProductPrice()%>">
                        </div>
                </div>
                <div class="form-group">
                    <label class="control-label">商品品牌</label>
                        <div class="control-label">
                            <input type="text" class="form-control" name="productBrand" value="<%=productVO.getProductBrand()%>">
                        </div>
                </div>
                <div class="form-group">
                    <label class="control-label">商品庫存數量</label>
                        <div class="control-label">
                            <input type="text" class="form-control" name="productInventory" value="<%=productVO.getProductInventory()%>">
                        </div>
                </div>
                <div class="form-group">
                    <label>商品敘述</label><br>
                    <textarea class="textarea" name="productDescription"><%=productVO.getProductDescription()%></textarea>
                </div>
                <div class="form-group">
                    <label>商品購買須知</label><br>
                    <textarea class="textarea" name="shoppingInformation"><%=productVO.getShoppingInformation()%></textarea>
                </div>
                <div id="page-wrapper">

                    <h3>商品圖片一</h3>
                    <div>請選擇圖片: 
                        <input type="file" id="fileInput1" name="productPic1">                        
                    </div>
                    <div id="fileDisplayArea1">
                    <img src="<%=request.getContextPath()%>/product/PicServlet?productId=<%=productVO.getProductId()%>&pic=1">
                    </div>                
                </div>
                <div id="page-wrapper">
                    <h3>商品圖片二</h3>
                    <div>請選擇圖片: 
                        <input type="file" id="fileInput2" name="productPic2">                        
                    </div>
                    <div id="fileDisplayArea2">
                    <img src="<%=request.getContextPath()%>/product/PicServlet?productId=<%=productVO.getProductId()%>&pic=2">
                    </div>                
                </div>
                <div id="page-wrapper">
                    <h3>商品圖片三</h3>
                    <div>請選擇圖片: 
                        <input type="file" id="fileInput3" name="productPic3">                        
                    </div>
                    <div id="fileDisplayArea3">
                    <img src="<%=request.getContextPath()%>/product/PicServlet?productId=<%=productVO.getProductId()%>&pic=3">
                    </div>                
                </div>           
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="productStatus" value="<%=productVO.getProductStatus()%>">
            <input type="hidden" name="productId" value="<%=productVO.getProductId()%>">
            <input type="submit" value="確認修改">                                          
            </FORM>                    
    </div>  
</body>