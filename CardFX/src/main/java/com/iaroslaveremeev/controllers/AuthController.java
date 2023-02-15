package com.iaroslaveremeev.controllers;

import com.iaroslaveremeev.Program;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthController {
    public TextField authLogin;
    public PasswordField authPassword;

    public void initialize() throws IOException {
    }

    public void checkAuthorization(ActionEvent actionEvent) {
    }

    public void openRegForm(ActionEvent actionEvent) throws IOException {
        Program.openWindow("/registration.fxml", null);
        Stage stage = (Stage) authLogin.getScene().getWindow();
        stage.close();
    }

}
