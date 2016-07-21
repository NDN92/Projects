package de.NDN.app.writeInvoice.document;

import de.NDN.app.globalObjects.CompanyHeader;
import de.NDN.app.globalObjects.LineItem;

public class PageInvoice {
	
	private final int printableArea[] = {50, 50, 50, 50};	//Page-Margin Top, Right, Bottom, Left
	
	private DocumentInvoice documentInvoice;
	private int pageNumber;	
	private CompanyHeader companyHeader;
	private InvoiceHeader invoiceHeader;
	private LineItem[] lineItems;
	
	public PageInvoice(DocumentInvoice documentInvoice, int pageNumber) {
		this.documentInvoice = documentInvoice;
		this.pageNumber = pageNumber;
		
		init();
	}
	
	public void init() {
		this.companyHeader = new CompanyHeader();
		this.invoiceHeader = new InvoiceHeader();
	}

	public DocumentInvoice getDocumentInvoice() {
		return documentInvoice;
	}

	public void setDocumentInvoice(DocumentInvoice documentInvoice) {
		this.documentInvoice = documentInvoice;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public CompanyHeader getCompanyHeader() {
		return companyHeader;
	}

	public void setCompanyHeader(CompanyHeader companyHeader) {
		this.companyHeader = companyHeader;
	}

	public InvoiceHeader getInvoiceHeader() {
		return invoiceHeader;
	}

	public void setInvoiceHeader(InvoiceHeader invoiceHeader) {
		this.invoiceHeader = invoiceHeader;
	}

	public LineItem[] getLineItems() {
		return lineItems;
	}

	public void setLineItems(LineItem[] lineItems) {
		this.lineItems = lineItems;
	}

	public int[] getPrintableArea() {
		return printableArea;
	}
	
}
