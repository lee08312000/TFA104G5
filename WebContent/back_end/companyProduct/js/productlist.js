$(function(){
  
        // 開啟 Modal 彈跳視窗
        $("button.btn_open").on("click", function(){
        	let product = $(this).closest("tr");
        	let describe = product.attr("data-describe");
        	let notice = product.attr("data-notice");
        	let time = product.attr("data-time");
        	
        	let productId = product.find("td").eq(0).text();
        	let productTypeName = product.find("td").eq(1).text();
        	let productName = product.find("td").eq(2).text();
        	let productBrand = product.find("td").eq(3).text();
        	let productPrice = product.find("td").eq(4).text();
        	let productInventory = product.find("td").eq(5).text();
        	let productSellAllnum = product.find("td").eq(6).text();
        	let productStatus = product.find("td").eq(7).text();
        	
        	$("span#productName").text(productName);
        	$("span#productStatus").text(productStatus);
        	$("span#productPrice").text(productPrice);
        	$("span#productBrand").text(productBrand);
        	$("span#productInventory").text(productInventory);
        	$("span#productSellAllnum").text(productSellAllnum);
        	$("span#productStatus").text(productStatus);
        	$("div#describe").text(describe);
        	$("div#notice").text(notice);
        	$("img#pic1").attr("src", `/CampingParadiseCopy/product/PicServlet?productId=${productId}&pic=1`);
        	$("img#pic2").attr("src", `/CampingParadiseCopy/product/PicServlet?productId=${productId}&pic=2`);
        	$("img#pic3").attr("src", `/CampingParadiseCopy/product/PicServlet?productId=${productId}&pic=3`);
        	$("span#time").text(time);
        	
        	
            $("div.overlay").fadeIn();
        });
        
        // 關閉 Modal
        $("button.btn_modal_close").on("click", function(){
            $("div.overlay").fadeOut();
        });
        
        });