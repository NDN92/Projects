package de.NDN.app.writeInvoice.document.print;

import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;

public class DocumentInvoicePrinter {
	
	private Book book;
	private ArrayList<Printable> printableDocuments;
	private double pageSize[];
	private int printableArea[];
	private double printableAreaWidth;
	private double printableAreaHeight;	
	
	public DocumentInvoicePrinter() {
		
	}	
	
	public void doPrint() {
		PrinterJob job = PrinterJob.getPrinterJob();
		
		PageFormat pf = job.defaultPage();		
		if(this.printableArea != null && this.pageSize != null) {
			Paper paper = new Paper();
			paper.setSize(this.pageSize[0], this.pageSize[1]);
			paper.setImageableArea(this.printableArea[3], this.printableArea[0], this.printableAreaWidth, this.printableAreaHeight);
			pf.setPaper(paper);
		}
		
		
		this.book = new Book();
		for(int i = 0; i < this.printableDocuments.size(); i++) {
			Printable printableDocument = (Printable) this.printableDocuments.get(i);
			this.book.append( printableDocument, pf );
		}
		
        job.setPageable(this.book);
        boolean ok = job.printDialog();
        if (ok) {
            try {
                 job.print();
            } catch (PrinterException ex) {
             /* The job did not successfully complete */
            }
        }
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public ArrayList<Printable> getPrintableDocuments() {
		return printableDocuments;
	}

	public void setPrintableDocuments(ArrayList<Printable> printableDocuments) {
		this.printableDocuments = printableDocuments;
	}

	public int[] getPrintableArea() {
		return printableArea;
	}

	public void setPrintableArea(int[] printableArea) {
		this.printableArea = printableArea;
	}

	public double getPrintableAreaWidth() {
		return printableAreaWidth;
	}

	public void setPrintableAreaWidth(double printableAreaWidth) {
		this.printableAreaWidth = printableAreaWidth;
	}

	public double getPrintableAreaHeight() {
		return printableAreaHeight;
	}

	public void setPrintableAreaHeight(double printableAreaHeight) {
		this.printableAreaHeight = printableAreaHeight;
	}

	public double[] getPageSize() {
		return pageSize;
	}

	public void setPageSize(double[] pageSize) {
		this.pageSize = pageSize;
	}
	
}
