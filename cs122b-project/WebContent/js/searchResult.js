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
     var result=resultData["data"].movieVoList;
     
     
     
      console.log(result);  
   for (let i = 0; i < result.length; i++) {
        let rowHTML = "";
        rowHTML += "<tr>";
        rowHTML += "<th>" +

        '<a href="single-movie.html?id=' + result[i]['id'] + '">'
        + result[i]["title"] +    
        '</a>' +
        "</th>";
        rowHTML += "<th>" + result[i]["year"] + "</th>";
        rowHTML += "<th>" + result[i]["director"] + "</th>";
        rowHTML += "</th>";
        rowHTML += "<th>";
        var genreArray = result[i]["genresList"];
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
        
        var starArray = result[i]["starsList"];
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
       
        rowHTML += "<th>" + result[i]["rating"]["rating"] + "</th>";
        rowHTML += "<th><button type='button'class='btn btn-outline-primary'id='"+result[i]["id"]+"'>Add to Cart</button>";
       
        rowHTML += "</tr>";
                	
        	
        
        // Append the row created to the table body, which will refresh the page
        movieTable.append(rowHTML);
        
   }  
    
    console.log("table: ",movieTable);
    
}


 /*   $(".addCart").click(function(){
        var href = $(this).attr("href");
        // alert(href);
        var attr1 = href.split(",")[0];
        var attr2 = href.split(",")[1];
        $.ajax({
            type: "GET",
            url: "api/addOrUpdateShoppingCart",
            data: {"movieId": attr1, "count": attr2},
            dataType: "json",
            success: function (responseJson) {
                alert(responseJson.msg);
                window.location.reload();
            },
            error: function (responseJson, status, xhr) {
                alert("bad add " + responseJson + " " + status + " " + xhr);
            }
        });
*/
    


/**
 * Once this .js is loaded, following scripts will be executed by the browser\
 */

// Get id from URL
let browseType= getParameterByName('browseType');
let category= getParameterByName('category');
let sequenceBase=getParameterByName('sequenceBase');
let ascOrDes= getParameterByName('ascOrDes');
let limit=getParameterByName('limit');
let offset=getParameterByName('offset');
if(limit==null) limit="20";
if(sequenceBase==null) sequenceBase="title";
if(ascOrDes==null) ascOrDes="asc";
if(offset==null) offset="0";
	

