// package com.library.student;

// import com.library.database.DatabaseConnection;

// import javax.swing.*;
// import java.awt.*;
// import java.sql.*;

// public class ViewStatus extends JFrame {

//     private int studentId;  // Store the student ID

//     public ViewStatus(int studentId) {
//         this.studentId = studentId;

//         setTitle("View Borrowed Books");
//         setSize(400, 300);
//         setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//         setLocationRelativeTo(null);

//         JPanel panel = new JPanel();
//         panel.setLayout(new BorderLayout());

//         JButton viewButton = new JButton("View Borrowed Books");
//         JTextArea textArea = new JTextArea();
//         textArea.setEditable(false);

//         panel.add(viewButton, BorderLayout.NORTH);
//         panel.add(new JScrollPane(textArea), BorderLayout.CENTER);

//         add(panel);

//         viewButton.addActionListener(e -> {
//             // Fetch borrowed books for the student
//             fetchBorrowedBooks(textArea);
//         });
//     }

//     private void fetchBorrowedBooks(JTextArea textArea) {
//         try (Connection conn = DatabaseConnection.connect()) {
//             // SQL query to fetch borrowed books for the student
//             String query = "SELECT b.title, bb.borrow_date, bb.due_date " +
//                            "FROM borrowed_books bb " +
//                            "JOIN books b ON bb.book_id = b.book_id " +
//                            "WHERE bb.student_id = ?";

//             PreparedStatement stmt = conn.prepareStatement(query);
//             stmt.setInt(1, studentId);
//             ResultSet rs = stmt.executeQuery();

//             // Clear the text area before displaying new data
//             textArea.setText("");

//             boolean found = false;
//             while (rs.next()) {
//                 found = true;
//                 String title = rs.getString("title");
//                 Date borrowDate = rs.getDate("borrow_date");
//                 Date dueDate = rs.getDate("due_date");

//                 // Format the borrowed books information
//                 textArea.append("Title: " + title + "\n");
//                 textArea.append("Borrowed on: " + borrowDate + "\n");
//                 textArea.append("Due on: " + dueDate + "\n\n");
//             }

//             if (!found) {
//                 textArea.append("No borrowed books found.");
//             }

//         } catch (SQLException e) {
//             e.printStackTrace();
//             textArea.setText("Error fetching borrowed books.");
//         }
//     }

//     // For testing
//     public static void main(String[] args) {
//         SwingUtilities.invokeLater(() -> new ViewStatus(1).setVisible(true));  // Use a valid studentId for testing
//     }
// }


package com.library.student;

import com.library.database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ViewStatus extends JFrame {

    private int studentId;

    public ViewStatus(int studentId) {
        this.studentId = studentId;

        setTitle("View Borrowed Books");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JButton viewButton = new JButton("View Borrowed Books");
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        panel.add(viewButton, BorderLayout.NORTH);
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        add(panel);

        viewButton.addActionListener(e -> fetchBorrowedBooks(textArea));
    }

    private void fetchBorrowedBooks(JTextArea textArea) {
        try (Connection conn = DatabaseConnection.connect()) {
            String query = "SELECT b.title, bb.borrow_date " +
                           "FROM borrowed_books bb " +
                           "JOIN books b ON bb.book_id = b.id " +
                           "WHERE bb.student_id = ?";

            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            textArea.setText(""); // Clear previous output

            boolean found = false;
            while (rs.next()) {
                found = true;
                String title = rs.getString("title");
                Date borrowDate = rs.getDate("borrow_date");

                textArea.append("Title       : " + title + "\n");
                textArea.append("Borrowed On : " + borrowDate + "\n");
                textArea.append("-------------------------------\n");
            }

            if (!found) {
                textArea.setText("No borrowed books found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            textArea.setText("Error fetching borrowed books: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ViewStatus(1).setVisible(true));
    }
}
