$(function () {

  // 分頁按鈕
  $(document).on("click", "ul.pages li", function (e) {
    e.preventDefault();
    let that = this;
    $("ul.pages li").removeClass("active");
    $(that).addClass("active");

    let searchValue = (new URL(location.href)).searchParams.get('search');
    if (searchValue != null && searchValue.trim() != "") {

      // 利用searchValue 去資料庫搜尋商品名稱
      useSearchBar(searchValue.trim(), $(that).children("a").text());
    } else {
      getProducts($(that).children("a").text());
    }

    window.scrollTo(0, 350);

  });

  // 按空的愛心
  $(document).on("click", "i.addFavoriteProduct", function () {

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



  // 按商品排序查詢商品
  $("select.orderBy").on("change", function () {
    initSearch();
  });
  // 按廠商查詢商品
  $("select.company").on("change", function () {
    initSearch();
  });

  // 點擊商品分類變色，從資料庫撈出此分類的資料
  $(document).on("click", "div.filters > ul > li", function () {
    let that = this;
    $("div.filters > ul > li").removeClass("active");
    $(that).addClass("active");
    history.replaceState(null, "page2", "mall_products_list.html");

    // 從資料庫撈出商品
    getProducts(1);
    getPages();
  });
 
 // 從網址抓參數
  function initSearch() {

    let searchValue = (new URL(location.href)).searchParams.get('search');

    let productTypeIdValue = (new URL(location.href)).searchParams.get('productTypeId');

    if (searchValue != null && searchValue.trim() != "") {

      $("div.filters li.active").removeClass("active");
      // 利用searchValue 去資料庫搜尋商品名稱
      useSearchBar(searchValue.trim(), 1);
      getPagesByProductName(searchValue.trim());

    } else if (productTypeIdValue != null && productTypeIdValue != "") {

      $("div.filters > ul li").each(function (index, item) {
        if ($(item).attr("data-productTypeId") == productTypeIdValue) {
          $(item).click();
        }
      });


    } else {
      getProducts(1);
      getPages();
    }
  }

  // 此方法為去資料庫搜尋商品名稱
  function useSearchBar(productName, page) {
    $.ajax({
      url: "/TFA104G5/product/BrowseServlet",
      type: "POST",
      data: {
        "action": "getMallProductsByName",
        "productName": productName,
        "companyId": $("select.company").val(),
        "orderType": $("select.orderBy").val(),
        "page": page
      },
      dataType: "json",
      beforeSend: function () {
        $("div.row.grid").html("<div style='text-align: center; width: 100%;'><h2>正在查詢...</h2></div>");
      },
      success: function (productList) {
        $("div.row.grid").html("");
        if (productList.length == 0) {
          $("div.row.grid").html("<div style='text-align: center; width: 100%;'><h2>查無商品</h2></div>");
        } else {

          $.each(productList, function (index, product) {
            let product_item =
              `<div class="col-md-4">
                <div data-productId="${product.productId}" data-productTypeId="${product.productTypeId}" class="product-item">
                  <a href="mall_product_detail.html?productId=${product.productId}&productTypeId=${product.productTypeId}"><img src="/TFA104G5/product/PicServlet?productId=${product.productId}&pic=1" alt="${product.productName}"></a>
                  <div class="down-content">
                    <a href="mall_product_detail.html?productId=${product.productId}&productTypeId=${product.productTypeId}" title="${product.productName}">
                      <h4 style="overflow : hidden; text-overflow :ellipsis; white-space: nowrap; width: 75%">
                        ${product.productName}
                      </h4>
                    </a>
                    <h6>NT${product.productPrice}</h6>
                    <p></p>
                    <ul class="stars">
                      <li><i class="fa fa-star" ${product.productCommentstarAvg >= 1 ? "" : "style='display:none'"}></i></li>
                      <li><i class="fa fa-star" ${product.productCommentstarAvg >= 2 ? "" : "style='display:none'"}></i></li>
                      <li><i class="fa fa-star" ${product.productCommentstarAvg >= 3 ? "" : "style='display:none'"}></i></li>
                      <li><i class="fa fa-star" ${product.productCommentstarAvg >= 4 ? "" : "style='display:none'"}></i></li>
                      <li><i class="fa fa-star" ${product.productCommentstarAvg >= 5 ? "" : "style='display:none'"}></i></li>
                    </ul>
                    <span><img class = "redHeart icon ${product.heart == 1 ? "" : "-off"}" style="position: relative; bottom: 6px;" src="images/heart.png"><i class="far fa-heart addFavoriteProduct ${product.heart == 0 ? "" : "-off"}" style="color: gray; font-size: 25px;"></i>&nbsp;&nbsp;<i class="fas fa-cart-plus addShoppingCart" style="color: gray; font-size: 25px;"></i></span>
                  </div>
                </div>
              </div>`;

            $("div.row.grid").append(product_item);
          });
        }
      },
      complete: function (xhr) {
        // console.log(xhr);
      }
    });
  }

  // 此方法為用商品類別去資料庫搜尋商品
  function getProducts(page) {
    $.ajax({
      url: "/TFA104G5/product/BrowseServlet",
      type: "POST",
      data: {
        "action": "getMallProducts",
        "productTypeId": $("ul#productTypes > li.active").attr("data-productTypeId"),
        "companyId": $("select.company").val(),
        "orderType": $("select.orderBy").val(),
        "page": page
      },
      dataType: "json",
      beforeSend: function () {
        $("div.row.grid").html("<div style='text-align: center; width: 100%;'><h2>正在查詢...</h2></div>");
      },
      success: function (productList) {
        $("div.row.grid").html("");
        if (productList.length == 0) {
          $("div.row.grid").html("<div style='text-align: center; width: 100%;'><h2>查無商品</h2></div>");
        } else {

          $.each(productList, function (index, product) {
            let product_item =
              `<div class="col-md-4">
                <div data-productId="${product.productId}" data-productTypeId="${product.productTypeId}" class="product-item">
                  <a href="mall_product_detail.html?productId=${product.productId}&productTypeId=${product.productTypeId}"><img src="/TFA104G5/product/PicServlet?productId=${product.productId}&pic=1" alt="${product.productName}"></a>
                  <div class="down-content">
                    <a href="mall_product_detail.html?productId=${product.productId}&productTypeId=${product.productTypeId}" title="${product.productName}">
                      <h4 style="overflow : hidden; text-overflow :ellipsis; white-space: nowrap; width: 75%">
                        ${product.productName}
                      </h4>
                    </a>
                    <h6>NT${product.productPrice}</h6>
                    <p></p>
                    <ul class="stars">
                      <li><i class="fa fa-star" ${product.productCommentstarAvg >= 1 ? "" : "style='display:none'"}></i></li>
                      <li><i class="fa fa-star" ${product.productCommentstarAvg >= 2 ? "" : "style='display:none'"}></i></li>
                      <li><i class="fa fa-star" ${product.productCommentstarAvg >= 3 ? "" : "style='display:none'"}></i></li>
                      <li><i class="fa fa-star" ${product.productCommentstarAvg >= 4 ? "" : "style='display:none'"}></i></li>
                      <li><i class="fa fa-star" ${product.productCommentstarAvg >= 5 ? "" : "style='display:none'"}></i></li>
                    </ul>
                    <span><img class = "redHeart icon ${product.heart == 1 ? "" : "-off"}" style="position: relative; bottom: 6px;" src="images/heart.png"><i class="far fa-heart addFavoriteProduct ${product.heart == 0 ? "" : "-off"}" style="color: gray; font-size: 25px;"></i>&nbsp;&nbsp;<i class="fas fa-cart-plus addShoppingCart" style="color: gray; font-size: 25px;"></i></span>
                  </div>
                </div>
              </div>`;

            $("div.row.grid").append(product_item);
          });
        }
      },
      complete: function (xhr) {
        // console.log(xhr);
      }
    });
  }

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

  // 調出分頁
  function getPages() {
    $.ajax({
      url: "/TFA104G5/product/BrowseServlet",
      type: "POST",
      data: {
        "action": "getMallProducts",
        "productTypeId": $("ul#productTypes > li.active").attr("data-productTypeId"),
        "companyId": $("select.company").val(),
        "orderType": $("select.orderBy").val(),
        "page": 0
      },
      dataType: "json",
      beforeSend: function () {
        $("div.row.grid").html("<div style='text-align: center; width: 100%;'><h2>正在查詢...</h2></div>");
      },
      success: function (productList) {
        let pageNum = productList.length / 6;
        let pageNumMod = productList.length % 6;

        if (pageNumMod != 0) {
          pageNum = pageNum + 1;
        }

        let pagesStr = "";
        for (var i = 1; i <= pageNum; i++) {
          pagesStr +=
            `<li ${i == 1 ? "class='active'" : ""}><a href="#">${i}</a></li>`;
        }

        $("ul.pages").html("");
        $("ul.pages").html(pagesStr);
      },
      complete: function (xhr) {
        // console.log(xhr);
      }
    });


  }

  // 調出分頁by商品名稱
  function getPagesByProductName(productName) {
    $.ajax({
      url: "/TFA104G5/product/BrowseServlet",
      type: "POST",
      data: {
        "action": "getMallProductsByName",
        "productName": productName,
        "companyId": $("select.company").val(),
        "orderType": $("select.orderBy").val(),
        "page": 0
      },
      dataType: "json",
      beforeSend: function () {
        $("div.row.grid").html("<div style='text-align: center; width: 100%;'><h2>正在查詢...</h2></div>");
      },
      success: function (productList) {
        let pageNum = productList.length / 6;
        let pageNumMod = productList.length % 6;

        if (pageNumMod != 0) {
          pageNum = pageNum + 1;
        }

        let pagesStr = "";
        for (var i = 1; i <= pageNum; i++) {
          pagesStr +=
            `<li ${i == 1 ? "class='active'" : ""}><a href="#">${i}</a></li>`;
        }

        $("ul.pages").html("");
        $("ul.pages").html(pagesStr);
      },
      complete: function (xhr) {
        // console.log(xhr);
      }
    });

  }

  // init() 為一進頁面的動作
  function init() {
    // 從資料庫調出商品分類
    $.ajax({
      url: "/TFA104G5/product/BrowseServlet",
      type: "POST",
      data: {
        "action": "getProductType"
      },
      dataType: "json",
      beforeSend: function () {

      },
      success: function (productTypeList) {
        $.each(productTypeList, function (index, item) {

          let product_type = `<li data-productTypeId="${item.productTypeId}" style="background-color: #d7ab75; color: white; border-radius: 20px; padding: 0 5px;">${item.productTypeName}</li>`;

          $("div.filters > ul").append(product_type);
        });

        initSearch();
      },
      complete: function (xhr) {

      }
    });

    // 從資料庫撈出廠商
    $.ajax({
      url: "/TFA104G5/product/BrowseServlet",
      type: "POST",
      data: {
        "action": "getCompany"
      },
      dataType: "json",
      beforeSend: function () {

      },
      success: function (companyList) {
        $.each(companyList, function (index, item) {

          let company = `<option value="${item.companyId}">${item.companyName}</option>`;

          $("select.company").append(company);
        });
      },
      complete: function (xhr) {

      }
    });

    refreshCartNum();
  }

  init();
});
