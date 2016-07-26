package de.NDN.app.globalObjects;

import javafx.scene.image.Image;

public class CompanyHeader {
	
	private final int companyHeaderHeight = 100;
	
	private String companyLogoURL;
	private Address companyAddress;
	private String phoneNumber;
	private String faxNumber;
	private String eMail;
	private String web;
	
	public CompanyHeader() {
		this.companyLogoURL = de.NDN.app.NDN_Rechnungsprogramm.class.getResource("").toExternalForm() + "images/companyLogo.png";
		this.companyAddress = new Address();
		this.companyAddress.setCompanyName("Malerbetrieb Roland Nowak");
		this.companyAddress.setStreet("Hauweg");
		this.companyAddress.setNumber("11");
		this.companyAddress.setZipcode("75045");
		this.companyAddress.setTown("Walzbachtal");
		this.phoneNumber = "07203 / 3462346";
		this.faxNumber = "07203 / 3462347";
		this.eMail = "info@malermeister-nowakroland.de";
		this.web = "www.roland-nowak.de";
	}

	public String getCompanyLogoURL() {
		return companyLogoURL;
	}

	public void setCompanyLogoURL(String companyLogoURL) {
		this.companyLogoURL = companyLogoURL;
	}

	public Address getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(Address companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public int getCompanyHeaderHeight() {
		return companyHeaderHeight;
	}
}
