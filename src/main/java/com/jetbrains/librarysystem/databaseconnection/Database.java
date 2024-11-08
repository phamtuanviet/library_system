package com.jetbrains.librarysystem.databaseconnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    /* Tạo liên kết để kết nối database. */
    public static Connection connectToDb() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_management"
                    , "root", "123456");
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
