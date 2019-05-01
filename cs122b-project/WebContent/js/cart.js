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
function addToCart(event,m_id,value) {
	 console.log("add");
	 if(value<1 || value>20) {
		 alert("Check the input. Quantity must be over 1 and below 20");
	}
	 else{
     var attr1 = m_id;
     
     var attr2 = value;
     $.ajax({
         type: "GET",
         url: "../api/addOrUpdateShoppingCart",
         data: {"movieId": attr1, "count": attr2},
         dataType: "json",
         success: function (responseJson) {
             
             window.location.reload();
         }
//         error: function (responseJson, status, xhr) {
//        	 alert("Check the input");
//         }
     });
    jQuery("input").serialize();
	 }
    
}

function remove(event, m_id) {
    console.log("remove a movie");
    //event.preventDefault();
       $.ajax({
        type: "GET",
        url: "../api/deleteShoppingCart",
        data: {"movieId": m_id},
        dataType: "json"
      /* success: function (responseJson) {
            
            window.location.reload();
        },
        error: function (responseJson, status, xhr) {
            alert("bad add " + responseJson + " " + status + " " + xhr);
        }*/
    });
    jQuery(".remove").serialize();
}

function handleResult(resultData) {
	
	
	
	
	var result=resultData["data"];
    let movieTableBodyElement = jQuery("#cart_body");

    // Concatenate the html tags with resultData jsonObject to create table rows
    for (let i = 0; i < result.length; i++) {
    
        let rowHTML = "";
        rowHTML += "<tr id='"+result[i].movieVo['id']+"'>";
        rowHTML += "<th>" + result[i].movieVo["title"] + "</th>";
       /* console.log(result[i]["quantity"]);*/
        rowHTML += "<th><from><input placeholder='" + result[i]["quantity"] + "' id='" + result[i].movieVo["id"] + "' name='" + result[i].movieVo["title"] + "'></input></th>";
        rowHTML += "<th>";
        rowHTML += "<button class='input btn-outline-info' id='" + result[i].movieVo["id"] + "' name='" + result[i].movieVo["title"] + "'>Update</button>";
		rowHTML += "</th>";
		rowHTML += "<th>";	
        rowHTML += "<button class='remove btn-outline-info' id='" + result[i].movieVo["id"] + "' name='" + result[i].movieVo["title"] + "'>Remove</button>";
        rowHTML += "</th>";
        rowHTML += "</tr>";

        // Append the row created to the table body, which will refresh the page
        movieTableBodyElement.append(rowHTML);
    }
    
    
    	
    $(document).on("input propertychange", 'input', function(event){     	
    	
    	var s=this.value;
    	var i=this.id;
    	console.log(s);
    	console.log(i);
     $(document).on("click", '.input', function(event) {
    	
    		    	 
    	 addToCart(event,i,s);
    	 
    	 }); 
     
       jQuery("#"+this.id).value = s;
    });
     
     
    
    $(document).on("click", '.remove', function(event) {
    	remove(event, this.id);
    	jQuery("#"+this.id).attr('placeholder', '0');
    	jQuery("#"+this.id).remove();
        
    	});
}


jQuery.ajax({
    dataType: "json",  // Setting return data type
    method: "GET",// Setting request method
    url: "../api/shoppingCart", // Setting request url, which is mapped by StarsServlet in Stars.java
    success: (resultData) => handleResult(resultData) // Setting callback function to handle data returned successfully by the SingleStarServlet
});
