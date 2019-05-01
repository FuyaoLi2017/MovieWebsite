let r="";
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

function handlePayResult(resultDataString) {
    resultDataJson = resultDataString;
    r=resultDataString;
    console.log("handle payment response");
    console.log(resultDataJson.expiration);
    console.log(resultDataJson["status"]);

    if (resultDataJson["status"] === "SUCCESS") {
    	
    	
    	jQuery("#success").text("Successfully added");
    	
       
    }
    
    else {
        console.log("show error message");
        /*alert(resultDataJson["message"]);
        window.location.reload();*/
        jQuery("#login_error_message").text(resultDataJson["message"]);
        setTimeout(function(){
    	    window.location.reload();
    	},1000);
    	
       
        
    }
}








function submitBillingForm(formSubmitEvent) {
		    	   	
	let starName=$("#star").val();
	let birthYear=$("#year").val();
	console.log(starName);
	console.log("submit star form");
    formSubmitEvent.preventDefault();
  
    jQuery.ajax({
        dataType: "json",  // Setting return data type
        method: "GET",// Setting request method
        url: "../api/addStar?starName=" +starName+"&birthYear="+birthYear, // Setting request url, which is mapped by StarsServlet in Stars.java
        success: function(resultData) {
        	handlePayResult(resultData) ;// Setting callback function to handle data returned successfully by the SingleStarServlet
        
        }
           

});
}

// Bind the submit action of the form to a handler function
jQuery("#billing_form").submit((event) => submitBillingForm(event));



