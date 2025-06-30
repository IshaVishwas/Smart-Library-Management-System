package com.library.student;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.library.database.DatabaseConnection;

public class ReissueBooks extends JFrame {

    private int studentId;

    public ReissueBooks(int studentId) {
        this.studentId = studentId;

        setTitle("Reissue Books");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter Book ID to Reissue:");
        JTextField bookIdField = new JTextField(15);
        JButton reissueButton = new JButton("Reissue");

        panel.add(label);
        panel.add(bookIdField);
        panel.add(reissueButton);

        add(panel);

        reissueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookIdText = bookIdField.getText();
                if (bookIdText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter a book ID.");
                    return;
                }

                try {
                    int bookId = Integer.parseInt(bookIdText);
                    reissueBook(bookId);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid book ID format.");
                }
            }
        });
    }

    private void reissueBook(int bookId) {
        try (Connection conn = DatabaseConnection.connect()) {
            // Check if the book is borrowed by the student
            String query = "SELECT due_date, reissue_count FROM issued_books WHERE user_id = ? AND book_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, studentId);
            stmt.setInt(2, bookId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Date dueDate = rs.getDate("due_date");
                int reissueCount = rs.getInt("reissue_count");

                if (dueDate.before(new Date())) {
                    JOptionPane.showMessageDialog(null, "Cannot reissue. The book is overdue.");
                    return;
                }

                if (reissueCount >= 1) {
                    JOptionPane.showMessageDialog(null, "You have already reissued this book once.");
                    return;
                }

                // Extend due date by 10 more days
                long newDueTime = dueDate.getTime() + (10L * 24 * 60 * 60 * 1000);
                java.sql.Date newDueDate = new java.sql.Date(newDueTime);

                String updateQuery = "UPDATE issued_books SET due_date = ?, reissue_count = reissue_count + 1 WHERE user_id = ? AND book_id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setDate(1, newDueDate);
                updateStmt.setInt(2, studentId);
                updateStmt.setInt(3, bookId);

                int rows = updateStmt.executeUpdate();
                if (rows > 0) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    JOptionPane.showMessageDialog(null, "Book reissued successfully! New due date: " + sdf.format(newDueDate));
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to reissue the book.");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Book not found or not borrowed by you.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error occurred.");
        }
    }

    // For testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ReissueBooks(1).setVisible(true)); // Use valid studentId
    }
}
