/**
 * 
 */

//Controller
window.onload = function () {
	document.getElementById("btnZoomIn").addEventListener('click', function () {
		wInv.zoomInViewPage();
	});
	document.getElementById("btnZoomOut").addEventListener('click', function () {
		wInv.zoomOutViewPage();
	});
	
	docL.setDefaults();
};


//View
function changeViewPageZoom(viewPageZoom, viewPageShadowBlur, viewPageMargin) {
	document.getElementById('viewPage').style.zoom = viewPageZoom + '%';
	document.getElementById('viewPageZoomDisplayBoard').innerHTML = viewPageZoom + '%';
	
	document.getElementById('viewPage').style.boxShadow = '0px 0px ' + viewPageShadowBlur + 'px 0px grey';
	document.getElementById('viewPage').style.margin = viewPageMargin + 'px auto ' + viewPageMargin + 'px';
}
function setPageSize(pageWidth, pageHeight) {
	document.getElementById('viewPage').style.width = pageWidth + 'px';
	document.getElementById('viewPage').style.height = pageHeight + 'px';
}

function setPrintableArea(topMargin, rightMargin, bottomMargin, leftMargin) {
	document.getElementById('viewPage').style.padding = topMargin + 'px ' +  rightMargin + 'px ' + bottomMargin + 'px ' + leftMargin + 'px';
}
function setCompanyHeader(height, logoURL) {
	
	document.getElementById('viewPagePrintableArea').innerHTML = '<div></div>';
}

//Interface Getter
function getViewPageWrapperWidth() {
	return document.getElementById('viewPageWrapper').offsetWidth;
}
function getViewPageWrapperHeight() {
	return document.getElementById('viewPageWrapper').offsetHeight;
}
