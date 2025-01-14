package com.example.proyectofinalprimero;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.List;

public class GestionLibrosControllerDos {

    @FXML
    private TextField txtTitulo, txtAutor, txtGenero, txtISBN, txtPublicacion;
    @FXML
    private TableView<Libro> tablaLibros;
    @FXML
    private TableColumn<Libro, String> colTitulo, colAutor, colGenero;
    @FXML
    private TableColumn<Libro, Long> colISBN;
    @FXML
    private TableColumn<Libro, Integer> colAnoPublicacion;
    @FXML
    private Label txtOutput;

    private final ManejadorXML manejadorXML;
    private final DatabaseManager databaseManager;

    // Constructor
    public GestionLibrosControllerDos() {
        try {
            this.manejadorXML = new ManejadorXML();
            this.databaseManager = new DatabaseManager();
        } catch (IOException e) {
            throw new RuntimeException("Error al inicializar el manejador XML: " + e.getMessage());
        }
    }

    // Método para agregar un libro
    @FXML
    public void onAgregar() {
        String titulo = txtTitulo.getText();
        String autor = txtAutor.getText();
        String genero = txtGenero.getText();
        String isbnStr = txtISBN.getText();
        String publicacionStr = txtPublicacion.getText();

        try {
            Long isbn = Long.parseLong(isbnStr);
            int publicacion = Integer.parseInt(publicacionStr);

            Libro nuevoLibro = new Libro(titulo, autor, genero, isbn, publicacion);
            manejadorXML.crearXML(nuevoLibro);
            databaseManager.insertarLibro(nuevoLibro);
            mostrarMensaje("Libro agregado correctamente.");
            onListar();
            clearFields();
        } catch (NumberFormatException e) {
            mostrarMensaje("Error: ISBN y Publicación deben ser números.");
        }
    }

    // Método para actualizar un libro
    @FXML
    public void onActualizar() {
        String isbnSeleccionado = txtISBN.getText();
        if (isbnSeleccionado.isEmpty()) {
            mostrarMensaje("Error: Debes proporcionar un ISBN para actualizar.");
            return;
        }

        try {
            Long isbn = Long.parseLong(isbnSeleccionado);
            Libro libroActualizado = new Libro(
                    txtTitulo.getText(), txtAutor.getText(), txtGenero.getText(),
                    isbn, Integer.parseInt(txtPublicacion.getText())
            );

            if (manejadorXML.actualizar(libroActualizado)) {
                databaseManager.actualizarLibro(libroActualizado);
                mostrarMensaje("Libro actualizado correctamente.");
                onListar();
            } else {
                mostrarMensaje("No se encontró el libro con el ISBN especificado.");
            }
        } catch (NumberFormatException e) {
            mostrarMensaje("Error: ISBN y Publicación deben ser números.");
        }
        clearFields();
    }

    // Método para eliminar un libro
    @FXML
    public void onEliminar() {
        String isbnSeleccionado = txtISBN.getText();
        if (isbnSeleccionado.isEmpty()) {
            mostrarMensaje("Error: Debes proporcionar un ISBN para eliminar.");
            return;
        }

        try {
            Long isbn = Long.parseLong(isbnSeleccionado);
            boolean eliminado = manejadorXML.eliminar(isbn);
            if (eliminado) {
                databaseManager.eliminarLibro(isbn);
                mostrarMensaje("Libro eliminado correctamente.");
                onListar();
            } else {
                mostrarMensaje("No se encontró el libro con el ISBN especificado.");
            }
        } catch (NumberFormatException e) {
            mostrarMensaje("Error: ISBN debe ser un número.");
        }
        clearFields();
    }

    // Método para buscar libros
    @FXML
    public void onBuscar() {
        String criterio = txtTitulo.getText();
        if (criterio.isEmpty()) {
            mostrarMensaje("Error: Debe introducir un Título.");
            return;
        }

        List<Libro> libros = databaseManager.buscarLibrosPorPatron(criterio);
        mostrarLibrosEnTabla(libros);
    }

    // Método para listar todos los libros
    @FXML
    public void onListar() {
        List<Libro> libros = databaseManager.listarLibros();
        if (libros == null || libros.isEmpty()) {
            mostrarMensaje("No hay libros en la base de datos.");
        } else {
            mostrarLibrosEnTabla(libros);
        }
    }

    // Método para mostrar los libros en la tabla
    private void mostrarLibrosEnTabla(List<Libro> libros) {
        tablaLibros.getItems().clear();
        if (libros.isEmpty()) {
            tablaLibros.getItems().add(new Libro("No hay libros", "", "", 0L, 0));
        } else {
            tablaLibros.getItems().addAll(libros);
        }
        txtOutput.setText("Listado de libros");
    }

    // Método para mostrar un mensaje en la interfaz
    private void mostrarMensaje(String mensaje) {
        txtOutput.setText(mensaje);
        txtOutput.setVisible(true);
    }

    // Método para limpiar los campos de texto
    private void clearFields() {
        txtTitulo.clear();
        txtAutor.clear();
        txtGenero.clear();
        txtISBN.clear();
        txtPublicacion.clear();
        txtOutput.setVisible(false);
    }

    // Método para volver a la pantalla principal
    @FXML
    public void onVolver() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("menu-principal.fxml"));
        Stage stage = (Stage) txtOutput.getScene().getWindow();
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    // Método de inicialización para las columnas de la tabla
    @FXML
    public void initialize() {
        colTitulo.setCellValueFactory(cellData -> cellData.getValue().tituloProperty());
        colAutor.setCellValueFactory(cellData -> cellData.getValue().autorProperty());
        colGenero.setCellValueFactory(cellData -> cellData.getValue().generoProperty());
        colISBN.setCellValueFactory(cellData -> cellData.getValue().isbnProperty().asObject());
        colAnoPublicacion.setCellValueFactory(cellData -> cellData.getValue().publicacionProperty().asObject());
    }
}
