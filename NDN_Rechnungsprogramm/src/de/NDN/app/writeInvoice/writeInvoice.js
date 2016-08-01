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
	document.getElementById("btnPrint").addEventListener('click', function () {
		wInv.printDocument();
	});
	
	docL.setDefaults();
};
function setEventListener() {
	document.getElementById("addressField").addEventListener('focusout', function () {
		wInv.updateAddressField(this.id, this.innerHTML, this.parentElement.outerHTML);
	});
	document.getElementById("weekNumber").addEventListener('focusout', function () {
		wInv.updateText(this.id, this.innerHTML);
	});
}



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
function setCompanyHeader(logoHeight, logoURL, logoX, logoY, hrHeight, hrWidth, hrX, hrY) {
	var root = document.getElementById('viewPagePrintableArea');
	
	var logo = document.createElement('img');
	logo.setAttribute('id', 'companyLogo');
	logo.setAttribute('src', logoURL)
	logo.style.height = logoHeight + 'px';
	logo.style.marginLeft = logoX + 'px';
	logo.style.marginTop = logoY + 'px';	
	root.appendChild(logo)
	
	var hr = document.createElement('hr');
	hr.setAttribute('id', 'hrSeparator');
	hr.style.border = hrHeight + 'px solid #000';
	hr.style.width = hrWidth + 'px';
	hr.style.marginLeft = hrX + 'px';
	hr.style.marginTop = hrY + 'px';	
	root.appendChild(hr);	
}
function setText(id, text, fontName, fontSize, color, lineHeight, textX, textY) {
	var root = 	document.getElementById('viewPagePrintableArea');
	
	var textElem = document.createElement('span');
	if(id != "") {
		textElem.setAttribute('id', id);
	}
	textElem.setAttribute('class', 'textElem');
	textElem.innerHTML = text;
	textElem.style.fontFamily = fontName;
	textElem.style.fontSize = fontSize + "px";
	textElem.style.lineHeight = lineHeight + "px";
	textElem.style.color = color;
	textElem.style.marginLeft = textX + "px";
	textElem.style.marginTop = textY + "px";
	root.appendChild(textElem);	
}
function setMultilineTextarea(id, width, height, fontName, fontSize, color, lineHeight, verticalAlign, afX, afY) {
	var root = 	document.getElementById('viewPagePrintableArea');
	
	var addressFieldWrapper = document.createElement('div');
	if(id != "") {
		addressFieldWrapper.setAttribute('id', id + 'Wrapper');
	}	
	addressFieldWrapper.setAttribute('class', 'multilineTextareaWrapper');
	addressFieldWrapper.style.width = width + "px";
	addressFieldWrapper.style.height = height + "px";
	addressFieldWrapper.style.marginLeft = afX + "px";
	addressFieldWrapper.style.marginTop = afY + "px";
	root.appendChild(addressFieldWrapper);
	
	var addressField = document.createElement('div');
	if(id != "") {
		addressField.setAttribute('id', id);
	}	
	addressField.setAttribute('class', 'multilineTextarea');	
	addressField.setAttribute('contenteditable', 'true');
	addressField.style.fontFamily = fontName;
	addressField.style.fontSize = fontSize + "px";
	addressField.style.color = color;
	addressField.style.lineHeight = lineHeight + "px";	
	addressField.style.verticalAlign = verticalAlign;
	addressFieldWrapper.appendChild(addressField);
}
function setRectangle(width, height, bgColor, border, rectX, rectY) {
	var root = 	document.getElementById('viewPagePrintableArea');
	
	var rect = document.createElement('div');
	rect.setAttribute('class', 'rectangle');
	rect.style.width = width + 'px';
	rect.style.height = height + 'px';
	rect.style.backgroundColor = bgColor;
	rect.style.border = border;
	rect.style.marginLeft = rectX + "px";
	rect.style.marginTop = rectY + "px";
	root.appendChild(rect);
}
function setLabelWithValue(labelId, labelName, valueId, valueContent, fontName, fontSize, color, lineHeight, lX, lY, vX, vY) {
	var root = 	document.getElementById('viewPagePrintableArea');
	
	var lbl = document.createElement('span');
	if(labelId != "") {
		lbl.setAttribute('id', labelId);
	}	
	lbl.setAttribute('class', 'labels');
	lbl.innerHTML = labelName;
	lbl.style.fontFamily = fontName;
	lbl.style.fontSize = fontSize + "px";
	lbl.style.color = color;
	lbl.style.lineHeight = lineHeight + "px";
	lbl.style.marginLeft = lX + "px";
	lbl.style.marginTop = lY + "px";
	root.appendChild(lbl);
	
	var value = document.createElement('div');
	if(valueId != "") {
		value.setAttribute('id', valueId);
	}	
	value.setAttribute('class', 'labelvalues');
	value.innerHTML = valueContent;
	value.style.fontFamily = fontName;
	value.style.fontSize = fontSize + "px";
	value.style.color = color;
	value.style.lineHeight = lineHeight + "px";
	value.style.marginLeft = vX + "px";
	value.style.marginTop = vY + "px";
	if(valueContent == -1) {
		value.className += " selectable";
		value.setAttribute('contenteditable', 'true');		
	}
	root.appendChild(value);	
}

//Interface Getter
function getViewPageWrapperWidth() {
	return document.getElementById('viewPageWrapper').offsetWidth;
}
function getViewPageWrapperHeight() {
	return document.getElementById('viewPageWrapper').offsetHeight;
}
