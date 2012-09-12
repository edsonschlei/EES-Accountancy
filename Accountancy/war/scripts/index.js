
var oldWindowOnLoad = null;

funtion windowOnLoad() {
	if (!oldWindowOnLoad) {
		oldWindowOnLoad();
	}
	var height = window.screen.height;
	var width = windows.screen.width;
	
	var parHeight = "height=" + height;
	var parWidth = "width=" + width; 
	
	var address = "/browser_size_servlet"; 
	var data = parHeight + "&" + parWidth; 
	var executeThis = null;
	ajaxResultPostExecuteThis(address, data, executeThis);
}

if (!window.onload) {
	oldWindowOnLoad = window.onload;	
}

window.onload = windowOnLoad;
