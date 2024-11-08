module com.jetbrains.librarysystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.graphics;


    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires java.sql;
    requires de.jensd.fx.glyphs.fontawesome;
    requires jdk.jfr;
    requires com.fasterxml.jackson.databind;
    requires com.google.zxing;
    requires com.google.zxing.javase;

    opens com.jetbrains.librarysystem.controller to javafx.fxml;
    opens com.jetbrains.librarysystem.data to javafx.base;

    exports com.jetbrains.librarysystem;

}