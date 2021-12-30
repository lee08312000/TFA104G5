$(function () {
	
	getProducts();
	
	//	選取條顯示
	$(document).on("click", "#deal", function(e){
        $("#search-deal").removeClass("-off");
        $("#search-delivery").addClass("-off");
    });
    $(document).on("click", "#delivery", function(e){
        $("#search-delivery").removeClass("-off");
        $("#search-deal").addClass("-off");
    });
    
    
	//一開始進入頁面
    function getProducts() {
        $.ajax({
          url: "/TFA104G5/mallOrder/CompanyBrowseServlet",
          type: "POST",
          data: {
            "action": "getMallOrders"                       
          },
          dataType: "json",
          beforeSend: function () {
            
          },
          success: function (mallOrderList) {            
            if (mallOrderList == 0) {
              $("div.orderList").html("");	
              $("div.orderList").html("<div style='margin-left: 100px; width: 100%;color: black;'><h2>查無訂單......</h2></div>");
            } else {
            	$("div.orderList").html("");
            	$("div.orderList").append(
            			`<table id="miyazaki">
            			<thead>
                        <tr><th>訂單編號<th>訂購人<th>收件人電話<th>總金額<th>訂單狀態<th>貨品狀態<th>訂單成立時間<th>操作
                        <tbody class="tbody">    	            
                        </tbody>
                        </table> `);
            	
              $.each(mallOrderList, function (index, mallOrder) {
                let mallOrder_item =
                  `<tr>
		                <td>${mallOrder.mallOrderId}
		                <td>${mallOrder.memberName}
		                <td>${mallOrder.receiverPhone}
		                <td>${mallOrder.mailOrderTotalAmount}
		                <td>${mallOrder.mallOrderStatus == 0 ? "處理中" : mallOrder.mallOrderStatus == 1 ? "已確認" : "已完成"}
		                <td>${mallOrder.mallOrderDeliveryStatus == 0 ? "未發貨" : mallOrder.mallOrderDeliveryStatus == 1 ? "已發貨" : "已收貨"}
		                <td>${mallOrder.mallOrderConfirmedTime}                
		                <td><input class="button" type="button" value="查看訂單明細" onclick="location.href='/TFA104G5/back_end/companyProduct/html/productOrderListDetail.html?mallOrderId=${mallOrder.mallOrderId}'">`;
                $(".tbody").append(mallOrder_item);
              });
            }
          },
          complete: function (xhr) {
            // console.log(xhr);
          },
          error: function(xhr){           
              console.log("error");  
          }
        });
      }   
    
    //點選全部商品按鈕
    $(document).on("click", "#search-all", function (){
    	$("#search-deal").addClass("-off");
    	$("#search-delivery").addClass("-off");
    	getProducts();    	
    });
    
    //依狀態查詢訂單    
    $("#search-deal").on("change", function () {
    	$.ajax({
            url: "/TFA104G5/mallOrder/CompanyBrowseServlet",
            type: "POST",
            data: {
              "action": "getMallOrdersByOrderStatus",
              "mallOrderStatus": $("#search-deal").val(),
            },
            dataType: "json",
            beforeSend: function () {
              
            },
            success: function (mallOrderList) {            
              if (mallOrderList == 0) {
                $("div.orderList").html("");	
                $("div.orderList").html("<div style='margin-left: 100px; width: 100%;color: black;'><h2>查無訂單......</h2></div>");
              } else {
              	$("div.orderList").html("");
              	$("div.orderList").append(
              			`<table id="miyazaki">
              			<thead>
                          <tr><th>訂單編號<th>訂購人<th>收件人電話<th>總金額<th>訂單狀態<th>貨品狀態<th>訂單成立時間<th>操作
                          <tbody class="tbody">    	            
                          </tbody>
                          </table> `);              		

                $.each(mallOrderList, function (index, mallOrder) {
                  let mallOrder_item =
                    `<tr>
  		                <td>${mallOrder.mallOrderId}
  		                <td>${mallOrder.memberName}
  		                <td>${mallOrder.receiverPhone}
  		                <td>${mallOrder.mailOrderTotalAmount}
  		                <td>${mallOrder.mallOrderStatus == 0 ? "處理中" : mallOrder.mallOrderStatus == 1 ? "已確認" : "已完成"}
		                <td>${mallOrder.mallOrderDeliveryStatus == 0 ? "未發貨" : mallOrder.mallOrderDeliveryStatus == 1 ? "已發貨" : "已收貨"}
  		                <td>${mallOrder.mallOrderConfirmedTime}                
  		                <td><input class="button" type="button" value="查看訂單明細" onclick="location.href='/TFA104G5/back_end/companyProduct/html/productOrderListDetail.html?mallOrderId=${mallOrder.mallOrderId}'">`;
                  $(".tbody").append(mallOrder_item);
                });
              }
            },
            complete: function (xhr) {
              // console.log(xhr);
            },
            error: function(xhr){           
                console.log("error");  
            }
        });    	
    });
    
    
  //依物流狀態查詢訂單    
    $("#search-delivery").on("change", function () {
    	$.ajax({
            url: "/TFA104G5/mallOrder/CompanyBrowseServlet",
            type: "POST",
            data: {
              "action": "getMallOrdersByDeliveryStatus",
              "deliveryStatus": $("#search-delivery").val(),
            },
            dataType: "json",
            beforeSend: function () {
              
            },
            success: function (mallOrderList) {            
              if (mallOrderList == 0) {
                $("div.orderList").html("");	
                $("div.orderList").html("<div style='margin-left: 100px; width: 100%;color: black;'><h2>查無訂單......</h2></div>");
              } else {
              	$("div.orderList").html("");
              	$("div.orderList").append(
              			`<table id="miyazaki">
              			<thead>
                          <tr><th>訂單編號<th>訂購人<th>收件人電話<th>總金額<th>訂單狀態<th>貨品狀態<th>訂單成立時間<th>操作
                          <tbody class="tbody">    	            
                          </tbody>
                          </table> `);              		

                $.each(mallOrderList, function (index, mallOrder) {
                  let mallOrder_item =
                    `<tr>
  		                <td>${mallOrder.mallOrderId}
  		                <td>${mallOrder.memberName}
  		                <td>${mallOrder.receiverPhone}
  		                <td>${mallOrder.mailOrderTotalAmount}
  		                <td>${mallOrder.mallOrderStatus == 0 ? "處理中" : mallOrder.mallOrderStatus == 1 ? "已確認" : "已完成"}
		                <td>${mallOrder.mallOrderDeliveryStatus == 0 ? "未發貨" : mallOrder.mallOrderDeliveryStatus == 1 ? "已發貨" : "已收貨"}
  		                <td>${mallOrder.mallOrderConfirmedTime}                
  		                <td><input class="button" class="button" type="button" value="查看訂單明細" onclick="location.href='/TFA104G5/back_end/companyProduct/html/productOrderListDetail.html?mallOrderId=${mallOrder.mallOrderId}'">`;
                  $(".tbody").append(mallOrder_item);
                });
              }
            },
            complete: function (xhr) {
              // console.log(xhr);
            },
            error: function(xhr){           
                console.log("error");  
            }
        });    	
    });
    
  //依訂單編號    
    $("#search-one").on("click", function () {
    	$.ajax({
            url: "/TFA104G5/mallOrder/CompanyBrowseServlet",
            type: "POST",
            data: {
              "action": "getMallOrdersById",
              "mallOrderId": $("input[name='searchId']").val(),
            },
            dataType: "json",
            beforeSend: function () {
              
            },
            success: function (mallOrder) {            
              if (mallOrder == 0) {
                $("div.orderList").html("");	
                $("div.orderList").html("<div style='margin-left: 100px; width: 100%;color: black;'><h2>查無訂單......</h2></div>");
              } else {
              	$("div.orderList").html("");
              	$("div.orderList").append(
              			`<table id="miyazaki">
              			<thead>
                          <tr><th>訂單編號<th>訂購人<th>收件人電話<th>總金額<th>訂單狀態<th>貨品狀態<th>訂單成立時間<th>操作
                          <tbody class="tbody">    	            
                          </tbody>
                          </table> `);            		
                
                  let mallOrder_item =
                    `<tr>
  		                <td>${mallOrder.mallOrderId}
  		                <td>${mallOrder.memberName}
  		                <td>${mallOrder.receiverPhone}
  		                <td>${mallOrder.mailOrderTotalAmount}
  		                <td>${mallOrder.mallOrderStatus == 0 ? "處理中" : mallOrder.mallOrderStatus == 1 ? "已確認" : "已完成"}
		                <td>${mallOrder.mallOrderDeliveryStatus == 0 ? "未發貨" : mallOrder.mallOrderDeliveryStatus == 1 ? "已發貨" : "已收貨"}
  		                <td>${mallOrder.mallOrderConfirmedTime}                
  		                <td><input class="button" type="button" value="查看訂單明細" onclick="location.href='/TFA104G5/back_end/companyProduct/html/productOrderListDetail.html?mallOrderId=${mallOrder.mallOrderId}'">`;
                  $(".tbody").append(mallOrder_item);
                
              }
            },
            complete: function (xhr) {
              // console.log(xhr);
            },
            error: function(xhr){           
                console.log(xhr);  
            }
        });    	
    });
	
	
    
	
	
	
    
	
	
	
	
});