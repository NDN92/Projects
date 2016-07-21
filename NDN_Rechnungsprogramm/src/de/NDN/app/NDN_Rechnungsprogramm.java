package de.NDN.app;

import de.NDN.app.globalObjects.CustomerType;
import de.NDN.app.writeInvoice.WriteInvoiceModel;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class NDN_Rechnungsprogramm extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Button btn = new Button("Rechnungen Schreiben");
		btn.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {		    	
		        WriteInvoiceModel wInvM = new WriteInvoiceModel(CustomerType.PRIVATE);
		        wInvM.initWriteInvoice();
		        primaryStage.close();
		    }
		});		
		Scene scene = new Scene(btn);		
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
