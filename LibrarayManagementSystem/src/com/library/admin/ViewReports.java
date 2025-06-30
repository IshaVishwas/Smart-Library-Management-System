// 

// 

package com.library.admin;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import com.library.database.DatabaseConnection;

public class ViewReports extends JFrame {

    private JTextArea reportArea;

    public ViewReports() {
        setTitle("Library Reports");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(reportArea), BorderLayout.CENTER);

        loadReports();
        setVisible(true);
    }

    private void loadReports() {
        try (Connection conn = DatabaseConnection.connect()) {
            Statement stmt = conn.createStatement();

            // === Available Books ===
            reportArea.append("=== Available Books ===\n");
            String availableBooksQuery = "SELECT * FROM books WHERE available_copies > 0";
            ResultSet availableBooks = stmt.executeQuery(availableBooksQuery);
            while (availableBooks.next()) {
                reportArea.append("ID: " + availableBooks.getInt("id") +
                        ", Title: " + availableBooks.getString("title") +
                        ", Author: " + availableBooks.getString("author") +
                        ", Category: " + availableBooks.getString("category") +
                        ", Available Copies: " + availableBooks.getInt("available_copies") + "\n");
            }

            // === Borrowed Books ===
            reportArea.append("\n=== Borrowed Books ===\n");
            String borrowedBooksQuery =
                    "SELECT b.id AS book_id, b.title, u.fullname AS student, bb.borrow_date, bb.return_date " +
                    "FROM borrowed_books bb " +
                    "JOIN books b ON bb.book_id = b.id " +
                    "JOIN users u ON bb.student_id = u.id " +
                    "WHERE u.role = 'Student'";
            ResultSet borrowedBooks = stmt.executeQuery(borrowedBooksQuery);
            while (borrowedBooks.next()) {
                reportArea.append("Book ID: " + borrowedBooks.getInt("book_id") +
                        ", Title: " + borrowedBooks.getString("title") +
                        ", Issued To: " + borrowedBooks.getString("student") +
                        ", Issue Date: " + borrowedBooks.getDate("borrow_date") +
                        ", Return Date: " + borrowedBooks.getDate("return_date") + "\n");
            }

            // === Fine Reports ===
            reportArea.append("\n=== Fine Reports ===\n");
            String fineReportsQuery =
                    "SELECT u.fullname, bb.book_id, DATEDIFF(CURDATE(), bb.return_date) AS overdue_days " +
                    "FROM borrowed_books bb " +
                    "JOIN users u ON bb.student_id = u.id " +
                    "WHERE u.role = 'Student' AND bb.return_date < CURDATE()";
            ResultSet fines = stmt.executeQuery(fineReportsQuery);
            while (fines.next()) {
                int daysLate = fines.getInt("overdue_days");
                double fineAmount = daysLate * 5.0; // Rs.5 per day
                reportArea.append("Student: " + fines.getString("fullname") +
                        ", Book ID: " + fines.getInt("book_id") +
                        ", Days Late: " + daysLate +
                        ", Fine: ₹" + fineAmount + "\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            reportArea.setText("Error loading reports: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ViewReports::new);
    }
}
