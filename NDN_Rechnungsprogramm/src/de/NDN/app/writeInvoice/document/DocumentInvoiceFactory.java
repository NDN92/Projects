package de.NDN.app.writeInvoice.document;

import java.util.ArrayList;

import de.NDN.app.globalObjects.CustomerType;
import javafx.scene.web.WebEngine;

public class DocumentInvoiceFactory {
	
	private DocumentInvoice doc;
	private WebEngine engine;
	
	public DocumentInvoiceFactory(WebEngine engine) {
		this.engine = engine;
	}
	
	public void createNewDocument(CustomerType customerType) {
		this.doc = new DocumentInvoice(customerType, new ArrayList<PageInvoice>());
		this.doc.getPages().add( new PageInvoice(this.doc, 1) );
	}
	
	public void showDocumentOnViewPage() {
		if(this.doc != null) {
			
			//Company Header
			
			
			
			if(this.doc.getCustomerType() == CustomerType.PRIVATE) {
				
				this.engine.executeScript("setPrintableArea(" +
						this.doc.getPages().get(0).getPrintableArea()[0] + ", " +
						this.doc.getPages().get(0).getPrintableArea()[1] + ", " +
						this.doc.getPages().get(0).getPrintableArea()[2] + ", " +
						this.doc.getPages().get(0).getPrintableArea()[3] + ");");
				
			}
			
		}
	}
	
	
	
}
