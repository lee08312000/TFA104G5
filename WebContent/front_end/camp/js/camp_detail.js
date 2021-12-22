document.addEventListener("DOMContentLoaded", function() {
    ////////////////////////////////////解析請求參數//////////////////////////////////
    //取得完整網址
    let campURL = location.href;
    let githubURL = new URL(campURL);
    //取得請求參數
    var querystring = githubURL.searchParams.toString();
    // console.log(querystring);
    //解析請求參數
    var params = githubURL.searchParams;
    // for (let pair of params.entries()) {
    //     console.log(`key: ${pair[0]}, value: ${pair[1]}`);
    // }
    //檢查是否有campid這個請求參數
    // console.log(params.has("campid"));
    //取得campid參數值
    // console.log(params.get("campid"));


    ///////////////////////////////////////////線上按鈕綁訂導入到月曆空位畫面//////////////////////////////////////////

    var bookbtn = document.getElementById("booking");
    bookbtn.setAttribute("campid", params.get("campid"));

    bookbtn.addEventListener("click", function() {
        location.href = "./page4.html" + "?campid=" + bookbtn.getAttribute("campid");

    });


    /////////////////////////////////fetch語法///////////////////////////////
    //組合請求參數到網址上
    let reqURL = new URL('http://localhost:8081/TFA104G5/CampServlet2');
    var searchParams = new URLSearchParams('action=detailcamp&' + querystring);
    reqURL.search = searchParams;
    fetch(reqURL, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8'
            }
        })
        .then(res => {
            return res.json();
        }).then(result => {
            console.log(result);

            let campdata = result[0]; //營地資料
            let tagname = result[1]; //標籤名稱
            let areadata = result[2]; //營位資料
            const content1 = document.getElementById('content1');
            const pageid = document.getElementById('pageid');
            let jsonData = {};
            jsonData = result[3]; //訂單評論資料
            pagination(jsonData, 1);
            var orderdata = jsonData[0];



            //設定經緯度


            initMap(campdata.lattitude, campdata.longitude);

            //導入營地特色在圖片上
            var stylecamp = document.querySelectorAll("#stylecamp");
            console.log(stylecamp);
            stylecamp.forEach(function(item, i) {
                item.innerText = campdata.campDiscription;
            });







            //圖片轉base64
            function base64ArrayBuffer(arrayBuffer) {
                var base64 = ''
                var encodings = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/'

                var bytes = new Uint8Array(arrayBuffer)
                var byteLength = bytes.byteLength
                var byteRemainder = byteLength % 3
                var mainLength = byteLength - byteRemainder

                var a, b, c, d
                var chunk

                // Main loop deals with bytes in chunks of 3
                for (var i = 0; i < mainLength; i = i + 3) {
                    // Combine the three bytes into a single integer
                    chunk = (bytes[i] << 16) | (bytes[i + 1] << 8) | bytes[i + 2]

                    // Use bitmasks to extract 6-bit segments from the triplet
                    a = (chunk & 16515072) >> 18 // 16515072 = (2^6 - 1) << 18
                    b = (chunk & 258048) >> 12 // 258048   = (2^6 - 1) << 12
                    c = (chunk & 4032) >> 6 // 4032     = (2^6 - 1) << 6
                    d = chunk & 63 // 63       = 2^6 - 1

                    // Convert the raw binary segments to the appropriate ASCII encoding
                    base64 += encodings[a] + encodings[b] + encodings[c] + encodings[d]
                }

                // Deal with the remaining bytes and padding
                if (byteRemainder == 1) {
                    chunk = bytes[mainLength]

                    a = (chunk & 252) >> 2 // 252 = (2^6 - 1) << 2

                    // Set the 4 least significant bits to zero
                    b = (chunk & 3) << 4 // 3   = 2^2 - 1

                    base64 += encodings[a] + encodings[b] + '=='
                } else if (byteRemainder == 2) {
                    chunk = (bytes[mainLength] << 8) | bytes[mainLength + 1]

                    a = (chunk & 64512) >> 10 // 64512 = (2^6 - 1) << 10
                    b = (chunk & 1008) >> 4 // 1008  = (2^6 - 1) << 4

                    // Set the 2 least significant bits to zero
                    c = (chunk & 15) << 2 // 15    = 2^4 - 1

                    base64 += encodings[a] + encodings[b] + encodings[c] + '='
                }

                return base64
            }

            var picarr = new Array();

            var campPic1 = (campdata.campPic1 != null) ? (base64ArrayBuffer(campdata.campPic1)) : '';
            var campPic2 = (campdata.campPic2 != null) ? (base64ArrayBuffer(campdata.campPic2)) : '';
            var campPic3 = (campdata.campPic3 != null) ? (base64ArrayBuffer(campdata.campPic3)) : '';
            var campPic4 = (campdata.campPic4 != null) ? (base64ArrayBuffer(campdata.campPic4)) : '';
            var campPic5 = (campdata.campPic5 != null) ? (base64ArrayBuffer(campdata.campPic5)) : '';


            for (let i = 0; i < areadata.length; i++) {
                var areaPic = base64ArrayBuffer(areadata[i].campAreaPic);
                picarr.push(areaPic);
            }
            picarr.push(campPic1);
            picarr.push(campPic2);
            picarr.push(campPic3);
            picarr.push(campPic4);
            picarr.push(campPic5);

            let newArr = []

            picarr.forEach(item => {
                if (item) {
                    newArr.push(item)
                }
            })


            //載入營地圖片logo
            var logocamp = document.getElementsByClassName("imgl")[0];
            logocamp.setAttribute("src", `data:image/png;base64,${campPic1}`);







            // 載入圖片OK
            var picParent = document.getElementById("featured_slide");


            var pic = picParent.querySelectorAll("img");

            var j = 0;
            for (let i = 0; i <= 19; i++) {
                //對每個載入圖片
                if (j >= newArr.length) {
                    j = 0;
                }
                pic[i].setAttribute("src", `data:image/png;base64,${newArr[j]}`);
                pic[i].style.objectFit = "cover";
                j++;
            }

            //載入營地名稱，電話，地址OK

            var campinfo = document.getElementsByClassName("fl_right")[0];
            var campname = campinfo.firstElementChild;
            campname.innerText = campdata.campName;
            var camptel = campname.nextElementSibling;
            camptel.innerText = "電話 : " + campdata.campPhone;
            var campsite = camptel.nextElementSibling;
            campsite.innerText = "地址 : " + campdata.campAddress;

            //載入特色標籤OK

            var camptags = document.getElementById("popular");
            var taglist = camptags.querySelectorAll("li");
            for (let i = 0; i < tagname.length; i++) {


                taglist[i].innerText = tagname[i];
                taglist[i].style.visibility = "visible";

            }


            //載入營位資訊OK

            var camparea = document.getElementsByTagName("tbody")[0];

            for (let i = 0; i < areadata.length; i++) {

                let rowdata = `<tr>
                 <td>123</td>
                 <td>${areadata[i].campAreaName}</td>
                <td>${areadata[i].campAreaMax}</td>
                <td>${areadata[i].weekdayPrice}</td>
                <td>${areadata[i].holidayPrice}</td>
                <td>${areadata[i].perCapitationFee}</td>
                <td>${areadata[i].capitationMax}</td>
            </tr>`;
                camparea.insertAdjacentHTML("beforeend", rowdata);



            }

            //載入營地介紹&營地租借規則OK
            //介紹
            var camprule = document.getElementById("camprule");
            var introduction = camprule.getElementsByTagName("p")[0];
            console.log(introduction);
            introduction.innerText = campdata.campDiscription;

            //規則
            var Rule = document.getElementById("rulelist");
            //規則資料整理(以句號座分隔)

            var rulelist = campdata.campRule.split("。");
            for (let i = 0; i < rulelist.length - 1; i++) {
                var rulesheet = `<li>${rulelist[i]}</li>`;

                Rule.insertAdjacentHTML("beforeend", rulesheet);
            }







            ////////////////////////////////////////////////////訂單處理/////////////////////////////////////////////////////////////


            function pagination(jsonData, nowPage) {
                console.log(nowPage);
                // 取得全部資料長度
                const dataTotal = jsonData.length;

                // 設定要顯示在畫面上的資料數量
                // 預設每一頁只顯示 5 筆資料。
                const perpage = 5;

                // page 按鈕總數量公式 總資料數量 / 每一頁要顯示的資料
                // 這邊要注意，因為有可能會出現餘數，所以要無條件進位。
                const pageTotal = Math.ceil(dataTotal / perpage);

                // 當前頁數，對應現在當前頁數
                let currentPage = nowPage;

                // 因為要避免當前頁數筆總頁數還要多，假設今天總頁數是 3 筆，就不可能是 4 或 5
                // 所以要在寫入一個判斷避免這種狀況。
                // 當"當前頁數"比"總頁數"大的時候，"當前頁數"就等於"總頁數"
                // 注意這一行在最前面並不是透過 nowPage 傳入賦予與 currentPage，所以才會寫這一個判斷式，但主要是預防一些無法預期的狀況，例如：nowPage 突然發神經？！
                if (currentPage > pageTotal) {
                    currentPage = pageTotal;
                }

                // 由前面可知 最小數字為 6 ，所以用答案來回推公式。
                const minData = (currentPage * perpage) - perpage + 1;
                const maxData = (currentPage * perpage);

                // 先建立新陣列
                const data = [];
                // 這邊將會使用 ES6 forEach 做資料處理
                // 首先必須使用索引來判斷資料位子，所以要使用 index
                jsonData.forEach((item, index) => {
                    // 獲取陣列索引，但因為索引是從 0 開始所以要 +1。
                    const num = index + 1;
                    // 這邊判斷式會稍微複雜一點
                    // 當 num 比 minData 大且又小於 maxData 就push進去新陣列。
                    if (num >= minData && num <= maxData) {
                        data.push(item);
                    }
                })


                // 用物件方式來傳遞資料
                const page = {
                    pageTotal,
                    currentPage,
                    hasPage: currentPage > 1,
                    hasNext: currentPage < pageTotal,
                }
                displayData(data);
                pageBtn(page);
            }

            function displayData(data) {
                let str = '';
                data.forEach((item) => {
                    str += '<li class="comment_odd">';
                    str += '<div class="author"> <div class="star_block">';
                    str += '<span class="star' + (item.campCommentStar >= 1 ? " -on" : "") + '" data-star="1"><i class="fas fa-star"></i></span>';
                    str += '<span class="star' + (item.campCommentStar >= 2 ? " -on" : "") + '" data-star="1"><i class="fas fa-star"></i></span>';
                    str += '<span class="star' + (item.campCommentStar >= 3 ? " -on" : "") + '" data-star="1"><i class="fas fa-star"></i></span>';
                    str += '<span class="star' + (item.campCommentStar >= 4 ? " -on" : "") + '" data-star="1"><i class="fas fa-star"></i></span>';
                    str += '<span class="star' + (item.campCommentStar >= 5 ? " -on" : "") + '" data-star="1"><i class="fas fa-star"></i></span>';
                    str += ' </div><span class="name"><a href="#">A Name</a></span> <span class="wrote">wrote:</span></div>';
                    str += '<div class="submitdate"><a href="#">' + item.campOrderCommentTime.substr(0, 10) + '</a></div>';
                    str += '<p>' + item.campComment + '</p>';
                    str += '</li>';
                });
                content1.innerHTML = str;

            }


            //用來製作下面分頁按鈕
            function pageBtn(page) {
                let str = '';
                const total = page.pageTotal;

                if (page.hasPage) {
                    str += `<li class="page-item"><a class="page-link" href="#" data-page="${Number(page.currentPage) - 1}">Previous</a></li>`;
                } else {
                    str += `<li class="page-item disabled"><span class="page-link">Previous</span></li>`;
                }


                for (let i = 1; i <= total; i++) {
                    if (Number(page.currentPage) === i) {
                        str += `<li class="page-item active"><a class="page-link" href="#" data-page="${i}">${i}</a></li>`;
                    } else {
                        str += `<li class="page-item"><a class="page-link" href="#" data-page="${i}">${i}</a></li>`;
                    }
                };

                if (page.hasNext) {
                    str += `<li class="page-item"><a class="page-link" href="#" data-page="${Number(page.currentPage) + 1}">Next</a></li>`;
                } else {
                    str += `<li class="page-item disabled"><span class="page-link">Next</span></li>`;
                }

                pageid.innerHTML = str;
            }


            //click分頁按鈕監聽
            function switchPage(e) {
                e.preventDefault();
                if (e.target.nodeName !== 'A') return;
                const page = e.target.dataset.page;
                pagination(jsonData, page);
            }
            pageid.addEventListener('click', switchPage);





        });
});


