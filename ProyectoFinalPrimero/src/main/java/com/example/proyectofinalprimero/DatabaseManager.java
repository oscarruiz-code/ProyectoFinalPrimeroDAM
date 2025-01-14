package com.example.proyectofinalprimero;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {

    private static final String URL = "jdbc:mysql://localhost:3306/biblioteca";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public DatabaseManager() {
        createTable();
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS libros (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "titulo VARCHAR(255)," +
                "autor VARCHAR(255)," +
                "genero VARCHAR(255)," +
                "isbn BIGINT," +
                "publicacion INT)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertarLibro(Libro libro) {
        String sql = "INSERT INTO libros(titulo, autor, genero, isbn, publicacion) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, libro.getTitulo());
            pstmt.setString(2, libro.getAutor());
            pstmt.setString(3, libro.getGenero());
            pstmt.setLong(4, libro.getIsbn());
            pstmt.setInt(5, libro.getPublicacion());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Libro> listarLibros() {
        ArrayList<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libros";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Libro libro = new Libro(
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getString("genero"),
                        rs.getLong("isbn"),
                        rs.getInt("publicacion"));
                libros.add(libro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libros;
    }

    public void actualizarLibro(Libro libro) {
        String sql = "UPDATE libros SET titulo = ?, autor = ?, genero = ?, publicacion = ? WHERE isbn = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, libro.getTitulo());
            pstmt.setString(2, libro.getAutor());
            pstmt.setString(3, libro.getGenero());
            pstmt.setInt(4, libro.getPublicacion());
            pstmt.setLong(5, libro.getIsbn());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarLibro(long isbn) {
        String sql = "DELETE FROM libros WHERE isbn = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, isbn);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Libro> buscarLibrosPorPatron(String patron) {
        ArrayList<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM libros WHERE titulo REGEXP ? OR autor REGEXP ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, patron);
            pstmt.setString(2, patron);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Libro libro = new Libro(
                            rs.getString("titulo"),
                            rs.getString("autor"),
                            rs.getString("genero"),
                            rs.getLong("isbn"),
                            rs.getInt("publicacion"));
                    libros.add(libro);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return libros;
    }
}
