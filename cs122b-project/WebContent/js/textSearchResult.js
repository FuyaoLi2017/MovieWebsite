

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
     
     var resultMovie=resultData.data.autoCompleteList;
     console.log(resultMovie);
     
    
     
     
   for(let i=0;i<resultMovie.length;i++) {
     
     var result=resultMovie[i]["data"];
     
  
      
     
      console.log(result);  
 
        let rowHTML = "";
        rowHTML += "<tr>";
        rowHTML += "<th>" +

        '<a href="single-movie.html?id=' + result['id'] + '">'
        + result["title"] +    
        '</a>' +
        "</th>";
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
       
        rowHTML += "<th>" + result["rating"]["rating"] + "</th>";
        rowHTML += "<th><button type='button'class='btn btn-outline-primary'id='"+result["id"]+"'>Add to Cart</button>";
       
        rowHTML += "</tr>";
                	
        	
        
        // Append the row created to the table body, which will refresh the page
        movieTable.append(rowHTML);
        
   }  
    
   

}



/**
 * Once this .js is loaded, following scripts will be executed by the browser\
 */

// Get id from URL
let query= getParameterByName('query');
let sequenceBase=getParameterByName('sequenceBase');
let ascOrDes= getParameterByName('ascOrDes');
let limit=getParameterByName('limit');
let offset=getParameterByName('offset');


// Makes the HTTP GET request and registers on success callback function handleResult
jQuery.ajax({
    dataType: "json",  // Setting return data type
    method: "GET",// Setting request method
    url: "../api/fullTextSearch?query="+query+"&sequenceBase="+sequenceBase+"&ascOrDes="+ascOrDes+"&limit="+limit+"&offset="+offset, // Setting request url, which is mapped by StarsServlet in Stars.java
    success:function(resultData){
    	handleResult(resultData);
    	 $("#title-asc").attr("href","textSearchResult.html?query=" + query+"&sequenceBase=title&ascOrDes=asc&limit="+limit+"&offset="+offset);
    	    $("#title-desc").attr("href","textSearchResult.html?query=" + query+"&sequenceBase=title&ascOrDes=desc&limit="+limit+"&offset="+offset);
    	    $("#year-asc").attr("href","textSearchResult.html?query=" + query+"&sequenceBase=year&ascOrDes=asc&limit="+limit+"&offset="+offset);
    	    $("#year-desc").attr("href","textSearchResult.html?query=" + query+"&sequenceBase=year&ascOrDes=desc&limit="+limit+"&offset="+offset);
    	    $("#rating-asc").attr("href","textSearchResult.html?query=" + query+"&sequenceBase=rating&ascOrDes=asc&limit="+limit+"&offset="+offset);
    	    $("#rating-desc").attr("href","textSearchResult.html?query=" + query+"&sequenceBase=rating&ascOrDes=desc&limit="+limit+"&offset="+offset);
    	    $("#5-pp").attr("href","textSearchResult.html?query=" + query+"&sequenceBase="+sequenceBase+"&ascOrDes="+ascOrDes+"&limit=5&offset="+offset);
    	    $("#10-pp").attr("href","textSearchResult.html?query=" + query+"&sequenceBase="+sequenceBase+"&ascOrDes="+ascOrDes+"&limit=10&offset="+offset);
    	    $("#20-pp").attr("href","textSearchResult.html?query=" + query+"&sequenceBase="+sequenceBase+"&ascOrDes="+ascOrDes+"&limit=20&offset="+offset);
  

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
            /*success: function (responseJson) {
               
                window.location.reload();
            },
            error: function (responseJson, status, xhr) {
                alert("bad add " + responseJson + " " + status + " " + xhr);
            }*/
        });

        return false;
    });
    
    let cur = offset/limit + 1;
    let pre = cur - 1;
    let post = cur + 1;
    let preOffset = (cur - 2) * limit;
    let postOffset = (cur) *limit;
    let numOfRecords= resultData.data["numOfRecords"];
    let pageNum= numOfRecords/limit;
   
    
    var x=localStorage.setItem('x','0');
    x=Number(offset)+Number(limit);
    console.log(x);
    
    if(offset==0 && x>numOfRecords){
    	$("#previous").attr("href","javascript:void(0)").attr("style","color:black");
    	$("#next").attr("href","javascript:void(0)").attr("style","color:black");
    	
    	localStorage.setItem('x','0');
    
    }
    
   
     else if(offset==0 && cur==1){
    	$("#previous").attr("href","javascript:void(0)").attr("style","color:black");
    	$("#next").attr("href","textSearchResult.html?query=" + query+"&sequenceBase="+sequenceBase+"&ascOrDes="+ascOrDes+"&limit="+limit+"&offset="+postOffset);
    	localStorage.setItem('x','0');
    }
    
    
    
    else if(x>numOfRecords){
    	$("#previous").attr("href","textSearchResult.html?query=" + query+"&sequenceBase="+sequenceBase+"&ascOrDes="+ascOrDes+"&limit="+limit+"&offset="+preOffset);
    	$("#next").attr("href","javascript:void(0)").attr("style","color:black");
    

    	localStorage.setItem('x','0');
    	
    
    }
    
    else{
    
    	$("#previous").attr("href","textSearchResult.html?query=" + query+"&sequenceBase="+sequenceBase+"&ascOrDes="+ascOrDes+"&limit="+limit+"&offset="+preOffset);
    	$("#next").attr("href","textSearchResult.html?query=" + query+"&sequenceBase="+sequenceBase+"&ascOrDes="+ascOrDes+"&limit="+limit+"&offset="+postOffset);
       	localStorage.setItem('x','0');
       
    	    
    }
    
    
    }




});
    


    