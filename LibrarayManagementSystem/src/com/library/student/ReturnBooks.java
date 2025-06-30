// package com.library.student;

// import com.library.database.DatabaseConnection;

// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.sql.*;

// public class ReturnBooks extends JFrame {

//     private int studentId;  // Store the student ID

//     public ReturnBooks(int studentId) {
//         this.studentId = studentId;

//         setTitle("Return Books");
//         setSize(300, 200);
//         setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//         setLocationRelativeTo(null);

//         JPanel panel = new JPanel();
//         panel.setLayout(new GridLayout(3, 1, 10, 10));
//         panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

//         JLabel label = new JLabel("Enter Book ID to Return:");
//         JTextField bookIdField = new JTextField(15);
//         JButton returnButton = new JButton("Return");

//         panel.add(label);
//         panel.add(bookIdField);
//         panel.add(returnButton);

//         add(panel);

//         returnButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 String bookId = bookIdField.getText().trim();
//                 if (bookId.isEmpty()) {
//                     JOptionPane.showMessageDialog(null, "Please enter a book ID.");
//                 } else {
//                     // Attempt to return the book
//                     returnBook(bookId);
//                 }
//             }
//         });
//     }

//     private void returnBook(String bookId) {
//         try (Connection conn = DatabaseConnection.connect()) {
//             // Check if the book is borrowed by this student
//             String checkBookQuery = "SELECT * FROM borrowed_books WHERE student_id = ? AND book_id = ?";
//             PreparedStatement checkStmt = conn.prepareStatement(checkBookQuery);
//             checkStmt.setInt(1, studentId);
//             checkStmt.setInt(2, Integer.parseInt(bookId));
//             ResultSet rs = checkStmt.executeQuery();

//             if (rs.next()) {
//                 // Book is borrowed, proceed to return
//                 String returnQuery = "DELETE FROM borrowed_books WHERE student_id = ? AND book_id = ?";
//                 PreparedStatement returnStmt = conn.prepareStatement(returnQuery);
//                 returnStmt.setInt(1, studentId);
//                 returnStmt.setInt(2, Integer.parseInt(bookId));
//                 returnStmt.executeUpdate();

//                 // Update available copies of the book
//                 String updateBookQuery = "UPDATE books SET available_copies = available_copies + 1 WHERE book_id = ?";
//                 PreparedStatement updateBookStmt = conn.prepareStatement(updateBookQuery);
//                 updateBookStmt.setInt(1, Integer.parseInt(bookId));
//                 updateBookStmt.executeUpdate();

//                 JOptionPane.showMessageDialog(null, "Book " + bookId + " returned successfully.");
//             } else {
//                 JOptionPane.showMessageDialog(null, "You have not borrowed this book.");
//             }
//         } catch (SQLException e) {
//             e.printStackTrace();
//             JOptionPane.showMessageDialog(null, "Error returning the book. Please try again.");
//         }
//     }

//     // For testing
//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(() -> new ReturnBooks(1).setVisible(true));  // Use a valid studentId for testing
//     }
// }



package com.library.student;

import com.library.database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ReturnBooks extends JFrame {

    private int studentId;

    public ReturnBooks(int studentId) {
        this.studentId = studentId;

        setTitle("Return Books");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel("Enter Book ID to Return:");
        JTextField bookIdField = new JTextField(15);
        JButton returnButton = new JButton("Return");

        panel.add(label);
        panel.add(bookIdField);
        panel.add(returnButton);

        add(panel);

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookId = bookIdField.getText().trim();
                if (bookId.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a book ID.");
                } else {
                    returnBook(bookId);
                }
            }
        });
    }

    private void returnBook(String bookId) {
        try (Connection conn = DatabaseConnection.connect()) {
            int parsedBookId = Integer.parseInt(bookId);

            // Check if this book is borrowed by this student
            String checkQuery = "SELECT * FROM borrowed_books WHERE student_id = ? AND book_id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setInt(1, studentId);
            checkStmt.setInt(2, parsedBookId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // Delete from borrowed_books table
                String deleteQuery = "DELETE FROM borrowed_books WHERE student_id = ? AND book_id = ?";
                PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
                deleteStmt.setInt(1, studentId);
                deleteStmt.setInt(2, parsedBookId);
                deleteStmt.executeUpdate();

                // Update available copies in books table (books.id)
                String updateQuery = "UPDATE books SET available_copies = available_copies + 1 WHERE id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setInt(1, parsedBookId);
                updateStmt.executeUpdate();

                JOptionPane.showMessageDialog(null, "Book ID " + bookId + " returned successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "You have not borrowed this book.");
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Book ID must be a valid number.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error returning the book. Please try again.");
        }
    }

    // For testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ReturnBooks(1).setVisible(true));
    }
}

