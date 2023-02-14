package com.iaroslaveremeev.controllers;

import com.iaroslaveremeev.model.User;
import com.iaroslaveremeev.repository.UserRepository;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegController {

    public TextField regLogin;
    public TextField regName;
    public PasswordField regPassword;

    public void signUp(ActionEvent actionEvent) {
        try {
            UserRepository userRepository = new UserRepository();
            userRepository.register(new User(regLogin.getText(), regName.getText(), regPassword.getText()));
            Stage stage = (Stage) regLogin.getScene().getWindow();
            stage.close();
        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.show();
        }

    }
}
