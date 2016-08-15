package de.ndn.test.printing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javafx.scene.control.Label;

public class HelloWorldPrinter implements Printable {

	public void doPrint() {
		PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);
        boolean ok = job.printDialog();
        if (ok) {
            try {
                 job.print();
            } catch (PrinterException ex) {
             /* The job did not successfully complete */
            }
        }
	}

	@Override
	public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
		if (page > 0) { /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }
		
		System.out.println("Orientation: " + pf.getOrientation());
		System.out.println("Width: " + pf.getWidth());
		System.out.println("Height: " + pf.getHeight());
		System.out.println("IWidth: " + pf.getImageableWidth());
		System.out.println("IHeight: " + pf.getImageableHeight());
		System.out.println("IX: " + pf.getImageableX());
		System.out.println("IY: " + pf.getImageableY());
		
		
        /* User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());

        /* Now we perform our rendering */
        g.drawString("Hello world!", 0, 10);
        

        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
	}

}
