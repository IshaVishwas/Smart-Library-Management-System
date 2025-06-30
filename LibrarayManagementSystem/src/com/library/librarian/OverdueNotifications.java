package com.library.librarian;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Date;
import com.library.database.DatabaseConnection;

public class OverdueNotifications extends JFrame {

    private JTextArea area;

    public OverdueNotifications() {
        setTitle("Overdue Notifications");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        area = new JTextArea();
        area.setEditable(false);
        add(new JScrollPane(area), BorderLayout.CENTER);

        // Load overdue notifications from the database
        loadOverdueNotifications();

        setVisible(true);
    }

    private void loadOverdueNotifications() {
        try (Connection conn = DatabaseConnection.connect()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT b.title, u.name AS student_name, i.return_date " +
                                             "FROM books b " +
                                             "JOIN issued_books i ON b.id = i.book_id " +
                                             "JOIN users u ON i.user_id = u.id " +
                                             "WHERE i.return_date < CURDATE() AND i.status = 'issued'");

            while (rs.next()) {
                java.sql.Date returnDate = rs.getDate("return_date"); // Return date from database
                long diff = new Date().getTime() - returnDate.getTime(); // Difference between current date and return date
                long overdueDays = diff / (1000 * 60 * 60 * 24); // Convert difference from milliseconds to days

                area.append("Book: " + rs.getString("title") +
                            ", Issued to: " + rs.getString("student_name") +
                            ", Overdue by: " + overdueDays + " days\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            area.setText("Error loading overdue notifications.");
        }
    }
}
