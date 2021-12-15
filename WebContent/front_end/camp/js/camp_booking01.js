var el;
window.addEventListener("load", function() {
    // 計算平日幾天&假日幾天
    var begin = document.getElementById("begin").innerText.slice(5, 15).trim();
    console.log(begin);
    var beginday = new Date(begin);
    var night = document.getElementById("daynum").innerText.slice(8, 9).trim();

    var holidays = getWorkDate(beginday, night);
    var weekdays = night - holidays;
    console.log(holidays);
    console.log(weekdays);


    var inputweekday = document.getElementsByTagName("input")[0];
    var inputholiday = document.getElementsByTagName("input")[1];

    inputweekday.value = weekdays;

    inputholiday.value = holidays;
    
  if(weekdays==0){
	  var ww = document.querySelectorAll("input[name=weeknum]");
	  ww.forEach(function(item,i){
		  
		  item.setAttribute("max",0);
		  
		  
	  });
	  
  }
  if(holidays==0){
	  var hh = document.querySelectorAll("input[name=holinum]");
	  hh.forEach(function(item,i){
		 
		 item.setAttribute("max",0);
		  
		  
	  });
	  
  }
    


    // /////////////////////////////////////////////////////////////////////////////////////////

    $("tr").each(function() {
        var wsubtotal = parseInt($(this).children(".wprice").text().replace("$", ""));
        var wamount = parseInt($(this).children(".wamount").children("input").val());

        var hsubtotal = parseInt($(this).children(".hprice").text().replace("$", ""));
        var hamount = parseInt($(this).children(".hamount").children("input").val());

        var psubtotal = parseInt($(this).children(".pprice").text().replace("$", ""));
        var pamount = parseInt($(this).children(".pamount").children("input").val());

        $(this).children(".pricesubtotal").text("$" +
            (Math.round(
            		  wsubtotal * wamount * 100 * weekdays + hsubtotal * hamount * 100 * holidays + psubtotal * pamount * 100 * (holidays + weekdays)
            ) / 100).toFixed(0));
    });





    $(".wamount > input,.hamount > input,.pamount > input").bind("change keyup", function() {

        var wsubtotal = parseInt($(this).parents("td").parents("tr").children(".wprice").text().replace("$", ""));
        var wamount = parseInt($(this).parents("td").parents("tr").children(".wamount").children("input").val());

        var hsubtotal = parseInt($(this).parents("td").parents("tr").children(".hprice").text().replace("$", ""));
        var hamount = parseInt($(this).parents("td").parents("tr").children(".hamount").children("input").val());

        var psubtotal = parseInt($(this).parents("td").parents("tr").children(".pprice").text().replace("$", ""));
        var pamount = parseInt($(this).parents("td").parents("tr").children(".pamount").children("input").val());


        $(this).parents("td").parents("tr").children(".pricesubtotal").text("$" +
            (Math.round(
            		  wsubtotal * wamount * 100 * weekdays + hsubtotal * hamount * 100 * holidays + psubtotal * pamount * 100 * (holidays + weekdays)
            ) / 100).toFixed(0));
        changed();
    });



    $(".remove > button").click(function() {
        console.log(1);
        $(this).parents("td").parents("tr").children(".wamount").children("input").val(0);
        $(this).parents("td").parents("tr").children(".hamount").children("input").val(0);
        $(this).parents("td").parents("tr").children(".pamount").children("input").val(0);

        $(this).parents("td").parents("tr").children(".pricesubtotal").text("$" + 0);

        changed();
    });

    function changed() {
        var subtotal = 0;
        $(".p").each(function() {
            subtotal = subtotal + parseInt($(this).children(".pricesubtotal").text().replace("$", ""));
        });
        $(".totalpricesubtotal").text("$" + (Math.round(subtotal * 100) / 100).toFixed(0));
        var a = (subtotal / 100 * 100) + parseInt($(".shipping").text())
        var total = (Math.round(a * 100) / 100).toFixed(0) - 10;
        $(".realtotal").text(total);
        $(".taxval").text("($" + (Math.round(subtotal * 1) / 100).toFixed(0) + ") ");
    }

    $("#checkout").click(function() {
        alert("And that's $" + $(".realtotal").text() + ", please.");
    });

    changed();

    $("#expand").click(function() {
        $("#coolstuff").toggle();
    });
});



var confirm = document.getElementById("confirm");

