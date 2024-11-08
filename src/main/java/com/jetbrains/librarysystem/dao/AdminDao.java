package com.jetbrains.librarysystem.dao;

import com.jetbrains.librarysystem.databaseconnection.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminDao {
    private static AdminDao instance;

    private AdminDao() {
    }

    public static synchronized AdminDao getInstance() {
        if (instance == null) {
            instance = new AdminDao();
        }
        return instance;
    }

    public boolean checkLogin(String username, String password) {
        String sql = "Select * from admin where username = ? and password = ?";
        try (Connection connection = Database.connectToDb()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return true;
                    }
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
