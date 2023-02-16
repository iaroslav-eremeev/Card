package com.iaroslaveremeev;

import com.iaroslaveremeev.controllers.ControllerData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

import static javafx.application.Application.launch;

public class Program extends Application {
    private static Scene scene;
    private boolean userLoggedIn; //TODO Посмотреть класс Preferences
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

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Program.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static <T> Stage openWindow(String name, T data) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(Program.class.getResource(name));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setScene(new Scene(loader.load()));
            if(data != null) {
                ControllerData<T> controller = loader.getController();
                controller.initData(data);
            }
            stage.showAndWait();
            return stage;
        } catch (IOException | IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Check connection and server settings!");
            alert.show();
            return null;
        }
    }
}