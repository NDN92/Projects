package de.NDN.app.writeInvoice.document;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.awt.print.Printable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.NDN.app.globalObjects.AppFont;
import de.NDN.app.globalObjects.Converter;
import de.NDN.app.globalObjects.CustomerType;
import de.NDN.app.globalObjects.PageElementId;
import de.NDN.app.writeInvoice.WriteInvoiceModel;
import de.NDN.app.writeInvoice.document.print.DocumentInvoicePrintable;
import de.NDN.app.writeInvoice.document.print.DocumentInvoicePrinter;
import javafx.scene.web.WebEngine;

public class DocumentInvoiceFactory {
	
	//Standard-Fonts
	private final AppFont ARIAL_STD = new AppFont("ARIAL_STD");
	private final AppFont ARIAL_BLD = new AppFont("ARIAL_BLD");
	private final AppFont ARIAL_IT  = new AppFont("ARIAL_IT");
	
	//DIN-Norm for Address-Field
	private final double ADDRESS_FIELD_TOP = 142;
	private final double ADDRESS_FIELD_LEFT = 57;
	private final double ADDRESS_FIELD_WIDTH = 241;
	private final double ADDRESS_FIELD_HEIGHT = 113;
	private final AppFont ADDRESS_FIELD_FONT = new AppFont(ARIAL_STD, 16, Color.BLACK, 20);
	
	private DocumentInvoice doc;
	private WebEngine engine;
	private ArrayList<Printable> printableDocuments;
	private DocumentInvoicePrinter documentInvoicePrinter;
	private WriteInvoiceModel writeInvoiceModel;
	
	private Converter conv = new Converter();
	
	public DocumentInvoiceFactory(WebEngine engine, WriteInvoiceModel writeInvoiceModel) {
		this.engine = engine;
		this.writeInvoiceModel = writeInvoiceModel;
	}
	
	public void createNewDocument(CustomerType customerType) {
		this.doc = new DocumentInvoice(customerType, new ArrayList<PageInvoice>());
		this.doc.getPages().add( new PageInvoice(this.doc, 1) );
	}
	
