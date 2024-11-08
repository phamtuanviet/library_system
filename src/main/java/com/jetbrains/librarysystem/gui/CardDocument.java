package com.jetbrains.librarysystem.gui;

import com.google.zxing.WriterException;
import com.jetbrains.librarysystem.alert.Notification;
import com.jetbrains.librarysystem.controller.DashBoardController;
import com.jetbrains.librarysystem.dao.DocumentDao;
import com.jetbrains.librarysystem.data.DocumentData;
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
import java.io.IOException;

public class
CardDocument extends BaseCard {
    private DocumentData documentData;
    private DocumentDao documentDao = DocumentDao.getInstance();


    public DocumentData getDocumentData() {
        return documentData;
    }

    public CardDocument(DocumentData documentData, DashBoardController dashBoardController, boolean isAvailable) {
        this.documentData = documentData;
        this.dashBoardController = dashBoardController;
        contentBox = new VBox();


        ImageView imageView = new ImageView();
        Task<Image> loadImageTask = new Task<Image>() {
            @Override
            protected Image call() throws Exception {
                Image image;
                if (documentData.getImage() == null || documentData.getImage().isEmpty()) {
                    File imageFile = new File("src/main/resources/image/others/defaultbook.jpg");
                    image = new Image(imageFile.toURI().toString());
                } else {
                    image = new Image(documentData.getImage());
                }
                return image;
            }

        };
        loadImageTask.setOnSucceeded(event -> {
            Image loadedImage = loadImageTask.getValue();
            if (loadedImage == null) {
                System.out.println("image loaded");
            }
            imageView.setImage(loadedImage);
            imageView.setFitHeight(142);
            imageView.setFitWidth(182);
            imageView.setPreserveRatio(true);
            imageView.setPickOnBounds(true);
        });
        Thread thread = new Thread(loadImageTask);
        thread.setDaemon(true);
        thread.start();


        Label idLabel = new Label("ID :" + documentData.getId());
        idLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
        idLabel.setFont(Font.font("Arial"));

        Label titleLabel = new Label("Title :" + documentData.getTitle());
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
        titleLabel.setFont(Font.font("Arial"));

        Label authorLabel = new Label("Author: " + documentData.getAuthor());
        authorLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
        authorLabel.setFont(Font.font("Arial"));

        Label categoryLabel = new Label("Category: " + documentData.getCategory());
        categoryLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
        categoryLabel.setFont(Font.font("Arial"));

        Label quantityLabel = new Label("quantity " + documentData.getQuantity());
        quantityLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12px;");
        quantityLabel.setFont(Font.font("Arial"));

        contentBox.setPrefHeight(260);
        contentBox.setSpacing(10);
        contentBox.setAlignment(Pos.TOP_CENTER);
        contentBox.setPrefWidth(205);
        contentBox.setStyle("-fx-padding: 10;");
        contentBox.getStyleClass().add("v_box");

        buttonBox = new VBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        Button descriptionButton = new Button("Qr");
        descriptionButton.setPrefHeight(26);
        descriptionButton.setPrefWidth(133);
        descriptionButton.getStyleClass().add("qr-button");
        descriptionButton.setOnAction(event -> {
            try {
                handleDescriptionButton();
            } catch (IOException | WriterException e) {
                throw new RuntimeException(e);
            }
        });

        if (isAvailable) {
            contentBox.getChildren().addAll(imageView, idLabel, titleLabel, authorLabel, categoryLabel, quantityLabel);
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
            buttonBox.getChildren().addAll(updateButton, deleteButton, descriptionButton);
            buttonBox.setVisible(false);
        } else {
            contentBox.getChildren().addAll(imageView, idLabel, titleLabel, authorLabel, categoryLabel);
            Button addButton = new Button("Add");
            addButton.setPrefHeight(26);
            addButton.setPrefWidth(133);
            addButton.getStyleClass().add("delete_btn");
            addButton.setOnAction(event -> handleAddButton());
            buttonBox.getChildren().addAll(addButton, descriptionButton);
            buttonBox.setVisible(false);
        }

        this.getChildren().addAll(contentBox, buttonBox);
        this.setOnMouseClicked(event -> handleCardClick(event));
        this.setOnMouseExited(event -> resetCard());
    }

    private void handleDeleteButton() {
        boolean isDelete = Notification.confirmationDialog("Confirmation Message",
                "Are you sure you want to delete this document?");
        if (isDelete) {
            Task<Void> newTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    documentDao.deleteDocument(documentData.getId());
                    return null;
                }
            };
            newTask.setOnSucceeded(deleteEvent -> {
                dashBoardController.removeDocument(documentData);
                Platform.runLater(() -> {
                    Notification.showAlert(Alert.AlertType.INFORMATION
                            , "Success", "Document deleted successfully!");

                });
            });

            newTask.setOnFailed(updateEvent -> {
                Platform.runLater(() -> {
                    Notification.showAlert(Alert.AlertType.ERROR, "Error Message", "Failed to delete document!");
                });
            });

            Thread addThread = new Thread(newTask);
            addThread.setDaemon(true);
            addThread.start();
        }
    }

    private void handleDescriptionButton() throws IOException, WriterException {
        dashBoardController.showQrFrame(documentData.getLinkDescription());
    }

    private void handleUpdateButton() {
        dashBoardController.setSelectedDocument(documentData);
        dashBoardController.showDocumentActionFrame();
    }

    private void handleAddButton() {
        dashBoardController.setSelectedDocument(documentData);
        dashBoardController.showDocumentActionFrame();
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

