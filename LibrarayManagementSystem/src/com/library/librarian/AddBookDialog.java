// 


package com.library.librarian;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import com.library.database.DatabaseConnection;

public class AddBookDialog extends JDialog {

    private JTextField titleField, authorField, categoryField, copiesField;
    private JButton addButton;

    public AddBookDialog(JFrame parent) {
        super(parent, "Add New Book", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(5, 2));

        add(new JLabel("Title:"));
        titleField = new JTextField();
        add(titleField);

        add(new JLabel("Author:"));
        authorField = new JTextField();
        add(authorField);

        add(new JLabel("Category:"));
        categoryField = new JTextField();
        add(categoryField);

        add(new JLabel("Available Copies:"));
        copiesField = new JTextField();
        add(copiesField);

        addButton = new JButton("Add Book");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });
        add(addButton);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void addBook() {
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String category = categoryField.getText().trim();
        String copiesStr = copiesField.getText().trim();

        if (title.isEmpty() || author.isEmpty() || category.isEmpty() || copiesStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled in.");
            return;
        }

        int availableCopies;
        try {
            availableCopies = Integer.parseInt(copiesStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Available copies must be a number.");
            return;
        }

        try (Connection conn = DatabaseConnection.connect()) {
            String query = "INSERT INTO books (title, author, category, available_copies) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, title);
            ps.setString(2, author);
            ps.setString(3, category);
            ps.setInt(4, availableCopies);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Book added successfully.");
                dispose();  // Close the dialog
                ((ManageBooks) getParent()).loadBooks();  // Reload the books in the main window
            } else {
                JOptionPane.showMessageDialog(this, "Error adding book.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding book.");
        }
    }
}
