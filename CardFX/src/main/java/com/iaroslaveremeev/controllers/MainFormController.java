package com.iaroslaveremeev.controllers;

import com.iaroslaveremeev.model.Card;
import com.iaroslaveremeev.model.Category;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainFormController {
    public ComboBox<Category> categoryComboBoxTop;
    public ComboBox<Card> cardComboBox; // Карточки относятся к выбранной сверху категории
    public TextField question;
    public TextField answer;
    public ComboBox<Category> categoryComboBoxBottom;

    //TODO добавить кнопку для апдейта карточек
    public void initialize(){
        /*this.categoryComboBoxTop.setItems(FXCollections.observableList();*/
    }

    public void addNewCategory(ActionEvent actionEvent) {
    }
    public void deleteChosenCategory(ActionEvent actionEvent) {
    }
    public void addNewCard(ActionEvent actionEvent) {
    }
    public void deleteChosenCard(ActionEvent actionEvent) {
    }

    public void updateCard(ActionEvent actionEvent) {
    }
}
