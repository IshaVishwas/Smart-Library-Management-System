package com.library.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import com.library.database.DatabaseConnection;

public class ChangePassword extends JFrame {

    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;
    private String username;

    public ChangePassword(String username) {
        this.username = username;

        setTitle("Change Password");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("Old Password:"));
        oldPasswordField = new JPasswordField();
        add(oldPasswordField);

        add(new JLabel("New Password:"));
        newPasswordField = new JPasswordField();
        add(newPasswordField);

        add(new JLabel("Confirm New Password:"));
        confirmPasswordField = new JPasswordField();
        add(confirmPasswordField);

        JButton changeBtn = new JButton("Change Password");
        changeBtn.addActionListener(e -> handleChangePassword());
        add(changeBtn);

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(e -> dispose());
        add(cancelBtn);

        setVisible(true);
    }

    private void handleChangePassword() {
        String oldPassword = new String(oldPasswordField.getPassword());
        String newPassword = new String(newPasswordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (!newPassword.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "New passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DatabaseConnection.connect()) {
            PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            checkStmt.setString(1, username);
            checkStmt.setString(2, oldPassword);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                PreparedStatement updateStmt = conn.prepareStatement("UPDATE users SET password = ? WHERE username = ?");
                updateStmt.setString(1, newPassword);
                updateStmt.setString(2, username);
                int updated = updateStmt.executeUpdate();

                if (updated > 0) {
                    JOptionPane.showMessageDialog(this, "Password changed successfully.");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Password change failed.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Old password is incorrect.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
