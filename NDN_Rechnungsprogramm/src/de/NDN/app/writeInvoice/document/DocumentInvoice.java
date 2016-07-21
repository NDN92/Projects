package de.NDN.app.writeInvoice.document;

import java.util.ArrayList;

import de.NDN.app.globalObjects.CustomerType;

public class DocumentInvoice {
	
	private CustomerType customerType;
	private ArrayList<PageInvoice> pages;
	
	public DocumentInvoice(CustomerType customerType, ArrayList<PageInvoice> pages) {
		this.customerType = customerType;
		this.pages = pages;
	}
	
	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	public ArrayList<PageInvoice> getPages() {
		return pages;
	}

	public void setPages(ArrayList<PageInvoice> pages) {
		this.pages = pages;
	}
}
