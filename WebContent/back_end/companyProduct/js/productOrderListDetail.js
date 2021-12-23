$(function () {
	
	let mallOrderId = (new URL(location.href)).searchParams.get('mallOrderId');
	init();
	
	function init(){
		
		// 獲得商品詳細資訊
		$.ajax({
		  url: "/TFA104G5copy/mallOrder/CompanyBrowseServlet",
		  type: "POST",
		  data: {
		    "action": "getMallOrderDetail",
		    "mallOrderId": mallOrderId
		  },
		  dataType: "json",
		  beforeSend: function () {

		  },
		  success: function (mallOrderDetailList) {
		    if (mallOrderDetailList.length == 0) {
		      alert("查無資料");
		      location.href = "productOrderList.html";
		    } else {	    	
		    	$(".table-hover").html("");
		    	$.each(mallOrderDetailList, function (index, mallOrderDetail) {
		    		let detailAmount = (mallOrderDetail.productPurchasePrice)*(mallOrderDetail.productPurchaseQuantity);
		    		$(".mallOrderId").text(`訂單編號:  `+`${mallOrderDetail.mallOrderId}`);
		    		$(".mallOrderConfirmedTime").text(`訂單日期:  `+`${mallOrderDetail.mallOrderConfirmedTime}`);
		    		
		    		let mallOrderDetail_item =
		                  `<tr>
				                <td class="text-center" ><img src="/TFA104G5copy/product/PicServlet?productId=${mallOrderDetail.productId}&pic=1" style="width:120px;" alt="商品照片"></td>
				                <td class="text-left">${mallOrderDetail.productName}</td>
				                <td class="text-left">${mallOrderDetail.productPurchasePrice}</td>
				                <td class="text-left">${mallOrderDetail.productPurchaseQuantity}</td>
				                <td class="text-left">${detailAmount}</td>
				                <td class="text-left">${mallOrderDetail.mallOrderStatus == 0 ? "處理中" : mallOrderDetail.mallOrderStatus == 1 ? "已確認" : "已完成"}</td>
				                <td class="text-left">${mallOrderDetail.mallOrderDeliveryStatus == 0 ? "未發貨" : mallOrderDetail.mallOrderDeliveryStatus == 1 ? "已發貨" : "已收貨"}</td>
				            </tr>`;
		    		
		    			$(".table-hover").append(mallOrderDetail_item);	    	
	              });
		    	
		    	$(".table-hover").append(
		    	  `<tr>
		    			<td class="text-left" colspan="6">
				            <div>訂單總金額:  ${mallOrderDetailList[0].mailOrderTotalAmount}</div>
				            <div>收件人姓名:  ${mallOrderDetailList[0].receiverName}</div>
				            <div>收件人電話:  ${mallOrderDetailList[0].receiverPhone}</div>
				            <div>送貨地址:  ${mallOrderDetailList[0].receiverAddress}</div>
				        </td>
				        <td class="text-center" id="updateButton">
				                                
				        </td>
				    </tr>`);
		    	
		    	if(mallOrderDetailList[0].mallOrderStatus == 0){
		    		$("#updateButton").append(`<button class="updateButton" id="confirm" type="button">確認訂單</button>`);	
		    	}else if(mallOrderDetailList[0].mallOrderDeliveryStatus == 0){
		    		$("#updateButton").append(`<button class="updateButton" id="delivery" type="button">確認出貨</button>`);	
		    	}else{
		    		
		    	}

		    }
		  },
		  complete: function (xhr) {
		    // console.log(xhr);
		  }
		});		
	}
	//更改訂單狀態
	$(document).on("click", "#confirm", function (){
		$.ajax({
            url: "/TFA104G5copy/mallOrder/CompanyBrowseServlet",
            type: "POST",
            data: {
            	"action": "updateMallOrderStatus",
    		    "mallOrderId": mallOrderId
            },
            dataType: "json",
            beforeSend: function () {
              
            },
            success: function (mallOrder) {            
              alert("訂單狀態成功更新為已確認");               
              init();
            },
            complete: function (xhr) {
              // console.log(xhr);
            },
            error: function(xhr){           
                console.log("error");  
            }
        }); 	
    });
	
	//更改物流狀態
	$(document).on("click", "#delivery", function (){
		$.ajax({
            url: "/TFA104G5copy/mallOrder/CompanyBrowseServlet",
            type: "POST",
            data: {
            	"action": "updateMallOrderdDeliveryStatus",
    		    "mallOrderId": mallOrderId
            },
            dataType: "json",
            beforeSend: function () {
              
            },
            success: function (mallOrder) {            
              alert("物流狀態成功更新為已確認");               
              init();
            },
            complete: function (xhr) {
              // console.log(xhr);
            },
            error: function(xhr){           
                console.log("error");  
            }
        }); 	
    });
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
});