package com.mycompany.app;

import java.sql.Statement;
//import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
//import java.sql.DriverManager;
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.mycompany.app.DBConnection;

//import javax.naming.spi.DirStateFactory.Result;

public class fileBackup {
  public static void backupToCSV(String csvFilePath, String sqlQuery) {
    try (
        Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery(sqlQuery);
        FileWriter csvWriter = new FileWriter(csvFilePath);) {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
          csvWriter.append(metaData.getColumnName(i));
          if (i < columnCount) {
            csvWriter.append(",");
          }
          csvWriter.append("\n");
      }
      while (resultSet.next()) {
        for (int i = 1; i <= columnCount; i++) {
          csvWriter.append(resultSet.getString(i));
          if (i < columnCount)
            csvWriter.append(",");
        }
        csvWriter.append("\n");
      }
      System.out.println("CSV file created successfully at " + csvFilePath);
    } catch (SQLException e) {
        System.out.println("Database error: " + e.getMessage());
        e.printStackTrace();
    } catch (IOException e) {
        System.out.println("Error writing to CSV file: " + e.getMessage());
    }
  }
}