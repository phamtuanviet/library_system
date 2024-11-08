package com.jetbrains.librarysystem.dao;

import com.jetbrains.librarysystem.data.TransactionDisplay;
import com.jetbrains.librarysystem.databaseconnection.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDisplayDao {
    private static TransactionDisplayDao instance;

    private TransactionDisplayDao() {
    }

    public static synchronized TransactionDisplayDao getInstance() {
        if (instance == null) {
            instance = new TransactionDisplayDao();
        }
        return instance;
    }

    public List<TransactionDisplay> searchTransactionDisplayToReturn(String searchText) {
        List<TransactionDisplay> transactionDisplays = new ArrayList<TransactionDisplay>();
        try (Connection connection = Database.connectToDb()) {
            String text = "%" + searchText.trim() + "%";
            String sql = "SELECT t.id AS transactionId, u.id AS userID, " +
                    "CONCAT(u.firstName, ' ', u.lastName) AS userName, " +
                    "d.title AS titleDocument, t.borrowDate " +
                    "FROM transaction t " +
                    "JOIN user u ON t.userId = u.id " +
                    "JOIN document d ON t.documentId = d.id " +
                    "WHERE ((CAST(t.id AS CHAR) LIKE ?) " +
                    "OR (CAST(u.id AS CHAR) LIKE ?) " +
                    "OR (CONCAT(u.firstName, ' ', u.lastName) LIKE  ?) " +
                    "OR (d.title LIKE ?) " +
                    "OR (DATE_FORMAT(t.borrowDate, '%Y-%m-%d') LIKE ?)) " +
                    "AND (t.status = 'borrowed');";
            ;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, text);
                preparedStatement.setString(2, text);
                preparedStatement.setString(3, text);
                preparedStatement.setString(4, text);
                preparedStatement.setString(5, text);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        TransactionDisplay transactionDisplay = new TransactionDisplay(
                                resultSet.getLong("transactionId")
                                , resultSet.getLong("userId")
                                , resultSet.getDate("borrowDate")
                                , resultSet.getString("titleDocument")
                                , resultSet.getString("userName")
                        );
                        transactionDisplays.add(transactionDisplay);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionDisplays;
    }

    public List<TransactionDisplay> searchTransactionDisplayToQuerry(String searchText) {
        List<TransactionDisplay> transactionDisplays = new ArrayList<TransactionDisplay>();
        try (Connection connection = Database.connectToDb()) {
            String text = "%" + searchText.trim() + "%";
            String sql = "SELECT t.id AS transactionId, u.id AS userID, " +
                    "CONCAT(u.firstName, ' ', u.lastName) AS userName, " +
                    "d.title AS titleDocument, t.borrowDate, t.returnDate , t.status " +
                    "FROM transaction t " +
                    "JOIN user u ON t.userId = u.id " +
                    "JOIN document d ON t.documentId = d.id " +
                    "WHERE (CAST(t.id AS CHAR) LIKE ?) " +
                    "OR (CAST(u.id AS CHAR) LIKE ?) " +
                    "OR (CONCAT(u.firstName, ' ', u.lastName) LIKE  ?) " +
                    "OR (d.title LIKE ?) " +
                    "OR (DATE_FORMAT(t.borrowDate, '%Y-%m-%d') LIKE ?) " +
                    "OR (DATE_FORMAT(t.returnDate, '%Y-%m-%d') LIKE ?) " +
                    "OR (t.status LIKE ?);";
            ;
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, text);
                preparedStatement.setString(2, text);
                preparedStatement.setString(3, text);
                preparedStatement.setString(4, text);
                preparedStatement.setString(5, text);
                preparedStatement.setString(6, text);
                preparedStatement.setString(7, text);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        TransactionDisplay transactionDisplay = new TransactionDisplay(
                                resultSet.getLong("transactionId")
                                , resultSet.getLong("userId")
                                , resultSet.getDate("returnDate")
                                , resultSet.getString("status")
                                , resultSet.getString("titleDocument")
                                , resultSet.getString("userName")
                                , resultSet.getDate("borrowDate")
                        );
                        transactionDisplays.add(transactionDisplay);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionDisplays;
    }
}
