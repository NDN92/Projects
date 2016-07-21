package de.NDN.app.writeInvoice;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

public class WriteInvoiceStage extends Stage {
	
	private WebView browser;
	private WebEngine engine;
	
	public WriteInvoiceStage(WriteInvoiceModel wInvM) {
		VBox root = new VBox();
		
		final Menu file = new Menu("Datei");
		final Menu edit = new Menu("Bearbeiten");
		MenuBar menuBar = new MenuBar();
		menuBar.prefWidthProperty().bind(this.widthProperty());
		menuBar.getMenus().addAll(file, edit);		
		root.getChildren().add(menuBar);
		
		this.browser = new WebView();
		this.browser.prefHeightProperty().bind(this.heightProperty());
		this.engine = this.browser.getEngine();
		
		//wInvM.setwInvJsI(new WriteInvoiceJsInterfaceImpl(this.engine));
		
		String url = getClass().getResource("").toExternalForm() + "writeInvoice.html";
		this.engine.getLoadWorker().stateProperty().addListener(
				new ChangeListener<State>() {
					public void changed(ObservableValue<? extends Worker.State> ov, State oldState, State newState) {
						JSObject window = (JSObject) engine.executeScript("window");
						window.setMember("docL", wInvM);
						if(newState == State.SUCCEEDED) {							
							window.setMember("wInv", wInvM);
							//window.setMember("wInvJsI", wInvM.getwInvJsI());
						}
						
					}
				});
		this.engine.load(url);
		root.getChildren().add(this.browser);		
		
		Scene scene = new Scene(root);		
		this.setMaximized(true);
		this.setScene(scene);
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
}
