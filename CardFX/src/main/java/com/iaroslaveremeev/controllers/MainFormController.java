package com.iaroslaveremeev.controllers;

import com.iaroslaveremeev.Program;
import com.iaroslaveremeev.model.Card;
import com.iaroslaveremeev.model.Category;
import com.iaroslaveremeev.model.User;
import com.iaroslaveremeev.repository.CategoryRepository;
import com.iaroslaveremeev.repository.UserRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.prefs.Preferences;

public class MainFormController {
    public ComboBox<Category> categoryComboBoxTop;
    public ComboBox<Card> cardComboBox; // Карточки относятся к выбранной сверху категории
    public TextField question;
    public TextField answer;
    public ComboBox<Category> categoryComboBoxBottom;

    public void initialize(){
        CategoryRepository categoryRepository = new CategoryRepository();
        int userId = Preferences.userRoot().node("userId").getInt("userId", 0);
        this.categoryComboBoxTop
                .setItems(FXCollections.observableList(categoryRepository.getUserCategories(userId)));
        this.categoryComboBoxBottom
                .setItems(FXCollections.observableList(categoryRepository.getUserCategories(userId)));
    }

    public void addNewCategory(ActionEvent actionEvent) throws IOException {
        Stage mainStage = Program.openWindow("/addCategoryForm.fxml", null);
        assert mainStage != null;
        mainStage.showAndWait();
        initialize();
    }
    public void deleteChosenCategory(ActionEvent actionEvent) {
        CategoryRepository categoryRepository = new CategoryRepository();
        Category catToDelete = this.categoryComboBoxTop.getSelectionModel().getSelectedItem();
        categoryRepository.deleteCategory(catToDelete.getId());
        this.categoryComboBoxTop.getItems().remove(catToDelete);
        this.categoryComboBoxBottom.getItems().remove(catToDelete);
    }
    public void addNewCard(ActionEvent actionEvent) {
    }
    public void deleteChosenCard(ActionEvent actionEvent) {
    }

    public void updateCard(ActionEvent actionEvent) {
    }
}
