// package com.library.admin;

// import javax.swing.*;
// import java.awt.*;
// import java.sql.*;
// import com.library.database.DatabaseConnection;

// public class FineManagement extends JFrame {

//     public FineManagement() {
//         setTitle("Fine Management");
//         setSize(600, 400);
//         setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//         setLocationRelativeTo(null);
//         setLayout(new BorderLayout());

//         JTextArea area = new JTextArea();
//         area.setEditable(false);
//         add(new JScrollPane(area), BorderLayout.CENTER);

//         try (Connection conn = DatabaseConnection.connect()) {
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery("SELECT * FROM books WHERE status = 'issued'");
//             while (rs.next()) {
//                 Date returnDate = rs.getDate("return_date");
//                 java.util.Date today = new java.util.Date();
//                 long overdue = today.getTime() - returnDate.getTime();

//                 double fine = 0;
//                 if (overdue > 0) {
//                     fine = (overdue / (1000 * 60 * 60 * 24)) * 2;
//                 }

//                 area.append("Book ID: " + rs.getInt("id") + ", Fine: $" + fine + "\n");
//             }
//         } catch (SQLException e) {
//             e.printStackTrace();
//             area.setText("Error loading fine data.");
//         }

//         setVisible(true);
//     }
// }


package com.library.admin;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import com.library.database.DatabaseConnection;

public class FineManagement extends JFrame {

    public FineManagement() {
        setTitle("Fine Management");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(area), BorderLayout.CENTER);

        try (Connection conn = DatabaseConnection.connect()) {
            Statement stmt = conn.createStatement();
            String query = "SELECT f.id, u.fullname, f.amount, f.paid, f.fine_date " +
                           "FROM fines f JOIN users u ON f.student_id = u.id " +
                           "WHERE u.role = 'Student'";
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String status = rs.getInt("paid") == 1 ? "Paid" : "Unpaid";
                area.append("Fine ID: " + rs.getInt("id") +
                            ", Student: " + rs.getString("fullname") +
                            ", Amount: ₹" + rs.getDouble("amount") +
                            ", Status: " + status +
                            ", Date: " + rs.getDate("fine_date") + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            area.setText("Error loading fine data.");
        }

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FineManagement::new);
    }
}
