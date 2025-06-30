// package com.library.student;

// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.*;
// import java.sql.*;
// import com.library.database.DatabaseConnection;

// public class ChangePassword extends JFrame {

//     private String username;

//     public ChangePassword(String username) {
//         this.username = username;

//         setTitle("Change Password");
//         setSize(400, 250);
//         setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//         setLocationRelativeTo(null);
//         setLayout(new GridLayout(4, 2, 10, 10));

//         JLabel currentPassLabel = new JLabel("Current Password:");
//         JPasswordField currentPassField = new JPasswordField();

//         JLabel newPassLabel = new JLabel("New Password:");
//         JPasswordField newPassField = new JPasswordField();

//         JLabel confirmPassLabel = new JLabel("Confirm Password:");
//         JPasswordField confirmPassField = new JPasswordField();

//         JButton changeBtn = new JButton("Change Password");

//         add(currentPassLabel); add(currentPassField);
//         add(newPassLabel); add(newPassField);
//         add(confirmPassLabel); add(confirmPassField);
//         add(new JLabel()); add(changeBtn);

//         changeBtn.addActionListener(e -> {
//             String currentPass = String.valueOf(currentPassField.getPassword());
//             String newPass = String.valueOf(newPassField.getPassword());
//             String confirmPass = String.valueOf(confirmPassField.getPassword());

//             if (!newPass.equals(confirmPass)) {
//                 JOptionPane.showMessageDialog(this, "New passwords do not match.");
//                 return;
//             }

//             try (Connection conn = DatabaseConnection.connect()) {
//                 PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
//                 checkStmt.setString(1, username);
//                 checkStmt.setString(2, currentPass);
//                 ResultSet rs = checkStmt.executeQuery();

//                 if (!rs.next()) {
//                     JOptionPane.showMessageDialog(this, "Current password is incorrect.");
//                     return;
//                 }

//                 PreparedStatement updateStmt = conn.prepareStatement("UPDATE users SET password = ? WHERE username = ?");
//                 updateStmt.setString(1, newPass);
//                 updateStmt.setString(2, username);
//                 updateStmt.executeUpdate();

//                 JOptionPane.showMessageDialog(this, "Password changed successfully.");
//                 dispose();

//             } catch (SQLException ex) {
//                 ex.printStackTrace();
//                 JOptionPane.showMessageDialog(this, "Error updating password.");
//             }
//         });

//         setVisible(true);
//     }
// }

package com.library.student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import com.library.database.DatabaseConnection;

public class ChangePassword extends JFrame {

    private String username;

    public ChangePassword(String username) {
        this.username = username;

        setTitle("Change Password");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        JLabel currentPassLabel = new JLabel("Current Password:");
        JPasswordField currentPassField = new JPasswordField();

        JLabel newPassLabel = new JLabel("New Password:");
        JPasswordField newPassField = new JPasswordField();

        JLabel confirmPassLabel = new JLabel("Confirm Password:");
        JPasswordField confirmPassField = new JPasswordField();

        JButton changeBtn = new JButton("Change Password");

        add(currentPassLabel); add(currentPassField);
        add(newPassLabel); add(newPassField);
        add(confirmPassLabel); add(confirmPassField);
        add(new JLabel()); add(changeBtn);

        changeBtn.addActionListener(e -> {
            String currentPass = String.valueOf(currentPassField.getPassword());
            String newPass = String.valueOf(newPassField.getPassword());
            String confirmPass = String.valueOf(confirmPassField.getPassword());

            if (!newPass.equals(confirmPass)) {
                JOptionPane.showMessageDialog(this, "New passwords do not match.");
                return;
            }

            try (Connection conn = DatabaseConnection.connect()) {
                PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
                checkStmt.setString(1, username);
                checkStmt.setString(2, currentPass);
                ResultSet rs = checkStmt.executeQuery();

                if (!rs.next()) {
                    JOptionPane.showMessageDialog(this, "Current password is incorrect.");
                    return;
                }

                PreparedStatement updateStmt = conn.prepareStatement("UPDATE users SET password = ? WHERE username = ?");
                updateStmt.setString(1, newPass);
                updateStmt.setString(2, username);
                updateStmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Password changed successfully.");
                dispose();

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error updating password.");
            }
        });

        setVisible(true);
    }
}

