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
    console.log(result["name"]);
  
      
       let rowHTML = "";
       rowHTML += "<tr>";
       rowHTML += "<th>" + result["name"] + "</th>";
       rowHTML += "<th>" + result["birthYear"] + "</th>";
       rowHTML += "</th>";
       rowHTML += "<th>";
       var movieArray = result["moviesList"];
       for(var z = 0; z < movieArray.length; z++)
       	{
       	if(z == (movieArray.length-1))
       		{
       	 rowHTML +="<a href='single-movie.html?id=" + movieArray[z]["id"] + "'>"+ movieArray[z]["title"] + "</a>";
       		break;
       		}	
       	rowHTML +="<a href='single-movie.html?id=" + movieArray[z]["id"] + "'>"+ movieArray[z]["title"] + "</a>"+ ",";
       	}
       rowHTML += "</th>";
       /*rowHTML += "<th>";
       */
     
       rowHTML += "</tr>";

       // Append the row created to the table body, which will refresh the page
       movieTable.append(rowHTML);
       
 
   console.log("table: ",movieTable);
   
}


let starId = getParameterByName('id');
console.log("id:",starId);

// Makes the HTTP GET request and registers on success callback function handleResult
jQuery.ajax({
    dataType: "json",  // Setting return data type
    method: "GET",// Setting request method
    url: "../api/single-star?id=" + starId, // Setting request url, which is mapped by StarsServlet in Stars.java
    success: function(resultData) {
    	handleResult(resultData) ;// Setting callback function to handle data returned successfully by the SingleStarServlet
    
    }
});