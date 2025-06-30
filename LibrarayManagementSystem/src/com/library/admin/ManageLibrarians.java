// package com.library.admin;

// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.*;
// import java.sql.*;
// import com.library.database.DatabaseConnection;

// public class ManageLibrarians extends JFrame {
//     private JTextArea area;

//     public ManageLibrarians() {
//         setTitle("Manage Librarians");
//         setSize(600, 400);
//         setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//         setLocationRelativeTo(null);
//         setLayout(new BorderLayout());

//         area = new JTextArea();
//         area.setEditable(false);
//         add(new JScrollPane(area), BorderLayout.CENTER);

//         JPanel buttonPanel = new JPanel();
//         JButton addButton = new JButton("Add Librarian");
//         JButton removeButton = new JButton("Remove Librarian");
//         buttonPanel.add(addButton);
//         buttonPanel.add(removeButton);
//         add(buttonPanel, BorderLayout.SOUTH);

//         addButton.addActionListener(e -> addLibrarian());
//         removeButton.addActionListener(e -> removeLibrarian());

//         loadLibrarians();

//         setVisible(true);
//     }

//     private void loadLibrarians() {
//         area.setText("");
//         try (Connection conn = DatabaseConnection.connect()) {
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE role = 'librarian'");
//             while (rs.next()) {
//                 area.append("ID: " + rs.getInt("id") +
//                             ", Name: " + rs.getString("name") +
//                             ", Username: " + rs.getString("username") + "\n");
//             }
//         } catch (SQLException e) {
//             e.printStackTrace();
//             area.setText("Failed to load librarians.");
//         }
//     }

//     private void addLibrarian() {
//         JTextField nameField = new JTextField();
//         JTextField usernameField = new JTextField();
//         JPasswordField passwordField = new JPasswordField();

//         Object[] fields = {
//             "Name:", nameField,
//             "Username:", usernameField,
//             "Password:", passwordField
//         };

//         int result = JOptionPane.showConfirmDialog(null, fields, "Add Librarian", JOptionPane.OK_CANCEL_OPTION);
//         if (result == JOptionPane.OK_OPTION) {
//             try (Connection conn = DatabaseConnection.connect()) {
//                 PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (name, username, password, role) VALUES (?, ?, ?, 'librarian')");
//                 stmt.setString(1, nameField.getText());
//                 stmt.setString(2, usernameField.getText());
//                 stmt.setString(3, new String(passwordField.getPassword()));
//                 stmt.executeUpdate();
//                 JOptionPane.showMessageDialog(this, "Librarian added successfully!");
//                 loadLibrarians();
//             } catch (SQLException e) {
//                 e.printStackTrace();
//                 JOptionPane.showMessageDialog(this, "Error adding librarian.");
//             }
//         }
//     }

//     private void removeLibrarian() {
//         String username = JOptionPane.showInputDialog(this, "Enter username of librarian to remove:");
//         if (username != null && !username.trim().isEmpty()) {
//             try (Connection conn = DatabaseConnection.connect()) {
//                 PreparedStatement stmt = conn.prepareStatement("DELETE FROM users WHERE username = ? AND role = 'librarian'");
//                 stmt.setString(1, username);
//                 int rows = stmt.executeUpdate();
//                 if (rows > 0) {
//                     JOptionPane.showMessageDialog(this, "Librarian removed successfully!");
//                     loadLibrarians();
//                 } else {
//                     JOptionPane.showMessageDialog(this, "Librarian not found.");
//                 }
//             } catch (SQLException e) {
//                 e.printStackTrace();
//                 JOptionPane.showMessageDialog(this, "Error removing librarian.");
//             }
//         }
//     }
// }


package com.library.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import com.library.database.DatabaseConnection;

public class ManageLibrarians extends JFrame {

    private JTextArea area;

    public ManageLibrarians() {
        setTitle("Manage Librarians");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        area = new JTextArea();
        area.setEditable(false);
        add(new JScrollPane(area), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton addBtn = new JButton("Add Librarian");
        JButton removeBtn = new JButton("Remove Librarian");

        buttonPanel.add(addBtn);
        buttonPanel.add(removeBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        loadLibrarians();

        // Add librarian
        addBtn.addActionListener(e -> addLibrarian());

        // Remove librarian
        removeBtn.addActionListener(e -> removeLibrarian());

        setVisible(true);
    }

    private void loadLibrarians() {
        area.setText("");
        try (Connection conn = DatabaseConnection.connect()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE role = 'Librarian'");
            while (rs.next()) {
                area.append("ID: " + rs.getInt("id") +
                        ", Full Name: " + rs.getString("fullname") +
                        ", Username: " + rs.getString("username") +
                        ", Email: " + rs.getString("email") + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            area.setText("Failed to load librarians.");
        }
    }

    private void addLibrarian() {
        JTextField fullNameField = new JTextField();
        JTextField usernameField = new JTextField();
        JTextField passwordField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField phoneField = new JTextField();

        Object[] fields = {
                "Full Name:", fullNameField,
                "Username:", usernameField,
                "Password:", passwordField,
                "Email:", emailField,
                "Phone Number:", phoneField
        };

        int option = JOptionPane.showConfirmDialog(this, fields, "Add Librarian", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try (Connection conn = DatabaseConnection.connect()) {
                String query = "INSERT INTO users (username, password, role, email, phone_number, fullname, status) VALUES (?, ?, 'Librarian', ?, ?, ?, 'Active')";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, usernameField.getText());
                ps.setString(2, passwordField.getText());
                ps.setString(3, emailField.getText());
                ps.setString(4, phoneField.getText());
                ps.setString(5, fullNameField.getText());

                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Librarian added successfully.");
                loadLibrarians();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error adding librarian.");
            }
        }
    }

    private void removeLibrarian() {
        String idStr = JOptionPane.showInputDialog(this, "Enter ID of librarian to remove:");
        if (idStr != null && !idStr.isEmpty()) {
            try (Connection conn = DatabaseConnection.connect()) {
                String query = "DELETE FROM users WHERE id = ? AND role = 'Librarian'";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setInt(1, Integer.parseInt(idStr));
                int rows = ps.executeUpdate();
                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Librarian removed successfully.");
                    loadLibrarians();
                } else {
                    JOptionPane.showMessageDialog(this, "No librarian found with that ID.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error removing librarian.");
            }
        }
    }
}
