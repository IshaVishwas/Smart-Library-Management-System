package com.library.librarian;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import com.library.database.DatabaseConnection;

public class ViewIssuedBooks extends JFrame {

    private JTextArea area;

    public ViewIssuedBooks() {
        setTitle("View Issued Books");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        area = new JTextArea();
        area.setEditable(false);
        add(new JScrollPane(area), BorderLayout.CENTER);

        // Load issued books from the database
        loadIssuedBooks();

        setVisible(true);
    }

    private void loadIssuedBooks() {
        try (Connection conn = DatabaseConnection.connect()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT b.title, b.author, u.name AS student_name, i.issue_date " +
                                             "FROM books b " +
                                             "JOIN issued_books i ON b.id = i.book_id " +
                                             "JOIN users u ON i.user_id = u.id");
            while (rs.next()) {
                area.append("Book: " + rs.getString("title") +
                            ", Author: " + rs.getString("author") +
                            ", Issued to: " + rs.getString("student_name") +
                            ", Issue Date: " + rs.getDate("issue_date") + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            area.setText("Error loading issued books.");
        }
    }
}
