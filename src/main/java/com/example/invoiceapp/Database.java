package com.example.invoiceapp;
import java.sql.*;

public class Database {
    private final static String url = "jdbc:mysql://localhost:3306/INVOICE_MANAGEMENT";
    private final static String userName = "root";
    private final static String password = "Mysql@1234";
    
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load MySQL driver", e);
        }
    }
    
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
        	System.out.println("Database Connection Failure");
            throw new RuntimeException(e);
        }
    }
}
