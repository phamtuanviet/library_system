package com.jetbrains.librarysystem.dao;

import com.jetbrains.librarysystem.databaseconnection.Database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class TransactionDao {
    private static TransactionDao instance;

    private TransactionDao() {
    }

    public static synchronized TransactionDao getInstance() {
        if (instance == null) {
            instance = new TransactionDao();
        }
        return instance;
    }

    public void borrowDocument(Long userId, String documentId, LocalDate borrowDate) {
        Date date = Date.valueOf(borrowDate);
        try (Connection connection = Database.connectToDb()) {
            String sql = "INSERT INTO transaction (userId,documentId,borrowDate,status) VALUES (?, ?, ?,'borrowed')";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, userId);
                preparedStatement.setString(2, documentId);
                preparedStatement.setDate(3, date);
                preparedStatement.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Đếm số lượng sách mà user đã mượn để kiểm tra có cho mượn tiếp hay không. */
    public Integer countBorrowedDocuments(Long userId) {
        try (Connection connection = Database.connectToDb()) {
            String sql = "SELECT COUNT(*) FROM transaction WHERE userId = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, userId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt(1);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void returnDocument(Long id, LocalDate returnDate) {
        Date date = Date.valueOf(returnDate);
        try (Connection connection = Database.connectToDb()) {
            String sql = "UPDATE transaction SET returnDate = ?, status = 'returned' WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setDate(1, date);
                preparedStatement.setLong(2, id);
                preparedStatement.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Đếm tổng số giao dịch. */
    public Integer countTotalTransactions() {
        try (Connection connection = Database.connectToDb()) {
            String sql = "SELECT COUNT(*) FROM transaction";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt(1);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
