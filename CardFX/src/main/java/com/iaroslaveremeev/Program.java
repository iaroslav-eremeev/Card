package com.iaroslaveremeev;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.application.Application.launch;

public class Program extends Application {
    private static Scene scene;
    private boolean userLoggedIn;
    public static void main(String[] args) {
        launch();
    }
    public void start(Stage stage) throws IOException {
        if (userLoggedIn){
            scene = new Scene(loadFXML("/mainForm"), 600, 440);
        }
        else {
            scene = new Scene(loadFXML("/authorization"), 600, 300);
        }
        stage.setScene(scene);
        stage.show();
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Program.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
}