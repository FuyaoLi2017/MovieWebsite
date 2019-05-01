$(document).ready(function () {

	//get all genres
    $.ajax({
        type: "GET",
        url: "../api/genres",
        success: function (responseJson) {
            var str1 = '', str2 = '', str3 = '';
            $.each(responseJson.data, function(k, genre){
                var className = "genre" + k;
                var url = 'searchResult.html?browseType=genre&category=' + genre.name;
                if (k < 8) {
                    str1 += '<li><a class=' + className + ' href="' + url + '">';
                    str1 += genre.name;
                    str1 += '</a></li>';
                } else if (k < 16) {
                    str2 += '<li><a class=' + className + ' href="' + url + '">';
                    str2 += genre.name;
                    str2 += '</a></li>';
                } else {
                    str3 += '<li><a class=' + className + ' href="' + url + '">';
                    str3 += genre.name;
                    str3 += '</a></li>';
                }
                
            });
            $(".genres-list-1").append(str1);
            $(".genres-list-2").append(str2);
            $(".genres-list-3").append(str3);

        },
        error: function (responseJson) {
            alert(responseJson);
        }
    });


        	   
      	   
        	   
        	   
        	   
        	
    var numStr1 = "", numStr2 = "";
    var letterStr1 = "", letterStr2 = "", letterStr3 = "", letterStr4 = "";
    for (var i = 0; i <= 9; i++) {
        var url = 'searchResult.html?browseType=firstLetter&category=' + i;
        var className = "num" + i;
        if (i <= 4) {
            numStr1 += '<li><a class=' + className + ' href="' + url + '">';
            numStr1 += i;
            numStr1 += '</a></li>';
        } else {
            numStr2 += '<li><a class=' + className + ' href="' + url + '">';
            numStr2 += i;
            numStr2 += '</a></li>';
        }
    }
    $(".number-list-1").append(numStr1);
    $(".number-list-2").append(numStr2);

    for (var j = 65; j < 91; j++) {
        var url = 'searchResult.html?browseType=firstLetter&category=' + String.fromCharCode(j); 
        var className = "letter" + String.fromCharCode(j);
        if (j <= 71) {
            letterStr1 += '<li><a class=' + className + ' href="' + url + '">';
            letterStr1 += String.fromCharCode(j);
            letterStr1 += '</a></li>';
        } else if (j <= 78){
            letterStr2 += '<li><a class=' + className + ' href="' + url + '">';
            letterStr2 += String.fromCharCode(j);
            letterStr2 += '</a></li>';
        } else if (j <= 84) {
            letterStr3 += '<li><a class=' + className + ' href="' + url + '">';
            letterStr3 += String.fromCharCode(j);
            letterStr3 += '</a></li>';
        } else {
            letterStr4 += '<li><a class=' + className + ' href="' + url + '">';
            letterStr4 += String.fromCharCode(j);
            letterStr4 += '</a></li>';
        }
    }
    $(".letter-list-1").append(letterStr1);
    $(".letter-list-2").append(letterStr2);
    $(".letter-list-3").append(letterStr3);
    $(".letter-list-4").append(letterStr4);

    
});   

   