package org.example.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionTest {
    private static final String URL = "jdbc:mysql://localhost:3307/studentDB";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            if (conn != null) {
                System.out.println("Database Connection Successful!");
                conn.close();
            }
        } catch (SQLException e) {
            System.err.println("Database Connection Failed!");
            e.printStackTrace();
        }
    }
}

