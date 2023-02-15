package com.iaroslaveremeev.controllers;

import com.iaroslaveremeev.Program;
import com.iaroslaveremeev.repository.UserRepository;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthController {
    public TextField authLogin;
    public PasswordField authPassword;

    public void checkAuthorization(ActionEvent actionEvent) {
        try {
            UserRepository userRepository = new UserRepository();
            String login = this.authLogin.getText();
            String password = this.authPassword.getText();
            if (userRepository.authorize(login, password)) {
                Program.openWindow("/mainForm.fxml", null);
            }
        } catch (IOException ignored) {}
    }

    public void openRegForm(ActionEvent actionEvent) throws IOException {
        Program.openWindow("/registration.fxml", null);
        Stage stage = (Stage) authLogin.getScene().getWindow();
        stage.close();
    }

}
