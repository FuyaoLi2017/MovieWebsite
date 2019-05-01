function handleResult(resultData) {

    console.log("handleResult: populating movie table from resultData");

    let movieTableBodyElement = jQuery("#movie_table_body");
    
        for (let i = 0; i < resultData.length; i++) {
        let rowHTML = "";
        rowHTML += "<tr>";
        rowHTML += "<th>" +

        '<a href="single-movie.html?id=' + resultData[i]['movie_id'] + '">'
        + resultData[i]["movie_title"] +    
        '</a>' +
        "</th>";
        rowHTML += "<th>" + resultData[i]["movie_year"] + "</th>";
        rowHTML += "<th>" + resultData[i]["movie_director"] + "</th>";
        rowHTML += "</th>";
        rowHTML += "<th>";
        var genreArray = resultData[i]["genresList"];
        for(var z = 0; z < genreArray.length; z++)
        	{
        	if(z == (genreArray.length-1))
        		{
        		rowHTML += genreArray[z]["genreName"];
        		break;
        		}	
        	rowHTML += genreArray[z]["genreName"]+',';
        	}
        rowHTML += "</th>";
        rowHTML += "<th>";
        var starArray = resultData[i]["starsList"];
        for(var t = 0; t < starArray.length; t++)
        	{
        	if(t == (starArray.length-1))
        		{
        		rowHTML += '<a href="single-star.html?id=' + starArray[t]['starId'] + '">'
                + starArray[t]["starName"] +    
                '</a>';
        		 
        		break;
        		}	
        	rowHTML +='<a href="single-star.html?id=' + starArray[t]['starId'] + '">'
	        + starArray[t]["starName"] +    
	        '</a>' +',';
        	}
        rowHTML += "</th>";
        rowHTML += "</th>";
        rowHTML += "<th>" + resultData[i]["rating"] + "</th>";
        rowHTML += "<th><button type='button'class='btn btn-outline-primary'id='"+result[i]["id"]+"'>Add to Cart</button>";
        rowHTML += "</tr>";
        
        

        // Append the row created to the table body, which will refresh the page
        movieTableBodyElement.append(rowHTML);
    }
        
        console.log("table: ",movieTableBodyElement);
}

/**
 * Once this .js is loaded, following scripts will be executed by the browser\
 */


jQuery.ajax({
	
    dataType: "json",  // Setting return data type
    method: "GET",// Setting request method
    url: "../api/movies",
    success: function(resultData){
    	handleResult(resultData);// Setting callback function to handle data returned successfully by the SingleStarServlet
    	  $(".btn").click(function(){
    	    	
    	        console.log("add");
    	        var attr1 = $(this).attr("id");
    	        console.log(attr1);
    	        var attr2 = 1;
    	        $.ajax({
    	            type: "GET",
    	            url: "api/addOrUpdateShoppingCart",
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
    	    
    }
});

