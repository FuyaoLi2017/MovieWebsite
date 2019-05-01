let movieTable = jQuery("#order_body");
let result=JSON.parse(sessionStorage.getItem('d'));

 console.log(result);
for (let i = 0; i < result.length; i++) {
   let rowHTML = "";
   rowHTML += "<tr>";
   rowHTML += "<th>";
   var salesArray = result[i]["salesIdList"];
   console.log(salesArray);
   for(var z = 0; z < salesArray.length; z++)
   	{
   	if(z == (salesArray.length-1))
   		{
   	 rowHTML += salesArray[z];
   		break;
   		}	
   	rowHTML += salesArray[z]+ ",";
   	}
   rowHTML += "</th>";
   rowHTML += "<th>" + result[i]["title"] + "</th>";
   rowHTML += "<th>" + result[i]["quantity"] + "</th>";
   
   
 
   rowHTML += "</tr>";

   // Append the row created to the table body, which will refresh the page
   movieTable.append(rowHTML);

}