// 推薦營地名稱
// var rec = document.getElementById("commend");
// var recli = rec.querySelectorAll("li");
// console.log(recli);
// recli.forEach(function(item, i) {

//     var recimg = item.querySelectorAll("img")[0];
//     var recp = item.querySelectorAll("p")[0];
//     var nextrecp = recp.nextElementSibling;
//     recimg.setAttribute("src", "img/11111.jpg");
//     recp.innerText = "營地名稱:xxxxx";
//     nextrecp.innerText = "營地介紹";
// });



















//////////////////////////////////////// 載入地圖/////////////////////////////////////////////


function initMap(lat, lng) {

    var markers = [];


    var myLatLng = {
        lat: parseFloat(lat),
        lng: parseFloat(lng)
    }

    var map = new google.maps.Map(document.getElementById('map'), {
        center: myLatLng,
        zoom: 12,
    });



    //建立地圖 marker 的集合
    var marker_config = [{
        position: myLatLng,
        map: map,
        title: '光復國中'
    }];

    // var marker_config = [{
    //     position: { lat: 25.04, lng: 121.512 },
    //     map: map,
    //     title: '總統府'
    // }, {
    //     position: { lat: 25.035, lng: 121.519 },
    //     map: map,
    //     title: '中正紀念堂'
    // }];

    //標出 marker
    marker_config.forEach(function(e, i) {
        markers[i] = new google.maps.Marker(e);
        markers[i].setMap(map);
    });

    //InfoWindow 建構函式
    var content = '<h6>機場捷運<h6>' + '<p>A21 環北站</p>' + '<img src="img/11111.jpg" class="infoImg"></img>';


    var infowindow = new google.maps.InfoWindow({
        content: content
    });



    //對每一個marker加上觸發事件，顯示資訊視窗
    markers.forEach(function(e, i) {
        markers[i].addListener('click', function() {
            infowindow.open(map, markers[i]);

        });

    });



}
































//輸入關鍵字地圖找座標
// var geocoder = new google.maps.Geocoder();


// 官方提供
// geocoder.geocode({ 'address': address }, function(results, status) {
//     if (status == google.maps.GeocoderStatus.OK) {
//         map.setCenter(results[0].geometry.location);
//         var marker = new google.maps.Marker({
//             map: map,
//             position: results[0].geometry.location
//         });
//     } else {
//         alert("Geocode was not successful for the following reason: " + status);
//     }
// });

// //修改過後
// function _geocoder(address, callback) {
//     geocoder.geocode({
//         address: address
//     }, function(results, status) {
//         if (status == google.maps.GeocoderStatus.OK) {
//             loaction = results[0].geometry.location;
//             callback(loaction); //用一個 callback 就不用每次多寫上面這段
//         }
//     });
// }

// _geocoder('光復國中', function(address) {
//     var map = new google.maps.Map(document.getElementById('map'), {
//         center: address,
//         zoom: 14
//     });
// });