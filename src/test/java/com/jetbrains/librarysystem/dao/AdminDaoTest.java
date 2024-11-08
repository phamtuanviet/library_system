package com.jetbrains.librarysystem.dao;

import com.jetbrains.librarysystem.databaseconnection.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdminDaoTest {
    private AdminDao adminDao;

    @BeforeEach
    public void setUp() {
        adminDao = AdminDao.getInstance();
        try (Connection connection = Database.connectToDb()) {
            String sql = "INSERT INTO admin (username, password) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, "testUser");
                preparedStatement.setString(2, "testPass");
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void tearDown() {
        try (Connection connection = Database.connectToDb()) {
            String sql = "DELETE FROM admin WHERE username = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, "testUser");
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCheckLogin_Success() {
        boolean result = adminDao.checkLogin("testUser", "testPass");
        assertTrue(result, "Login Successful.");
    }

    @Test
    public void testCheckLogin_Failure() {
        boolean result = adminDao.checkLogin("wrongUser", "wrongPass");
        assertFalse(result, "Wrong username or password.");
    }
}
