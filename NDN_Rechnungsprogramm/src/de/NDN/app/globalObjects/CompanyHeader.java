package de.NDN.app.globalObjects;

import javafx.scene.image.Image;

public class CompanyHeader {
	
	private Image companyLogo;
	private Address companyAddress;
	private String phoneNumber;
	private String faxNumber;
	private String eMail;
	private String web;
	
	public CompanyHeader() {
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
}
