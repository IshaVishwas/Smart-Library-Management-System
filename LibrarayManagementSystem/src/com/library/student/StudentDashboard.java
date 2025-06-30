// // package com.library.student;

// // import javax.swing.*;
// // import java.awt.*;
// // import java.awt.event.ActionEvent;
// // import java.awt.event.ActionListener;

// // public class StudentDashboard extends JFrame {

// //     private int studentId;

// //     public StudentDashboard(int studentId) {
// //         this.studentId = studentId;
// //         setTitle("Student Dashboard");
// //         setSize(600, 450);
// //         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
// //         setLocationRelativeTo(null);

// //         JPanel panel = new JPanel();
// //         panel.setLayout(new GridLayout(8, 1, 10, 10));
// //         panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

// //         JLabel welcomeLabel = new JLabel("Welcome to Student Dashboard! (ID: " + studentId + ")", JLabel.CENTER);
// //         welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
// //         panel.add(welcomeLabel);

// //         // Borrow Books
// //         JButton borrowBooksButton = new JButton("Borrow Books");
// //         borrowBooksButton.addActionListener(e -> new BorrowBooks(studentId).setVisible(true));
// //         panel.add(borrowBooksButton);

// //         // Return Books
// //         JButton returnBooksButton = new JButton("Return Books");
// //         returnBooksButton.addActionListener(e -> new ReturnBooks(studentId).setVisible(true));
// //         panel.add(returnBooksButton);

// //         // View Status
// //         JButton viewStatusButton = new JButton("View Status");
// //         viewStatusButton.addActionListener(e -> new ViewStatus(studentId).setVisible(true));
// //         panel.add(viewStatusButton);

// //         // Request New Books
// //         JButton requestBooksButton = new JButton("Request New Books");
// //         requestBooksButton.addActionListener(e -> new RequestBooks(studentId).setVisible(true));
// //         panel.add(requestBooksButton);

// //         // Reissue Books
// //         JButton reissueBooksButton = new JButton("Reissue Books");
// //         reissueBooksButton.addActionListener(e -> new ReissueBooks(studentId).setVisible(true));
// //         panel.add(reissueBooksButton);

// //         // Notifications
// //         JButton notificationsButton = new JButton("Notifications");
// //         notificationsButton.addActionListener(e -> new Notifications(studentId).setVisible(true));
// //         panel.add(notificationsButton);

// //         // Logout
// //         JButton logoutButton = new JButton("Logout");
// //         logoutButton.addActionListener(e -> {
// //             int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?", "Logout", JOptionPane.YES_NO_OPTION);
// //             if (confirm == JOptionPane.YES_OPTION) {
// //                 dispose(); // Close dashboard
// //                 // Optionally go back to login screen (if needed)
// //             }
// //         });
// //         panel.add(logoutButton);

// //         add(panel);
// //     }

// //     public static void main(String[] args) {
// //         new StudentDashboard(1).setVisible(true);  // Example with studentId = 1
// //     }
// // }

// package com.library.student;

// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;

// public class StudentDashboard extends JFrame {

//     private int studentId;

//     public StudentDashboard(int studentId) {
//         this.studentId = studentId;
//         setTitle("Student Dashboard");
//         setSize(600, 500);  // Adjusted size to fit the new button
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setLocationRelativeTo(null);

//         JPanel panel = new JPanel();
//         panel.setLayout(new GridLayout(9, 1, 10, 10));  // Added one more row for Change Password button
//         panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

//         JLabel welcomeLabel = new JLabel("Welcome to Student Dashboard! (ID: " + studentId + ")", JLabel.CENTER);
//         welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
//         panel.add(welcomeLabel);

//         // Borrow Books
//         JButton borrowBooksButton = new JButton("Borrow Books");
//         borrowBooksButton.addActionListener(e -> new BorrowBooks(studentId).setVisible(true));
//         panel.add(borrowBooksButton);

//         // Return Books
//         JButton returnBooksButton = new JButton("Return Books");
//         returnBooksButton.addActionListener(e -> new ReturnBooks(studentId).setVisible(true));
//         panel.add(returnBooksButton);

