package com.jetbrains.librarysystem.dao;

import com.jetbrains.librarysystem.data.UserData;
import com.jetbrains.librarysystem.databaseconnection.Database;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private static UserDao instance;

    private UserDao() {
    }

    public static synchronized UserDao getInstance() {
        if (instance == null) {
            instance = new UserDao();
        }
        return instance;
    }

    public static String urlImageCurrent;

    public boolean checkExsitingById(Long userId) {
        try (Connection connection = Database.connectToDb()) {
            String sql = "SELECT * FROM user WHERE id = ? ";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, userId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return true;
                }
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<UserData> getUsersInit() {
        List<UserData> users = new ArrayList<>();
        try (Connection connection = Database.connectToDb()) {
            String sql = "SELECT * FROM user LIMIT 20";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        UserData userData = new UserData(
                                resultSet.getLong("id"),
                                resultSet.getString("firstName"),
                                resultSet.getString("lastName"),
                                resultSet.getDate("dateOfBirth"),
                                resultSet.getString("gender"),
                                resultSet.getString("image")
                        );
                        users.add(userData);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public void addUser(String firstName, String lastName, LocalDate dateOfBirth, String gender, String image) {
        Date date = Date.valueOf(dateOfBirth);
        try (Connection connection = Database.connectToDb()) {
            String sql = "INSERT INTO user VALUES (null, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
                preparedStatement.setDate(3, date);
                preparedStatement.setString(4, gender);
                preparedStatement.setString(5, image);
                preparedStatement.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUser(Long id, String firstName, String lastName, LocalDate dateOfBirth, String gender, String image) {
        Date date = Date.valueOf(dateOfBirth);
        try (Connection connection = Database.connectToDb()) {
            String sql = "UPDATE user SET firstName = ?, lastName = ?, dateofBirth = ?, gender = ?, image = ? WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
                preparedStatement.setDate(3, date);
                preparedStatement.setString(4, gender);
                preparedStatement.setString(5, image);
                preparedStatement.setLong(6, id);
                preparedStatement.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(Long userId) {
        try (Connection connection = Database.connectToDb()) {
            String sql = "DELETE FROM user WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setLong(1, userId);
                preparedStatement.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<UserData> searchUsers(String keyword) throws Exception {
        List<UserData> users = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE "
                + "CAST(id AS CHAR) LIKE ? OR "
                + "firstName LIKE ? OR "
                + "lastName LIKE ? OR "
                + "CAST(dateOfBirth AS CHAR) LIKE ? OR "
                + "gender LIKE ? "
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
                        UserData user = new UserData(
                                resultSet.getLong("id"),
                                resultSet.getString("firstName"),
                                resultSet.getString("lastName"),
                                resultSet.getDate("dateOfBirth"),
                                resultSet.getString("gender"),
                                resultSet.getString("image")
                        );
                        users.add(user);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public Integer countTotalUsers() {
        try (Connection connection = Database.connectToDb()) {
            String sql = "SELECT COUNT(*) FROM user";
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

