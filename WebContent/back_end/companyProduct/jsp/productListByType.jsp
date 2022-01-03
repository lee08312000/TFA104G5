<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.product.model.*"%>
<%@ page import="com.productType.model.*"%>


<%
	List<ProductVO> list = (List<ProductVO>) request.getAttribute("producList");	

	pageContext.setAttribute("list",list);
%>
<jsp:useBean id="productTypeSvc" scope="page" class="com.productType.model.ProductTypeService" />

<html>
<head>
<title>商品列表</title>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm" crossorigin="anonymous">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/back_end/companyProduct/css/productlist.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/back_end/companyProduct/js/productlist.js"></script>

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
                <li><a href="#" id="logout">登出</a>
                <form id="logoutForm" style="display: none;" method="post" action="<%=request.getContextPath()%>/Company/VendorLogoutServlet">
					<input type="hidden" name="action" value="logout">
					<input type="submit" value="登出">
				</form></li>
                <li><a href="<%=request.getContextPath()%>/back_end/camp/campindex.jsp"><i class="fas fa-user"></i></a></li>                
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
                                <li><a href="<%=request.getContextPath()%>/back_end/camp/campindex.jsp"><i class="fas fa-cannabis"></i>我的營地</a></li>					
                                <li><a href="<%=request.getContextPath()%>/back_end/camp/insertCampShelves.jsp"><i class="fas fa-cannabis"></i>營地上下架</a></li>
                                <li><a href="<%=request.getContextPath()%>/back_end/camp/selectCampCertificatenum.jsp"><i class="fas fa-cannabis"></i>審核狀況</a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="<%=request.getContextPath()%>/back_end/companyProduct/jsp/productlist.jsp" class="light">
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
                                <li><a href="<%=request.getContextPath()%>/back_end/camp/backcal.jsp"><i class="fas fa-cannabis"></i>日程表管理</a></li>					
                                <li><a href="<%=request.getContextPath()%>/back_end/camp/listAllCampOrder.jsp"><i class="fas fa-cannabis"></i>營地訂單管理</a></li>
                                <li><a href="<%=request.getContextPath()%>/back_end/companyProduct/html/productOrderList.html"><i class="fas fa-cannabis"></i>商城訂單管理</a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="<%=request.getContextPath()%>/back_end/companyProduct/jsp/companyImformation.jsp" class="light">
                                <i class="fas fa-calendar-week"></i>
                                <strong>廠商資料</strong>
                                <small>Vendor Information</small>
                            </a>
                    <ul>
                                <li><a href="<%=request.getContextPath()%>/back_end/companyProduct/jsp/companyImformation.jsp"><i class="fas fa-cannabis"></i>基本資料瀏覽,修改</a></li>					
                                <li><a href="<%=request.getContextPath()%>/back_end/companyProduct/jsp/updatePassword.jsp"><i class="fas fa-cannabis"></i>更改密碼</a></li>				
                            </ul>
                        </li>
                        <li>
                            <a href="" class="light">
                                <i class="fa fa-comment-alt"></i>
                                <strong>我的評論</strong>
                                <small>Comment</small>
                            </a>
                            <ul>
                                <li><a href="<%=request.getContextPath()%>/back_end/camp/campComment.jsp"><i class="fas fa-cannabis"></i>營地評價</a></li>					
                                <li><a href="<%=request.getContextPath()%>/back_end/companyProduct/jsp/vendorProductComment.jsp"><i class="fas fa-cannabis"></i>商品評價</a></li>				
                            </ul>
                        </li>							
                    </ul>
                </nav>
            </div>                 
    </aside>

    <main class="main">
    	<div class="search"> 
	    	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/Product/ProductBrowseServlet" style="margin-bottom: 0px;">
	    			<input type="text" name="searchName" class="form-control" placeholder="請輸入商品名稱" value="">
					<input class="button" type="submit" value="送出">				
					<input type="hidden" name="action"	value="getList_By_Name">
			</FORM>
		</div>
		<div class="search">
			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/Product/ProductBrowseServlet" style="margin-bottom: 0px;">
	    			<select size="1" name="productTypeId">
	                    	<c:forEach var="productType" items="${productTypeSvc.allProductType}">
								<option value="${productType.productTypeId}" ${(productVO.productTypeId == productType.productTypeId) ? "selected" : "" }>${productType.productTypeName}</option>
							</c:forEach>             
	                </select>
					<input class="button" type="submit" value="查詢">				
					<input type="hidden" name="action"	value="getList_By_Type">
			</FORM>
		</div>
		<div class="addProduct">
        <input class="button" type="button" value="新增商品" onclick="location.href='<%=request.getContextPath()%>/back_end/companyProduct/jsp/addProduct.jsp'">
        </div>
        <c:if test="${empty list}">
        	<h1>查無資料......</h1>
        </c:if>
        <c:if test="${not empty list}">
        <table id="miyazaki">
            <thead>
            <tr><th>商品編號<th>商品分類<th>商品名稱<th>商品品牌<th>商品單價<th>商品庫存數量<th>商品售出總數<th>商品狀態<th>變更狀態<th>操作
            <tbody>
            
            <c:forEach var="productVO" items="${list}">
	            <tr data-describe="${productVO.productDescription}" data-notice="${productVO.shoppingInformation}" data-time='<fmt:formatDate value="${productVO.productLaunchedTime}" pattern="yyyy-MM-dd HH:mm:ss"/>'>
	                <td>${productVO.productId}</td>
	                <td>${productTypeSvc.getOneProductType(productVO.productTypeId).productTypeName}</td>
	                <td>${productVO.productName}</td>
	                <td>${productVO.productBrand}</td>
	                <td>$${productVO.productPrice}</td>
	                <td>${productVO.productInventory}</td>
	                <td>${productVO.productSellAllnum}</td>
	                <td>${(1==productVO.productStatus)? '上架':'下架'}</td>
	                <td><FORM METHOD="post" ACTION="<%=request.getContextPath()%>/Product/ProductServlet" style="margin-bottom: 0px;">
		                		 <input class="button" type="submit" value="上架">
		                		 <input type="hidden" name="productId"  value="${productVO.productId}">
							     <input type="hidden" name="productStatus"  value="1">
							     <input type="hidden" name="action"	value="update_Status">
		                	</FORM>
		                	 	        
	                    	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/Product/ProductServlet" style="margin-bottom: 0px;">
		                		 <input class="button" type="submit" value="下架">
		                		 <input type="hidden" name="productId"  value="${productVO.productId}">
							     <input type="hidden" name="productStatus"  value="0">
							     <input type="hidden" name="action"	value="update_Status">
		                	</FORM>  
	                </td>    
	                <td>
	                	<button type="button" class="btn_open">查看</button>
	                	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/Product/ProductServlet" style="margin-bottom: 0px;">
						     <input class="button" type="submit" value="修改">
						     <input type="hidden" name="productId"  value="${productVO.productId}">
						     <input type="hidden" name="action"	value="getOne_For_Update">
					    </FORM>
	                </td>
	             </tr>   
	                </c:forEach>          
        </table>
        </c:if>        
    </main>
    
    <div class="overlay" style="border: 1px solid red;">
        <article>
            <div class="article-group">
                <label class="control-label">商品名稱:</label>                
                	<span id="productName">這是商名稱資訊</span>                    
            </div>
            <div class="article-group">
                <label class="control-label">商品分類:</label>                
                	<span id="productStatus">商品分類</span>                    
            </div>
            <div class="article-group">
                <label class="control-label">商品單價:</label>                
               		<span id="productPrice">這是商品單價</span>                    
            </div>
            <div class="article-group">
                <label class="control-label">商品品牌:</label>                
               		 <span id="productBrand">這是商品品牌</span>                    
            </div>
            <div class="article-group">
                <label class="control-label">商品庫存數量:</label>                
               		<span id="productInventory"> 這是庫存數量 </span>                   
            </div>
            <div class="article-group">
                <label class="control-label">商品售出總數:</label>                
               		 <span id="productSellAllnum">這是商品售出總數</span>                    
            </div>
            <div class="article-group">
                <label class="control-label">商品狀態:</label>                
              		 <span id="productStatus">這是商品狀態</span>                    
            </div>
            <div class="article-group">
                <label class="control-label">商品敘述:</label>                
                <div class="text-frame" id="describe">
			                       這是商品敘述
                </div>                                    
            </div>
            <div class="article-group">
                <label class="control-label">商品購買須知:</label>                
                <div class="text-frame" id="notice">
                   	這是商品購買須知
                </div>                      
            </div>
            <label class="control-label">商品圖片一:</label>
            <div class="img">                             
                <img id="pic1" src="./img/chuba_logo.png" />                       
            </div>
            <label class="control-label">商品圖片二:</label>
            <div class="img">                             
                <img id="pic2" src="./img/chuba_logo.png" />                       
            </div>
            <label class="control-label">商品圖片三:</label>
            <div class="img">                             
                <img id="pic3" src="./img/chuba_logo.png" />                       
            </div>
            <div class="time">
                <label class="control-label">上架時間:</label>                
                	<span id="time">這是商品上架時間</span>                  
            </div>
          <button type="button" class="btn_modal_close">關閉</button>
        </article>
    </div>  

</body>
</html> 