/* Set rates + misc */
var taxRate = 0;
var shippingRate = 0;
var fadeTime = 0;


/* Assign actions */
$('.product-quantity input').change(function () {
    //        	console.log("123");
    let that = this;
    if ($(that).val().trim() <= 0 || $(that).val().trim() == "") {

        $(that).closest("div.product").find('.product-removal button').eq(0).click();

        updateQuantity(that);
        let subtotal = 0;
        let orederSmallMoney = $(that).closest("div.oneOrder").find("div.totals-value").eq(0);
    
        let line_prices = $(that).closest("div.oneOrder").find("div.product-line-price");
        line_prices.each(function (index, item) {
            subtotal = parseInt(subtotal) + parseInt($(item).text());
        });
        orederSmallMoney.text(subtotal);

    } else {
        // 去後端session改商品數量
        let updateProduct = {
            "action": "updateByAjax",
            "productId": $(that).closest("div.product").attr("data-productid"),
            "productPurchaseQuantity": $(that).val()
        };

        $.ajax({
            url: "/TFA104G5/Cart/CartServlet",
            type: "POST",
            data: updateProduct,
            dataType: "json",
            beforeSend: function () {
                $(that).prop("disabled",true);
            },
            success: function (data) {
                if (data.msg == "success") {
                    console.log("修改成功");
                } else if (data.msg == "denied") {
                    $(that).val(data.productInventory);
                    console.log("修改失敗");
                    alert("您的購買數量已超過庫存量");
                }
                refreshCartNum();
            },
            complete: function (xhr) {
                $(that).prop("disabled",false);
                updateQuantity(that);
                let subtotal = 0;
                let orederSmallMoney = $(that).closest("div.oneOrder").find("div.totals-value").eq(0);
            
                let line_prices = $(that).closest("div.oneOrder").find("div.product-line-price");
                line_prices.each(function (index, item) {
                    subtotal = parseInt(subtotal) + parseInt($(item).text());
                });
                orederSmallMoney.text(subtotal);
            }
        });

    }

});

// 刪除購物車session中的商品
$('.product-removal button').click(function () {
    let that = this;

    let deleteProduct = {
        "action": "delete",
        "productId": $(that).closest("div.product").attr("data-productid")
    };

    $.ajax({
        url: "/TFA104G5/Cart/CartServlet",
        type: "POST",
        data: deleteProduct,
        dataType: "json",
        beforeSend: function () {

        },
        success: function (data) {
            if (data.msg == "success") {
                console.log("刪除成功");
                removeItem(that);
            }
            refreshCartNum();
        },
        complete: function (xhr) {
            // console.log(xhr);
        }
    });

});


/* Recalculate cart */
function recalculateCart() {
    var subtotal = 0;

    /* Sum up row totals */
    $('.product').each(function () {
        subtotal += parseFloat($(this).children('.product-line-price').text());
    });

    /* Calculate totals */
    var total = subtotal;

    /* Update totals display */
    $('.totals-value').fadeOut(fadeTime, function () {
        // $('.totals-value').html(subtotal.toFixed(2));


        $('#cart-total').html(total);
        if (total == 0) {
            $('.checkout').fadeOut(fadeTime);
        } else {
            $('.checkout').fadeIn(fadeTime);
        }
        $('.totals-value').fadeIn(fadeTime);
    });

    cartIsEmpty();
}


/* Update quantity */
function updateQuantity(quantityInput) {
    /* Calculate line price */
    var productRow = $(quantityInput).parent().parent();
    var price = productRow.children('.product-price').text();
    var quantity = $(quantityInput).val();
    var linePrice = price * quantity;

    /* Update line price display and recalc cart totals */
    productRow.children('.product-line-price').each(function () {
        $(this).fadeOut(fadeTime, function () {
            $(this).text(linePrice);
            recalculateCart();
            $(this).fadeIn(fadeTime);
        });
    });
}


/* Remove item from cart */
function removeItem(removeButton) {
    /* Remove row from DOM and recalc cart total */
    var productRow = $(removeButton).parent().parent();
    let removeMonry = productRow.find("div.product-line-price").text();
    let subtotal = 0;
    // console.log($(this).parent().parent().parent().find("div.totals-value").eq(0));
    let orederSmallMoney = $(removeButton).closest("div.oneOrder").find("div.totals-value").eq(0);
    let line_prices = $(removeButton).closest("div.oneOrder").find("div.product-line-price");
    line_prices.each(function (index, item) {
        subtotal = parseInt(subtotal) + parseInt($(item).text());
    });
    orederSmallMoney.text(subtotal - removeMonry);

    productRow.slideUp(fadeTime, function () {

        if ($(removeButton).closest("div.oneOrder").find("div.product").length == 1) {
            let oneOrder = $(removeButton).closest("div.oneOrder");
            oneOrder.remove();
        }
        productRow.remove();
        recalculateCart();
    });
}

// 一進來頁面的動作
$("div.oneOrder").each(function (index, item) {

    let subtotal = 0;
    // console.log($(this).parent().parent().parent().find("div.totals-value").eq(0));
    let orederSmallMoney = $(this).find("div.totals-value").eq(0);

    let line_prices = $(this).find("div.product-line-price");
    line_prices.each(function (index, item) {
        subtotal = parseInt(subtotal) + parseInt($(item).text());
    });
    orederSmallMoney.text(subtotal);
});


recalculateCart();

// 判斷購物車為空的話，加入文字
function cartIsEmpty() {
    if ($("div.shopping-cart").find("div.oneOrder").length == 0) {
        $("div.shopping-cart").prepend("<h1 style='text-align: center;'>購物車是空的!!!<br><a href='/TFA104G5/front_end/mall/mall_products_list.html'>去逛街~</a></h1>");
    };
};

$("button#inputReceiverInfo").on("click", function () {
    let that = this;

    $.ajax({
        url: "/TFA104G5/Cart/CartServlet",
        type: "POST",
        data: {
            "action":"getMemberInfo"
        },
        dataType: "json",
        beforeSend: function () {
            $(that).prop("disabled", true);
        },
        success: function (data) {
            if (data.msg == "success") {
                $("input#receiverName").val(data.memberName);
                $("input#receiverPhone").val(data.memberPhone);
                $("input#receiverAddress").val(data.memberAddress);
                console.log("得到會員資料");
            } else if (data.msg == "denied") {
                console.log("查無會員資料");
            }
        },
        complete: function (xhr) {
            $(that).prop("disabled", false);
        }
    });

});

// 購物車小紅點
function refreshCartNum() {

    $.ajax({
      url: "/TFA104G5/Cart/CartServlet",
      type: "POST",
      data: {
        "action": "getCartNum"
      },
      dataType: "json",
      beforeSend: function () {
        
      },
      success: function (data) {
        if (data.cartNum != 0) {
          $("div#cartNum").removeClass("-off");
          $("div#cartNum").text(data.cartNum);
        } else {
          $("div#cartNum").addClass("-off");
        }
      },
      complete: function (xhr) {
        
      }
    });
  }


  refreshCartNum();

