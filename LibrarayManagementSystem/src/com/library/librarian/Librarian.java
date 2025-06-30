package com.library.librarian;

import com.library.database.DatabaseConnection;

import java.sql.*;

public class Librarian {

    // Manage Books - Add/Remove/View Books
    public static void manageBooks(String action, int bookId, String title, String author) {
        try (Connection conn = DatabaseConnection.connect()) {
            Statement stmt = conn.createStatement();
            if (action.equals("add")) {
                String query = "INSERT INTO books (title, author, status) VALUES ('" + title + "', '" + author + "', 'available')";
                stmt.executeUpdate(query);
            } else if (action.equals("remove")) {
                String query = "DELETE FROM books WHERE id = " + bookId;
                stmt.executeUpdate(query);
            } else if (action.equals("view")) {
                String query = "SELECT * FROM books WHERE id = " + bookId;
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    System.out.println("Book ID: " + rs.getInt("id"));
                    System.out.println("Title: " + rs.getString("title"));
                    System.out.println("Author: " + rs.getString("author"));
                    System.out.println("Status: " + rs.getString("status"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Issue Book
    public static void issueBook(int bookId, int userId, Date issueDate, Date returnDate) {
        try (Connection conn = DatabaseConnection.connect()) {
            String query = "INSERT INTO issued_books (book_id, user_id, issue_date, return_date, status) VALUES (?, ?, ?, ?, 'issued')";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, bookId);
            pstmt.setInt(2, userId);
            pstmt.setDate(3, issueDate);
            pstmt.setDate(4, returnDate);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Return Book
    public static void returnBook(int bookId, int userId) {
        try (Connection conn = DatabaseConnection.connect()) {
            String query = "UPDATE issued_books SET status = 'returned' WHERE book_id = ? AND user_id = ? AND status = 'issued'";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, bookId);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // View Issued Books
    public static void viewIssuedBooks(int userId) {
        try (Connection conn = DatabaseConnection.connect()) {
            String query = "SELECT b.title, b.author, ib.issue_date, ib.return_date " +
                           "FROM books b " +
                           "JOIN issued_books ib ON b.id = ib.book_id " +
                           "WHERE ib.user_id = ? AND ib.status = 'issued'";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println("Book: " + rs.getString("title") + " by " + rs.getString("author"));
                System.out.println("Issued on: " + rs.getDate("issue_date"));
                System.out.println("Return by: " + rs.getDate("return_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // View Student Records
    public static void viewStudentRecords() {
        try (Connection conn = DatabaseConnection.connect()) {
            String query = "SELECT id, name, username FROM users WHERE role = 'student'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println("Student ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") + ", Username: " + rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Overdue Notifications (Already implemented in OverdueNotifications.java)
}
