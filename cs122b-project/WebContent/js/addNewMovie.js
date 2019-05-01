let r="";

function handlePayResult(resultDataString) {
    resultDataJson = resultDataString;
    r=resultDataString;
    console.log("handle payment response");
    console.log(resultDataJson.expiration);
    console.log(resultDataJson["status"]);

    if (resultDataJson["status"] === "SUCCESS") {
    	
      
    	jQuery("#login_error_message").text("Movie already exists!");
    	setTimeout(function(){
    	    window.location.reload();
    	},1000);
    	
    	
       
    }
    
    else {
    	window.location.replace("addComplete.html");    }
}






function submitBillingForm(formSubmitEvent) {
		    	   	
	let title=$("#title").val();
	let year=$("#year").val();
	let director=$("#director").val();
	sessionStorage.setItem('titleMovie', title);
	sessionStorage.setItem('yearMovie', year);
	sessionStorage.setItem('directorMovie', director);
	console.log("submit star form");
    formSubmitEvent.preventDefault();
  
    jQuery.ajax({
        dataType: "json",  // Setting return data type
        method: "GET",// Setting request method
        url: "../api/verifyMovie?title=" +title+"&year="+year+"&director="+director, // Setting request url, which is mapped by StarsServlet in Stars.java
        success: function(resultData) {
        	handlePayResult(resultData) ;// Setting callback function to handle data returned successfully by the SingleStarServlet
        
        }
           

});
}
// Bind the submit action of the form to a handler function
jQuery("#star_form").submit((event) => submitBillingForm(event));


