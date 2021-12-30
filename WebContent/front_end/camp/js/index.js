// 設定輪播用的
$(function() {
    $(".flexslider").flexslider({
        animation: "slide", //圖片變換方式：淡入淡出或者滑動"fade" or "slide"
        slideDirection: "horizontal", //圖片滑動時的滑動方向：左右或者上下"horizontal" or "vertical"
        slideshow: true, //載入頁面時，是否自動播放
        slideshowSpeed: 2000, //自動播放速度毫秒
        animationDuration: 600, //內容切換時間
        touch: true, //是否支援觸控滑動
        directionNav: true, //是否顯示左右控制按鈕
        controlNav: true, //是否顯示控制選單
        keyboardNav: true, //鍵盤左右方向鍵控制圖片滑動
        mousewheel: false, //滑鼠滾輪控制製圖片滑動
        // minItems: 2                 //一次最少展示滑動內容的單元個數
        // maxItems: 2                 //一次最多展示滑動內容的單元個數
        // move: 1                     //一次滑動的單元個數
        // prevText: "Previous",       //String:  上一項的文字
        // nextText: "Next",           //String:  下一項的文字
        // pausePlay: false,           //Boolean: 是否顯示播放暫停按鈕
        // pauseText: 'Pause',         //String:  暫停文字
        // playText: 'Play',           //String: 播放文字
        randomize: false, //Boolean: 是否隨機幻燈片
        slideToStart: 0, //Integer:  初始化第一次顯示圖片位置
        animationLoop: true, //是否迴圈滾動
        pauseOnAction: true, //手動滾動內容後，是否暫停滾動
        pauseOnHover: true, //滑鼠懸停內容上，是否暫停滾動
        // controlsContainer: "",      //Selector:  be taken.
        // manualControls: "",         //自定義控制導航
        // manualControlEvent:"",      //String:自定義導航控制觸發事件:預設是click,可以設定hover
        // start: function(){},        //載入第一頁觸發
        // before: function(){},       //每個滾動動畫開始時非同步觸發
        // after: function(){},        //每個滾動動畫結束時觸發
        // end: function(){}           //滾動到最後一頁時非同步觸發
    });
});
var loading = `<div class="loader">
<h1>LOADING <span class="bullets">.</span></h1>
</div>`;


//匯入熱門商品
window.addEventListener("load", function() {
	var memberid=sessionStorage['memberid'];
    sessionStorage.removeItem("findcamp");


    let sec = selectcon.querySelectorAll("input[name='section']");
    let fea = selectcon.querySelectorAll("input[name='feature']");
    let ord = document.querySelectorAll("select[name='orderby']");

    sec.forEach(function(item, i) {
        item.checked = false;
    });

    fea.forEach(function(item, i) {
        item.checked = false;
    });

    ord[0].value = 0;







    $.ajax({
        url: "/TFA104G5/CampServlet2", // 資料請求的網址
        type: "GET", // GET | POST | PUT | DELETE | PATCH
        data: {
            'action': 'hotcamp'
        },
        dataType: "json",
        timeout: 0,
        beforeSend: function() {
            document.getElementsByClassName("tm-gallery")[0].insertAdjacentHTML("afterbegin", loading)
        },
        cache: false,

        success: function(data) {
            var gallery = document.getElementsByClassName("loader")[0];
            gallery.parentNode.removeChild(gallery);



            // console.log(data);
            var northclass = document.getElementById("tm-gallery-page-north");
            var southclass = document.getElementById("tm-gallery-page-south");
            var eastclass = document.getElementById("tm-gallery-page-east");
            var westclass = document.getElementById("tm-gallery-page-west");
            var count = 0;
            for (let i = 0; i < data.length; i++) {
                let camp = data[i];
                let id=camp.campId;
                let hotcampdata = `
            <article class="col-lg-3 col-md-4 col-sm-6 col-12 tm-gallery-item" campid=${camp.campId}>
            <figure class="figcol">
                <img src='data:image/png;base64,${camp.imgBase64}' alt="Image" class="img-fluid tm-gallery-img"/> 
                <figcaption>
                    <h4 class="tm-gallery-title">${camp.name}</h4>
                    <a href="#" class="addlove btn_modal" campid=${camp.campId}>`+loadfavorcamp(memberid,id)+`</i></a>                        
                    <p class="tm-gallery-description">${camp.address.substr(0,6)}</p>
                    <ul class="camp_target">
                    <li style="visibility:hidden"><a id="accept-btn" class="btn btn__accept btn_small" href="camp_detail.html?campid=${camp.campId}"></a></li>
                    <li style="visibility:hidden"><a id="accept-btn" class="btn btn__accept btn_small" href="camp_detail.html?campid=${camp.campId}"></a></li>
                    <li style="visibility:hidden"><a id="accept-btn" class="btn btn__accept btn_small" href="camp_detail.html?campid=${camp.campId}"></a></li>
                    <li style="visibility:hidden"><a id="accept-btn" class="btn btn__accept btn_small" href="camp_detail.html?campid=${camp.campId}"></a></li>
                    <li style="visibility:hidden"><a id="accept-btn" class="btn btn__accept btn_small" href="camp_detail.html?campid=${camp.campId}"></a></li>
                    <li style="visibility:hidden"><a id="accept-btn" class="btn btn__accept btn_small" href="camp_detail.html?campid=${camp.campId}"></a></li>
                    <li style="visibility:hidden"><a id="accept-btn" class="btn btn__accept btn_small" href="camp_detail.html?campid=${camp.campId}"></a></li>
                    <li style="visibility:hidden"><a id="accept-btn" class="btn btn__accept btn_small" href="camp_detail.html?campid=${camp.campId}"></a></li>
                   
                    </ul>
                        <div class="camp_detailcontainer"><a id="accept-btn" class="btn btn__accept" href='camp_detail.html?campid=${camp.campId}'">了解更多</a></div>
                </figcaption>
            </figure>
            </article>`;




                for (let j = 0; j < camp.tags.length; j++) {
                    switch (camp.tags[j]) {
                        case "北部":
                            northclass.insertAdjacentHTML("beforeend", hotcampdata);

                            break;
                        case "西部":
                            westclass.insertAdjacentHTML("beforeend", hotcampdata);
                            break;
                        case "南部":
                            southclass.insertAdjacentHTML("beforeend", hotcampdata);
                            break;
                        case "東部":
                            eastclass.insertAdjacentHTML("beforeend", hotcampdata);
                            break;
                        default:

                    }
                    var count = 0;
                    var addtag = document.querySelectorAll(".camp_target")[i];

                    var tagchild1 = addtag.getElementsByTagName("a");
                    var tagchild2 = addtag.getElementsByTagName("li");
                    tagchild1[j].innerText = camp.tags[j];
                    tagchild2[j].style.visibility = "visible";




                }
                count++;

               

            }

            // 我的最愛
            $("a.btn_modal").on("click", function(e) {
                e.preventDefault();
                e.stopPropagation();
                let campid=(e.target.parentElement).getAttribute('campid');
                let memberid=sessionStorage['memberid'];
                var target=e.target;
                addfav(memberid,campid,target);
            });

        },
        error: function(xhr) {
            console.log(xhr);
        },
        complete: function(xhr) {
            // console.log(xhr);
        }
    });



});


