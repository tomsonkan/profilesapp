package com.mycompany.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import com.mycompany.app.DBConnection;


public class App {
/*  private String url;
    private String user;
    private String password;
    
    public App(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }
*/
    public static void main(String[] args) {
//      App app = new App("jdbc:postgresql://localhost:5433/myjavaappdb", "postgres", "Lkan3288");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your name:");
        String name = scanner.nextLine();
        System.out.println("Enter your email:");
        String email = scanner.nextLine();

//      try (Connection conn = DriverManager.getConnection(app.url, app.user, app.password)) {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.println("Connected to PostgreSQL!");
    
            String sql = "INSERT INTO users(name, email) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, name);
                pstmt.setString(2, email);
                pstmt.executeUpdate();
                System.out.println("Data inserted." + pstmt.toString());    
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Backup to CSV?");
        String backup = scanner.nextLine();

        if(backup.equalsIgnoreCase("yes")) {
            String csvFilePath = "backup_users.csv";
            String sqlQuery = "SELECT * FROM users";
            fileBackup.backupToCSV(csvFilePath, sqlQuery);
        } else {
            System.out.println("Backup skipped.");
        }
    }
};