confirm.addEventListener("click", function(e) {
    e.preventDefault();

    // //////////////////////////////////////第一步先刪除確認訂單李的所有內容////////////////////////////////////////////

    var close_container1 = document.getElementById("outerbox");


    close_container1.innerHTML = `<ul class="innerbox innerbox2" id="thead">
        <li>營區分位</li>
        <li>平日/每帳價格</li>
        <li>帳數</li>
        <li>假日日/每帳價格</li>
        <li>帳數</li>
        <li>加購人頭</li>
        <li>人頭數量</li>
        <li>總計</li>
    </ul>
    <ul class="innerbox" id="thead">
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
                <li>總計:</li>
                <li id="total">0</li>
            </ul>`;
    var close_container = document.getElementById("thead");



    // ////////////////////////////////////////第二步把有total>0的新增到確認訂單內容裡/////////////////////////////////////////////////////
    var total = document.getElementsByClassName("totalpricesubtotal")[0].innerText.slice(1);

    if (parseInt(total) != NaN) {


        var tabo = document.getElementsByTagName("tbody")[0];
        var choose = tabo.getElementsByClassName("pricesubtotal");

        for (let i of choose) {

            if (parseInt(i.innerText.slice(1)) > 0) {
                var par = i.parentNode;
                
               
                let campareaid = par.getElementsByClassName("campAreaId")[0].innerText;
                let name = par.getElementsByClassName("name")[0].innerText;
                let wprice = par.getElementsByClassName("wprice")[0].innerText;
                let wamount = par.querySelectorAll("input[name=weeknum]")[0].value;
                let hprice = par.getElementsByClassName("hprice")[0].innerText;
                let hamount = par.querySelectorAll("input[name=holinum]")[0].value;
                let pprice = par.getElementsByClassName("pprice")[0].innerText;
                let pamount = par.querySelectorAll("input[name=pernum]")[0].value;
                let subtotal = par.getElementsByClassName("pricesubtotal")[0].innerText;


                close_container.insertAdjacentHTML("afterend", ` 
                	<ul name="campAreaId" value="${campareaid}" class="innerbox">
                    <li name="campAreaName" value="${name}">${name}</li>
                    <li name="weekdayPrice" value="${wprice}">${wprice}</li>
                    <li name="weekdayNum" value="${wamount}">${wamount}</li>
                    <li name="holidayPrice" value="${hprice}">${hprice}</li>
                    <li name="holidayNum" value="${hamount}">${hamount}</li>
                    <li name="perCapitationFee" value="${pprice}">${pprice}</li>
                    <li name="perCapitationNum" value="${pamount}">${pamount}</li>
                    <li name="subtotal" value="${subtotal}">${subtotal}</li>
                </ul>
                <input type="hidden" name="campAreaId" value="${campareaid}" class="innerbox">
                    <input type="hidden" name="campAreaName" value="${name}"></input>
                    <input type="hidden" name="weekdayPrice" value="${wprice}"></input>
                    <input type="hidden" name="weekdayNum" value="${wamount}"></input>
                    <input type="hidden" name="holidayPrice" value="${hprice}"></input>
                    <input type="hidden" name="holidayNum" value="${hamount}"></input>
                    <input type="hidden" name="perCapitationFee" value="${pprice}"></input>
                    <input type="hidden" name="perCapitationNum" value="${pamount}"></input>
                    <input type="hidden" name="subtotal" value="${subtotal}"></input>
                </input>`);


               

            }

            
        }
        document.getElementById("total").innerText = document.getElementsByClassName("totalpricesubtotal")[0].innerText
    
console.log(close_container1);
        $('.pop-box, .cover').show();
        
        $('.x-btn').click(function() {
            $('.cover, .pop-box').hide();
        });
    }


});



var orderbtn = document.getElementById("orderbtn");

orderbtn.addEventListener("click", function(e) {
	var begin = document.getElementById("begin").innerText.slice(6, 16).trim();
    var night = document.getElementById("daynum").innerText.slice(8, 9).trim();
    document.getElementById("chooseDate").value=begin;
    console.log(begin);
    document.getElementById("chooseDay").value=night;

    var total = document.getElementsByClassName("totalpricesubtotal")[0].innerText.slice(1);
    if (parseInt(total) == NaN || parseInt(total) == 0) {

        e.preventDefault();
        alert("請選擇喜歡的營位")


    }


});




// 如何抓取 今天是星期幾?
// JavaScript 如何抓取 今天是星期幾? 程式要怎麼寫?
// 今天是星期幾 的 程式寫法
// new Date().getDay(); // 會是 0 ~ 6 的值, 分別代表下述:
// 0 星期日
// 1 星期一
// 2 星期二
// 3 星期三
// 4 星期四
// 5 星期五
// 6 星期六
function getWorkDate(startDate, limitDay) {
    var time = Date.parse(startDate);
    var startTime = new Date(Date.parse(startDate));
    var startTime = startTime.getTime();
    var T = 24 * 60 * 60 * 1000;
    var endTime = startTime + (limitDay * T);
    if (limitDay > 0) {
        var holidays = 0;
        for (var i = startTime; i < endTime; i += T) {
            var date = new Date(i);

            // 此處爲節假日邏輯
            if (date.getDay() == 0 || date.getDay() == 6 || date.getDay() == 5) {
                holidays++;
            }
            // 判斷日期是否在 節假日數組中
            // if (isInArray(Holiday, date.toLocaleDateString()) == true) {
            // holidays++;
            // }
        }
        return holidays;
    } else {
        return startDate.toLocaleDateString();

    }
}