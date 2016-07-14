package de.NDN.test.GUI;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GUI extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {			
		Group root = new Group();
		Scene scene = new Scene(root, Color.LIGHTGREY);	
		
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		int res = Toolkit.getDefaultToolkit().getScreenResolution();
		System.out.println("Width: " + width + ", Height: " + height + ", Resolution: " + res);
		double wWidth = primaryStage.getMaxWidth();
		double wHeight = primaryStage.getHeight();
		System.out.println("wWidth: " + wWidth + ", wHeight: " + wHeight);
		
		StackPane pane = new StackPane();
		pane.setStyle("-fx-background-color: #FFF;");
		pane.setPrefWidth(width * 0.25);
		pane.setPrefHeight(height * 0.25);
		root.getChildren().add(pane);
		
			
		primaryStage.setMaximized(true);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
