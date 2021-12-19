<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.member.model.*"%> 
   
    
    <%
    // 會員假資料
    // MemberService memberSvc = new MemberService();
    // MemberVO memberVO = memberSvc.getOneMember(1);
    // request.setAttribute("memberVO", memberVO);
 	// 會員假資料
    %>  
    
<!DOCTYPE html>
<html lang="zh-Hant">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"
        integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm" crossorigin="anonymous">
    <link href="<%=request.getContextPath()%>/front_end/member/css/member_main.css" rel="stylesheet" type="text/css">
    <title>會員主頁</title>
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
                        <li><a href="<%= request.getContextPath() %>/front_end/member/member_favorite_product.jsp" value="">我的最愛商品</a>
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
                        <li>
                        <a href="<%= request.getContextPath() %>/front_end/member/member_reset_info.jsp" value="">修改會員資訊與密碼</a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href=""><i class="fas fa-sign-out-alt"></i><span>&nbsp;登出</span></a>
                </li>
        </div>
    </aside>

    <div class="main_content">
    <img class="member_pic" src="<%=request.getContextPath()%>/member/PicServlet?memberId=${ memberVO.memberId }">
        <input class="button" type="submit" value="頭貼上傳" />
        <a>歡迎 ${ memberVO.memberName } 登入</a>
    </div>

    <div class="main_content2">
        <table>
            <tr>
                <td>姓名</td>
                <td>${ memberVO.memberName }</td>
            </tr>
            <tr>
                <td>會員帳號</td>
                <td>${ memberVO.memberAccount }</td>
            </tr>
            <tr>
                <td>地址</td>
                <td>${ memberVO.memberAddress }</td>
            </tr>
            <tr>
                <td>Email</td>
                <td>${ memberVO.memberEmail }</td>
            </tr>
            <tr>
                <td>手機號碼</td>
                <td>${ memberVO.memberPhone }</td>
            </tr>
        </table>
    </div>

    <script src="<%=request.getContextPath()%>/front_end/member/vandors/jQuery/jquery-3.6.0.min.js"></script>
    <script>
        $("#leftside-navigation .sub-menu > a").click(function (e) {
            $("#leftside-navigation ul ul").slideUp(), $(this).next().is(":visible") || $(this).next().slideDown(),
                e.stopPropagation()
        })
    </script>
</body>