// Makes the HTTP GET request and registers on success callback function handleResult
jQuery.ajax({
    dataType: "json",  // Setting return data type
    method: "GET",// Setting request method
    url: "../api/browse?browseType=" + browseType+"&category="+category+"&sequenceBase="+sequenceBase+"&ascOrDes="+ascOrDes+"&limit="+limit+"&offset="+offset, // Setting request url, which is mapped by StarsServlet in Stars.java
    success:function(resultData){
    	handleResult(resultData);
    $("#title-asc").attr("href","searchResult.html?browseType=" + browseType+"&category="+category+"&sequenceBase=title&ascOrDes=asc"+"&limit="+limit+"&offset="+offset);
    $("#title-desc").attr("href","searchResult.html?browseType=" + browseType+"&category="+category+"&sequenceBase=title&ascOrDes=desc"+"&limit="+limit+"&offset="+offset);
    $("#year-asc").attr("href","searchResult.html?browseType=" + browseType+"&category="+category+"&sequenceBase=year&ascOrDes=asc"+"&limit="+limit+"&offset="+offset);
    $("#year-desc").attr("href","searchResult.html?browseType=" + browseType+"&category="+category+"&sequenceBase=year&ascOrDes=desc"+"&limit="+limit+"&offset="+offset);
    $("#rating-asc").attr("href","searchResult.html?browseType=" + browseType+"&category="+category+"&sequenceBase=rating&ascOrDes=asc"+"&limit="+limit+"&offset="+offset);
    $("#rating-desc").attr("href","searchResult.html?browseType=" + browseType+"&category="+category+"&sequenceBase=rating&ascOrDes=desc"+"&limit="+limit+"&offset="+offset);
    $("#5-pp").attr("href","searchResult.html?browseType=" + browseType+"&category="+category+"&sequenceBase="+sequenceBase+"&ascOrDes="+ascOrDes+"&limit=5"+"&offset="+offset);
    $("#10-pp").attr("href","searchResult.html?browseType=" + browseType+"&category="+category+"&sequenceBase="+sequenceBase+"&ascOrDes="+ascOrDes+"&limit=10"+"&offset="+offset);
    $("#20-pp").attr("href","searchResult.html?browseType=" + browseType+"&category="+category+"&sequenceBase="+sequenceBase+"&ascOrDes="+ascOrDes+"&limit=20"+"&offset="+offset);
    /*$("#previous").attr("href","searchResult.html?browseType=" + browseType+"&category="+category+"&sequenceBase="+sequenceBase+"&ascOrDes="+ascOrDes+"&limit="+limit"&offset"+preOffset);
    $("#next").attr("href","searchResult.html?browseType=" + browseType+"&category="+category+"&sequenceBase="+sequenceBase+"&ascOrDes="+ascOrDes+"&limit="+limit"&offset"+postOffset);	
*/  
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
   
    console.log(numOfRecords);
    var x=localStorage.setItem('x','0');
    x=Number(offset)+Number(limit);
    console.log(x);
    
    if(offset==0 && x>numOfRecords){
    	$("#previous").attr("href","javascript:void(0)").attr("style","color:black");
    	$("#next").attr("href","javascript:void(0)").attr("style","color:black");
    	
    	localStorage.setItem('x','0');
    
    }
    
   
    
    
    
    
    /*else if(offset==0 && cur==1){
    	$("#previous").attr("href","javascript:void(0)").attr("style","color:black");
    	$("#next").attr("href","searchResult.html?browseType=" + browseType+"&category="+category+"&sequenceBase="+sequenceBase+"&ascOrDes="+ascOrDes+"&limit="+limit+"&offset="+postOffset);
        pageNum--;
    }
    
    else{
    
    
    while(pageNum>0){
    	

   	 $("#previous").attr("href","searchResult.html?browseType=" + browseType+"&category="+category+"&sequenceBase="+sequenceBase+"&ascOrDes="+ascOrDes+"&limit="+limit+"&offset="+preOffset);
     $("#next").attr("href","searchResult.html?browseType=" + browseType+"&category="+category+"&sequenceBase="+sequenceBase+"&ascOrDes="+ascOrDes+"&limit="+limit+"&offset="+postOffset);
      
     pageNum--;
     	
    	
    }
    
    if(pageNum==1){
    	$("#previous").attr("href","searchResult.html?browseType=" + browseType+"&category="+category+"&sequenceBase="+sequenceBase+"&ascOrDes="+ascOrDes+"&limit="+limit+"&offset="+preOffset);
    	$("#next").attr("href","javascript:void(0)").attr("style","color:black");
    	pageNum--;
    
    }
    
    }*/
    
    else if(offset==0 && cur==1){
    	$("#previous").attr("href","javascript:void(0)").attr("style","color:black");
    	$("#next").attr("href","searchResult.html?browseType=" + browseType+"&category="+category+"&sequenceBase="+sequenceBase+"&ascOrDes="+ascOrDes+"&limit="+limit+"&offset="+postOffset);
    	localStorage.setItem('x','0');
    }
    
    
    
    else if(x>numOfRecords){
    	$("#previous").attr("href","searchResult.html?browseType=" + browseType+"&category="+category+"&sequenceBase="+sequenceBase+"&ascOrDes="+ascOrDes+"&limit="+limit+"&offset="+preOffset);
    	$("#next").attr("href","javascript:void(0)").attr("style","color:black");
    

    	localStorage.setItem('x','0');
    	
    
    }
    
    else{
    
    	 $("#previous").attr("href","searchResult.html?browseType=" + browseType+"&category="+category+"&sequenceBase="+sequenceBase+"&ascOrDes="+ascOrDes+"&limit="+limit+"&offset="+preOffset);
       	 $("#next").attr("href","searchResult.html?browseType=" + browseType+"&category="+category+"&sequenceBase="+sequenceBase+"&ascOrDes="+ascOrDes+"&limit="+limit+"&offset="+postOffset);
       	localStorage.setItem('x','0');
       
    	    
    }
    
    
    }







});
    

