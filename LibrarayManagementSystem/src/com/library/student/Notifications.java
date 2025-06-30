package com.library.student;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.library.database.DatabaseConnection;

public class Notifications extends JFrame {

    private JTextArea notificationArea;
    private int studentId;

    public Notifications(int studentId) {
        this.studentId = studentId;

        setTitle("Student Notifications");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        notificationArea = new JTextArea();
        notificationArea.setEditable(false);
        add(new JScrollPane(notificationArea), BorderLayout.CENTER);

        loadNotifications();
    }

    private void loadNotifications() {
        try (Connection conn = DatabaseConnection.connect()) {
            boolean found = false;

            // Notifications for borrowed/issued books
            String query = "SELECT b.title, i.return_date, i.status " +
                           "FROM issued_books i JOIN books b ON i.book_id = b.id " +
                           "WHERE i.user_id = ?";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                found = true;
                String title = rs.getString("title");
                LocalDate returnDate = rs.getDate("return_date").toLocalDate();
                LocalDate today = LocalDate.now();
                String status = rs.getString("status");

                long daysLeft = ChronoUnit.DAYS.between(today, returnDate);
                long fine = 0;

                if (daysLeft < 0) {
                    fine = Math.abs(daysLeft) * 5; // ₹5 fine per day
                    notificationArea.append("⚠️ Overdue: \"" + title + "\" | Fine: ₹" + fine + " | Due: " + returnDate + "\n");
                } else {
                    notificationArea.append("📘 Reminder: \"" + title + "\" is due in " + daysLeft + " days (" + returnDate + ")\n");
                }
            }

            // Notifications for requested books
            String requestQuery = "SELECT title, status FROM book_requests WHERE student_id = ?";
            PreparedStatement reqStmt = conn.prepareStatement(requestQuery);
            reqStmt.setInt(1, studentId);
            ResultSet reqRs = reqStmt.executeQuery();

            while (reqRs.next()) {
                found = true;
                String reqTitle = reqRs.getString("title");
                String reqStatus = reqRs.getString("status");
                notificationArea.append("📥 Request: \"" + reqTitle + "\" | Status: " + reqStatus + "\n");
            }

            if (!found) {
                notificationArea.setText("No notifications at the moment.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            notificationArea.setText("❌ Error loading notifications.");
        }
    }

    // For testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Notifications(1).setVisible(true)); // Test with studentId = 1
    }
}
