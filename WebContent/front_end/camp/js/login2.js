$(document).ready(
				function() {
					$("ul > li:has(ol) > a").append(
							'<div class="arrow-bottom"></div>');
					$("ul > li ol li:has(ul) > a").append(
							'<div class="arrow-right"></div>');
				});


var favorlist=[];
document.addEventListener("DOMContentLoaded", function() {
	$.ajax({
		  url: "/TFA104G5/CampServlet2",           // 資料請求的網址
		  type: "GET",                  // GET | POST | PUT | DELETE | PATCH
		  data: {
			  'action':'islogin',			  
		  },                  // 傳送資料到指定的 url
		  dataType: "json",             // 預期會接收到回傳資料的格式： json | xml | html
		  timeout: 0,                   // request 可等待的毫秒數 | 0 代表不設定 timeout
		  beforeSend: function(){       // 在 request 發送之前執行
		  },
		  headers: {                    // request 如果有表頭資料想要設定的話
		    // "X-CSRF-Token":"abcde"   // 參考寫法
		  },
		  statusCode: {                 // 狀態碼
		    200: function (res) {
		    },
		    404: function (res) {
		    },
		    500: function (res) {
		    }
		  },
		  success: function(data){      // request 成功取得回應後執行
		    console.log(data);
		  

		    if(typeof(data['not-login'])=='undefined'){
		    	//更改header上面的東東
		    	sessionStorage.setItem('memberid', data['memberid']);
		    	var membershow=document.getElementsByClassName("islogin");
		    	var membershow_a=document.querySelectorAll("a.islogin");
		    	for(let i=0;i<membershow.length;i++){
		    		
		    		membershow[i].classList.remove("islogin");
		    	}
		    	for(let i=0;i<membershow_a.length;i++){
		    	
		    		membershow_a[i].classList.remove("islogin");
		    	}
		    	
		    	
		    	
		    	////把登入|註冊那欄抓出-2
		    	
		    	var memberlogin=document.getElementById("islogin2");	
		    	
		    	memberlogin.classList.add("islogin");
		    	
		    	
		    	//我最愛的營地id 存入session
		    	let list=data['favorlist'];
		    	for(let i=0;i<list.length;i++){
		    		favorlist.push(list[i].campId);
		    	}

		    }
		    		    
		  },
		  error: function(xhr){         // request 發生錯誤的話執行
		    console.log(xhr);
		  },
		  complete: function(xhr){      // request 完成之後執行(在 success / error 事件之後執行)
		    console.log(xhr);
		    console.log(favorlist);
		  }
		});
});



//登出
var logout=document.getElementsByClassName("out")[0];

logout.addEventListener("click",function(){
	

	sessionStorage.removeItem("memberid");
	$.ajax({
		  url: "/TFA104G5/member/MemberServlet",           // 資料請求的網址
		  type: "GET",                  // GET | POST | PUT | DELETE | PATCH
		   data: {
			   "action":"logout",               // 傳送資料到指定的 url
		   },
		  dataType: "text",             // 預期會接收到回傳資料的格式： json | xml | html
		  success: function(data){      // request 成功取得回應後執行
			alert("登出成功");
			
//			location.href='/TFA104G5/front_end/camp/camp_index.html';
			window.location.replace("/TFA104G5/front_end/camp/camp_index.html"); 
		  }
		});
	
	
	
});




