package com.library.admin;

import javax.swing.*;

public class Admin {

    public static void main(String[] args) {
        // Ensure the Admin Dashboard is displayed on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AdminDashboard();  // Launch Admin Dashboard
            }
        });
    }
}
