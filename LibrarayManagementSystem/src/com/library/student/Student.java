package com.library.student;

import com.library.database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Student {

    private int id;
    private String name;
    private String email;
    private String phoneNumber;

    // Constructor
    public Student(int id, String name, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Method to fetch student data from the database
    public static Student getStudentById(int studentId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DatabaseConnection.connect();  // Use your DatabaseConnection class to get the connection
            String query = "SELECT * FROM students WHERE id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, studentId);

            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Student(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone_number")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null; // Return null if student not found
    }
}
