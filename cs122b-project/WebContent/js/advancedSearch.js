$(document).ready(function () {
    $("#search").click(function () {
        var title = $("#inputTitle").val();
        var year = $("#inputYear").val();
        var director = $("#inputDirector").val();
        var star = $("#inputStarName").val();
        console.log(star);
        

         $.ajax({
             type: "GET",
             url: "../api/search",
             data: {"title": title, "year": year, "star": star, "director": director},
            dataType: "json",
             success: function (responseJson) {
            	 console.log(responseJson.data);
            	 handleResult(responseJson);
                if (responseJson.status == "SUCCESS") {
                	sessionStorage.setItem("advance",JSON.stringify(responseJson.data));
                	 var url = "aSearchResult.html?title=" + title + "&&year=" + year + "&&star=" + star + "&&director=" + director;
                     window.location.href=url;
                     
                 }
            },
            error: function (responseJson, status, xhr) {
                alert(responseJson + " " + status + " " + xhr);
             }
         });
       
    });
});