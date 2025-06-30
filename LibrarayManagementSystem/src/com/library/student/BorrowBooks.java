package com.library.student;

import com.library.database.DatabaseConnection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BorrowBooks extends JFrame {

    private int studentId;  // Store the student ID

    public BorrowBooks(int studentId) {
        this.studentId = studentId;

        setTitle("Borrow Books");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter Book ID to Borrow:");
        JTextField bookIdField = new JTextField(15);
        JButton borrowButton = new JButton("Borrow");

        panel.add(label);
        panel.add(bookIdField);
        panel.add(borrowButton);

        add(panel);

        borrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookId = bookIdField.getText().trim();
                if (bookId.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a book ID.");
                } else {
                    // Try to borrow the book
                    if (borrowBook(studentId, bookId)) {
                        JOptionPane.showMessageDialog(null, "Book " + bookId + " borrowed successfully.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to borrow book " + bookId + ".");
                    }
                }
            }
        });
    }


private boolean borrowBook(int studentId, String bookId) {
    Connection connection = null;
    PreparedStatement checkBookStmt = null;
    PreparedStatement insertBorrowStmt = null;
    PreparedStatement updateBookStmt = null;
    ResultSet resultSet = null;

    try {
        connection = DatabaseConnection.connect();

        // Step 1: Check if book exists and has available copies
        String checkBookQuery = "SELECT available_copies FROM books WHERE id = ? AND available_copies > 0";
        checkBookStmt = connection.prepareStatement(checkBookQuery);
        checkBookStmt.setInt(1, Integer.parseInt(bookId));
        resultSet = checkBookStmt.executeQuery();

        if (resultSet.next()) {
            // Step 2: Insert record into borrowed_books
            String insertQuery = "INSERT INTO borrowed_books (student_id, book_id, borrow_date) VALUES (?, ?, NOW())";
            insertBorrowStmt = connection.prepareStatement(insertQuery);
            insertBorrowStmt.setInt(1, studentId);
            insertBorrowStmt.setInt(2, Integer.parseInt(bookId));
            insertBorrowStmt.executeUpdate();

            // Step 3: Decrease available copies
            String updateBookQuery = "UPDATE books SET available_copies = available_copies - 1 WHERE id = ?";
            updateBookStmt = connection.prepareStatement(updateBookQuery);
            updateBookStmt.setInt(1, Integer.parseInt(bookId));
            updateBookStmt.executeUpdate();

            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Book is not available or does not exist.");
            return false;
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
        return false;
    } finally {
        try {
            if (checkBookStmt != null) checkBookStmt.close();
            if (insertBorrowStmt != null) insertBorrowStmt.close();
            if (updateBookStmt != null) updateBookStmt.close();
            if (resultSet != null) resultSet.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
}
