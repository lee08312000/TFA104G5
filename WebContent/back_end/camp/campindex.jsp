<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href='lib/main.css' rel='stylesheet' />
    <script src='lib/main.js'></script>

    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm" crossorigin="anonymous">
    <script src='https://momentjs.com/downloads/moment.min.js'></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <link rel="stylesheet" href="<%=request.getContextPath()%>/back_end/css/colorbox.css" />
<script src="<%=request.getContextPath()%>/back_end/js/jquery.colorbox.js"></script>
    <script src='../js/backcal.js?v=4'></script>
    <link href='../css/backcal.css?v=2' rel='stylesheet' />
    <link href='../css/aside.css' rel='stylesheet' />
    <link href="img/header/chuba_logo.png" rel="shortcut icon"/>
    <script>
    $(document).on('click', '.update', function(e) {
    	
		var campOrderId = $(this).attr("name");
		param = {
			data : {
				"action":"GETONECAMPNOUPDATE",
				"campOrderId" : campOrderId
			},
			title : "��a�q��",
			href : '<c:url value="/camp/campOrder.do" />',
			innerHeight : 350,
			innerWidth : 650,
			opacity : 0.5,
			top : 100
		};
		$.colorbox(param);
});
    </script>
      <title>��O�t�ӭ���</title>
    
<title>Insert title here</title>
</head>
<body>


<!-- --------head�ϰ�------- -->
	<header class="header-outer">
		<div class="header-inner responsive-wrapper">
			<div class="header-logo">
				<a style="display: inline-block; vertical-align: middle;"
					href="����URL"> <img
					src="<%=request.getContextPath()%>/back_end/images/camp_paradise_logo.png" />
				</a> <span style="display: inline-block; vertical-align: middle;">Camping
					Paradise</span>
			</div>
			   
       	
				<nav class="header-navigation">
					<a href="#">Home</a> <a href="#"></a>
					<c:if test ="${companyVO!=null}">
			                <li>${companyVO.getCompanyAccount()} �A�n</li>
			                <li><label for="logout">�n�X</label>
			                <form id="logoutForm" style="display: none;" method="post" action="<%=request.getContextPath()%>/Company/VendorLogoutServlet">
									<input type="hidden" name="action" value="logout">
									<input id="logout" type="submit" value="�n�X">
								</form></li>           
       			 </c:if>
       				<c:if test ="${companyVO==null}">
					   <a href="<%=request.getContextPath()%>/back_end/companyLogin/register.jsp">���U</a>  <a href="<%=request.getContextPath()%>/back_end/companyLogin/vendorLogin.jsp">�n�J</a> <a href="#"> <i class="fas fa-user"></i></a>
					</c:if> 
					<button>Menu</button>
				</nav>
			 
		</div>
	</header>
<main>
		<div class="dotdiv">
        <ul>
            <li><span class="dot" style="background-color:rgb(95, 151, 10);"></span><strong>��~��</strong></li>
            <li><span class="dot" style="background-color:gray;"></span><strong>�w�B��</strong></li>
            <li><span class="dot" style="background-color:red;"></span><strong>����</strong></li>
        </ul>
		</div>
        <div class="flexcontainer">
            <div id='calendar' class="cal"></div>

            <div style="float: inline-start;" class="closedset">
                <label for="bookdate" class="daynum">�s�W������ : </label>
                <input type="date" id="closeddate" name="closeddate" value="" required pattern="[0-9]{4}-[0-9]{2}-[0-9]{2}">
                <button type="button" id="closedbtn">����</button>
            </div>
        </div>

        <h3>�q����</h3>
        <div>
            <span>�Ƨ�:</span>
            <select style="float:inline-start;">
                <option value="0" selected>������(��->��)</option>
                <option value="1">�m�W(���e)</option>
                <option value="2">�q���`���B(�j->�p)</option>
                <option value="3">���A</option>
            </select>
        </div>


        <table class="products-table">

            <thead>
                <tr>
                    <th>�q��s��</th>
                    <th>check-out-date</th>
                    <th>�m�W</th>
                    <th>�p���q��</th>
                    <th>�`���B</th>
                    <th>�q�檬�A</th>
                    <th>�ާ@1</th>


                </tr>
            </thead>
            <tbody id="orderlist">


            </tbody>
        </table>
    </main>
