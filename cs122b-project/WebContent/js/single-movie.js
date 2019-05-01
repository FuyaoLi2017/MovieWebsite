/**
 * This example is following frontend and backend separation.
 *
 * Before this .js is loaded, the html skeleton is created.
 *
 * This .js performs three steps:
 *      1. Get parameter from request URL so it know which id to look for
 *      2. Use jQuery to talk to backend API to get the json data.
 *      3. Populate the data to correct html elements.
 */


/**
 * Retrieve parameter from request URL, matching by parameter name
 * @param target String
 * @returns {*}
 */
function getParameterByName(target) {
    // Get request URL
    let url = window.location.href;
    // Encode target parameter name to url encoding
    target = target.replace(/[\[\]]/g, "\\$&");

    // Ues regular expression to find matched parameter value
    let regex = new RegExp("[?&]" + target + "(=([^&#]*)|&|#|$)"),
        results = regex.exec(url);
    if (!results) return null;
    if (!results[2]) return '';

    // Return the decoded parameter value
    return decodeURIComponent(results[2].replace(/\+/g, " "));
}

function handleResult(resultData) {

     console.log("handleResult: populating movie table from resultData");

     let movieTable = jQuery("#movie_table_body");
     
     let result=resultData.data;
   
        let rowHTML = "";
        rowHTML += "<tr>";
        rowHTML += "<th>" + result["id"] + "</th>";
        rowHTML += "<th>" + result["title"] + "</th>";
        rowHTML += "<th>" + result["year"] + "</th>";
        rowHTML += "<th>" + result["director"] + "</th>";
        rowHTML += "</th>";
        rowHTML += "<th>";
        var genreArray = result["genresList"];
        for(var z = 0; z < genreArray.length; z++)
        	{
        	if(z == (genreArray.length-1))
        		{
        		rowHTML += genreArray[z]["name"];
        		break;
        		}	
        	rowHTML += genreArray[z]["name"]+',';
        	}
        rowHTML += "</th>";
        rowHTML += "<th>";
        var starArray = result["starsList"];
        for(var t = 0; t < starArray.length; t++)
        	{
        	if(t == (starArray.length-1))
        		{
        		 rowHTML +="<a href='single-star.html?id=" + starArray[t]["id"] + "'>"+ starArray[t]["name"] + "</a>";
        		break;
        		}	
        	 rowHTML += "<a href='single-star.html?id=" + starArray[t]["id"] + "'>"+ starArray[t]["name"] + "</a>"+',';
        	}
        rowHTML += "</th>";
        rowHTML += "</th>";
        rowHTML += "<th>" + result.rating["rating"] + "</th>";
        rowHTML += "<th><button type='button'class='btn btn-outline-primary'id='"+result["id"]+"'>Add to Cart</button>";
        rowHTML += "</tr>";

        // Append the row created to the table body, which will refresh the page
        movieTable.append(rowHTML);
        
   }  
    
    


/**
 * Once this .js is loaded, following scripts will be executed by the browser\
 */

// Get id from URL
let movieId = getParameterByName('id');
console.log("id:",movieId);
// Makes the HTTP GET request and registers on success callback function handleResult
jQuery.ajax({
    dataType: "json",  // Setting return data type
    method: "GET",// Setting request method
    url: "../api/single-movie?id=" + movieId, // Setting request url, which is mapped by StarsServlet in Stars.java
    success: function(resultData){
    	handleResult(resultData) ;// Setting callback function to handle data returned successfully by the SingleStarServlet
    	$(".btn").click(function(){
        	
            console.log("add");
            var attr1 = $(this).attr("id");
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
    }
});

