
function handleAddResult(resultDataString) {
    resultDataJson = resultDataString;
    
    console.log("handle response");
    console.log(resultDataJson);
    console.log(resultDataJson.message);
    

    if (resultDataJson["status"] === "SUCCESS") {
    	
    	
    	jQuery("#success").text("Sucessfully added!");
       
    }
    
    else {
    	alert("error");
        console.log("show error message");
        console.log(resultDataJson["message"]);
        jQuery("#login_error_message").text(resultDataJson["message"]);
    }
}






/*function submitForm(formSubmitEvent) {
	
	let genreName=$("#genreName").val();
	let starName = $('#starName').val();
	let birthYear=$("#starBirth").val();
	console.log(starName);
	var isCheckedGenre = $('#ExistingGenre:checked').val()?true:false;
	var isCheckedStar = $('#ExistingStar:checked').val()?true:false;
	var isNewGenre = $('#NewGenre:checked').val()?true:false;
	var isCheckedStar = $('#NewStar:checked').val()?true:false;
	console.log(isCheckedStar);
	console.log(isCheckedGenre);
     
        
        let genreId=" ";
        if(isCheckedGenre){
		console.log("Existing genre");
		console.log(genreName);
		jQuery.ajax({
	        dataType: "json",  // Setting return data type
	        method: "GET",// Setting request method
	        url: "../api/searchGenre?partialGenre="+genreName, // Setting request url, which is mapped by StarsServlet in Stars.java
	        success: function(resultData) {
	        	 console.log(resultData["status"]);
	        	if (resultData.data.length=== 0) {
	        		jQuery("#login_error_message").text("Genre doesn't exist");
	        		
	               
	            }
	            
	            else {
	            	sessionStorage.setItem('genreId',resultData.data[0].id);	
	                
	            }
	             	
	        },
	         

	});
        }
        
      
        
	if(isCheckedStar){
	    event.preventDefault();
	    
		console.log("Existing star");
		console.log(starName);
		console.log("../api/searchStar?partialStarName="+starName);
		jQuery.ajax({
	        dataType: "json",  // Setting return data type
	        method: "GET",// Setting request method
	        url: "../api/searchStar?partialStarName="+starName, // Setting request url, which is mapped by StarsServlet in Stars.java
	        success: function(resultData) {
	        	 console.log(resultData["status"]);
	        	if (resultData.data.length=== 0) {
	        		jQuery("#login_error_message").text("Star doesn't exist");
	        		
	               
	            }
	            
	            else {
	            	sessionStorage.setItem('starId',resultData.data[0].id);	
	                
	            }
	             	
	        },
	         
	           

	});
	}
		
	
	jQuery.ajax({
	        dataType: "json",  // Setting return data type
	        method: "GET",// Setting request method
	        url: "../api/addMovie?title=" +title+"&year="+year+"&director="+director+"&genreId="+genreId+"&genreName="+genreName+"&starName="+starName, // Setting request url, which is mapped by StarsServlet in Stars.java
	        success: function(resultData) {
	        	handlePayResult(resultData) ;// Setting callback function to handle data returned successfully by the SingleStarServlet
	        
	        }
		});
   
    	
           

}*/

// Bind the submit action of the form to a handler function
/*jQuery("#movie_form").submit((event) => submitForm(event));*/
jQuery("#add").click(function(){
	let title=sessionStorage.getItem('titleMovie');
	let year=sessionStorage.getItem('yearMovie');
	let director=sessionStorage.getItem('directorMovie');
	var genreName=" ";
	var starName=" ";
	let birthYear=$("#starBirth").val();
	var isCheckedGenre = $('#ExistingGenre:checked').val()?true:false;
	var isCheckedStar = $('#ExistingStar:checked').val()?true:false;
	
	var isNewGenre=$('#NewGenre:checked').val()?true:false;
	var isNewStar=$('#NewStar:checked').val()?true:false;
	console.log(isNewGenre);
    if(isCheckedGenre){
        $("#genreN").attr('placeholder','');
    	genreName=$("#genreName").val();
		console.log("Existing genre");
		console.log(genreName);
		jQuery.ajax({
	        dataType: "json",  // Setting return data type
	        method: "GET",// Setting request method
	        url: "../api/searchGenre?partialGenre="+genreName, // Setting request url, which is mapped by StarsServlet in Stars.java
	        success: function(resultData) {
	        	 console.log(resultData["status"]);
	        	if (resultData.data.length=== 0) {
	        		jQuery("#login_error_message").text("Genre doesn't exist");
	        		
	        		
	               
	            }
	            
	            else {
	            	sessionStorage.setItem('genreId',resultData.data[0].id);
	            	console.log(sessionStorage.getItem('genreId'));
	                console.log("heloo");
	                
	            }
	             	
	        },
	         

	});
        }
        
	else if(isNewGenre){
		genreName=$("#genreN").val();
		console.log(genreName);
	}
	
	
	if(isCheckedStar){
	    event.preventDefault();
	    starName=$("#starName").val();
		console.log("Existing star");
		console.log(starName);
		console.log("../api/searchStar?partialStarName="+starName);
		jQuery.ajax({
	        dataType: "json",  // Setting return data type
	        method: "GET",// Setting request method
	        url: "../api/searchStar?partialStarName="+starName, // Setting request url, which is mapped by StarsServlet in Stars.java
	        success: function(resultData) {
	        	 console.log(resultData["status"]);
	        	if (resultData.data.length=== 0) {
	        		jQuery("#login_error_message").text("Star doesn't exist");
	        		
	               
	            }
	            
	            else {
	            	
	            	sessionStorage.setItem('starId',resultData.data[0].id);
	            	
	                
	            }
	             	
	        },
	         
	           

	});
	}
	else if(isNewStar){
		starName=$("#starN").val();
	}
	
	var genreId="";
	if(isCheckedGenre){
	genreId=sessionStorage.getItem('genreId');
	}
	else if(isNewGenre){
		genreId="";
	}
	var starId="";
	if(isCheckedStar){
		starId=sessionStorage.getItem('starId');
	}
	else if(isNewStar){
		starId="";
	}
		
	console.log(genreId);
	 event.preventDefault();
	jQuery.ajax({
        dataType: "json",  // Setting return data type
        method: "GET",// Setting request method
        url: "../api/addMovie?title=" +title+"&year="+year+"&director="+director+"&genreId="+genreId+"&genreName="+genreName+"&starId="+starId+"&starName="+starName+"&birthYear="+birthYear, // Setting request url, which is mapped by StarsServlet in Stars.java
        success: function(resultData) {
        	handleAddResult(resultData) ;// Setting callback function to handle data returned successfully by the SingleStarServlet
            console.log("add");
        }
	});

	
});
