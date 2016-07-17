package de.NDN.app.writeInvoice;

import javafx.scene.web.WebEngine;

public class WriteInvoiceJsInterfaceImpl implements WriteInvoiceJsInterface {
	
	private WebEngine engine;
	
	public WriteInvoiceJsInterfaceImpl(WebEngine engine) {
		this.engine = engine;
	}
	
	@Override
	public int getViewPageWrapperWidth() {
		Object jsObj = this.engine.executeScript("getViewPageWrapperWidth()");
		if(jsObj instanceof Integer) {
			return (int) jsObj;
		} else {
			return -1;
		}
	}

	@Override
	public int getViewPageWrapperHeight() {
		Object jsObj = this.engine.executeScript("getViewPageWrapperHeight()");
		if(jsObj instanceof Integer) {
			return (int) jsObj;
		} else {
			return -1;
		}
	}

}
