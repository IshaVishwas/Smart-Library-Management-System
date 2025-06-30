// 


package com.library.librarian;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import com.library.database.DatabaseConnection;

public class StudentRecords extends JFrame {

    private JTextArea area;
    private JTextField searchField;
    private JButton searchButton;

    public StudentRecords() {
        setTitle("Student Records");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchField = new JTextField(20);
        searchButton = new JButton("Search");

        searchButton.addActionListener(e -> searchStudentRecords());

        searchPanel.add(new JLabel("Search (Name/ID):"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH);

        // Text area to show results
        area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(area), BorderLayout.CENTER);

        loadStudentRecords();
        setVisible(true);
    }

    private void loadStudentRecords() {
        try (Connection conn = DatabaseConnection.connect()) {
            Statement stmt = conn.createStatement();
            // Case-insensitive check using LOWER()
            ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE LOWER(role) = 'student'");

            area.setText(""); // Clear previous data
            boolean found = false;
            while (rs.next()) {
                found = true;
                area.append(formatStudentRecord(rs));
            }
            if (!found) {
                area.setText("No student records found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            area.setText("Error loading student records.");
        }
    }

    private void searchStudentRecords() {
        String query = searchField.getText().trim();
        if (query.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Name or ID.");
            return;
        }

        try (Connection conn = DatabaseConnection.connect()) {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM users WHERE LOWER(role) = 'student' AND (id = ? OR fullname LIKE ?)"
            );
            ps.setString(1, query);
            ps.setString(2, "%" + query + "%");

            ResultSet rs = ps.executeQuery();
            area.setText("");

            boolean found = false;
            while (rs.next()) {
                found = true;
                area.append(formatStudentRecord(rs));
            }

            if (!found) {
                area.setText("No matching student records found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            area.setText("Error searching student records.");
        }
    }

    private String formatStudentRecord(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String fullname = rs.getString("fullname");
        String username = rs.getString("username");
        String email = rs.getString("email");
        String phone = rs.getString("phone_number");

        return String.format("ID: %d | Name: %s | Username: %s | Email: %s | Phone: %s\n",
                             id, fullname, username, email, phone);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentRecords::new);
    }
}
