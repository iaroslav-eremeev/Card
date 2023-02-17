package com.iaroslaveremeev.controllers;

import com.iaroslaveremeev.repository.CategoryRepository;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.prefs.Preferences;

public class AddCategoryController {
    public TextField categoryName;

    //TODO Если у юзера ни одной категории, не нажимается "добавить категорию"
    public void addCategoryInForm(ActionEvent actionEvent) {
        CategoryRepository categoryRepository = new CategoryRepository();
        try {
            categoryRepository
                    .addCategory(categoryName.getText(),
                            Preferences.userRoot().node("userId").getInt("userId", 0));
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "New category not added. " +
                    "Check if you typed the name correctly");
            alert.show();
        }
        Stage stage = (Stage) this.categoryName.getScene().getWindow();
        stage.close();
    }
}