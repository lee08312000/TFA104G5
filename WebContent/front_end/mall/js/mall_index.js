$(function () {

  // 按空的愛心
  $(document).on("click", "i.addFavoriteProduct", function () {
    // 檢查是否有登入
    //頁面導向
    let that = this;
    let addFavoriteProduct = {
      "action": "addFavoriteProduct",
      "productId": $(that).closest("div.product-item").attr("data-productId"),
    };
    
    $.ajax({
      url: "/TFA104G5/product/BrowseServlet",
      type: "POST",
      data: addFavoriteProduct,
      dataType: "json",
      beforeSend: function () {
      
      },
      success: function (data) {
        if (data.msg == "success") {
          $(that).addClass("-off");
          $(that).closest("span").find("img.icon").removeClass("-off");
          alert("成功加入我的最愛商品");
        } else if (data.msg == "isExisted") {
          alert("已加入我的最愛商品");
        } else if (data.msg == "noLogin") {
          alert("請先登入");
        } else if (data.msg == "denied") {
          alert("無法加入我的最愛商品");
        }
      },
      complete: function (xhr) {
        
      }
    });
  });

   // 按紅色愛心
   $(document).on("click", "img.redHeart", function () {
    // 檢查是否有登入
    //頁面導向
    let that = this;
    let deleteFavoriteProduct = {
      "action": "deleteFavoriteProduct",
      "productId": $(that).closest("div.product-item").attr("data-productId"),
    };
    
    $.ajax({
      url: "/TFA104G5/product/BrowseServlet",
      type: "POST",
      data: deleteFavoriteProduct,
      dataType: "json",
      beforeSend: function () {
      
      },
      success: function (data) {
        if (data.msg == "success") {
          $(that).addClass("-off");
          $(that).closest("span").find("i.addFavoriteProduct").removeClass("-off");
          alert("成功從我的最愛商品移除");
        } else if (data.msg == "isDeleted") {
          $(that).addClass("-off");
          $(that).closest("span").find("i.addFavoriteProduct").removeClass("-off");
          alert("已從我的最愛商品移除");
        } else if (data.msg == "noLogin") {
          alert("請先登入");
        } else if (data.msg == "denied") {
          alert("無法從我的最愛商品移除");
        }
      },
      complete: function (xhr) {
        
      }
    });
  });

  // 按購物車icon
  $(document).on("click", "i.addShoppingCart", function () {
    // 檢查是否有登入
    //頁面導向
    let that = this;
    let addProduct = {
      "action": "add",
      "productId": $(that).closest("div.product-item").attr("data-productId"),
      "productPurchaseQuantity": 1
    };

    $.ajax({
      url: "/TFA104G5/Cart/CartServlet",
      type: "POST",
      data: addProduct,
      dataType: "json",
      beforeSend: function () {
        $(that).css("color", "black");
      },
      success: function (data) {
        if (data.msg == "success") {
          alert("成功加入購物車");
        } else if (data.msg == "denied") {
          alert(`商品庫存量為${data.productInventory}, 您的選購數量已超過庫存量`);
        }
        refreshCartNum();
      },
      complete: function (xhr) {
        $(that).css("color", "gray");
      }
    });


  });

  // 首頁熱門商品3區塊
  $.ajax({
    url: "/TFA104G5/product/BrowseServlet",
    type: "POST",
    data: {
      "action": "getMallProducts3",
      "orderBy": 1
    },
    dataType: "json",
    beforeSend: function () {

    },
    success: function (data) {
      $.each(data, function (index, hotProducts) {
        let product_item =
          `<div class="col-md-4">
          <div data-productId="${hotProducts.productId}" data-productTypeId="${hotProducts.productTypeId}" class="product-item">
            <a href="mall_product_detail.html?productId=${hotProducts.productId}&productTypeId=${hotProducts.productTypeId}"><img src="/TFA104G5/product/PicServlet?productId=${hotProducts.productId}&pic=1" alt="${hotProducts.productName}"></a>
            <div class="down-content">
              <a href="mall_product_detail.html?productId=${hotProducts.productId}&productTypeId=${hotProducts.productTypeId}" title="${hotProducts.productName}">
                <h4 style="overflow : hidden; text-overflow :ellipsis; white-space: nowrap; width: 75%">
                  ${hotProducts.productName}
                </h4>
              </a>
              <h6>NT${hotProducts.productPrice}</h6>
              <p></p>
              <ul class="stars">
                <li><i class="fa fa-star" ${hotProducts.productCommentstarAvg >= 1 ? "" : "style='display:none'"}></i></li>
                <li><i class="fa fa-star" ${hotProducts.productCommentstarAvg >= 2 ? "" : "style='display:none'"}></i></li>
                <li><i class="fa fa-star" ${hotProducts.productCommentstarAvg >= 3 ? "" : "style='display:none'"}></i></li>
                <li><i class="fa fa-star" ${hotProducts.productCommentstarAvg >= 4 ? "" : "style='display:none'"}></i></li>
                <li><i class="fa fa-star" ${hotProducts.productCommentstarAvg >= 5 ? "" : "style='display:none'"}></i></li>
              </ul>
              <span><img class = "redHeart icon ${hotProducts.heart == 1 ? "" : "-off"}" style="position: relative; bottom: 6px;" src="images/heart.png"><i class="far fa-heart addFavoriteProduct ${hotProducts.heart == 0 ? "" : "-off"}" style="color: gray; font-size: 25px;"></i>&nbsp;&nbsp;<i class="fas fa-cart-plus addShoppingCart" style="color: gray; font-size: 25px;"></i></span>
            </div>
          </div>
        </div>`;

        $("div.hotProducts").append(product_item);
      });
    },
    complete: function (xhr) {
      // console.log(xhr);
    }
  });

  // // 從資料庫查熱門商品並列出
  // var hotProducts = {
  //   "productId": 1,
  //   "productTypeId": 1,
  //   "productName": "酷炫帳篷-L",
  //   "productPrice": 2000,
  //   "productCommentstarAvg": 4,
  //   "heart": 1
  // };

  // 首頁最新商品3區塊
  $.ajax({
    url: "/TFA104G5/product/BrowseServlet",
    type: "POST",
    data: {
      "action": "getMallProducts3",
      "orderBy": 2
    },
    dataType: "json",
    beforeSend: function () {

    },
    success: function (data) {
      $.each(data, function (index, latestProducts) {
        let product_item =
          `<div class="col-md-4">
        <div data-productId="${latestProducts.productId}" data-productTypeId="${latestProducts.productTypeId}" class="product-item">
          <a href="mall_product_detail.html?productId=${latestProducts.productId}&productTypeId=${latestProducts.productTypeId}"><img src="/TFA104G5/product/PicServlet?productId=${latestProducts.productId}&pic=1" alt="${latestProducts.productName}"></a>
          <div class="down-content">
            <a href="mall_product_detail.html?productId=${latestProducts.productId}&productTypeId=${latestProducts.productTypeId}">
              <h4 style="overflow : hidden; text-overflow :ellipsis; white-space: nowrap; width: 75%" title="${latestProducts.productName}">
                ${latestProducts.productName}
              </h4>
            </a>
            <h6>NT${latestProducts.productPrice}</h6>
            <p></p>
            <ul class="stars">
              <li><i class="fa fa-star" ${latestProducts.productCommentstarAvg >= 1 ? "" : "style='display:none'"}></i></li>
              <li><i class="fa fa-star" ${latestProducts.productCommentstarAvg >= 2 ? "" : "style='display:none'"}></i></li>
              <li><i class="fa fa-star" ${latestProducts.productCommentstarAvg >= 3 ? "" : "style='display:none'"}></i></li>
              <li><i class="fa fa-star" ${latestProducts.productCommentstarAvg >= 4 ? "" : "style='display:none'"}></i></li>
              <li><i class="fa fa-star" ${latestProducts.productCommentstarAvg >= 5 ? "" : "style='display:none'"}></i></li>
            </ul>
            <span><img class = "redHeart icon ${latestProducts.heart == 1 ? "" : "-off"}" style="position: relative; bottom: 6px;" src="images/heart.png"><i class="far fa-heart addFavoriteProduct ${latestProducts.heart == 0 ? "" : "-off"}" style="color: gray; font-size: 25px;"></i>&nbsp;&nbsp;<i class="fas fa-cart-plus addShoppingCart" style="color: gray; font-size: 25px;"></i></span>
          </div>
        </div>
      </div>`;

        $("div.latestProducts").append(product_item);
      });
    },
    complete: function (xhr) {
      // console.log(xhr);
    }
  });


  // var latestProducts = {
  //   "productId": 1,
  //   "productTypeId": 1,
  //   "productName": "酷炫帳篷-L",
  //   "productPrice": 2500,
  //   "productCommentstarAvg": 1,
  //   "heart": 0
  // };

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

});