// 

package com.library.librarian;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import com.library.database.DatabaseConnection;

public class ManageBooks extends JFrame {

    private JTextArea area;

    public ManageBooks() {
        setTitle("Manage Books");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        area = new JTextArea();
        area.setEditable(false);
        add(new JScrollPane(area), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Book");
        JButton removeButton = new JButton("Remove Book");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddBookDialog(ManageBooks.this).setVisible(true);
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeBook();
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load and display all books
        loadBooks();
    }

    void loadBooks() {
        try (Connection conn = DatabaseConnection.connect()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM books");
            area.setText("");  // Clear text area before loading new data
            while (rs.next()) {
                area.append("ID: " + rs.getInt("id") + ", Title: " + rs.getString("title") +
                            ", Author: " + rs.getString("author") + ", Category: " + rs.getString("category") +
                            ", Available Copies: " + rs.getInt("available_copies") + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            area.setText("Failed to load books.");
        }
    }

    private void removeBook() {
        String bookIdStr = JOptionPane.showInputDialog("Enter Book ID to remove:");
        if (bookIdStr != null && !bookIdStr.isEmpty()) {
            int bookId = Integer.parseInt(bookIdStr);
            try (Connection conn = DatabaseConnection.connect()) {
                // Remove book from database
                PreparedStatement ps = conn.prepareStatement("DELETE FROM books WHERE id = ?");
                ps.setInt(1, bookId);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Book removed successfully.");
                    loadBooks();  // Reload books after removal
                } else {
                    JOptionPane.showMessageDialog(this, "Book ID not found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error removing book.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ManageBooks().setVisible(true);
            }
        });
    }
}