//隱藏其他分類

$(document).ready(function() {

    var allclass = document.getElementsByClassName('tm-paging-link');

    $('.tm-paging-link').click(function(e) {
        e.preventDefault();
        var page = e.target.getAttribute("page");

        var inpage = document.querySelectorAll("div[name=section]");




        for (let i = 0; i <= 3; i++) {
            if (allclass[i].classList.contains('active')) {
                allclass[i].classList.remove('active');
            }
        }
        $(this).addClass("active");
        for (let i = 0; i <= 3; i++) {
            if (!(inpage[i].classList.contains("hidden"))) {
                inpage[i].classList.add('hidden');

            }
        }

        $('#tm-gallery-page-' + page).removeClass('hidden');


    });

});


//搜尋欄查詢
var searchbtn = document.querySelectorAll(".searchButton")[0];

searchbtn.addEventListener("click", function(e) {
    e.preventDefault();
    let searchtext = document.getElementsByClassName("searchTerm")[0];
    console.log(searchtext.value);
    sessionStorage.setItem("searchtext", searchtext.value);
    location.href = "./camp_select.html";

});

//儲存首頁條件按鈕的選項，存到sessionStorage


var selectcon = document.getElementById("myform");

var cbtn = document.getElementById("cbtn");


var findcamp = {
    section: [],
    feature: [],
    orderby: 0

};







cbtn.addEventListener("click", function(e) {


    e.preventDefault();
    
    findcamp.section = [];
    findcamp.feature = [];
    findcamp.orderby = 0;

    let sec = selectcon.querySelectorAll("input[name='section']");
    let fea = selectcon.querySelectorAll("input[name='feature']");
    let ord = document.querySelectorAll("select[name='orderby']");

    console.log(sec);
    sec.forEach(function(item, i) {
        if ($(item).prop("checked")) {
            console.log(item);
            console.log(item.value);
            findcamp.section.push(item.value);
        }

    });


    fea.forEach(function(item, i) {
        if ($(item).prop("checked")) {
            console.log(item);
            console.log(item.value);
            findcamp.feature.push(item.value);
        }

    });

    if (ord[0].value > 0) {
        findcamp.orderby = ord[0].value;
    }

    if (findcamp.section.length != 0 || findcamp.feature.length != 0) {
        sessionStorage.setItem("findcamp", JSON.stringify(findcamp));

        location.href = "./camp_select.html";

    } else {
        alert("請篩選搜尋條件");
    }


});



function addfav(memberid,campid,target){

	if(memberid==null||Number(memberid)==NaN){
		alert("請先登入會員，謝謝!");
		return;		
	}
	$.ajax({
		  url: "http://localhost:8081/TFA104G5/FavorCampServlet",           // 資料請求的網址
		  type: "GET",                  // GET | POST | PUT | DELETE | PATCH
		   data: {
			   'action':'addfav',
			   'memberid':memberid,
				   'campid':campid

		   },               // 傳送資料到指定的 url
		  dataType: "text",             // 預期會接收到回傳資料的格式： json | xml | html
		  success: function(data){      // request 成功取得回應後執行
		    console.log(data);
		    switch(data){
		    case "已經加入過了，移除我的最愛成功":
		    	alert("已經加入過了，移除我的最愛成功");
		    	if(target.classList.contains("fas")){
		    		target.classList.remove("fas");
		    		target.classList.add("far");
		    	}
		    	break;
		    case "新增我的最愛成功"	:
		    	alert("新增我的最愛成功");
		    	if(target.classList.contains("far")){
		    	target.classList.remove("far");
		    	target.classList.add("fas");
		    	}
		    	break;
		    case "新增失敗，請重新登入":
		    	alert("新增失敗，請重新登入");
		    	break;
		    }

		    }
		    		  
		});
	

}


function unbind() {

    window.onscroll = false;
}



function loadfavorcamp(memberid,campid){
	
	if(memberid==null){
		
		return `<i class="far fa-heart"></i>`;
		
	}
	if(favorlist.includes(campid)){
	
		return `<i class="fas fa-heart"></i>`;

	}else{
		
		return `<i class="far fa-heart"></i>`;
		
	}
	
}