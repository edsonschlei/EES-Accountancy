// Get the browser-specific request object, either for
// Firefox, Safari, Opera, Mozilla, Netscape, or IE 7 (top entry);
// or for Internet Explorer 5 and 6 (bottom entry). 

function getRequestObject() {
  if (window.XMLHttpRequest) {
    return(new XMLHttpRequest());
  } else if (window.ActiveXObject) { 
    return(new ActiveXObject("Microsoft.XMLHTTP"));
  } else {
    return(null); 
  }
}

// Make an HTTP request to the given address. 
// Display result in the HTML element that has given ID.

function ajaxResult(address, resultRegion) {
  var request = getRequestObject();
  request.onreadystatechange = 
    function() { showResponseText(request, 
                                  resultRegion); };
  request.open("GET", address, true);
  request.send(null);
}

// Make an HTTP request to the given address. 
// Display result in the HTML element that has given ID.
// Use POST. 

function ajaxResultPostExecuteThis(address, data, executeThis) {
  var request = getRequestObject();
  request.onreadystatechange = 
    function() { whenResponseOkExecuteThis(request, executeThis); };
  request.open("POST", address, true);
  request.setRequestHeader("Content-Type", 
                           "application/x-www-form-urlencoded");
  request.send(data);
}


function ajaxResultPost(address, data, resultRegion) {
  var request = getRequestObject();
  request.onreadystatechange = 
    function() { showResponseText(request, 
                                  resultRegion); };
  request.open("POST", address, true);
  request.setRequestHeader("Content-Type", 
                           "application/x-www-form-urlencoded");
  request.send(data);
}

function ajaxResultPost(address, data, resultRegion, transformResponseText) {
  var request = getRequestObject();
  request.onreadystatechange = 
    function() { showResponseText(request, 
                                  resultRegion, transformResponseText); };
  request.open("POST", address, true);
  request.setRequestHeader("Content-Type", 
                           "application/x-www-form-urlencoded");
  request.send(data);
}


// Put response text in the HTML element that has given ID.

function showResponseText(request, resultRegion) {
  if ((request.readyState == 4) &&
      (request.status == 200)) {
    htmlInsert(resultRegion, request.responseText);
  }
}

function showResponseText(request, resultRegion, transformResponseText) {
	if ((request.readyState == 4) &&
			(request.status == 200)) {
		htmlInsert(resultRegion, transformResponseText(request.responseText));
	}
}

function whenResponseOkExecuteThis(request, executeThis) {
	if ((request.readyState == 4) && (request.status == 200)) {
		executeThis(request);
	}
}

// Insert the html data into the element that has the specified id.

function htmlInsert(id, htmlData) {
  document.getElementById(id).innerHTML = htmlData;
}

// Return escaped value of textfield that has given id.
// The builtin "escape" function converts < to &lt;, etc.

function getValue(id) {
  return(escape(document.getElementById(id).value));
}

//Takes as input an array of headings (to go into th elements)
//and an array-of-arrays of rows (to go into td
//elements). Builds an xhtml table from the data.

function getTable(headings, rows) {
var table = "<table border='1'>\n" +
           getTableHeadings(headings) +
           getTableBody(rows) +
           "</table>";
return(table);
}

function getTableHeadings(headings) {
var firstRow = "  <tr>";
for(var i=0; i<headings.length; i++) {
 firstRow += "<th>" + headings[i] + "</th>";
}
firstRow += "</tr>\n";
return(firstRow);
}

function getTableBody(rows) {
var body = "";
for(var i=0; i<rows.length; i++) {
 body += "  <tr>";
 var row = rows[i];
 for(var j=0; j<row.length; j++) {
   body += "<td>" + row[j] + "</td>";
 }
 body += "</tr>\n";
}
return(body);
}

//Trick so that the Firebug console.log function will
//be ignored (instead of crashing) in Internet Explorer.
//Also see Firebug Lite and Faux Console if you want 
//logging to actually do something on IE.
//Firebug Lite: http://www.getfirebug.com/lite.html
//Faux Console: http://icant.co.uk/sandbox/fauxconsole/

try { console.log("Loading script"); 
} catch(e) { console = { log: function() {} }; }
