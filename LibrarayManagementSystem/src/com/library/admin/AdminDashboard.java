// package com.library.admin;

// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;

// public class AdminDashboard extends JFrame {

//     public AdminDashboard() {
//         // Setting up the JFrame properties
//         setTitle("Admin Dashboard");
//         setSize(600, 400);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setLocationRelativeTo(null); // Center the window

//         // Panel to hold the components
//         JPanel panel = new JPanel();
//         panel.setLayout(new GridLayout(6, 1));  // Increased grid rows for the new buttons

//         // Welcome Label
//         JLabel welcomeLabel = new JLabel("Welcome to Admin Dashboard!");
//         panel.add(welcomeLabel);

//         // Button to manage librarians
//         JButton manageLibrariansButton = new JButton("Manage Librarians");
//         manageLibrariansButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 // Open Manage Librarians Window
//                 new ManageLibrarians();
//             }
//         });
//         panel.add(manageLibrariansButton);

//         // Button to view reports
//         JButton viewReportsButton = new JButton("View Reports");
//         viewReportsButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 // Open View Reports Window
//                 new ViewReports();
//             }
//         });
//         panel.add(viewReportsButton);

//         // Button to manage fines
//         JButton fineManagementButton = new JButton("Fine Management");
//         fineManagementButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 // Open Fine Management Window
//                 new FineManagement();
//             }
//         });
//         panel.add(fineManagementButton);

//         // Button to manage user accounts
//         JButton userAccountManagementButton = new JButton("User Account Management");
//         userAccountManagementButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 // Open User Account Management Window
//                 new UserAccountManagement();
//             }
//         });
//         panel.add(userAccountManagementButton);

//         // Logout Button
//         JButton logoutButton = new JButton("Logout");
//         logoutButton.addActionListener(new ActionListener() {
//             @Override
//             public void actionPerformed(ActionEvent e) {
//                 // Handle logout functionality
//                 int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?", "Logout", JOptionPane.YES_NO_OPTION);
//                 if (option == JOptionPane.YES_OPTION) {
//                     System.exit(0);  // Exit the program
//                 }
//             }
//         });
//         panel.add(logoutButton);

//         // Add the panel to the frame
//         add(panel);
//         setVisible(true);
//     }
// }

package com.library.admin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(600, 450);  // Slightly increased size to fit new button
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 1, 10, 10));  // 7 rows now

        JLabel welcomeLabel = new JLabel("Welcome to Admin Dashboard!", SwingConstants.CENTER);
        panel.add(welcomeLabel);

        JButton manageLibrariansButton = new JButton("Manage Librarians");
        manageLibrariansButton.addActionListener(e -> new ManageLibrarians());
        panel.add(manageLibrariansButton);

        JButton viewReportsButton = new JButton("View Reports");
        viewReportsButton.addActionListener(e -> new ViewReports());
        panel.add(viewReportsButton);

        JButton fineManagementButton = new JButton("Fine Management");
        fineManagementButton.addActionListener(e -> new FineManagement());
        panel.add(fineManagementButton);

        JButton userAccountManagementButton = new JButton("User Account Management");
        userAccountManagementButton.addActionListener(e -> new UserAccountManagement());
        panel.add(userAccountManagementButton);

        // ✅ New "Change Password" Button
        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.addActionListener(e -> new ChangePassword("admin")); // Assuming username is "admin"
        panel.add(changePasswordButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?", "Logout", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        panel.add(logoutButton);

        add(panel);
        setVisible(true);
    }
}

