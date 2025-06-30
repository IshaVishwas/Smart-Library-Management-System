package com.library.librarian;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Date;
import com.library.database.DatabaseConnection;

public class ReturnBooks extends JFrame {

    private JTextField bookIdField;
    private JTextField studentIdField;
    private JTextField fineField;

    public ReturnBooks() {
        setTitle("Return Books");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        panel.add(new JLabel("Book ID:"));
        bookIdField = new JTextField();
        panel.add(bookIdField);

        panel.add(new JLabel("Student ID:"));
        studentIdField = new JTextField();
        panel.add(studentIdField);

        panel.add(new JLabel("Fine:"));
        fineField = new JTextField();
        fineField.setEditable(false);
        panel.add(fineField);

        JButton returnButton = new JButton("Return Book");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnBook();
            }
        });
        panel.add(returnButton);

        add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void returnBook() {
        int bookId = Integer.parseInt(bookIdField.getText());
        int studentId = Integer.parseInt(studentIdField.getText());

        try (Connection conn = DatabaseConnection.connect()) {
            // Get the return date of the issued book
            PreparedStatement ps = conn.prepareStatement("SELECT return_date FROM issued_books WHERE book_id = ? AND user_id = ?");
            ps.setInt(1, bookId);
            ps.setInt(2, studentId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Date returnDate = rs.getDate("return_date");
                long diff = new Date().getTime() - returnDate.getTime();
                long overdueDays = diff / (1000 * 60 * 60 * 24);

                if (overdueDays > 0) {
                    fineField.setText("$" + (overdueDays * 0.5));  // Fine calculation ($0.5 per day)
                } else {
                    fineField.setText("$0");
                }

                // Update book status to 'available'
                ps = conn.prepareStatement("UPDATE books SET status = 'available' WHERE id = ?");
                ps.setInt(1, bookId);
                ps.executeUpdate();

                // Remove entry from issued_books table
                ps = conn.prepareStatement("DELETE FROM issued_books WHERE book_id = ? AND user_id = ?");
                ps.setInt(1, bookId);
                ps.setInt(2, studentId);
                ps.executeUpdate();

                JOptionPane.showMessageDialog(this, "Book returned successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "No issued book found for this ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error returning book.");
        }
    }
}
