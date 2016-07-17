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
function changeViewPageZoom(viewPageZoom) {
	document.getElementById('viewPage').style.zoom = viewPageZoom + '%';
	document.getElementById('viewPageZoomDisplayBoard').innerHTML = viewPageZoom + '%';
}
function setPageSize(pageWidth, pageHeight) {
	document.getElementById('viewPage').style.width = pageWidth + 'px';
	document.getElementById('viewPage').style.height = pageHeight + 'px';
}


//Interface Getter
function getViewPageWrapperWidth() {
	return document.getElementById('viewPageWrapper').offsetWidth;
}
function getViewPageWrapperHeight() {
	return document.getElementById('viewPageWrapper').offsetHeight;
}