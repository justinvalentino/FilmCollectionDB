package org.example.filmcollectiondb;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieRepository {

    public MovieRepository() {
        createTables();
    }

    // Movies & Genres
    private void createTables() {
        String sqlMovie = "CREATE TABLE IF NOT EXISTS movies (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT NOT NULL, " +
                "genre TEXT, " +
                "year INTEGER)";

        // Save List of Genres
        String sqlGenre = "CREATE TABLE IF NOT EXISTS genres (" +
                "name TEXT PRIMARY KEY)";

        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sqlMovie);
            stmt.execute(sqlGenre);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // MOVIE FEATURE
    public void addMovie(String title, String genre, int year) {
        String sql = "INSERT INTO movies(title, genre, year) VALUES(?,?,?)";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, genre);
            pstmt.setInt(3, year);
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void deleteMovie(int id) {
        String sql = "DELETE FROM movies WHERE id = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movies";
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                movies.add(new Movie(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("genre"),
                        rs.getInt("year")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return movies;
    }

    // GENRE FEATURE

    // Saves New Genre
    public void addGenre(String genreName) {
        String sql = "INSERT OR IGNORE INTO genres(name) VALUES(?)";
        // INSERT OR IGNORE
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, genreName);
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // Takes Every List of Genres to Dropdown
    public List<String> getAllGenres() {
        List<String> genres = new ArrayList<>();
        String sql = "SELECT name FROM genres ORDER BY name ASC";
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                genres.add(rs.getString("name"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return genres;
    }

    // UPDATE (EDIT DATA)
    public void updateMovie(int id, String title, String genre, int year) {
        String sql = "UPDATE movies SET title = ?, genre = ?, year = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, genre);
            pstmt.setInt(3, year);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
