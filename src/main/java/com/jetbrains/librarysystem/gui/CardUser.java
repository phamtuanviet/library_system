package com.jetbrains.librarysystem.gui;

import com.jetbrains.librarysystem.alert.Notification;
import com.jetbrains.librarysystem.controller.DashBoardController;
import com.jetbrains.librarysystem.dao.UserDao;
import com.jetbrains.librarysystem.data.UserData;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.File;

public class CardUser extends BaseCard {

    private UserData userData;
    private UserDao userDao = UserDao.getInstance();


    public UserData getUserData() {
        return userData;
    }

    public CardUser(UserData userData, DashBoardController dashBoardController) {
        this.userData = userData;
        this.dashBoardController = dashBoardController;
        contentBox = new VBox();

        File imageFile = new File("src/main/resources/" + userData.getImage());
        Image image = new Image(imageFile.toURI().toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(142);
        imageView.setFitWidth(182);
        imageView.setPreserveRatio(true);
        imageView.setPickOnBounds(true);

        Label idLabel = new Label("ID :" + userData.getId());
        idLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
        idLabel.setFont(Font.font("Arial"));

        Label firstNameLabel = new Label("First name: " + userData.getFirstName());
        firstNameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
        firstNameLabel.setFont(Font.font("Arial"));

        Label lastNameLabel = new Label("Last name: " + userData.getLastName());
        lastNameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
        lastNameLabel.setFont(Font.font("Arial"));

        Label dateOfBirthLabel = new Label("Date of birth: " + userData.getDateOfBirth());
        dateOfBirthLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
        dateOfBirthLabel.setFont(Font.font("Arial"));

        Label genderLabel = new Label("Gender: " + userData.getGender());
        genderLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
        genderLabel.setFont(Font.font("Arial"));


        contentBox.getChildren().addAll(imageView, idLabel, firstNameLabel, lastNameLabel, dateOfBirthLabel, genderLabel);
        contentBox.setSpacing(10);
        contentBox.setAlignment(Pos.TOP_CENTER);
        contentBox.setPrefHeight(260);
        contentBox.setPrefWidth(205);
        contentBox.setStyle("-fx-padding: 10;");
        contentBox.getStyleClass().add("v_box");

        buttonBox = new VBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        Button updateButton = new Button("Update");
        updateButton.setPrefHeight(26);
        updateButton.setPrefWidth(133);
        updateButton.getStyleClass().add("update_btn");

        Button deleteButton = new Button("Delete");
        deleteButton.setPrefHeight(26);
        deleteButton.setPrefWidth(133);
        deleteButton.getStyleClass().add("delete_btn");

        updateButton.setOnAction(event -> handleUpdateButton());
        deleteButton.setOnAction(event -> handleDeleteButton());


        buttonBox.getChildren().addAll(updateButton, deleteButton);
        buttonBox.setVisible(false);

        this.getChildren().addAll(contentBox, buttonBox);
        this.setOnMouseClicked(event -> handleCardClick(event));
        this.setOnMouseExited(event -> resetCard());
    }

    private void handleUpdateButton() {
        dashBoardController.setIsUpdate(true);
        dashBoardController.setSelectedUser(userData);
        dashBoardController.showUserActionFrame();
    }

    private void handleDeleteButton() {
        boolean isDelete = Notification.confirmationDialog("Confirmation Message",
                "Are you sure you want to delete this user?");
        if (isDelete) {
            Task<Void> newTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    userDao.deleteUser(userData.getId());
                    return null;
                }
            };
            newTask.setOnSucceeded(deleteEvent -> {
                dashBoardController.removeUser(userData);
                Platform.runLater(() -> {
                    Notification.showAlert(Alert.AlertType.INFORMATION
                            , "Success", "User deleted successfully!");

                });
            });

            newTask.setOnFailed(updateEvent -> {
                Platform.runLater(() -> {
                    Notification.showAlert(Alert.AlertType.ERROR, "Error Message", "Failed to delete user!");
                });
            });

            Thread addThread = new Thread(newTask);
            addThread.setDaemon(true);
            addThread.start();
        }
    }

    private void handleCardClick(MouseEvent event) {
        contentBox.setEffect(new GaussianBlur(10));
        buttonBox.setVisible(true);
    }

    private void resetCard() {
        contentBox.setEffect(null);
        buttonBox.setVisible(false);
    }
}