var data = {
    'action': 'selectedcamp',
    'section': [],
    'feature': [],
    'orderby': 0

}

var page = 0; //目前頁數
var endpage = 0;
var getallcamp = {
    'action': 'getallcamp',
    'callpage': page + 1, //請求頁數
    'rows': 8, //顯示筆數


}

//匯入符合篩選條件的營地
var cbtn = document.getElementById("cbtn");
var loading = `<div class="loader">
<h1>LOADING <span class="bullets">.</span></h1>
</div>`;



cbtn.addEventListener("click", loadquery, false);





//匯入篩選按鈕，第一頁篩選的東東
window.addEventListener("load", function() {


    //抓取首頁篩選的選項
    var selected = JSON.parse(sessionStorage.getItem("findcamp"));
    console.log(selected);
    if (selected != null) {
        let sec = document.querySelectorAll("input[name='section']");
        let fea = document.querySelectorAll("input[name='feature']");
        let ord = document.querySelectorAll("select[name='orderby']");

        let section = selected.section;
        let feature = selected.feature;
        let orderby = selected.orderby;


        if (section.length != 0) {
            for (let i of section) {
                sec[i].checked = true;
            }
        }
        if (feature.length != 0) {
            for (let i of feature) {
                fea[i].checked = true;
            }
        }

        ord[0].value = orderby;




        清空session
        sessionStorage.setItem("findcamp", JSON.stringify({
            section: [],
            feature: [],
            orderby: 0
        }));


        loadquery(e);

    } else {
        document.getElementById("a").checked = true;


    }
});

$(window).on("scroll", function() {
    //变量scrollTop是滚动条滚动时，滚动条上端距离顶部的距离
    var scrollTop = document.documentElement.scrollTop || document.body.scrollTop;

    //变量windowHeight是可视区的高度
    var windowHeight = document.documentElement.clientHeight || document.body.clientHeight;

    //变量scrollHeight是滚动条的总高度（当前可滚动的页面的总高度）
    var scrollHeight = document.documentElement.scrollHeight || document.body.scrollHeight;

    //滚动条到底部
    if (page <= endpage) {
        if ($(document).height() - ($(window).scrollTop() + $(window).height()) <= 20) {

            console.log("目前頁數" + page);
            console.log("請求頁數".concat(getallcamp.callpage));
            console.log("總頁數" + endpage);
            endpage = document.getElementById("endpage").value;
            allcamp(endpage, getallcamp.callpage);
            page = page + 1;
            getallcamp.callpage = page + 1;



        }
    } else {
        window.onscroll = null;


    }


});



