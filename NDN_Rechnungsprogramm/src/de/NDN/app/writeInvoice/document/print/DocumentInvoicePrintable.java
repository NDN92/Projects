package de.NDN.app.writeInvoice.document.print;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class DocumentInvoicePrintable implements Printable {
	
	private ArrayList<Shape> pageShapes;
	private ArrayList<Image> pageImages;
	
	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {		
		if (pageIndex > 0) {
	         return NO_SUCH_PAGE;
	    }
		
		Graphics2D graphics2D = (Graphics2D) graphics;
		double a = pageFormat.getImageableX();
		double b = pageFormat.getImageableY();
		graphics2D.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
		
		for(Shape shape : pageShapes) {
			graphics2D.draw(shape);
		}
		for(Image image : pageImages) {
			int w = image.getWidth(null);
			int h = image.getHeight(null);
			if( w != -1 && w != 0 && h != -1 && h != 0 ) {
//				int imgWidth = image.getWidth(null);
//				int imgHeight = image.getHeight(null);
				graphics2D.drawImage(image, 0, 0, 200, 50, null);
			}			
		}
		
		
		
		//Image img = new ImageIcon("companyLogo.png").getImage();
//		Image img = Toolkit.getDefaultToolkit().getImage("companyLogo.png");
//		int imgWidth = img.getWidth(null);
//		int imgHeight = img.getHeight(null);
//		graphics2D.drawImage(img, 0, 0, img.getWidth(null), img.getHeight(null), null);
		
		
		
		return Printable.PAGE_EXISTS;
	}

	public ArrayList<Shape> getPageShapes() {
		return pageShapes;
	}

	public void setPageShapes(ArrayList<Shape> pageShapes) {
		this.pageShapes = pageShapes;
	}

	public ArrayList<Image> getPageImages() {
		return pageImages;
	}

	public void setPageImages(ArrayList<Image> pageImages) {
		this.pageImages = pageImages;
	}

}
