window.addEventListener('load', function() {
    var calendarEl = document.getElementById('calendar');
//公休日陣列
    var closearray=[];

    let campURL = location.href;
    let githubURL = new URL(campURL);
    // 取得請求參數
    var querystring = githubURL.searchParams.toString();
    // console.log(querystring);
    // 解析請求參數
    var params = githubURL.searchParams;
   
    
    // 查看有無錯誤代碼
    var errormessage=params.get("errorcode");
    
  console.log(typeof errormessage);
    if(errormessage!=null){
    	var errorlist=document.getElementById("error");
    	console.log(errorlist);
    switch (errormessage) {
	case '1':
		errorlist.innerText="查無此資料，請重新選擇";
		errorlist.classList.remove("error_list");
		errorlist.classList.add("errorshow");
		break;
	case '2':
		errorlist.innerText="日期區間已滿位，請重新選擇";
		errorlist.classList.remove("error_list");
		errorlist.classList.add("errorshow");
		break;
	case '3':
		errorlist.innerText="日期區間包含公休日，請重新選擇";
		errorlist.classList.remove("error_list");
		errorlist.classList.add("errorshow");
		break;	
    }
    }
    
    

    var y = document.getElementById("campid");
    y.value = params.get("campid");



    var restlist = [];

    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        timeZone: 'Asia/Taipei',
        // initialDate: todaystr, //沒設定預設就是今天日期
        editable: false,

        buttonText: { // 按鈕文字
            today: '今天',
            prev: '上個月',
            next: '下個月'
        },
        selectable: false,
        businessHours: true,
        dayMaxEvents: true // allow "more" link when too many events

    });
    calendar.render();


    // 取得今天日期
    var today = document.querySelectorAll(".fc-day-today")[0];
    var todaystr = today.getAttribute("data-date");
    var loading = `<svg class="pl" viewBox="0 0 200 200" width="200" height="200" xmlns="http://www.w3.org/2000/svg">
    <defs>
        <linearGradient id="pl-grad1" x1="1" y1="0.5" x2="0" y2="0.5">
            <stop offset="0%" stop-color="hsl(313,90%,55%)" />
            <stop offset="100%" stop-color="hsl(223,90%,55%)" />
        </linearGradient>
        <linearGradient id="pl-grad2" x1="0" y1="0" x2="0" y2="1">
            <stop offset="0%" stop-color="hsl(313,90%,55%)" />
            <stop offset="100%" stop-color="hsl(223,90%,55%)" />
        </linearGradient>
    </defs>
    <circle class="pl__ring" cx="100" cy="100" r="82" fill="none" stroke="url(#pl-grad1)" stroke-width="36" stroke-dasharray="0 257 1 257" stroke-dashoffset="0.01" stroke-linecap="round" transform="rotate(-90,100,100)" />
    <line class="pl__ball" stroke="url(#pl-grad2)" x1="100" y1="18" x2="100.01" y2="182" stroke-width="36" stroke-dasharray="1 165" stroke-linecap="round" />
</svg>`

    $.ajax({
        url: "/TFA104G5/CampBookingServlet", // 資料請求的網址
        type: "GET", // GET | POST | PUT | DELETE | PATCH
        data: {
            "action": "calendar",
            "today": todaystr,
            "campid": params.get("campid")
        },
        dataType: "json", // 預期會接收到回傳資料的格式： json | xml | html
        timeout: 0, // request 可等待的毫秒數 | 0 代表不設定 timeout
        beforeSend: function() {
            document.getElementsByTagName("body")[0].insertAdjacentHTML("afterBegin", loading);
            document.getElementsByTagName("main")[0].classList.add("on");

        },
        headers: { // request 如果有表頭資料想要設定的話
            // "X-CSRF-Token":"abcde" // 參考寫法
        },
        statusCode: { // 狀態碼
            200: function(res) {},
            404: function(res) {},
            500: function(res) {}
        },
        success: function(data) { // request 成功取得回應後執行
            let sv = document.getElementsByTagName("svg")[0];
            var first = sv.firstElementChild;
            while (first) {
                first.remove();
                first = sv.firstElementChild;
            }
            document.getElementsByTagName("main")[0].classList.remove("on");

            console.log(data);


            // 抓取畫面上的每個日期
            // function addlist() {
            // var x = document.querySelectorAll("td.fc-daygrid-day");
            // for (let i = 0; i < x.length; i++) {
            // var oneday = x[i].getAttribute('data-date');

            for (let i = 0; i < data.length; i++) {
            	
                var dta = {
                    id: i,
                    title: (Object.values(data[i])[0] == -1) ? "今日公休" : (Object.values(data[i])[0] == 0) ? "已額滿" : "剩餘空位" + Object.values(data[i])[0],
                    start: Object.keys(data[i])[0],
                    end: Object.keys(data[i])[0],
                    allDay: true,
                    color: (Object.values(data[i])[0] == -1) ? 'red' : (Object.values(data[i])[0] == 0) ? "gray" : "green"

                }

                calendar.addEvent(dta);


                // 抓取公休日日期或位子額滿
                if (Object.values(data[i])[0] <= 0) {
                    restlist.push(Object.keys(data[i])[0]);

                }

            }
            console.log(restlist);



        }
   
    });


    var campimg = document.getElementById("campimg");
    campimg.setAttribute("src", "/TFA104G5/PicWithCampServlet?campid=" + params.get("campid") + "&pic=1")



    $.ajax({
        url: "/TFA104G5/CampBookingServlet", // 資料請求的網址
        type: "GET", // GET | POST | PUT | DELETE | PATCH
        data: {
            'action': 'getcampdata',
            'campid': params.get("campid")


        }, // 傳送資料到指定的 url
        dataType: "json", // 預期會接收到回傳資料的格式： json | xml | html
        success: function(data) { // request 成功取得回應後執行
           
            var Address = data.campAddress;
            var Name = data.campName;
            var Phone = data.campPhone;
            document.getElementsByClassName("camptext")[0].innerText = "營地名稱:" + Name;

            document.getElementsByClassName("camptext-in")[0].innerText = "營地地址:" + Address;

            document.getElementsByClassName("camptext-in")[1].innerText = "營地電話:" + Phone;

        }
    });


  //input=date 日期選單
    var cale=document.querySelectorAll("input[type=date]")[0];
  
    cale.addEventListener('change',function(){
    	
    	if(restlist.includes(String(cale.value))){
    		
    		alert("此日不開放營業，請重新選擇日期");
    		cale.value=null;
    		
    	}
    	
    	
    	
    });



});







// 點擊取得建立事件資訊
// calendar.on("eventClick", function(info) {

// console.log(info.event);

// });





// 日曆載入，抓當月每一個日期
// window.addEventListener("load", function() {
// let x = document.querySelectorAll("td.fc-daygrid-day");
// var datearray = [];
// setTimeout(function() {
// for (let i = 0; i <= x.length; i++) {
// let oneday = x[i].getAttribute("data-date");
// // console.log(oneday);
// datearray.push(oneday);
// }
// }, 4000);

// console.log(datearray);

// var prebtn = document.getElementsByClassName("fc-prev-button")[0];
// console.log(prebtn);
// prebtn.addEventListener("click", function() {
// let x = document.querySelectorAll("td.fc-daygrid-day");
// for (let i = 0; i <= x.length; i++) {
// console.log(x[i].getAttribute("data-date"));
// }
// });

// var nextbtn = document.getElementsByClassName("fc-next-button")[0];
// console.log(nextbtn);
// nextbtn.addEventListener("click", function() {
// let x = document.querySelectorAll("td.fc-daygrid-day");
// for (let i = 0; i <= x.length; i++) {
// console.log(x[i].getAttribute("data-date"));
// }
// });




// });