function allcamp(end, req) {

    if (req > end) {
        console.log("不要再傳了");
        return;
    }
    $.ajax({
        url: "http://localhost:8081/TFA104G5/CampServlet2", // 資料請求的網址
        type: "GET", // GET | POST | PUT | DELETE | PATCH
        traditional: true,
        data: getallcamp,
        dataType: "json",
        timeout: 0,
        beforeSend: function() {
            document.getElementsByClassName("tm-gallery")[0].insertAdjacentHTML("beforeend", loading)
        },
        cache: false, // 避免有圖片 cache 狀況

        success: function(data) { // request 成功取得回應後執行

            var gallery = document.getElementsByClassName("loader")[0];
            gallery.parentNode.removeChild(gallery);
            console.log(data);
            document.getElementById("endpage").value = data.allpage;
            var campdata = data.camplist;
            if (campdata.length != 0) {
                //判斷回傳陣列長度
                for (let i = 0; i < campdata.length; i++) {
                    let camp = campdata[i];
                    let selecampdata = `
            <article class="col-lg-3 col-md-4 col-sm-6 col-12 tm-gallery-item itempage2" campid=${camp.campId}>
            <figure>
                <img src='data:image/png;base64,${camp.imgBase64}' alt="Image" class="img-fluid tm-gallery-img"> 
                <figcaption>
                    <h4 class="tm-gallery-title">${camp.name}</h4>
                    <a href="#" class="addlove btn_modal"><i class="far fa-heart"></i></a>
                    <p class="tm-gallery-description">${camp.address.substr(0,6)}</p>
                    <ul class="camp_target">
                    <li style="visibility:hidden"><a id="accept-btn" class="btn btn__accept btn_small" href="page3.html?campid=${camp.campId}">123</a></li>
                        <li style="visibility:hidden"><a id="accept-btn" class="btn btn__accept btn_small" href="page3.html?campid=${camp.campId}">123</a></li>
                        <li style="visibility:hidden"><a id="accept-btn" class="btn btn__accept btn_small" href="page3.html?campid=${camp.campId}">123</a></li>
                        <li style="visibility:hidden"><a id="accept-btn" class="btn btn__accept btn_small" href="page3.html?campid=${camp.campId}">123</a></li>
                        <li style="visibility:hidden"><a id="accept-btn" class="btn btn__accept btn_small" href="page3.html?campid=${camp.campId}">123</a></li>
                        <li style="visibility:hidden"><a id="accept-btn" class="btn btn__accept btn_small" href="page3.html?campid=${camp.campId}">123</a></li>
                        <li style="visibility:hidden"><a id="accept-btn" class="btn btn__accept btn_small" href="page3.html?campid=${camp.campId}">123</a></li>
                        <li style="visibility:hidden"><a id="accept-btn" class="btn btn__accept btn_small" href="page3.html?campid=${camp.campId}">123</a></li>

                    </ul>
                    <div class="camp_detailcontainer"><a id="accept-btn" class="btn btn__accept" href="page3.html?campid=${camp.campId}">了解更多</a></div>
                </figcaption>
            </figure>
            </article>`;

                    var selecontainer = document.getElementsByClassName("tm-gallery")[0];
                    selecontainer.insertAdjacentHTML("beforeend", selecampdata);
                    for (let j = 0; j < camp.tags.length; j++) {

                        var addtag = document.querySelectorAll(".camp_target")[i];

                        var tagchild1 = addtag.getElementsByTagName("a");
                        var tagchild2 = addtag.getElementsByTagName("li");
                        tagchild1[j].innerText = camp.tags[j];
                        tagchild2[j].style.visibility = "visible";
                    }

                    $("a.btn_modal").on("click", function(e) {
                        e.preventDefault();
                        e.stopPropagation();
                        $("div.overlay").fadeIn();
                        $("div.overlay").fadeOut(2000);
                    });

                }
            } else {

                console.log("已經沒有資料了");
            }


            document.getElementById("endpage").value = data.allpage;



        },
        error: function(xhr) { // request 發生錯誤的話執行
            //    location.reload();
        },
        complete: function(xhr) { // request 完成之後執行(在 success / error 事件之後執行)

        }
    });




}



































