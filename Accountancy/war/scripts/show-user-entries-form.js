
// transform response text from json to html
function transformResponseText(responseText) {
    var data = eval("(" + responseText + ")");
    var table = getTable(data.headings, data.rows);
    return table;
}

// 
function changeAccount(box) {
	var yearBox = document.getElementById("yearBox");
	var chosenYear = yearBox.options[yearBox.selectedIndex];
	var parYear = "year=" + chosenYear.value;
	
	var monthBox = document.getElementById("monthBox"); 
	var chosenMonth = monthBox.options[monthBox.selectedIndex];
	var parMonth = "month=" + chosenMonth.value;
	
	var chosenoption = box.options[box.selectedIndex];
	var parCode = "code=" + chosenoption.value; 
	
	var data = parYear + "&" + parMonth + "&" + parCode;
	var address = "user-data-entries-filter-code-servlet";
	
	htmlInsert("entries", "");
	
	ajaxResultPost(address, data, "entries", transformResponseText);
	
//	htmlInsert("entries", chosenoption.value);
	
//	var selectmenu = document.getElementById("creditBox")
	// selectmenu.onchange = function() { //run some code when "onchange" event fires
	//   var chosenoption = this.options[this.selectedIndex] //this refers to "selectmenu"
	//   document.entryForm.credit.value = chosenoption.value
	// }
	
}


// changed the selected year
function changeYear(yearBox) {
	htmlInsert("entries", "");

	var parYear = "year=" + yearBox.options[yearBox.selectedIndex].value
	var address = "get-months-servlet";
	var data = parYear;
	ajaxResultPostExecuteThis(address, data, updateMonthBox);
}

// Update the month box when changed the year box
function updateMonthBox(request) {
    var data = eval("(" + request.responseText + ")");
	
    var monthBox = document.getElementById("monthBox");
    monthBox.options.lenght = data.months.length;
    
    for(var i = 0;  i < data.months.length; i++) {
    	var newOption = new Option(data.months[i].number, data.months[i].number, false, true);
    	newOption.innerText = data.months[i].description;
    	monthBox.options[i] = newOption;
    }
	applyFilter();
}

function applyFilter() {
    var accountBox = document.getElementById("accountBox");
    if (accountBox.selectedIndex > 0) {
    	changeAccount(accountBox);
    }
}

//
function changeMonth(box) {
	htmlInsert("entries", "");
	applyFilter();
}


