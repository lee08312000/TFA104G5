window.onload = function() {

        var fileInput1 = document.getElementById('fileInput1');
        var fileDisplayArea1 = document.getElementById('fileDisplayArea1');
        var fileInput2 = document.getElementById('fileInput2');
        var fileDisplayArea2 = document.getElementById('fileDisplayArea2');
        var fileInput3 = document.getElementById('fileInput3');
        var fileDisplayArea3 = document.getElementById('fileDisplayArea3');
        var fileInput4 = document.getElementById('fileInput4');
        var fileDisplayArea4 = document.getElementById('fileDisplayArea4');
        var fileInput5 = document.getElementById('fileInput5');
        var fileDisplayArea5 = document.getElementById('fileDisplayArea5');
        



        fileInput1.addEventListener('change', function(e) {
            var file = fileInput1.files[0];
            var imageType = /image.*/;

            if (file.type.match(imageType)) {
                var reader = new FileReader();

                reader.onload = function(e) {
                    fileDisplayArea1.innerHTML = "";

                    var img = new Image();
                    img.src = reader.result;

                    fileDisplayArea1.appendChild(img);
                }

                reader.readAsDataURL(file);	
            } else {
                fileDisplayArea1.innerHTML = "File not supported!"
            }
        });

        fileInput2.addEventListener('change', function(e) {
            var file = fileInput2.files[0];
            var imageType = /image.*/;

            if (file.type.match(imageType)) {
                var reader = new FileReader();

                reader.onload = function(e) {
                    fileDisplayArea2.innerHTML = "";

                    var img = new Image();
                    img.src = reader.result;

                    fileDisplayArea2.appendChild(img);
                }

                reader.readAsDataURL(file);	
            } else {
                fileDisplayArea2.innerHTML = "File not supported!"
            }
        });

        fileInput3.addEventListener('change', function(e) {
            var file = fileInput3.files[0];
            var imageType = /image.*/;

            if (file.type.match(imageType)) {
                var reader = new FileReader();

                reader.onload = function(e) {
                    fileDisplayArea3.innerHTML = "";

                    var img = new Image();
                    img.src = reader.result;

                    fileDisplayArea3.appendChild(img);
                }

                reader.readAsDataURL(file);	
            } else {
                fileDisplayArea3.innerHTML = "File not supported!"
            }
        });
        
        fileInput4.addEventListener('change', function(e) {
            var file = fileInput4.files[0];
            var imageType = /image.*/;

            if (file.type.match(imageType)) {
                var reader = new FileReader();

                reader.onload = function(e) {
                    fileDisplayArea4.innerHTML = "";

                    var img = new Image();
                    img.src = reader.result;

                    fileDisplayArea4.appendChild(img);
                }

                reader.readAsDataURL(file);	
            } else {
                fileDisplayArea4.innerHTML = "File not supported!"
            }
        });
        
        fileInput5.addEventListener('change', function(e) {
            var file = fileInput5.files[0];
            var imageType = /image.*/;

            if (file.type.match(imageType)) {
                var reader = new FileReader();

                reader.onload = function(e) {
                    fileDisplayArea5.innerHTML = "";

                    var img = new Image();
                    img.src = reader.result;

                    fileDisplayArea5.appendChild(img);
                }

                reader.readAsDataURL(file);	
            } else {
                fileDisplayArea5.innerHTML = "File not supported!"
            }
        });

        }