function loadquery(e) {

    //先清空之前搜尋的東東
    var mainsection = document.getElementsByClassName("page2-gallery")[0];
    var childs = mainsection.lastElementChild;
    while (childs) {
        mainsection.removeChild(childs);
        childs = mainsection.lastElementChild;
    }
    //先清空之前搜尋的東東

    //把點選的東西蒐集起來
    let sec = document.querySelectorAll("input[name='section']");
    let fea = document.querySelectorAll("input[name='feature']");
    let ord = document.querySelectorAll("select[name='orderby']");
    sec.forEach(function(item, i) {
        if ($(item).prop("checked")) {

            if (data.section.indexOf(item.value) == -1) {
                data.section.push(item.value);
            }
        }

    });

    fea.forEach(function(item, i) {
        if ($(item).prop("checked")) {

            if (data.feature.indexOf(item.value) == -1) {
                data.feature.push(item.value);
            }
        }

    });

    if (ord[0].value > 0) {
        data.orderby = ord[0].value;
    }
    console.log(data);

    $.ajax({
        url: "http://localhost:8081/TFA104G5/CampServlet2", // 資料請求的網址
        type: "GET", // GET | POST | PUT | DELETE | PATCH
        traditional: true,
        data: data,
        dataType: "json",
        timeout: 0,
        beforeSend: function() {
            document.getElementsByClassName("tm-gallery")[0].insertAdjacentHTML("afterbegin", loading)
        },
        cache: false, // 避免有圖片 cache 狀況

        success: function(data) { // request 成功取得回應後執行
            var gallery = document.getElementsByClassName("loader")[0];
            gallery.parentNode.removeChild(gallery);
            console.log(data);
            if (data.length != 0) {
                for (let i = 0; i < data.length; i++) {
                    let camp = data[i];
                    let selecampdata = `
            <article class="col-lg-3 col-md-4 col-sm-6 col-12 tm-gallery-item itempage2" campid=${camp.campId}>
            <figure>
                <img src='data:image/png;base64,${camp.imgBase64}' alt="Image" class="img-fluid tm-gallery-img"> 
                <figcaption>
                    <h4 class="tm-gallery-title">${camp.name}</h4>
                    <a href="#" class="addlove btn_modal"><i class="far fa-heart"></i></a>
                    <p class="tm-gallery-description">${camp.address.substr(0,6)}</p>
                    <ul class="camp_target">
                    <li style="visibility:hidden"><a id="accept-btn" class="btn btn__accept btn_small" href="page3.html?campid=${camp.campId}">123</a></li>
                        <li style="visibility:hidden"><a id="accept-btn" class="btn btn__accept btn_small" href="page3.html?campid=${camp.campId}">123</a></li>
                        <li style="visibility:hidden"><a id="accept-btn" class="btn btn__accept btn_small" href="page3.html?campid=${camp.campId}">123</a></li>
                        <li style="visibility:hidden"><a id="accept-btn" class="btn btn__accept btn_small" href="page3.html?campid=${camp.campId}">123</a></li>
                        <li style="visibility:hidden"><a id="accept-btn" class="btn btn__accept btn_small" href="page3.html?campid=${camp.campId}">123</a></li>
                        <li style="visibility:hidden"><a id="accept-btn" class="btn btn__accept btn_small" href="page3.html?campid=${camp.campId}">123</a></li>
                        <li style="visibility:hidden"><a id="accept-btn" class="btn btn__accept btn_small" href="page3.html?campid=${camp.campId}">123</a></li>
                        <li style="visibility:hidden"><a id="accept-btn" class="btn btn__accept btn_small" href="page3.html?campid=${camp.campId}">123</a></li>

                    </ul>
                    <div class="camp_detailcontainer"><a id="accept-btn" class="btn btn__accept" href="page3.html?campid=${camp.campId}">了解更多</a></div>
                </figcaption>
            </figure>
            </article>`;

                    var selecontainer = document.getElementsByClassName("tm-gallery")[0];
                    selecontainer.insertAdjacentHTML("beforeend", selecampdata);
                    for (let j = 0; j < camp.tags.length; j++) {

                        var addtag = document.querySelectorAll(".camp_target")[i];

                        var tagchild1 = addtag.getElementsByTagName("a");
                        var tagchild2 = addtag.getElementsByTagName("li");
                        tagchild1[j].innerText = camp.tags[j];
                        tagchild2[j].style.visibility = "visible";
                    }

                    $("a.btn_modal").on("click", function(e) {
                        e.preventDefault();
                        e.stopPropagation();
                        $("div.overlay").fadeIn();
                        $("div.overlay").fadeOut(2000);
                    });

                }
            } else {
                document.getElementsByClassName("tm-gallery")[0].insertAdjacentHTML("afterbegin", `<p style="color: crimson;font-size: 18px;margin: 0 auto;">查無符合的資料</p>`)

            }




        },
        error: function(xhr) { // request 發生錯誤的話執行
            //    location.reload();
        },
        complete: function(xhr) { // request 完成之後執行(在 success / error 事件之後執行)

            data.section = [];
            data.feature = [];
            data.orderby = 0;
        }
    });




}





//搜尋欄查詢
var searchbtn = document.getElementsByClassName("searchButton")[0];

