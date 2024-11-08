package com.jetbrains.librarysystem.dao;

import com.jetbrains.librarysystem.data.DocumentData;
import com.jetbrains.librarysystem.databaseconnection.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DocumentDao {
    public static String urlImageCurrent;
    public static String urlDescriptionCurrent;

    private static DocumentDao instance;

    private DocumentDao() {
    }

    public static synchronized DocumentDao getInstance() {
        if (instance == null) {
            instance = new DocumentDao();
        }
        return instance;
    }

    public boolean checkExsitingById(String documentId) {
        try (Connection connection = Database.connectToDb()) {
            String sql = "SELECT * FROM document WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, documentId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return true;
                    }
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    /* Lấy giá trị ban đầu cho document. */
    public List<DocumentData> getInitDocuments() {
        List<DocumentData> documents = new ArrayList<DocumentData>();
        try (Connection connection = Database.connectToDb()) {
            String sql = "SELECT * FROM document ORDER BY id ASC LIMIT 20";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        DocumentData documentData = new DocumentData(
                                resultSet.getString("id"),
                                resultSet.getString("title"),
                                resultSet.getString("author"),
                                resultSet.getString("category"),
                                resultSet.getInt("quantity"),
                                resultSet.getString("image"),
                                resultSet.getString("link_description")
                        );
                        documents.add(documentData);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return documents;
    }

    public void addDocument(String documentId, String title, String author, String category
            , int quantity, String image, String linkDescription) {
        try (Connection connection = Database.connectToDb()) {
            String sql = "INSERT INTO document VALUES (?, ?, ?, ?, ?, ? ,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, documentId);
                preparedStatement.setString(2, title);
                preparedStatement.setString(3, author);
                preparedStatement.setString(4, category);
                preparedStatement.setString(5, image);
                preparedStatement.setInt(6, quantity);
                preparedStatement.setString(7, linkDescription);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDocument(String documentId, int quantity) {
        try (Connection connection = Database.connectToDb()) {
            String sql = "UPDATE document SET quantity = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, quantity);
                preparedStatement.setString(2, documentId);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDocument(String documentId) {
        try (Connection connection = Database.connectToDb()) {
            String sql = "DELETE FROM document WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, documentId);
                preparedStatement.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<DocumentData> searchDocuments(String keyword) {
        List<DocumentData> documents = new ArrayList<>();
        String sql = "SELECT * FROM document WHERE "
                + "id  LIKE ? OR "
                + "title LIKE ? OR "
                + "author LIKE ? OR "
                + "category LIKE ? OR "
                + "quantity LIKE ? "
                + "LIMIT 20";
        try (Connection connection = Database.connectToDb()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                String searchKeyword = "%" + keyword + "%";
                preparedStatement.setString(1, searchKeyword);
                preparedStatement.setString(2, searchKeyword);
                preparedStatement.setString(3, searchKeyword);
                preparedStatement.setString(4, searchKeyword);
                preparedStatement.setString(5, searchKeyword);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        DocumentData documentData = new DocumentData(
                                resultSet.getString("id"),
                                resultSet.getString("title"),
                                resultSet.getString("author"),
                                resultSet.getString("category"),
                                resultSet.getInt("quantity"),
                                resultSet.getString("image"),
                                resultSet.getString("link_description")
                        );
                        documents.add(documentData);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return documents;
    }

    public Integer quantityDocument(String documentId) {
        try (Connection connection = Database.connectToDb()) {
            String sql = "SELECT quantity FROM document WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, documentId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("quantity");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /* Đếm số sách đang có trong database. */
    public Integer countTotalDocuments() {
        try (Connection connection = Database.connectToDb()) {
            String sql = "SELECT COUNT(*) FROM document";
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
