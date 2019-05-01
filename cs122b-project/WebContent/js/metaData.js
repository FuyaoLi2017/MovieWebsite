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

    let metaDataTable = jQuery("#metaData_table_body");
    let result=resultData.data;
    console.log(result);
  
    for (let i = 0; i < result.length; i++) {
       let rowHTML = "";
       rowHTML += "<tr>";
       rowHTML += "<th>" + result[i]["tableName"] + "</th>";
       var metaDataArray = result[i]["metadataVoList"];
       rowHTML +="<th>";
       for(var z = 0; z < metaDataArray.length; z++)
       	{
           	rowHTML += metaDataArray[z]["attribute"];
           	rowHTML += "<br>";
       	}
       
       rowHTML += "</th>";
      
       rowHTML +="<th>";
       for(var k = 0; k < metaDataArray.length; k++)
       	{
           	rowHTML += metaDataArray[k]["type"];
           	rowHTML += "<br>";
       	}
       
       rowHTML += "</th>";
       rowHTML += "</tr>";

       // Append the row created to the table body, which will refresh the page
       metaDataTable.append(rowHTML);
       
 
   
   
}
}


// Makes the HTTP GET request and registers on success callback function handleResult
jQuery.ajax({
    dataType: "json",  // Setting return data type
    method: "GET",// Setting request method
    url: "../api/showMetadata", 
    success: function(resultData) {
    	handleResult(resultData) ;// Setting callback function to handle data returned successfully by the SingleStarServlet
    
    }
});