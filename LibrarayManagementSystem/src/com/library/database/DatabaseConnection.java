package com.library.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class DatabaseConnection {
    private static final String CONFIG_PATH = "config/db_config.properties";

    public static Connection connect() {
        try {
            Properties props = new Properties();
            FileInputStream in = new FileInputStream(CONFIG_PATH);
            props.load(in);
            in.close();

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.username");
            String pass = props.getProperty("db.password");

            return DriverManager.getConnection(url, user, pass);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
