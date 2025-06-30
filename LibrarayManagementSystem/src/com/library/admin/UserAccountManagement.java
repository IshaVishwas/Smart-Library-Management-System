// 

package com.library.admin;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import com.library.database.DatabaseConnection;

public class UserAccountManagement extends JFrame {

    public UserAccountManagement() {
        setTitle("User Account Management");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(area), BorderLayout.CENTER);

        try (Connection conn = DatabaseConnection.connect()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                area.append("ID: " + rs.getInt("id") +
                            ", Name: " + rs.getString("fullname") +
                            ", Role: " + rs.getString("role") +
                            ", Username: " + rs.getString("username") + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            area.setText("Failed to load user accounts.");
        }

        setVisible(true);
    }
}
