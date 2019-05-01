var prevSearch = new Map();
$(document).ready(function () {

    
    $('#autocomplete').autocomplete({
        minChars: 3,
        
        
        
        
        lookup: function (query, doneCallback) {
            handleLookup(query, doneCallback)
        },
        onSelect: function(suggestion) {
            handleSelectSuggestion(suggestion)
        },
        
        
        deferRequestBy: 300,
        
    });

    

    $('#autocomplete').keypress(function(event) {
        
        if (event.keyCode == 13) {
           
            handleNormalSearch($('#autocomplete').val());
            return false;
        }
    });

    
    $('#fulltext').click(function () {
        handleNormalSearch($('#autocomplete').val());
    });

   
});

function handleSelectSuggestion(suggestion) {
    console.log("you select " + suggestion["value"]);
    var url = "single-movie.html?id=" + suggestion.data.id;
    console.log(url);
    window.location.replace(url);
}


function handleNormalSearch(query) {
    console.log("doing normal search with query: " + query);
    var url ="textSearchResult.html?query=" + query+"&sequenceBase=title&ascOrDes=asc&limit=20&offset=0";
    window.location.replace(url);
}

function handleLookup(query, doneCallback) {
    console.log("autocomplete initiated")
    console.log("sending AJAX request to backend Java Servlet")

    if (prevSearch.get(query) != null) {
        doneCallback( { suggestions: prevSearch.get(query) } );
        console.log("LookUp from cache");
    } else {
        console.log("LookUp from request");
        
        $.ajax({
            "method": "GET",
            
            "url": "../api/fullTextSearch?query=" + escape(query)+"&limit=10",
            "success": function (responseJson) {
              handleLookupAjaxSuccess(responseJson, query, doneCallback)
            },
            "error": function (errorData) {
                console.log("lookup ajax error")
                console.log(errorData)
            }
        });
    }
}


function handleLookupAjaxSuccess(responseJson, query, doneCallback) {
    console.log("lookup ajax successful");

    

    console.log(responseJson.data.autoCompleteList);
    
    prevSearch.set(query, responseJson.data.autoCompleteList);
   
    doneCallback( { 
    	
    	/* source: function(request, response) {
    	        var results = $.ui.autocomplete.filter( responseJson.data.autoCompleteList, request.term);
    	        
    	        response(results.slice(0, 10));
    	        console.log(responseJson.data.autoCompleteList);

    	    },*/
    	
    	suggestions: responseJson.data.autoCompleteList 
    	
    
    } );
}


