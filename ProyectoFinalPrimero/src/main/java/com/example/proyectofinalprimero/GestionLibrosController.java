package com.example.proyectofinalprimero;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.List;

public class GestionLibrosController {

    @FXML
    private TextField txtTitulo, txtAutor, txtGenero, txtISBN, txtPublicacion;
    @FXML
    private TableView<Libro> tablaLibros;
    @FXML
    private TableColumn<Libro, String> colTitulo, colAutor, colGenero;
    @FXML
    private TableColumn<Libro, Integer> colISBN, colAnoPublicacion;
    @FXML
    private Label txtOutput;

    private final ManejadorXML manejadorXML;

    public GestionLibrosController() {
        try {
            this.manejadorXML = new ManejadorXML();
        } catch (IOException e) {
            throw new RuntimeException("Error al inicializar el manejador XML: " + e.getMessage());
        }
    }

    @FXML
    public void onAgregar() {
        String titulo = txtTitulo.getText();
        String autor = txtAutor.getText();
        String genero = txtGenero.getText();
        String isbnStr = txtISBN.getText();
        String publicacionStr = txtPublicacion.getText();

        try {
            int isbn = Integer.parseInt(isbnStr);
            int publicacion = Integer.parseInt(publicacionStr);

            Libro nuevoLibro = new Libro(titulo, autor, genero, isbn, publicacion);
            manejadorXML.crearXML(nuevoLibro);
            mostrarMensaje("Libro agregado correctamente.");
            onListar();
            clearFields();
        } catch (NumberFormatException e) {
            mostrarMensaje("Error: ISBN y Publicación deben ser números.");
        }
    }

    @FXML
    public void onActualizar() {
        String isbnSeleccionado = txtISBN.getText();
        if (isbnSeleccionado.isEmpty()) {
            mostrarMensaje("Error: Debes proporcionar un ISBN para actualizar.");
            return;
        }

        int isbn = Integer.parseInt(isbnSeleccionado);
        Libro libroActualizado = new Libro(
                txtTitulo.getText(), txtAutor.getText(), txtGenero.getText(),
                isbn, Integer.parseInt(txtPublicacion.getText())
        );

        if (manejadorXML.actualizar(libroActualizado)) {
            mostrarMensaje("Libro actualizado correctamente.");
            onListar();
        } else {
            mostrarMensaje("No se encontró el libro con el ISBN especificado.");
        }

        clearFields();
    }

    @FXML
    public void onEliminar() {
        String isbnSeleccionado = txtISBN.getText();
        if (isbnSeleccionado.isEmpty()) {
            mostrarMensaje("Error: Debes proporcionar un ISBN para eliminar.");
            return;
        }

        int isbn = Integer.parseInt(isbnSeleccionado);
        if (manejadorXML.eliminar(isbn)) {
            mostrarMensaje("Libro eliminado correctamente.");
            onListar();
        } else {
            mostrarMensaje("No se encontró el libro con el ISBN especificado.");
        }

        clearFields();
    }

    @FXML
    public void onBuscar() {
        String criterio = txtTitulo.getText();
        if (criterio.isEmpty()) {
            mostrarMensaje("Error: Proporciona un criterio para buscar.");
            return;
        }

        List<Libro> libros = manejadorXML.buscarLibro("titulo", criterio);
        mostrarLibrosEnTabla(libros);
    }

    @FXML
    public void onListar() {
        List<Libro> libros = manejadorXML.listarCatalogo();
        mostrarLibrosEnTabla(libros);
    }

    private void mostrarLibrosEnTabla(List<Libro> libros) {
        tablaLibros.getItems().clear();
        if (libros.isEmpty()) {
            tablaLibros.getItems().add(new Libro("No hay libros", "", "", 0, 0));
        } else {
            tablaLibros.getItems().addAll(libros);
        }
        txtOutput.setText("Listado de libros");
    }

    private void mostrarMensaje(String mensaje) {
        txtOutput.setText(mensaje);
    }

    private void clearFields() {
        txtTitulo.clear();
        txtAutor.clear();
        txtGenero.clear();
        txtISBN.clear();
        txtPublicacion.clear();
    }

    @FXML
    public void onVolver() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("menu-principal.fxml"));
        Stage stage = (Stage) txtOutput.getScene().getWindow();
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    @FXML
    public void initialize() {
        colTitulo.setCellValueFactory(cellData -> cellData.getValue().tituloProperty());
        colAutor.setCellValueFactory(cellData -> cellData.getValue().autorProperty());
        colGenero.setCellValueFactory(cellData -> cellData.getValue().generoProperty());
        colISBN.setCellValueFactory(cellData -> cellData.getValue().isbnProperty().asObject());
        colAnoPublicacion.setCellValueFactory(cellData -> cellData.getValue().publicacionProperty().asObject());
    }
}
