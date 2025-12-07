package org.example.filmcollectiondb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:my_films.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            // Membuat koneksi ke SQLite
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("Koneksi Gagal: " + e.getMessage());
        }
        return conn;
    }
}