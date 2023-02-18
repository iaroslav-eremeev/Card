package com.iaroslaveremeev.controllers;

import com.iaroslaveremeev.Program;
import com.iaroslaveremeev.model.Card;
import com.iaroslaveremeev.model.Category;
import com.iaroslaveremeev.repository.CardRepository;
import com.iaroslaveremeev.repository.CategoryRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
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
    public void deleteCategory(ActionEvent actionEvent) {
        CategoryRepository categoryRepository = new CategoryRepository();
        Category catToDelete = this.categoryComboBoxTop.getSelectionModel().getSelectedItem();
        categoryRepository.deleteCategory(catToDelete.getId());
        this.categoryComboBoxTop.getItems().remove(catToDelete);
        this.categoryComboBoxBottom.getItems().remove(catToDelete);
        initialize();
    }

    public void categoryChosen(ActionEvent actionEvent) {
        if (this.categoryComboBoxTop.getSelectionModel().getSelectedItem() != null){
            CardRepository cardRepository = new CardRepository();
            int catId = this.categoryComboBoxTop.getSelectionModel().getSelectedItem().getId();
            this.cardComboBox
                    .setItems(FXCollections.observableList(cardRepository.getCategoryCards(catId)));
        }
        else this.cardComboBox = null;
    }

    public void showCardForUpdate(ActionEvent actionEvent) {
        this.question.clear();
        this.answer.clear();
        this.categoryComboBoxBottom.getSelectionModel().clearSelection();
        if (this.cardComboBox.getSelectionModel().getSelectedItem() != null){
            Card card = this.cardComboBox.getSelectionModel().getSelectedItem();
            this.question.setText(card.getQuestion());
            this.answer.setText(card.getAnswer());
            CategoryRepository categoryRepository = new CategoryRepository();
            int catId = card.getCategoryId();
            Category category = categoryRepository.getCategoryById(catId);
            this.categoryComboBoxBottom.getSelectionModel().select(category);
        }
    }

    public void updateCard(ActionEvent actionEvent) {
        CardRepository cardRepository = new CardRepository();
        Card oldCard = this.cardComboBox.getSelectionModel().getSelectedItem();
        Card updatedCard = new Card(oldCard.getId(), this.question.getText(),
                this.answer.getText(),
                this.categoryComboBoxBottom.getSelectionModel().getSelectedItem().getId(),
                oldCard.getCreationDate());
        cardRepository.updateCard(updatedCard);
    }

    public void deleteChosenCard(ActionEvent actionEvent) {
        CardRepository cardRepository = new CardRepository();
        Card cardToDelete = this.cardComboBox.getSelectionModel().getSelectedItem();
        cardRepository.deleteCard(cardToDelete.getId());
        this.cardComboBox.getItems().remove(cardToDelete);
        this.question.clear();
        this.answer.clear();
        this.categoryComboBoxBottom.getSelectionModel().clearSelection();
    }

    public void addNewCard(ActionEvent actionEvent) throws IOException {
        Stage mainStage = Program.openWindow("/addCardForm.fxml", null);
        assert mainStage != null;
        mainStage.showAndWait();
        this.question.clear();
        this.answer.clear();
        this.categoryComboBoxBottom.getSelectionModel().clearSelection();
        CardRepository cardRepository = new CardRepository();
        int catId = this.categoryComboBoxTop.getSelectionModel().getSelectedItem().getId();
        this.cardComboBox
                .setItems(FXCollections.observableList(cardRepository.getCategoryCards(catId)));
    }
}
