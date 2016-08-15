package de.NDN.test.GUI;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

import static javafx.concurrent.Worker.State;

// see http://docs.oracle.com/javafx/2/webview/jfxpub-webview.htm

public class HelloWebView extends Application
{
    private Scene scene;

    @Override
    public void start(Stage stage)
    {
        // create the scene
        stage.setTitle("Web View");
        scene = new Scene(new Browser(), 750, 500, Color.web("#666970"));
        stage.setScene(scene);
        scene.getStylesheets().add("webviewsample/BrowserToolbar.css");
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}

class Browser extends Region
{

    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();

    public Browser()
    {
        //apply the styles
        getStyleClass().add("browser");
        // load the web page
        webEngine.loadContent("" +
            "<script>" +
            "function localEvent(s){" +
            "   foo.ping();" +
            "   foo.event(s);" +
            "}"+
            "</script>" +
            "<html>\n" +
            "<body>\n" +
            "\n" +
            "<h1>My First Heading</h1>\n" +
            "\n" +
            "<p>My first paragraph.</p>\n" +
            "\n" +
            "<p><a href=\"about:blank\" onclick=\"foo.event(\"you clicked me!!!\")\">Invoke Foo</a></p>" +
            "<p><button type=\"button\" onclick=\"foo.event(\"you clicked me!!!\")\">Invoke Foo</button></p>" +
            "<p><button type=\"button\" onclick=\"localEvent(\"from html\")\">Invoke Local</button></p>" +
            "</body>\n" +
            "</html>");

        // extend the webview js context
        webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>()
        {
            @Override
            public void changed(final ObservableValue<? extends Worker.State> observableValue, final State oldState, final State newState)
            {
                if (newState == State.SUCCEEDED)
                {
                    JSObject win = (JSObject) webEngine.executeScript("window");
                    win.setMember("foo", new Foo());
                    webEngine.executeScript("foo.ping()");
                    webEngine.executeScript("foo.event(\"webEngine.executeScript...\")");
                }
            }
        });
        //add the web view to the scene
        getChildren().add(browser);

    }

    private Node createSpacer()
    {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    @Override
    protected void layoutChildren()
    {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(browser, 0, 0, w, h, 0, HPos.CENTER, VPos.CENTER);
    }

    @Override
    protected double computePrefWidth(double height)
    {
        return 750;
    }

    @Override
    protected double computePrefHeight(double width)
    {
        return 500;
    }

    public class Foo
    {
        public void ping()
        {
            System.out.println("ping() invoked from javascript");
        }
        public void event(String s)
        {
            System.out.println("event() invoked from javascript, s=" +s);
        }
    }
}