	public boolean showDocumentOnViewPage() {
		
		if(this.doc != null) {
			double pageDpiRatio = this.writeInvoiceModel.getPageDpiRatio();
			double[] printableArea = this.doc.getPages().get(0).getPrintableArea();
			
			double printableAreaTop = this.doc.getPages().get(0).getPrintableArea()[0];
			double printableAreaRight = this.doc.getPages().get(0).getPrintableArea()[1];
			double printableAreaBottom = this.doc.getPages().get(0).getPrintableArea()[2];
			double printableAreaLeft = this.doc.getPages().get(0).getPrintableArea()[3];			
			if(pageDpiRatio != 1) {
				printableAreaTop 	*= pageDpiRatio;
				printableAreaRight 	*= pageDpiRatio;
				printableAreaBottom *= pageDpiRatio;
				printableAreaLeft 	*= pageDpiRatio;
				printableArea = new double[] {printableAreaTop, printableAreaRight, printableAreaBottom, printableAreaLeft};
			}
			
			double printableAreaWidth = this.writeInvoiceModel.getPageWidth() - (printableAreaRight + printableAreaLeft);
			double printableAreaHeight = this.writeInvoiceModel.getPageHeight() - (printableAreaTop + printableAreaBottom);
			double[] pageSize = {this.writeInvoiceModel.getPageWidth(), this.writeInvoiceModel.getPageHeight()};			
			
						
			printableDocuments = new ArrayList<Printable>();
			DocumentInvoicePrintable currentPrintableDocument = new DocumentInvoicePrintable();			
			ArrayList<Object[]> currentPageShapes = new ArrayList<Object[]>(); //{Shape, Color}
			ArrayList<Object[]> currentPageImages = new ArrayList<Object[]>(); //{Image, imgWidth, imgHeight, imgX, imgY}
			ArrayList<Object[]> currentPageTexts = new ArrayList<Object[]>(); //{String, Font, Color, textX, textY}
			
			this.documentInvoicePrinter = new DocumentInvoicePrinter();
			
			if(this.doc.getCustomerType() == CustomerType.PRIVATE) {
				
				this.engine.executeScript("setPrintableArea(" +
						printableAreaTop + ", " +
						printableAreaRight + ", " +
						printableAreaBottom + ", " +
						printableAreaLeft + ");");
				this.documentInvoicePrinter.setPageSize(pageSize);
				this.documentInvoicePrinter.setPrintableArea(printableArea);
				this.documentInvoicePrinter.setPrintableAreaWidth(printableAreaWidth);
				this.documentInvoicePrinter.setPrintableAreaHeight(printableAreaHeight);
				
				
				//Company Header
				String companyLogoUrl = this.doc.getPages().get(0).getCompanyHeader().getCompanyLogoURL();
				String companyLogoUrlCSS = "../images" + companyLogoUrl.substring(companyLogoUrl.lastIndexOf("/"), companyLogoUrl.length());
				int logoWidth = -1;
				int logoHeight = this.doc.getPages().get(0).getCompanyHeader().getCompanyHeaderHeight() - 10;				
				int logoX = 0;
				int logoY = 0;
				double hrWidth = printableAreaWidth;
				int hrHeight = 1;				
				int hrX = 0;
				int hrY = this.doc.getPages().get(0).getCompanyHeader().getCompanyHeaderHeight() - hrHeight;				
				if(pageDpiRatio != 1) {
					//logoWidth = (int) Math.round(logoWidth * pageDpiRatio);
					logoHeight 	= (int) Math.round(logoHeight * pageDpiRatio);
					logoX		= (int) Math.round(logoX * pageDpiRatio);
					logoY 		= (int) Math.round(logoY * pageDpiRatio);
					
					hrHeight	= (int) Math.round(hrHeight * pageDpiRatio);
					hrX 		= (int) Math.round(hrX * pageDpiRatio);
					hrY 		= (int) Math.round(hrY * pageDpiRatio);
				}
				
				this.engine.executeScript("setCompanyHeader(" + 
						logoHeight + ", '" +
						companyLogoUrlCSS + "'," + 
						logoX + ", " +
						logoY + ", " +
						hrHeight + ", " +
						hrWidth + ", " +
						hrX + ", " +
						hrY + ");");
				
				try {
					Image companyLogo = Toolkit.getDefaultToolkit().getImage( URLDecoder.decode(companyLogoUrl.replace("file:/", ""), "UTF-8") );
					currentPageImages.add( new Object[] {PageElementId.UNDEFINED, companyLogo, logoWidth, logoHeight, logoX, logoY} );
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				Rectangle2D.Double hr = new Rectangle2D.Double();
				hr.setRect(hrX, hrY, hrWidth, hrHeight);
				currentPageShapes.add( new Object[] {PageElementId.UNDEFINED, hr, Color.BLACK} );
				
								
				
				double afW = this.ADDRESS_FIELD_WIDTH;
				double afH = this.ADDRESS_FIELD_HEIGHT;
				double afX = this.ADDRESS_FIELD_LEFT - this.doc.getPages().get(0).getPrintableArea()[3];
				double afY = this.ADDRESS_FIELD_TOP - this.doc.getPages().get(0).getPrintableArea()[0];
				AppFont afFont = this.ADDRESS_FIELD_FONT;
				if(pageDpiRatio != 1) {
					afW *= pageDpiRatio;
					afH *= pageDpiRatio;
					afX *= pageDpiRatio;
					afY *= pageDpiRatio;
				}
				this.engine.executeScript("setMultilineTextarea("
						+ "'" + PageElementId.ADDRESS_FIELD + "', "
						+ afW + ", "
						+ afH + ", "
						+ "'" + ARIAL_STD.getName() + "', "
						+ afFont.getSize() + ", "
						+ "'" + afFont.getColorInHex() + "', "
						+ afFont.getLineHeight() + ", "
						+ "'" + "middle" + "', "
						+ afX + ", "
						+ afY + ");");
				
				//Company Address and Infos
				String t1_1 = this.doc.getPages().get(0).getCompanyHeader().getCompanyAddress().getCompanyName();
				String t1_2 = this.doc.getPages().get(0).getCompanyHeader().getCompanyAddress().getStreet() + " " + this.doc.getPages().get(0).getCompanyHeader().getCompanyAddress().getNumber();
				String t1_3 = this.doc.getPages().get(0).getCompanyHeader().getCompanyAddress().getZipcode() + " " + this.doc.getPages().get(0).getCompanyHeader().getCompanyAddress().getTown();
				String t1_4 = "";
				String t1_5 = "Tel. " + this.doc.getPages().get(0).getCompanyHeader().getPhoneNumber();
				String t1_6 = "Fax " + this.doc.getPages().get(0).getCompanyHeader().getFaxNumber();
				String t1_7 = "";
				String t1_8 = this.doc.getPages().get(0).getCompanyHeader().geteMail();
				String t1_9 = this.doc.getPages().get(0).getCompanyHeader().getWeb();
				Object[] multiLineObjects = this.conv.getJsExCodeForMultilineSetText(
						new String[][] { 	
							{PageElementId.UNDEFINED, t1_1},
							{PageElementId.UNDEFINED, t1_2},
							{PageElementId.UNDEFINED, t1_3},
							{PageElementId.UNDEFINED, t1_4},
							{PageElementId.UNDEFINED, t1_5},
							{PageElementId.UNDEFINED, t1_6},
							{PageElementId.UNDEFINED, t1_7},
							{PageElementId.UNDEFINED, t1_8},
							{PageElementId.UNDEFINED, t1_9}     
						}, 
						new AppFont(ARIAL_STD, 8, Color.BLACK, 9),
						360, 
						//(int) Math.round(this.ADDRESS_FIELD_TOP - this.doc.getPages().get(0).getPrintableArea()[0]) + 1, 
						8,
						pageDpiRatio);
				for( int i = 0; i < ((String[]) multiLineObjects[0]).length; i++) {
					this.engine.executeScript( ((String[]) multiLineObjects[0])[i] );
					currentPageTexts.add( (Object[]) ((Object[]) multiLineObjects[1])[i] );
				}
				
				
				int textMargin1 = (int) Math.round(this.ADDRESS_FIELD_LEFT - this.doc.getPages().get(0).getPrintableArea()[3]);
				
				
				int ihRectWidth = (int) Math.round(printableAreaWidth);
				int ihRectHeight = -1;
				Color ihRectBgColor = Color.decode("#f9f9f9");
				BasicStroke ihRectBorder = new BasicStroke(1.0f);
				Color ihRectBorderColor = Color.decode("#e9e9e9");
				int ihRectX = 0;
				int ihRectY = 242;
				//
				String ihHeadline = "Rechnung";
				AppFont ihHeadlineFont = new AppFont(ARIAL_BLD, 13, Color.BLACK, 13);
				int ihHeadlineX = textMargin1;
				int ihHeadlineY = ihRectY + textMargin1;
				//
				int ihSpaceHlLV = 5;
				//
				String invoiceNumber = this.doc.getPages().get(0).getInvoiceHeader().getInvoiceNumber();
				Date invoiceDate = this.doc.getPages().get(0).getInvoiceHeader().getInvoiceDate();
				String customerNumber = this.doc.getPages().get(0).getInvoiceHeader().getCustomerNumber();
				Date payableDate = this.doc.getPages().get(0).getInvoiceHeader().getPayableDate();
				int weekNumber = -1;
				Object[][] ihLabelsAndValues = {
						{PageElementId.UNDEFINED, "Rechnungs-Nr:"}, 	{PageElementId.INVOICE_NUMBER, 	invoiceNumber}, 
						{PageElementId.UNDEFINED, "Rechnungs-Datum:"}, 	{PageElementId.INVOICE_DATE, 	new SimpleDateFormat("dd.MM.yyyy").format(invoiceDate)}, 
						{PageElementId.UNDEFINED, "Kunden-Nr:"}, 		{PageElementId.CUSTOMER_NUMBER, customerNumber}, 
						{PageElementId.UNDEFINED, "F�llig am:"}, 		{PageElementId.PAYABLE_DATE, 	new SimpleDateFormat("dd.MM.yyyy").format(payableDate)}, 
						{PageElementId.UNDEFINED, "Leistung von KW"}, 	{PageElementId.WEEK_NUMBER,		weekNumber}
				};				
				AppFont ihLabelsAndValuesFont = new AppFont(ARIAL_STD, 10, Color.BLACK, 13);
				int ihLabelsAndValuesX1 = ihHeadlineX;
				int ihLabelsAndValuesX2 = (int) Math.round((ihRectWidth - (2 * ihLabelsAndValuesX1)) / 2.0);
				int ihLabelsAndValuesY = ihHeadlineY + ihHeadlineFont.getLineHeight() + ihSpaceHlLV;
				int ihLabelWidth = ((int) Math.round(ihLabelsAndValuesX2 / 2.0)) - 20;
				
				
				ihRectHeight = (int) Math.round( 
						  ihHeadlineX
						+ ihHeadlineFont.getLineHeight()
						+ ihSpaceHlLV
						+ ( Math.round( (ihLabelsAndValues.length / 2) / 2.0 ) * ihLabelsAndValuesFont.getLineHeight() )
						- Math.round( (ihLabelsAndValuesFont.getLineHeight() - ihLabelsAndValuesFont.getSize() ) / 2.0 )  //Wichtig, da der Text �ber die ganze lineHeight vertikal zentriert wird
						+ ihHeadlineX 
				);
				this.engine.executeScript("setRectangle(" +
						ihRectWidth + ", " +
						ihRectHeight + ", " +
						"'" + this.conv.convertJavaColorToCssColor(ihRectBgColor) + "', " +
						"'" + ihRectBorder.getLineWidth() + "px solid " + this.conv.convertJavaColorToCssColor(ihRectBorderColor) + "', " +
						ihRectX + ", " +
						ihRectY + ");"
				);
				Rectangle2D.Double ihRect = new Rectangle2D.Double();
				ihRect.setRect(	ihRectX + ((int) Math.round(ihRectBorder.getLineWidth() / 2.0)), 
								ihRectY + ((int) Math.round(ihRectBorder.getLineWidth() / 2.0)), 
								ihRectWidth - ((int) ihRectBorder.getLineWidth()), 
								ihRectHeight - ((int) ihRectBorder.getLineWidth()));
				currentPageShapes.add( new Object[] {PageElementId.UNDEFINED, ihRect, ihRectBgColor, ihRectBorder, ihRectBorderColor} );
				
				this.engine.executeScript("setText("
						+ "'" + PageElementId.UNDEFINED + "', "
						+ "'" + ihHeadline + "', "
						+ "'" + ihHeadlineFont.getName() + "', "
						+ 	    ihHeadlineFont.getSize() + ", "
						+ "'" + ihHeadlineFont.getColorInHex() + "', "
						+ 	    ihHeadlineFont.getLineHeight() + ", "
						+ 	    ihHeadlineX + ", "
						+ 	    ihHeadlineY + ");"
				);
				currentPageTexts.add(new Object[] {
						PageElementId.UNDEFINED,
						ihHeadline,
						ihHeadlineFont,
						ihHeadlineX,
						this.conv.fontYOffset(ihHeadlineFont, ihHeadlineY)
				});
				Object[] invoiceHeaderObj = this.conv.getJsExcodeForInvoiceHeader(
						ihLabelsAndValues, 
						ihLabelWidth, 
						ihLabelsAndValuesFont, 
						ihLabelsAndValuesX1, 
						ihLabelsAndValuesX2, 
						ihLabelsAndValuesY, 
						pageDpiRatio);
				for( int i = 0; i < ((String[]) invoiceHeaderObj[0]).length; i++) {
					this.engine.executeScript( ((String[]) invoiceHeaderObj[0])[i] );					
				}
				for( int i = 0; i < ((Object[]) invoiceHeaderObj[1]).length; i++) {
					currentPageTexts.add( (Object[]) ((Object[]) invoiceHeaderObj[1])[i] );
				}
				
				
				int referenceLineWidth = (int) Math.round(printableAreaWidth);
				AppFont referenceLineFont = new AppFont(ARIAL_STD, 10, Color.BLACK, 13);
				int referenceLineHeight = referenceLineFont.getLineHeight();
				int referenceLineX = 0;
				int referenceLineY = ihRectY + ihRectHeight + 5;
				this.engine.executeScript("setMultilineTextarea("
						+ "'" + PageElementId.REFERENCE_LINE + "', "
						+ referenceLineWidth + ", "
						+ referenceLineHeight + ", "
						+ "'" + referenceLineFont.getName() + "', "
						+ referenceLineFont.getSize() + ", "
						+ "'" + referenceLineFont.getColorInHex() + "', "
						+ referenceLineFont.getLineHeight() + ", "
						+ "'" + "top" + "', "
						+ referenceLineX + ", "
						+ referenceLineY + ");");
				
				
				int rapportHeaderWidth = (int) Math.round(printableAreaWidth);
				AppFont rapportHeaderFont = new AppFont(ARIAL_BLD, 9, Color.BLACK, 13);
				int rapportHeaderHeight = rapportHeaderFont.getLineHeight();
				Color rapportHeaderRectBgColor = ihRectBgColor;
				BasicStroke rapportHeaderRectBorder = ihRectBorder;
				Color rapportHeaderRectBorderColor = ihRectBorderColor;
				int rapportHeaderX = 0;
				int rapportHeaderY = referenceLineY + referenceLineHeight + 5;
				this.engine.executeScript("setRectangle("
						+ rapportHeaderWidth + ", "
						+ rapportHeaderHeight + ", "
						+ "'" + this.conv.convertJavaColorToCssColor(rapportHeaderRectBgColor) + "', "
						+ "'" + rapportHeaderRectBorder.getLineWidth() + "px solid " + this.conv.convertJavaColorToCssColor(rapportHeaderRectBorderColor) + "', "
						+ rapportHeaderX + ", "
						+ rapportHeaderY + ");");
				
				
				
				currentPrintableDocument.setPageShapes(currentPageShapes);
				currentPrintableDocument.setPageImages(currentPageImages);
				currentPrintableDocument.setPageTexts(currentPageTexts);
			}
			
			printableDocuments.add(currentPrintableDocument);
			this.documentInvoicePrinter.setPrintableDocuments(printableDocuments);
			
			return true;
		} else {
			return false;
		}
		
	}	
	
	public void setAddressField(ArrayList<String> texts) {
		int lines = texts.size();
		if(texts.get(lines - 1).equals("")) lines--;
		
		int afY = (int) Math.round( (this.ADDRESS_FIELD_TOP - this.doc.getPages().get(0).getPrintableArea()[0]) + ((this.ADDRESS_FIELD_HEIGHT - (lines * this.ADDRESS_FIELD_FONT.getLineHeight())) / 2.0));
		
		String[][] idsWithTexts = new String[lines][2];
		for(int i = 0; i < lines; i++) {
			idsWithTexts[i][0] = PageElementId.ADDRESS_FIELD;
			idsWithTexts[i][1] = texts.get(i);
		}
		Object[] multiLineObjects = this.conv.getJsExCodeForMultilineSetText(
				idsWithTexts, 
				this.ADDRESS_FIELD_FONT,
				(int) Math.round(this.ADDRESS_FIELD_LEFT - this.doc.getPages().get(0).getPrintableArea()[3]), 
				afY,
				this.writeInvoiceModel.getPageDpiRatio());
		
		ArrayList<Printable> printableDocuments = getPrintableDocuments();
		for(Printable doc : printableDocuments) {
			ArrayList<Object[]> newPageTexts = ((DocumentInvoicePrintable) doc).getPageTexts();			
			for( int i = 0; i < ((String[]) multiLineObjects[0]).length; i++) {
				newPageTexts.add( (Object[]) ((Object[]) multiLineObjects[1])[i] );
			}
			((DocumentInvoicePrintable) doc).setPageTexts(newPageTexts);
		}
	}
	

	public DocumentInvoice getDoc() {
		return doc;
	}

	public void setDoc(DocumentInvoice doc) {
		this.doc = doc;
	}

	public WebEngine getEngine() {
		return engine;
	}

	public void setEngine(WebEngine engine) {
		this.engine = engine;
	}

	public ArrayList<Printable> getPrintableDocuments() {
		return printableDocuments;
	}

	public void setPrintableDocuments(ArrayList<Printable> printableDocuments) {
		this.printableDocuments = printableDocuments;
	}

	public DocumentInvoicePrinter getDocumentInvoicePrinter() {
		return documentInvoicePrinter;
	}

	public void setDocumentInvoicePrinter(DocumentInvoicePrinter documentInvoicePrinter) {
		this.documentInvoicePrinter = documentInvoicePrinter;
	}
	
	
	
}
