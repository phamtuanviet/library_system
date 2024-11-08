package com.jetbrains.librarysystem.dao;

import com.jetbrains.librarysystem.data.UserData;
import com.jetbrains.librarysystem.databaseconnection.Database;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoTest {
    private UserDao userDao;
    private Long testUserId;

    @BeforeEach
    public void setUp() {
        userDao = UserDao.getInstance();
        userDao.addUser("John", "Doe", LocalDate.of(1990, 1, 1), "Male", "image.jpg");
        testUserId = getLatestUserId();
    }

    @AfterEach
    public void tearDown() {
        deleteTestUser();
    }

    @Test
    public void testAddUser() {
        List<UserData> users = userDao.getUsersInit();
        assertTrue(users.size() > 0, "User list should contain at least one user.");
    }

    @Test
    public void testUpdateUser() {
        userDao.updateUser(testUserId, "John", "Smith", LocalDate.of(1990, 1, 1), "Male", "newImage.jpg");
        UserData updatedUser = getUserById(testUserId);
        assertEquals("Smith", updatedUser.getLastName(), "Last name should be updated.");
        assertEquals("newImage.jpg", updatedUser.getImage(), "Image should be updated.");
    }

    @Test
    public void testDeleteUser() {
        userDao.deleteUser(testUserId);
        UserData deletedUser = getUserById(testUserId);
        assertNull(deletedUser, "User should be deleted.");
    }

    @Test
    public void testSearchUsers() throws Exception {
        List<UserData> users = userDao.searchUsers("John");
        assertFalse(users.isEmpty(), "Search result should contain users with the name 'John'.");
    }

    @Test
    public void testCountTotalUsers() {
        Integer totalUsers = userDao.countTotalUsers();
        assertNotNull(totalUsers, "Total user count should not be null.");
        assertTrue(totalUsers > 0, "Total user count should be greater than 0.");
    }

    private Long getLatestUserId() {
        try (Connection connection = Database.connectToDb();
             PreparedStatement stmt = connection.prepareStatement("SELECT id FROM user ORDER BY id DESC LIMIT 1");
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getLong("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private UserData getUserById(Long userId) {
        try (Connection connection = Database.connectToDb();
             PreparedStatement stmt = connection.prepareStatement("SELECT * FROM user WHERE id = ?")) {
            stmt.setLong(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new UserData(
                            rs.getLong("id"),
                            rs.getString("firstName"),
                            rs.getString("lastName"),
                            rs.getDate("dateOfBirth"),
                            rs.getString("gender"),
                            rs.getString("image")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void deleteTestUser() {
        try (Connection connection = Database.connectToDb();
             PreparedStatement stmt = connection.prepareStatement("DELETE FROM user WHERE id = ?")) {
            stmt.setLong(1, testUserId);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
