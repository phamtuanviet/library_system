package com.jetbrains.librarysystem.gui;

import com.jetbrains.librarysystem.controller.DashBoardController;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public abstract class BaseCard extends StackPane {
    /* Mục nội dung.*/
    protected VBox contentBox;
    /* Mục nút bấm xự kiện khi click vào mục thông tin. */
    protected VBox buttonBox;
    protected DashBoardController dashBoardController;
}
