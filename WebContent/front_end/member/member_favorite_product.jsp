<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.member.model.*"%> 
<!DOCTYPE html>
<html lang="zh-Hant">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"
        integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm" crossorigin="anonymous">
    <link href="css\member_favorite_product.css" rel="stylesheet" type="text/css">
    <title>我的最愛商品</title>
    <!-- frontawesome把icon引入的東東 -->
    <script src="https://kit.fontawesome.com/05a51b0b98.js" crossorigin="anonymous"></script>
</head>

<body>

    <header class="header">
        <div class="header-inner responsive-wrapper">
            <div class="header-logo">
                <a style="display:inline-block; vertical-align: middle;" href="首頁URL">
                    <img src="<%=request.getContextPath()%>/front_end/mall/images/camp_paradise_logo.png" />
                </a>
                <span style="display:inline-block; vertical-align: middle;">Camping Paradise</span>
            </div>
        </div>
        <ul>
            <nav class="header-navigation">
                <li><a href="#">Home</a></li>
                <li><a href="#">線上商城</a></li>
                <li><a href="#"><img src="<%=request.getContextPath()%>/front_end/mall/images/heart.png"></a></li>
                <li><a href="<%= request.getContextPath() %>/front_end/member/register.jsp" value="">註冊</a></li>
                <li><a href="<%= request.getContextPath() %>/front_end/member/login.jsp" value="">登入</a></li>
                <li><a href="<%= request.getContextPath() %>/front_end/member/member_main.jsp" value=""><i class="fas fa-user"></i></a></li>
            </nav>
        </ul>
    </header>

    <aside class="sidebar">
        <div id="leftside-navigation" class="nano">
            <ul class="nano-content">
                <li class="sub-menu">
                    <a href="javascript:void(0);"><i class="fas fa-heart"></i><span>&nbsp;我的最愛</span><i
                            class="arrow fa fa-angle-right pull-right"></i></a>
                    <ul>
                        <li><a href="">我的最愛營地</a>
                        </li>
                        <li><a href="">我的最愛商品</a>
                        </li>
                    </ul>
                </li>
                <li class="sub-menu">
                    <a href="javascript:void(0);"><i class="far fa-list-alt"></i><span>&nbsp;我的訂單</span><i
                            class="arrow fa fa-angle-right pull-right"></i></a>
                    <ul>
                        <li><a href="">營地訂單</a>
                        </li>
                        <li><a href="">商品訂單</a>
                        </li>
                    </ul>
                </li>
                <li class="sub-menu">
                    <a href="javascript:void(0);"><i class="fa fa-table"></i><span>&nbsp;修改資料</span><i
                            class="arrow fa fa-angle-right pull-right"></i></a>
                    <ul>
                        <li><a href="">修改會員資訊</a>
                        </li>

                        <li><a href="">修改密碼</a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href=""><i class="fas fa-sign-out-alt"></i><span>&nbsp;登出</span></a>
                </li>
        </div>
    </aside>

    <div class="table-title">
        <h3>我的最愛商品</h3>
    </div>
    <table class="table-fill">
        <thead>
            <tr>
                <th class="text-left">商品圖片</th>
                <th class="text-left">價格</th>
                <th class="text-left">販售廠商</th>
                <th class="text-left">評價</th>
                <th class="text-left"></th>
            </tr>
        </thead>

        <tbody class="table-hover">
            <tr>
                <td class="text-center"><img class="product_pic" src="" alt="商品圖像"></td>
                <td class="text-left">$ 50,000.00</td>
                <td class="text-left"></td>
                <td class="text-left"></td>
                <td class="text-center">
                    <button class="button" type="button">查詢</button>
                    <button class="button" type="button">刪除</button>
                </td>
            </tr>
            <tr>
                <td class="text-center"><img class="product_pic" src="" alt="商品圖像"></td>
                <td class="text-left">$ 10,000.00</td>
                <td class="text-left"></td>
                <td class="text-left"></td>
                <td class="text-center">
                    <button class="button" type="button">查詢</button>
                    <button class="button" type="button">刪除</button>
                </td>
            </tr>
            <tr>
                <td class="text-center"><img class="product_pic" src="" alt="商品圖像"></td>
                <td class="text-left">$ 85,000.00</td>
                <td class="text-left"></td>
                <td class="text-left"></td>
                <td class="text-center">
                    <button class="button" type="button">查詢</button>
                    <button class="button" type="button">刪除</button>
                </td>
            </tr>
        </tbody>
    </table>

    <script src="vandors\jQuery\jquery-3.6.0.min.js"></script>
    <script>
        $("#leftside-navigation .sub-menu > a").click(function (e) {
            $("#leftside-navigation ul ul").slideUp(), $(this).next().is(":visible") || $(this).next().slideDown(),
                e.stopPropagation()
        })
    </script>
</body>