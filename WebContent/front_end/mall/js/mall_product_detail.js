$(function () {

  let page = 1;
  // 右邊商品加入我的最愛 按空的愛心
  $(document).on("click", "i.addRightFavoriteProduct", function () {
    
    let that = this;
    let addFavoriteProduct = {
      "action": "addFavoriteProduct",
      "productId": $("div.right-content").attr("data-productId"),
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

// 右邊商品加入我的最愛 按紅的愛心
$("img#right-redHeart").on("click", function(){

  let that = this;
    let deleteFavoriteProduct = {
      "action": "deleteFavoriteProduct",
      "productId": $("div.right-content").attr("data-productId"),
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
          $(that).closest("span").find("i.addRightFavoriteProduct").removeClass("-off");
          alert("成功從我的最愛商品移除");
        } else if (data.msg == "isDeleted") {
          $(that).addClass("-off");
          $(that).closest("span").find("i.addRightFavoriteProduct").removeClass("-off");
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

  // 右邊商品加入購物車
  $("input#addShoppingCart").on("click", function () {
    
    if ($("input#quantity").val() <= 0) {
      alert("請輸入大於0的數量");
      $("input#quantity").val(1);
    } else {

      let that = this;
      let addProduct = {
        "action": "add",
        "productId": $("div.right-content").attr("data-productId"),
        "productPurchaseQuantity": $("input#quantity").val()
      };
  
      $.ajax({
        url: "/TFA104G5/Cart/CartServlet",
        type: "POST",
        data: addProduct,
        dataType: "json",
        beforeSend: function () {
          $(that).css("background-color", "black");
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
          $(that).css("background-color", "#3a8bcd");
        }
      });

    }

  });
  
  // 商品評論排序
  $("select#productCommentOrderBy").on("change", function() {
    page = 1;
    $("div#productComments").html("");
    getProductComments(page);
  });

  // 商品評論查看更多
  $("button#moreCommentsBtn").on("click", function(){
    getProductComments(++page);
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



////////////////////////// 商品右半邊資訊 ////////////////////////////
// 從URL取參數給api查詢
let productId = (new URL(location.href)).searchParams.get('productId');
let productTypeId = (new URL(location.href)).searchParams.get('productTypeId');
// console.log(productIdValue);
// console.log(productTypeIdValue);

// 獲得商品詳細資訊
$.ajax({
  url: "/TFA104G5/product/BrowseServlet",
  type: "POST",
  data: {
    "action": "getMallProductDetail",
    "productId": productId
  },
  dataType: "json",
  beforeSend: function () {

  },
  success: function (product) {
    if (product.length == 0) {
      alert("查無資料");
      location.href = "mall_products_list.html";
    } else {
      // 商品編號
      $("div.right-content").attr("data-productId", product.productId);
      $("article#productReport").attr("data-productId", product.productId);
      // 商品名
      $("h4#productName").prepend(product.productName);
      $("span#reportProductName").text(product.productName);

      // 愛心
      if (product.heart == 1) {
        $("div.right-content img#right-redHeart").removeClass("-off");
        $("div.right-content i#right-grayHeart").addClass("-off");
      }
      // 價格
      $("span#right-price").append(product.productPrice);
      // 星星數
      var rightStar =
        `<li><i class="fa fa-star" ${product.productCommentstarAvg >= 1 ? "" : "style='display:none'"}></i></li>
                  <li><i class="fa fa-star" ${product.productCommentstarAvg >= 2 ? "" : "style='display:none'"}></i></li>
                  <li><i class="fa fa-star" ${product.productCommentstarAvg >= 3 ? "" : "style='display:none'"}></i></li>
                  <li><i class="fa fa-star" ${product.productCommentstarAvg >= 4 ? "" : "style='display:none'"}></i></li>
                  <li><i class="fa fa-star" ${product.productCommentstarAvg >= 5 ? "" : "style='display:none'"}></i></li>`;
      $("ul#right-star").append(rightStar);
      // 商品描述
      $("p#right-productInfo").text(product.productDescription);
      // 庫存量
      $("span#right-inventory").append(product.productInventory);
      // 商品分類
      $("a#right-productTypeName").text(product.productTypeName);
      $("a#right-productTypeName").attr("href", `mall_products_list.html?productTypeId=${product.productTypeId}`);
      $("span#right-productBrand").text(product.productBrand);
      // 上架時間
      $("span#right-launchedTime").text(product.productLaunchedTime);
      // 主商品圖片
      $("img#bigPic1").attr("src", `/TFA104G5/product/PicServlet?productId=${product.productId}&pic=1`);
      $("img#bigPic2").attr("src", `/TFA104G5/product/PicServlet?productId=${product.productId}&pic=2`);
      $("img#bigPic3").attr("src", `/TFA104G5/product/PicServlet?productId=${product.productId}&pic=3`);

      $("img#smallPic1").attr("src", `/TFA104G5/product/PicServlet?productId=${product.productId}&pic=1`);
      $("img#smallPic2").attr("src", `/TFA104G5/product/PicServlet?productId=${product.productId}&pic=2`);
      $("img#smallPic3").attr("src", `/TFA104G5/product/PicServlet?productId=${product.productId}&pic=3`);
      // 商品購買須知
      $("p#shoppingInfo").text(product.shoppingInformation);

    }
  },
  complete: function (xhr) {
    // console.log(xhr);
  }
});

// 從資料庫調出商品評論
function getProductComments(page) {

  $.ajax({
    url: "/TFA104G5/MallOrderDetail/MallOrderDetailServlet",
    type: "POST",
    data: {
      "action": "getProductComments",
      "productId": productId,
      "orderType": $("select#productCommentOrderBy").val(),
      "page": page
    },
    dataType: "json",
    beforeSend: function () {
      $("div#productComments").append("<h3 id='searching'>正在查詢</h3>");
    },
    success: function (productCommentList) {
      // $("div#productComments").html("");

      if (productCommentList.length == 0) {
        if (page == 1) {
          $("div#productComments").html("<h3>尚無評論</h3>");
        }

      } else {

        $.each(productCommentList, function(index, item) {

          let productComment =
        `<h5>${item.memberName}</h5>
        <span>
          <ul style="color: orange;" class="stars">
          <li><i class="fa fa-star" ${item.productCommentStar >= 1 ? "" : "style='display:none'"}></i></li>
          <li><i class="fa fa-star" ${item.productCommentStar >= 2 ? "" : "style='display:none'"}></i></li>
          <li><i class="fa fa-star" ${item.productCommentStar >= 3 ? "" : "style='display:none'"}></i></li>
          <li><i class="fa fa-star" ${item.productCommentStar >= 4 ? "" : "style='display:none'"}></i></li>
          <li><i class="fa fa-star" ${item.productCommentStar >= 5 ? "" : "style='display:none'"}></i></li>
          </ul>
        </span>
        <p>${item.productComment}</p>
        <p>${item.productCommentTime}</p>
        <hr>`;
  
        $("div#productComments").append(productComment);
        });
   
      }
      
      if (productCommentList.length >= 6) {
        $("button#moreCommentsBtn").removeClass("-off");
      } else {
        $("button#moreCommentsBtn").addClass("-off");
      }
     

    },
    complete: function (xhr) {
      $("h3#searching").remove();
    }
  });
}

getProductComments(page);
});
// var product = {
//   "productId": 1,
//   "productTypeId": 2,
//   "productTypeName": "衣服",
//   "productBrand": "露營玩家",
//   "productName": "酷炫帳篷-L",
//   "productDescription": "是一個很酷的帳篷這是一個很酷的帳篷這是一個很酷的帳篷這是一個很酷的帳篷這是一個很酷的帳篷這是一個很酷的帳篷這是一個很酷的帳篷",
//   "productCommentstarAvg": 4,
//   "heart": 0,
//   "productLaunchedTime": "2021-11-19",
//   "productPrice": 5000,
//   "productInventory": 39,
//   "shoppingInformation": "快買就對了!!!"
// };
