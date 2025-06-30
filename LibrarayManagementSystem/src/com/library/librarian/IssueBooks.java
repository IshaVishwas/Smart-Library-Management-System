// 

package com.library.librarian;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import com.library.database.DatabaseConnection;

public class IssueBooks extends JFrame {

    private JTextField bookIdField;
    private JTextField studentIdField;

    public IssueBooks() {
        setTitle("Issue Books");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        panel.add(new JLabel("Book ID:"));
        bookIdField = new JTextField();
        panel.add(bookIdField);

        panel.add(new JLabel("Student ID:"));
        studentIdField = new JTextField();
        panel.add(studentIdField);

        JButton issueButton = new JButton("Issue Book");
        issueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                issueBook();
            }
        });
        panel.add(issueButton);

        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void issueBook() {
        String bookIdStr = bookIdField.getText().trim();
        String studentIdStr = studentIdField.getText().trim();

        if (bookIdStr.isEmpty() || studentIdStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in both Book ID and Student ID.");
            return;
        }

        int bookId = Integer.parseInt(bookIdStr);
        int studentId = Integer.parseInt(studentIdStr);

        try (Connection conn = DatabaseConnection.connect()) {
            // Check if the book exists and is available for issue
            PreparedStatement ps = conn.prepareStatement("SELECT available_copies, status FROM books WHERE id = ?");
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int availableCopies = rs.getInt("available_copies");
                String status = rs.getString("status");

                if (availableCopies <= 0) {
                    JOptionPane.showMessageDialog(this, "Sorry, this book is not available.");
                    return;
                }

                if ("issued".equals(status)) {
                    JOptionPane.showMessageDialog(this, "This book has already been issued.");
                    return;
                }

                // Issue the book by updating the status and decreasing the available copies
                ps = conn.prepareStatement("UPDATE books SET status = 'issued', available_copies = available_copies - 1 WHERE id = ?");
                ps.setInt(1, bookId);
                ps.executeUpdate();

                // Insert into issued_books table
                ps = conn.prepareStatement("INSERT INTO issued_books (book_id, user_id, issue_date) VALUES (?, ?, NOW())");
                ps.setInt(1, bookId);
                ps.setInt(2, studentId);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(this, "Book issued successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Book ID not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error issuing book.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new IssueBooks().setVisible(true);
            }
        });
    }
}
