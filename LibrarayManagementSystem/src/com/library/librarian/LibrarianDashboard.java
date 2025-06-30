// package com.library.librarian;

// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;

// public class LibrarianDashboard extends JFrame {

//     public LibrarianDashboard() {
//         // Setting up the JFrame properties
//         setTitle("Librarian Dashboard");
//         setSize(600, 400);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setLocationRelativeTo(null); // Center the window

//         // Panel to hold the components
//         JPanel panel = new JPanel();
//         panel.setLayout(new GridLayout(7, 1));  // Added row for the new buttons

//         // Welcome Label
//         JLabel welcomeLabel = new JLabel("Welcome to Librarian Dashboard!");
//         panel.add(welcomeLabel);

//         // Button to manage books
//         JButton manageBooksButton = new JButton("Manage Books");
//         manageBooksButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 new ManageBooks().setVisible(true);  // Open Manage Books window
//             }
//         });
//         panel.add(manageBooksButton);

//         // Button to issue books
//         JButton issueBooksButton = new JButton("Issue Books");
//         issueBooksButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 new IssueBooks().setVisible(true);  // Open Issue Books window
//             }
//         });
//         panel.add(issueBooksButton);

//         // Button to view issued books
//         JButton viewIssuedBooksButton = new JButton("View Issued Books");
//         viewIssuedBooksButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 new ViewIssuedBooks().setVisible(true);  // Open View Issued Books window
//             }
//         });
//         panel.add(viewIssuedBooksButton);

//         // Button to return books
//         JButton returnBooksButton = new JButton("Return Books");
//         returnBooksButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 new ReturnBooks().setVisible(true);  // Open Return Books window
//             }
//         });
//         panel.add(returnBooksButton);

//         // Button to view student records
//         JButton studentRecordsButton = new JButton("Student Records");
//         studentRecordsButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 new StudentRecords().setVisible(true);  // Open Student Records window
//             }
//         });
//         panel.add(studentRecordsButton);

//         // Button to view overdue notifications
//         JButton overdueNotificationsButton = new JButton("Overdue Notifications");
//         overdueNotificationsButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 new OverdueNotifications().setVisible(true);  // Open Overdue Notifications window
//             }
//         });
//         panel.add(overdueNotificationsButton);

//         // Logout Button
//         JButton logoutButton = new JButton("Logout");
//         logoutButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?", "Logout", JOptionPane.YES_NO_OPTION);
//                 if (option == JOptionPane.YES_OPTION) {
//                     System.exit(0);  // Exit the program
//                 }
//             }
//         });
//         panel.add(logoutButton);

//         // Add the panel to the frame
//         add(panel);
//     }

//     public static void main(String[] args) {
//         // Make sure the GUI is created in the Event Dispatch Thread
//         SwingUtilities.invokeLater(new Runnable() {
//             @Override
//             public void run() {
//                 new LibrarianDashboard().setVisible(true);
//             }
//         });
//     }
// }

package com.library.librarian;

import com.library.librarian.ChangePassword;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibrarianDashboard extends JFrame {

    private String username;

    public LibrarianDashboard(String username) {
        this.username = username;

        setTitle("Librarian Dashboard");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 1, 10, 10));  // 8 rows for 8 buttons

        JLabel welcomeLabel = new JLabel("Welcome to Librarian Dashboard: " + username, JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(welcomeLabel);

        // Manage Books
        JButton manageBooksButton = new JButton("Manage Books");
        manageBooksButton.addActionListener(e -> new ManageBooks().setVisible(true));
        panel.add(manageBooksButton);

        // Issue Books
        JButton issueBooksButton = new JButton("Issue Books");
        issueBooksButton.addActionListener(e -> new IssueBooks().setVisible(true));
        panel.add(issueBooksButton);

        // View Issued Books
        JButton viewIssuedBooksButton = new JButton("View Issued Books");
        viewIssuedBooksButton.addActionListener(e -> new ViewIssuedBooks().setVisible(true));
        panel.add(viewIssuedBooksButton);

        // Return Books
        JButton returnBooksButton = new JButton("Return Books");
        returnBooksButton.addActionListener(e -> new ReturnBooks().setVisible(true));
        panel.add(returnBooksButton);

        // Student Records
        JButton studentRecordsButton = new JButton("Student Records");
        studentRecordsButton.addActionListener(e -> new StudentRecords().setVisible(true));
        panel.add(studentRecordsButton);

        // Overdue Notifications
        JButton overdueNotificationsButton = new JButton("Overdue Notifications");
        overdueNotificationsButton.addActionListener(e -> new OverdueNotifications().setVisible(true));
        panel.add(overdueNotificationsButton);

        // Change Password
        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.addActionListener(e -> new ChangePassword(username).setVisible(true));
        panel.add(changePasswordButton);

        // Logout
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?", "Logout", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                dispose(); // Close the dashboard
            }
        });
        panel.add(logoutButton);

        add(panel);
    }

    public static void main(String[] args) {
        // Replace "librarian1" with actual logged-in username when integrating with login system
        SwingUtilities.invokeLater(() -> new LibrarianDashboard("librarian1").setVisible(true));
    }
}
