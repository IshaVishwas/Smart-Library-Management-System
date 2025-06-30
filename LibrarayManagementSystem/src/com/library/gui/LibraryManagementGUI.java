package com.library.gui;

import com.library.database.DatabaseConnection;
import com.library.admin.AdminDashboard;
import com.library.librarian.LibrarianDashboard;
import com.library.student.StudentDashboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Random;

public class LibraryManagementGUI extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;

    public LibraryManagementGUI() {
        setTitle("Library Management System - Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        JLabel roleLabel = new JLabel("Role:");
        roleComboBox = new JComboBox<>(new String[]{"Admin", "Librarian", "Student"});

        JButton loginButton = new JButton("Login");
        JButton forgotPasswordButton = new JButton("Forgot Password?");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(roleLabel);
        panel.add(roleComboBox);
        panel.add(loginButton);
        panel.add(forgotPasswordButton);

        add(panel);

        // Login button action
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedRole = (String) roleComboBox.getSelectedItem();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (selectedRole.equals("Student")) {
                    int studentId = getStudentId(username, password);
                    if (studentId != -1) {
                        openStudentDashboard(studentId);
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid credentials for Student!");
                    }
                } else {
                    if (validateLogin(username, password, selectedRole)) {
                        if (selectedRole.equals("Admin")) {
                            openAdminDashboard();
                        } else if (selectedRole.equals("Librarian")) {
                            openLibrarianDashboard(username);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid credentials!");
                    }
                }
            }
        });

        // Forgot Password button action
        forgotPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usernameOrEmail = JOptionPane.showInputDialog(null, "Enter your username or email:");
                if (usernameOrEmail != null && !usernameOrEmail.trim().isEmpty()) {
                    handleForgotPassword(usernameOrEmail);
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter your username or email.");
                }
            }
        });
    }

    private boolean validateLogin(String username, String password, String role) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ? AND role = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);

            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private int getStudentId(String username, String password) {
        String query = "SELECT id FROM users WHERE username = ? AND password = ? AND role = 'Student'";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private void handleForgotPassword(String usernameOrEmail) {
        String query = "SELECT * FROM users WHERE username = ? OR email = ?";
        String updateQuery = "UPDATE users SET password = ? WHERE username = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement selectStmt = conn.prepareStatement(query);
             PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {

            selectStmt.setString(1, usernameOrEmail);
            selectStmt.setString(2, usernameOrEmail);

            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                String username = rs.getString("username");
                String temporaryPassword = generateTemporaryPassword();
                updateStmt.setString(1, temporaryPassword);
                updateStmt.setString(2, username);

                int rowsAffected = updateStmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Temporary password: " + temporaryPassword + "\nPlease change it after logging in.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error updating password. Try again.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "User not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error.");
        }
    }

    private String generateTemporaryPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

    private void openAdminDashboard() {
        AdminDashboard adminDashboard = new AdminDashboard();
        adminDashboard.setVisible(true);
        this.dispose();
    }

    // private void openLibrarianDashboard() {
    //     LibrarianDashboard librarianDashboard = new LibrarianDashboard();
    //     librarianDashboard.setVisible(true);
    //     this.dispose();
    // }

    private void openLibrarianDashboard(String username) {
        LibrarianDashboard librarianDashboard = new LibrarianDashboard(username);
        librarianDashboard.setVisible(true);
        this.dispose();
    }
    

    private void openStudentDashboard(int studentId) {
        StudentDashboard studentDashboard = new StudentDashboard(studentId);
        studentDashboard.setVisible(true);
        this.dispose();
    }
   
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LibraryManagementGUI loginScreen = new LibraryManagementGUI();
            loginScreen.setVisible(true);
        });
    }
}