searchbtn.addEventListener("click", function(e) {
    //先清空之前搜尋的東東
    var mainsection = document.getElementsByClassName("page2-gallery")[0];
    var childs = mainsection.lastElementChild;
    while (childs) {
        mainsection.removeChild(childs);
        childs = mainsection.lastElementChild;
    }
    //先清空之前搜尋的東東
    let searchtext = document.getElementsByClassName("searchTerm")[0];
    console.log(searchtext.value);
    var text = searchtext.value;
    if ((text = text.replace(/\s*/g, "")) != "") {

        $.ajax({
            url: "http://localhost:8081/TFA104G5/CampServlet2", // 資料請求的網址
            type: "get",
            traditional: true,
            data: {
                'action': 'searchbar',
                'searchtext': text
            },
            dataType: "JSON",
            timeout: 0,
            beforeSend: function() {
                document.getElementsByClassName("tm-gallery")[0].insertAdjacentHTML("afterbegin", loading)
            },
            cache: false, // 避免有圖片 cache 狀況
            success: function(data) {
                var gallery = document.getElementsByClassName("loader")[0];
                gallery.parentNode.removeChild(gallery);
                console.log(data);

                for (let i = 0; i < data.length; i++) {
                    let camp = data[i];
                    let selecampdata = `
                <article class="col-lg-3 col-md-4 col-sm-6 col-12 tm-gallery-item itempage2" campid=${camp.campId}>
                <figure>
                    <img src='data:image/png;base64,${camp.imgBase64}' alt="Image" class="img-fluid tm-gallery-img"> 
                    <figcaption>
                        <h4 class="tm-gallery-title">${camp.name}</h4>
                        <a href="#" class="addlove btn_modal"><i class="far fa-heart"></i></a>
                        <p class="tm-gallery-description">${camp.address.substr(0,6)}</p>
                        <ul class="camp_target">
                        <li style="visibility:hidden"><a id="accept-btn" class="btn btn__accept btn_small" href="page3.html?campid=${camp.campId}"> 2132321312</a></li>
                        <li style="visibility:hidden"><a id="accept-btn" class="btn btn__accept btn_small" href="page3.html?campid=${camp.campId}"> 1212</a></li>
                        <li style="visibility:hidden"><a id="accept-btn" class="btn btn__accept btn_small" href="page3.html?campid=${camp.campId}"> 212121</a></li>
                        <li style="visibility:hidden"><a id="accept-btn" class="btn btn__accept btn_small" href="page3.html?campid=${camp.campId}"> 122213213211</a></li>
                        <li style="visibility:hidden"><a id="accept-btn" class="btn btn__accept btn_small" href="page3.html?campid=${camp.campId}"> 123121</a></li>
                        <li style="visibility:hidden"><a id="accept-btn" class="btn btn__accept btn_small" href="page3.html?campid=${camp.campId}"> 21321</a></li>
                        <li style="visibility:hidden"><a id="accept-btn" class="btn btn__accept btn_small" href="page3.html?campid=${camp.campId}"> 1321</a></li>
                        <li style="visibility:hidden"><a id="accept-btn" class="btn btn__accept btn_small" href="page3.html?campid=${camp.campId}"> 2232132133</a></li>
    
                        </ul>
                        <div class="camp_detailcontainer"><a id="accept-btn" class="btn btn__accept" href='page3.html?campid=${camp.campId}'">了解更多</a></div>

                    </figcaption>
                </figure>
                </article>`;

                    var selecontainer = document.getElementsByClassName("tm-gallery")[0];
                    selecontainer.insertAdjacentHTML("beforeend", selecampdata);
                    for (let j = 0; j < camp.tags.length; j++) {

                        var addtag = document.querySelectorAll(".camp_target")[i];

                        var tagchild1 = addtag.getElementsByTagName("a");
                        var tagchild2 = addtag.getElementsByTagName("li");
                        tagchild1[j].innerText = camp.tags[j];
                        tagchild2[j].style.visibility = "visible";
                    }

                    $("a.btn_modal").on("click", function(e) {
                        e.preventDefault();

                        $("div.overlay").fadeIn();
                        $("div.overlay").fadeOut(2000);
                    });

                }


            }
        });
    } else {
        alert("提示:請輸入查詢營地名稱，謝謝!");

    }
});


//綁定enter鍵
$("body").keydown(function(e) {
    if (e.which == "13") { //13是enter键的键码
        searchbtn.click();
        //调用登录方法,在div中定义方法,或通过js绑定的方法都可以,我的登录方法就是通过jquery绑定的点击事件
    }
});


function unbind() {

    window.onscroll = false;
}