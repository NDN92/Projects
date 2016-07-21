package de.NDN.app.writeInvoice;

import de.NDN.app.globalObjects.CustomerType;
import de.NDN.app.writeInvoice.document.DocumentInvoice;
import de.NDN.app.writeInvoice.document.DocumentInvoiceFactory;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class WriteInvoiceModel {
	
	private WriteInvoiceStage wInvS;
	private WriteInvoiceJsInterface wInvJsI;
	private WriteInvoiceController wInvC;
	private WriteInvoiceView wInvV;
	private WebView browser;
	private WebEngine engine;
	private CustomerType customerType;
	private DocumentInvoice doc;
	private DocumentInvoiceFactory documentInvoiceFactory;
	
	private final int PAGE_WIDTH = 595;
	private final int PAGE_HEIGHT = 842;	
	private final int VIEW_PAGE_ZOOM_INTERVAL = 20;	
	private final int VIEW_PAGE_ZOOM_MIN = 10;
	private final int VIEW_PAGE_ZOOM_MAX = 500;
	private final int VIEW_PAGE_SHADOW_BLUR = 10;
	private final int VIEW_PAGE_MARGIN = 10;
	
	private int viewPageZoom = 100;
	private boolean viewPageFitWidth = false;
	private boolean viewPageFitHeight = false;	
	
	public WriteInvoiceModel(CustomerType customerType) {		
		this.customerType = customerType;
	}
	
	public void initWriteInvoice() {
		this.wInvS = new WriteInvoiceStage(this);
		this.browser = this.wInvS.getBrowser();
		this.engine = this.wInvS.getEngine();
		
		this.wInvJsI = new WriteInvoiceJsInterfaceImpl(this.engine);
//      	this.wInvC = new WriteInvoiceController(this.browser, this.engine);
//      	this.wInvV = new WriteInvoiceView(this.browser, this.engine);
      	
		this.documentInvoiceFactory = new DocumentInvoiceFactory(this.engine);
		this.documentInvoiceFactory.createNewDocument(this.customerType);
		
      	wInvS.show();
	}
	
	public void setDefaults() {
		
		setViewPageZoom( fitToViewPageHeight(this.viewPageZoom) );
		this.engine.executeScript("setPageSize(" + this.PAGE_WIDTH + ", " + this.PAGE_HEIGHT + ");");
		
		this.documentInvoiceFactory.showDocumentOnViewPage();
	}
	
	public void zoomInViewPage() {
		int newViewPageZoom = this.viewPageZoom + this.VIEW_PAGE_ZOOM_INTERVAL;		
		newViewPageZoom = checkViewPageFit(newViewPageZoom);		
		
		if(newViewPageZoom <= VIEW_PAGE_ZOOM_MAX) {
			setViewPageZoom(newViewPageZoom);
		}
	}
	public void zoomOutViewPage() {
		int newViewPageZoom = this.viewPageZoom - this.VIEW_PAGE_ZOOM_INTERVAL;		
		newViewPageZoom = checkViewPageFit(newViewPageZoom);
		
		if(newViewPageZoom >= VIEW_PAGE_ZOOM_MIN) {
			setViewPageZoom(newViewPageZoom);
		}
	}
	
	public int checkViewPageFit(int newViewPageZoom) {
		double oldPageWidth = this.PAGE_WIDTH * (this.viewPageZoom / 100.0);
		double newPageWidth = this.PAGE_WIDTH * (newViewPageZoom / 100.0);
		int width = this.wInvJsI.getViewPageWrapperWidth();		
		
		if(width > oldPageWidth && width < newPageWidth && !this.viewPageFitWidth) {
			double zoom = ( (width - 40.0) / this.PAGE_WIDTH ) * 100 ;
			newViewPageZoom = (int) zoom;
			this.viewPageFitWidth = true;
		} else {
			this.viewPageFitWidth = false;
			if((newViewPageZoom - 100) % this.VIEW_PAGE_ZOOM_INTERVAL != 0) {
				double zoom = ((double) newViewPageZoom - 100) / this.VIEW_PAGE_ZOOM_INTERVAL;
				newViewPageZoom = ((int) Math.round(zoom)) * this.VIEW_PAGE_ZOOM_INTERVAL + 100;
			}
			
			
			double oldPageHeight = this.PAGE_HEIGHT * (this.viewPageZoom / 100.0);
			double newPageHeight = this.PAGE_HEIGHT * (newViewPageZoom / 100.0);
			int height = this.wInvJsI.getViewPageWrapperHeight();
			
			if(height > oldPageHeight && height < newPageHeight && !this.viewPageFitHeight) {
				double zoom = ( (height - 40.0) / this.PAGE_HEIGHT ) * 100 ;
				newViewPageZoom = (int) zoom;
				this.viewPageFitHeight = true;
			} else {
				this.viewPageFitHeight = false;
				if((newViewPageZoom - 100) % this.VIEW_PAGE_ZOOM_INTERVAL != 0) {
					double zoom = ((double) newViewPageZoom - 100) / this.VIEW_PAGE_ZOOM_INTERVAL;
					newViewPageZoom = ((int) Math.round(zoom)) * this.VIEW_PAGE_ZOOM_INTERVAL + 100;
				}
			}
		}
		
		return newViewPageZoom;
	}
	
	private int fitViewToPageWidth(int newViewPageZoom) {
		int width = this.wInvJsI.getViewPageWrapperWidth();		
		double zoom = ( (width - 40.0) / this.PAGE_WIDTH ) * 100 ;
		newViewPageZoom = (int) zoom;
		this.viewPageFitWidth = true;
		
		return newViewPageZoom;
	}
	
	private int fitToViewPageHeight(int newViewPageZoom) {
		int height = this.wInvJsI.getViewPageWrapperHeight();
		double zoom = ( (height - 40.0) / this.PAGE_HEIGHT ) * 100 ;
		newViewPageZoom = (int) zoom;
		this.viewPageFitHeight = true;
		
		return newViewPageZoom;
	}
	
	
	public int getViewPageZoom() {
		return viewPageZoom;
	}

	public void setViewPageZoom(int viewPageZoom) {
		this.viewPageZoom = viewPageZoom;
		//this.wInvV.changeViewPageZoom(viewPageZoom);
		int newViewPageShadowBlur = (int) Math.round(this.VIEW_PAGE_SHADOW_BLUR / (viewPageZoom / 100.0));
		int newViewPageMargin = (int) Math.round(this.VIEW_PAGE_MARGIN / (viewPageZoom / 100.0));		
		
		this.engine.executeScript("changeViewPageZoom(" + viewPageZoom + ", " + newViewPageShadowBlur + ", " + newViewPageMargin + ");");
	}

	public WriteInvoiceStage getwInvS() {
		return wInvS;
	}

	public void setwInvS(WriteInvoiceStage wInvS) {
		this.wInvS = wInvS;
	}

	public WriteInvoiceJsInterface getwInvJsI() {
		return wInvJsI;
	}

	public void setwInvJsI(WriteInvoiceJsInterface wInvJsI) {
		this.wInvJsI = wInvJsI;
	}

	public WriteInvoiceController getwInvC() {
		return wInvC;
	}

	public void setwInvC(WriteInvoiceController wInvC) {
		this.wInvC = wInvC;
	}

	public WriteInvoiceView getwInvV() {
		return wInvV;
	}

	public void setwInvV(WriteInvoiceView wInvV) {
		this.wInvV = wInvV;
	}

	public WebView getBrowser() {
		return browser;
	}

	public void setBrowser(WebView browser) {
		this.browser = browser;
	}

	public WebEngine getEngine() {
		return engine;
	}

	public void setEngine(WebEngine engine) {
		this.engine = engine;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	public DocumentInvoice getDoc() {
		return doc;
	}

	public void setDoc(DocumentInvoice doc) {
		this.doc = doc;
	}

	public DocumentInvoiceFactory getDocumentInvoiceFactory() {
		return documentInvoiceFactory;
	}

	public void setDocumentInvoiceFactory(DocumentInvoiceFactory documentInvoiceFactory) {
		this.documentInvoiceFactory = documentInvoiceFactory;
	}

	public boolean isViewPageFitWidth() {
		return viewPageFitWidth;
	}

	public void setViewPageFitWidth(boolean viewPageFitWidth) {
		this.viewPageFitWidth = viewPageFitWidth;
	}

	public boolean isViewPageFitHeight() {
		return viewPageFitHeight;
	}

	public void setViewPageFitHeight(boolean viewPageFitHeight) {
		this.viewPageFitHeight = viewPageFitHeight;
	}

	public int getPAGE_WIDTH() {
		return PAGE_WIDTH;
	}

	public int getPAGE_HEIGHT() {
		return PAGE_HEIGHT;
	}

	public int getVIEW_PAGE_ZOOM_INTERVAL() {
		return VIEW_PAGE_ZOOM_INTERVAL;
	}

	public int getVIEW_PAGE_ZOOM_MIN() {
		return VIEW_PAGE_ZOOM_MIN;
	}

	public int getVIEW_PAGE_ZOOM_MAX() {
		return VIEW_PAGE_ZOOM_MAX;
	}

	public int getVIEW_PAGE_SHADOW_BLUR() {
		return VIEW_PAGE_SHADOW_BLUR;
	}

	public int getVIEW_PAGE_MARGIN() {
		return VIEW_PAGE_MARGIN;
	}
	

}
