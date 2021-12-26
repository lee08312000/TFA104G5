document.addEventListener("DOMContentLoaded", function() {
	$.ajax({
		  url: "http://localhost:8081/TFA104G5/CampServlet2",           // 資料請求的網址
		  type: "GET",                  // GET | POST | PUT | DELETE | PATCH
		  data: {
			  'action':'islogin',			  
		  },                  // 傳送資料到指定的 url
		  dataType: "text",             // 預期會接收到回傳資料的格式： json | xml | html
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
		    if(data!="not-login" && Number(data)!=NaN){
		    	sessionStorage.setItem('memberid', data);
		    	var membershow=document.getElementsByClassName("islogin");
		    	var membershow_a=document.querySelectorAll("a.islogin");
		    	for(let i=0;i<membershow.length;i++){
		    		
		    		membershow[i].classList.remove("islogin");
		    	}
		    	for(let i=0;i<membershow_a.length;i++){
		    	
		    		membershow_a[i].classList.remove("islogin");
		    	}
		    	
		    	
		    	var memberlogin=document.getElementById("islogin");
		    	var Pmemberlogin=memberlogin.parentNode;
		    	memberlogin.classList.add("islogin");
		    	Pmemberlogin.classList.add("islogin");

		    }
		    
		    
		    
		    
		    
		    
		    
		    
		  },
		  error: function(xhr){         // request 發生錯誤的話執行
		    console.log(xhr);
		  },
		  complete: function(xhr){      // request 完成之後執行(在 success / error 事件之後執行)
		    console.log(xhr);
		  }
		});
	
	
	
	
	
})