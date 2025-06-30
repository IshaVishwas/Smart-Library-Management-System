// 
package com.library.student;

import com.library.database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RequestBooks extends JFrame {

    private int studentId;

    public RequestBooks(int studentId) {
        this.studentId = studentId;

        setTitle("Request New Book");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Enter Book Title:");
        JTextField titleField = new JTextField(20);

        JLabel authorLabel = new JLabel("Enter Author Name:");
        JTextField authorField = new JTextField(20);

        JButton requestButton = new JButton("Request Book");

        panel.add(titleLabel);
        panel.add(titleField);
        panel.add(authorLabel);
        panel.add(authorField);
        panel.add(requestButton);

        add(panel);

        requestButton.addActionListener(e -> {
            String bookTitle = titleField.getText().trim();
            String author = authorField.getText().trim();

            if (bookTitle.isEmpty() || author.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter both book title and author.");
            } else {
                requestBook(bookTitle, author);
            }
        });
    }

    private void requestBook(String bookTitle, String author) {
        try (Connection conn = DatabaseConnection.connect()) {
            // Optional: Check for duplicate request
            String checkQuery = "SELECT * FROM book_requests WHERE student_id = ? AND LOWER(TRIM(book_title)) = ? AND LOWER(TRIM(author)) = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setInt(1, studentId);
            checkStmt.setString(2, bookTitle.toLowerCase().trim());
            checkStmt.setString(3, author.toLowerCase().trim());

            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "You have already requested this book.");
                return;
            }

            // Insert the request
            String insertQuery = "INSERT INTO book_requests (student_id, book_title, author, request_date) VALUES (?, ?, ?, CURRENT_DATE)";
            PreparedStatement stmt = conn.prepareStatement(insertQuery);
            stmt.setInt(1, studentId);
            stmt.setString(2, bookTitle);
            stmt.setString(3, author);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Book request sent successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to send book request.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error occurred while requesting the book.");
        }
    }

    // For testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RequestBooks(3).setVisible(true));  // Replace with a valid student ID
    }
}
