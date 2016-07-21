package de.NDN.app.writeInvoice.document;

import java.util.Date;

import de.NDN.app.globalObjects.Address;

public class InvoiceHeader {
	
	private Address customerAddress;
	private int customerNumber;
	private int invoiceNumber;
	private Date invoiceDate;
	//Woche in der die Leistung erbracht wurde
	private int weekNumber;
	
	//Volkswohnung-spezifische Attribute
	private String vwJobNumber;
	private String vwObjectNumber;
	private String vwLodger;	//Mieter
	private String vwBuilding;
	
	//Hardtwaldsiedlung-spezifische Attribute
	private String hsJobNumber;
	private String hsFlatNumber;
	private String hsFlatLocation;
	
	//Zentraler Kommunaler Versorgungsverband - spezifische Attribute
	private String zvkToken;
	private String zvkFlatLocation;
	
	public InvoiceHeader() {
		
	}

	public Address getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(Address customerAddress) {
		this.customerAddress = customerAddress;
	}

	public int getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(int customerNumber) {
		this.customerNumber = customerNumber;
	}

	public int getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(int invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public int getWeekNumber() {
		return weekNumber;
	}

	public void setWeekNumber(int weekNumber) {
		this.weekNumber = weekNumber;
	}

	public String getVwJobNumber() {
		return vwJobNumber;
	}

	public void setVwJobNumber(String vwJobNumber) {
		this.vwJobNumber = vwJobNumber;
	}

	public String getVwObjectNumber() {
		return vwObjectNumber;
	}

	public void setVwObjectNumber(String vwObjectNumber) {
		this.vwObjectNumber = vwObjectNumber;
	}

	public String getVwLodger() {
		return vwLodger;
	}

	public void setVwLodger(String vwLodger) {
		this.vwLodger = vwLodger;
	}

	public String getVwBuilding() {
		return vwBuilding;
	}

	public void setVwBuilding(String vwBuilding) {
		this.vwBuilding = vwBuilding;
	}

	public String getHsJobNumber() {
		return hsJobNumber;
	}

	public void setHsJobNumber(String hsJobNumber) {
		this.hsJobNumber = hsJobNumber;
	}

	public String getHsFlatNumber() {
		return hsFlatNumber;
	}

	public void setHsFlatNumber(String hsFlatNumber) {
		this.hsFlatNumber = hsFlatNumber;
	}

	public String getHsFlatLocation() {
		return hsFlatLocation;
	}

	public void setHsFlatLocation(String hsFlatLocation) {
		this.hsFlatLocation = hsFlatLocation;
	}

	public String getZvkToken() {
		return zvkToken;
	}

	public void setZvkToken(String zvkToken) {
		this.zvkToken = zvkToken;
	}

	public String getZvkFlatLocation() {
		return zvkFlatLocation;
	}

	public void setZvkFlatLocation(String zvkFlatLocation) {
		this.zvkFlatLocation = zvkFlatLocation;
	}
}