//         // View Status
//         JButton viewStatusButton = new JButton("View Status");
//         viewStatusButton.addActionListener(e -> new ViewStatus(studentId).setVisible(true));
//         panel.add(viewStatusButton);

//         // Request New Books
//         JButton requestBooksButton = new JButton("Request New Books");
//         requestBooksButton.addActionListener(e -> new RequestBooks(studentId).setVisible(true));
//         panel.add(requestBooksButton);

//         // Reissue Books
//         JButton reissueBooksButton = new JButton("Reissue Books");
//         reissueBooksButton.addActionListener(e -> new ReissueBooks(studentId).setVisible(true));
//         panel.add(reissueBooksButton);

//         // Notifications
//         JButton notificationsButton = new JButton("Notifications");
//         notificationsButton.addActionListener(e -> new Notifications(studentId).setVisible(true));
//         panel.add(notificationsButton);

//         // Change Password
//         JButton changePasswordButton = new JButton("Change Password");
//         changePasswordButton.addActionListener(e -> new ChangePassword(studentId).setVisible(true));  // Add Change Password functionality
//         panel.add(changePasswordButton);

//         // Logout
//         JButton logoutButton = new JButton("Logout");
//         logoutButton.addActionListener(e -> {
//             int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?", "Logout", JOptionPane.YES_NO_OPTION);
//             if (confirm == JOptionPane.YES_OPTION) {
//                 dispose(); // Close dashboard
//                 // Optionally go back to login screen (if needed)
//             }
//         });
//         panel.add(logoutButton);

//         add(panel);
//     }

//     public static void main(String[] args) {
//         new StudentDashboard(1).setVisible(true);  // Example with studentId = 1
//     }
// }

package com.library.student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import com.library.database.DatabaseConnection;

public class StudentDashboard extends JFrame {

    private int studentId;

    public StudentDashboard(int studentId) {
        this.studentId = studentId;
        setTitle("Student Dashboard");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 1, 10, 10));  // Adjusted to accommodate the new Change Password button
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JLabel welcomeLabel = new JLabel("Welcome to Student Dashboard! (ID: " + studentId + ")", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(welcomeLabel);

        // Borrow Books
        JButton borrowBooksButton = new JButton("Borrow Books");
        borrowBooksButton.addActionListener(e -> new BorrowBooks(studentId).setVisible(true));
        panel.add(borrowBooksButton);

        // Return Books
        JButton returnBooksButton = new JButton("Return Books");
        returnBooksButton.addActionListener(e -> new ReturnBooks(studentId).setVisible(true));
        panel.add(returnBooksButton);

        // View Status
        JButton viewStatusButton = new JButton("View Status");
        viewStatusButton.addActionListener(e -> new ViewStatus(studentId).setVisible(true));
        panel.add(viewStatusButton);

        // Request New Books
        JButton requestBooksButton = new JButton("Request New Books");
        requestBooksButton.addActionListener(e -> new RequestBooks(studentId).setVisible(true));
        panel.add(requestBooksButton);

        // Reissue Books
        JButton reissueBooksButton = new JButton("Reissue Books");
        reissueBooksButton.addActionListener(e -> new ReissueBooks(studentId).setVisible(true));
        panel.add(reissueBooksButton);

        // Notifications
        JButton notificationsButton = new JButton("Notifications");
        notificationsButton.addActionListener(e -> new Notifications(studentId).setVisible(true));
        panel.add(notificationsButton);

        // Change Password
        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.addActionListener(e -> {
            String username = getUsernameForStudent(studentId); // Fetch the username dynamically
            new ChangePassword(username).setVisible(true);
        });
        panel.add(changePasswordButton);

        // Logout
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?", "Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose(); // Close dashboard
                // Optionally go back to login screen (if needed)
            }
        });
        panel.add(logoutButton);

        add(panel);
    }

    private String getUsernameForStudent(int studentId) {
        String username = null;
        try (Connection conn = DatabaseConnection.connect()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT username FROM users WHERE student_id = ?");
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                username = rs.getString("username");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return username;
    }

    public static void main(String[] args) {
        new StudentDashboard(1).setVisible(true);  // Example with studentId = 1, you can pass any studentId
    }
}

