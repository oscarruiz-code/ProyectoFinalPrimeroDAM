package com.example.proyectofinalprimero.controller;

import com.example.proyectofinalprimero.model.entity.Libro;
import com.example.proyectofinalprimero.model.repository.xml.ManejadorXML;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Controlador para la gestión de libros en la aplicación.
 * Proporciona funcionalidades para agregar, actualizar, eliminar, buscar y listar libros.
 *
 * @version 1.0
 * @autor oscarruiz-code
 */
public class GestionLibrosController {

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

    /**
     * Constructor de la clase GestionLibrosController.
     * Inicializa una nueva instancia de ManejadorXML.
     */
    public GestionLibrosController() {
        try {
            this.manejadorXML = new ManejadorXML();
        } catch (IOException e) {
            throw new RuntimeException("Error al inicializar el manejador XML: " + e.getMessage());
        }
    }

    /**
     * Método para agregar un libro.
     * Toma los datos de los campos de texto y crea un nuevo libro en el manejador XML.
     */
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
            mostrarMensaje("Libro agregado correctamente.");
            onListar();  // Actualiza la tabla después de agregar
            clearFields();  // Limpia los campos
        } catch (NumberFormatException e) {
            mostrarMensaje("Error: ISBN y Publicación deben ser números.");
        }
    }

    /**
     * Método para actualizar un libro.
     * Toma los datos de los campos de texto y actualiza el libro correspondiente en el manejador XML.
     */
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
                mostrarMensaje("Libro actualizado correctamente.");
                onListar();  // Actualiza la tabla después de actualizar
            } else {
                mostrarMensaje("No se encontró el libro con el ISBN especificado.");
            }
        } catch (NumberFormatException e) {
            mostrarMensaje("Error: ISBN y Publicación deben ser números.");
        }
        clearFields();  // Limpia los campos
    }

    /**
     * Método para eliminar un libro.
     * Toma el ISBN del campo de texto y elimina el libro correspondiente en el manejador XML.
     */
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
                mostrarMensaje("Libro eliminado correctamente.");
                onListar();  // Actualiza la tabla después de eliminar
            } else {
                mostrarMensaje("No se encontró el libro con el ISBN especificado.");
            }
        } catch (NumberFormatException e) {
            mostrarMensaje("Error: ISBN debe ser un número.");
        }
        clearFields();  // Limpia los campos
    }

    /**
     * Método para buscar libros por título.
     * Toma el título del campo de texto y busca los libros correspondientes en el manejador XML.
     */
    @FXML
    public void onBuscar() {
        String criterio = txtTitulo.getText();  // Tomamos el texto del campo de búsqueda
        if (criterio.isEmpty()) {
            mostrarMensaje("Error: Debe introducir un Título, Autor o Género.");
            return;  // Si el campo está vacío, salimos de la función
        }

        // Realizamos la búsqueda de libros según el criterio y valor introducido
        List<Libro> libros = manejadorXML.buscarLibro("titulo", criterio);
        mostrarLibrosEnTabla(libros);  // Mostramos los libros encontrados en la tabla
    }

    /**
     * Método para listar todos los libros.
     * Muestra todos los libros del manejador XML en la tabla.
     */
    @FXML
    public void onListar() {
        List<Libro> libros = manejadorXML.listarCatalogo();
        if (libros == null || libros.isEmpty()) {
            mostrarMensaje("No hay libros en la base de datos.");
        } else {
            mostrarLibrosEnTabla(libros);  // Muestra los libros en la tabla
        }
    }

    /**
     * Método para mostrar los libros en la tabla.
     *
     * @param libros Lista de libros a mostrar en la tabla.
     */
    private void mostrarLibrosEnTabla(List<Libro> libros) {
        tablaLibros.getItems().clear();  // Limpiar los elementos actuales de la tabla
        if (libros.isEmpty()) {
            tablaLibros.getItems().add(new Libro("No hay libros", "", "", 0L, 0));
        } else {
            tablaLibros.getItems().addAll(libros);  // Añadir todos los libros a la tabla
        }
        txtOutput.setText("Listado de libros");
    }

    /**
     * Método para mostrar un mensaje en la interfaz.
     *
     * @param mensaje Mensaje a mostrar en la interfaz.
     */
    private void mostrarMensaje(String mensaje) {
        txtOutput.setText(mensaje);
        txtOutput.setVisible(true);  // Mostrar el mensaje en la interfaz
    }

    /**
     * Método para limpiar los campos de texto.
     */
    private void clearFields() {
        txtTitulo.clear();
        txtAutor.clear();
        txtGenero.clear();
        txtISBN.clear();
        txtPublicacion.clear();
        txtOutput.setVisible(false);  // Ocultar el mensaje
    }

    /**
     * Método para volver a la pantalla principal.
     *
     * @throws IOException Si hay un error al cargar la vista del menú principal.
     */
    @FXML
    public void onVolver() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/proyectofinalprimero/menu-principal.fxml"));
        Stage stage = (Stage) txtOutput.getScene().getWindow();
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    /**
     * Método de inicialización para las columnas de la tabla.
     * Configura las propiedades de las columnas con los datos correspondientes.
     */
    @FXML
    public void initialize() {
        colTitulo.setCellValueFactory(cellData -> cellData.getValue().tituloProperty());
        colAutor.setCellValueFactory(cellData -> cellData.getValue().autorProperty());
        colGenero.setCellValueFactory(cellData -> cellData.getValue().generoProperty());
        colISBN.setCellValueFactory(cellData -> cellData.getValue().isbnProperty().asObject());
        colAnoPublicacion.setCellValueFactory(cellData -> cellData.getValue().publicacionProperty().asObject());
    }
}