<!-- --------aside�ϰ�------- -->
	<div id="sidebar">
		<aside class="aside">
			<div class="container">
				<nav>
					<ul class="mcd-menu">
						<li><a href="" class="light"> <i class="fa fa-campground"></i>
								<strong>��a�޲z</strong> <small>Camp Management</small>
						</a>
							<ul>
							    <li><a  href="<%=request.getContextPath()%>/back_end/camp/campindex.jsp"  ><i class="fas fa-cannabis"></i>�ڪ���a</a></li>			
								<li><a  href="<%=request.getContextPath()%>/back_end/camp/selectCamp.jsp"  ><i class="fas fa-cannabis"></i>��a�W�U�[</a></li>
							    <li><a  href="<%=request.getContextPath()%>/back_end/camp/selectCampCertificatenum.jsp"  ><i class="fas fa-cannabis"></i>��a�f�֪��p</a></li>								
							</ul>
						</li>
							
							
							
						<li><a href="" class="light"> <i class="fa fa-edit"></i>
								<strong>�ӫ~�޲z</strong> <small>Commodity </small>
						</a></li>
						<li><a href="" class="light"> <i class="fa fa-gift"></i>
								<strong>�q��޲z</strong> <small>Order </small>
						</a>
							<ul>
							   <li><a  href="<%=request.getContextPath()%>/back_end/camp/campindexs.jsp"  ><i class="fas fa-cannabis"></i>��{��޲z</a></li>			
							   <li><a  href="<%=request.getContextPath()%>/back_end/camp/listAllCampOrder.jsp" ><i class="fas fa-cannabis"></i>��a�q��޲z</a></li>								   
							   <li><a  href="<%=request.getContextPath()%>/back_end/companyProduct/html/productOrderList.html"><i class="fas fa-cannabis"></i>�ӫ��q��޲z</a></li>
							</ul></li>
						<li><a href="" class="light"> <i
								class="fas fa-calendar-week"></i> <strong>�t�Ӹ��</strong> <small>Vendor
									Information</small>
						</a>
							<ul>
							
							     <li><a  href="<%=request.getContextPath()%>/back_end/companyProduct/jsp/companyImformation.jsp" ><i class="fas fa-cannabis"></i>�򥻸���s���έק�</a></li>
								<li><a href="#"><i class="fas fa-cannabis"></i>���K�X</a></li>
							</ul></li>
						<li><a href="" class="light"> <i
								class="fa fa-comment-alt"></i> <strong>�ڪ�����</strong> <small>Comment</small>
						</a>
							<ul>
							<li><a  href="<%=request.getContextPath()%>/back_end/camp/campComment.jsp"  ><i class="fas fa-cannabis"></i>��a����</a></li>
							<li><a  href="<%=request.getContextPath()%>/back_end/companyProduct/jsp/vendorProductComment.jsp" ><i class="fas fa-cannabis"></i>�ӫ~����</a></li>
							</ul></li>
					</ul>
				</nav>
			</div>
		</aside>
	</div>

    <footer class="tm-footer text-center">
        <pre>�A�ȱM�u�G(02)2252-7966�@�@ �ȪA�ɶ��G�g�@�ܶg��9:00~18:00�@�@ �ȪA�H�c�Gcamp@easycamp.com.tw</pre>
        <pre>Copyright &copy; 2020 Simple House | Design: <a style="text-decoration: none;" rel="nofollow" href="#">TFA104�Ĥ���</a></pre>
    </footer>




    <script>
        function convertToISO(timebit) {
            // remove GMT offset

            timebit.setHours(0, -timebit.getTimezoneOffset(), 0, 0);
            // format convert and take first 10 characters of result
            var isodate = timebit.toISOString().slice(0, 10);
            return isodate;
        }

        var bookdate = document.getElementById('bookdate');
        var currentdate = new Date();
        bookdate.min = convertToISO(currentdate);
        bookdate.placeholder = bookdate.min;
        var futuredate = new Date();
        // go forward 7 days into the future
        futuredate.setDate(futuredate.getDate() + 365);
        bookdate.max = convertToISO(futuredate);
    </script>
    
    
</body>
</html>