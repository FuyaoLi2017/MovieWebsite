$(document).ready(function () {
    //get top three movies
    $.ajax({
        type: "GET",
        url: "../api/topMovies",
        data: {"NumofMovie" : "3"},
        dataType: "json",
        success: function (responseJson) {
            // alert("index:" + responseJson.status);
            movieList = responseJson.data;
            if (responseJson.status == "0") {
                $.each(movieList, function (index, movie) {
                    $(".movie-detail-" + index).attr("href", "api/movies?id=" + movie.id);
                    $(".movie-title-" + index).append($("<div>")).text(movie.title).append($("<b class=\"movie-ratings-" + index + "\" style=\"float: right;\">"));
                    $(".movie-ratings-" + index).text(movie.ratings.rating);
                    $(".movie-director-" + index).append($("<div>")).text(movie.director).append($("<div class=\"movie-year-" + index + "\" style=\"float: right;\">"));
                    $(".movie-year-" + index).text(movie.year);
                    for (var id in movie.genresList) {
                        $("<div>").appendTo($(".movie-genres-" + index)).append($("<div>").text(movie.genresList[id].name));
                    }
                    for (var id in movie.starsList) {
                        if (id < 4) {
                            var starId = movie.starsList[id].id;
                            $("<li>").appendTo($(".movie-stars-" + index)).append($("<a href='./star?id=" + starId + "'>").text(movie.starsList[id].name));
                        }
                    }
                    $("<li>").appendTo($(".movie-stars-" + index)).append($("<div>").text("etc."));
                });
            } else if (responseJson.status == "SUCCESS") {
            	console.log(responseJson.data);
                $.each(movieList, function (index, movie) {
                    $(".movie-detail-" + index).attr("href", "#");
                    $(".movie-detail-" + index).click(function () {
                        alert(responseJson.msg);
                        window.location = "login.html";
                    });
                    $(".movie-title-" + index).append($("<div>")).text(movie.title).append($("<b class=\"movie-ratings-" + index + "\" style=\"float: right;\">"));
                    $(".movie-ratings-" + index).text(movie.rating.rating);
                    $(".movie-director-" + index).append($("<div>")).text(movie.director).append($("<div class=\"movie-year-" + index + "\" style=\"float: right;\">"));
                    $(".movie-year-" + index).text(movie.year);
                    for (var id in movie.genresList) {
                        $("<div>").appendTo($(".movie-genres-" + index)).append($("<div>").text(movie.genresList[id].name));
                    }
                    for (var id in movie.starsList) {
                        if (id < 4) {
                            var starId = movie.starsList[id].id;
                            $("<li>").appendTo($(".movie-stars-" + index)).append($("<a class='star-" + starId + "' href='single-star.html?id=" + starId + "'>").text(movie.starsList[id].name));
                            $(".star-" + starId).click(function () {
                                console.log(id);
                         
                            });
                        }
                        
                    }
                    
                    $("#add-" + index).click(function(){
                    	
                        console.log("add");
                        var attr1 = movie.id;
                        
                        console.log(attr1);
                        console.log(attr1);
                        var attr2 = 1;
                        $.ajax({
                            type: "GET",
                            url: "../api/addOrUpdateShoppingCart",
                            data: {"movieId": attr1, "count": attr2},
                            dataType: "json",
                            success: function (responseJson) {
                               
                                window.location.reload();
                            },
                            error: function (responseJson, status, xhr) {
                                alert("bad add " + responseJson + " " + status + " " + xhr);
                            }
                        });

                        return false;
                    });
                    
                   
                });
            }
        },
        error: function (responseJson) {
            alert("ERROR:" + responseJson.status);
        }
    });
    
    
 
    
    
    
    
    
});