window.addEventListener('load', function() {
    var calendarEl = document.getElementById('calendar');



    // let campURL = location.href;
    // let githubURL = new URL(campURL);
    // //取得請求參數
    // var querystring = githubURL.searchParams.toString();
    // // console.log(querystring);
    // //解析請求參數
    // var params = githubURL.searchParams;

    // var y = document.getElementById("campid");
    // y.value = params.get("campid"); //URL 的campid


    //選染日曆
    var restlist = [];

    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        timeZone: 'Asia/Taipei',
        editable: false,
        eventStartEditable: false, //允許事件的開始時間可通過拖動進行編輯
        initialDate: new Date(),
        buttonText: { //按鈕文字
            today: "這個月",
            prev: "上個月",
            next: "下個月"
        },
        selectable: false,
        businessHours: true,
        dayMaxEvents: true, // allow "more" link when too many events

    });

    calendar.render();



    ////////////////////////////////////////////////////////////////////////////////////
    //抓取畫面上的月份
    var title = calendarEl.getElementsByClassName("fc-toolbar-title")[0].innerText;
    var year = title.substr(-4, 4).trim();
    var month = title.substring(0, title.length - 4).trim();
    console.log(title);
    console.log(month);
    let monthNum = getnumMonth(month);
    console.log(year);
    getmonthseat(monthNum, year);



    //取得今天日期
    var today = document.querySelectorAll(".fc-day-today")[0];
    var todaystr = today.getAttribute("data-date");



    //綁定今天/上/下個月按鈕事件
    var bt = document.getElementsByClassName("fc-button");

    //抓取畫面上的月份
    $(bt).each(function() {
        $(this).click(function() {
            title = calendarEl.getElementsByClassName("fc-toolbar-title")[0].innerText;
            month = title.substring(0, title.length - 4).trim();
            year = title.substr(-4, 4).trim();

            let monthNum = getnumMonth(month);

            getmonthseat(monthNum, year);

        });
    });


    //英文月份轉數字版本
    function getnumMonth(e) {
        var chn_mon_arr = [01, 02, 03, 04, 05, 06, 07, 08, 09, 10, 11, 12]; //數字参照      
        var en_mon_arr = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"]; //英文月份
        var cur_month = e.substring(0, 3);
        for (var i = 0; i < en_mon_arr.length; i++) { //循环匹配
            if (cur_month == en_mon_arr[i]) {
                return chn_mon_arr[i];

            }
        }
    }

    //請求月份的空位資訊
    function getmonthseat(monthNum, yearNum) {

        var monthchoose = {
            'action': 'monthchoose',
            'yearNum': yearNum,
            'monthNum': monthNum,
            'campid': "1"
        }
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
            url: "http://localhost:8081/TFA104G5/CampCalManger", // 資料請求的網址
            type: "GET", // GET | POST | PUT | DELETE | PATCH
            data: monthchoose,
            dataType: "json", // 預期會接收到回傳資料的格式： json | xml | html
            timeout: 0, // request 可等待的毫秒數 | 0 代表不設定 timeout
            beforeSend: function() {
                document.getElementsByTagName("body")[0].insertAdjacentHTML("afterBegin", loading);
                document.getElementsByTagName("main")[0].classList.add("on");
            },
            headers: { // request 如果有表頭資料想要設定的話
                // "X-CSRF-Token":"abcde"   // 參考寫法
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


                //抓取畫面上的每個日期
                // function addlist() {
                // var x = document.querySelectorAll("td.fc-daygrid-day");
                // for (let i = 0; i < x.length; i++) {
                // var oneday = x[i].getAttribute('data-date');


                calendar.removeAllEvents();

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


                    //抓取公休日日期或位子額滿
                    if (Object.values(data[i])[0] <= 0) {
                        restlist.push(Object.keys(data[i])[0]);

                    }

                }



            },
            error: function(xhr) { // request 發生錯誤的話執行
                console.log(xhr);
            },
            complete: function(xhr) { // request 完成之後執行(在 success / error 事件之後執行)
                console.log(xhr);
            }
        });

    }








    // 廠商看點選日期的當日訂單

    var date = "";

    // 針對點擊的日期，拿到當日的訂單(廠商)
    calendar.on('dateClick', function(info) {
        alert('Clicked on: ' + info.dateStr);

        date = info.dateStr; //點選的日期
        info.dayEl.style.backgroundColor = '#00FF99'; //點擊後背景色改變

        console.log(info.title);


        $.ajax({
            url: "http://localhost:8081/TFA104G5/CampCalManger", // 資料請求的網址
            type: "GET", // GET | POST | PUT | DELETE | PATCH
            data: {
                'action': 'seeorder',
                'date': date,
                'campid': 1
            }, // 傳送資料到指定的 url
            dataType: "json", // 預期會接收到回傳資料的格式： json | xml | html
            success: function(data) { // request 成功取得回應後執行
                console.log(data);
                var obody = document.getElementById("orderlist");
                while (obody.firstChild) {
                    obody.removeChild(obody.firstChild);
                }

                for (let i = 0; i < data.length; i++) {

                    var orderlist = data[i];
                    let textorder = `<tr>
                        <td>${ orderlist.campOrderId}</td>
                        <td>${ orderlist.campCheckOutDate}</td>
                        <td>${orderlist.payerName}</td>
                        <td>${ orderlist.payerPhone}</td>
                        <td>${orderlist.campOrderTotalAmount}</td>
                        <td>${(orderlist.campOrderStatus==1)?"已完成":"處理中"}</td>
                        <td> <input  type="button" value="查看明細" name="${orderlist.campOrderId}" class="update"  /></td>
                        </tr>`

                    obody.insertAdjacentHTML("beforeend", textorder);

                }

            }
        });
    })





    //新增公休日

    var closebtn = document.getElementById("closedbtn");



    closebtn.addEventListener("click", function() {
        var closedate = document.getElementById("closeddate").value;
        console.log(closedate);
        $.ajax({
            url: "http://localhost:8081/TFA104G5/CampCalManger", // 資料請求的網址
            type: "GET", // GET | POST | PUT | DELETE | PATCH
            data: {
                'action': 'closedate',
                'campid': 1,
                'closedate': closedate

            },
            dataType: "text", // 預期會接收到回傳資料的格式： json | xml | html
            success: function(data) { // request 成功取得回應後執行
                console.log(data);
                alert(data);
                title = calendarEl.getElementsByClassName("fc-toolbar-title")[0].innerText;
                month = title.substring(0, title.length - 4).trim();
                year = title.substr(-4, 4).trim();

                let monthNum = getnumMonth(month);

                getmonthseat(monthNum, year);

            }
        });


    });

});

