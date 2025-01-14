package com.example.proyectofinalprimero;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controlador principal de la aplicación.
 * Gestiona la navegación entre diferentes pantallas.
 *
 * @version 1.0
 * @autor oscarruiz-code
 */
public class HelloController {

    // Referencia al Stage principal
    private Stage primaryStage;

    /**
     * Establece el Stage principal de la aplicación.
     *
     * @param stage El Stage principal.
     */
    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    /**
     * Maneja el evento de gestionar libros.
     * Carga y muestra la pantalla de gestión de libros.
     */
    @FXML
    public void handleGestionarLibros() {
        try {
            // Cargar el archivo FXML de la pantalla de gestión de libros
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectofinalprimero/GestionLibros.fxml")); // Ruta correcta
            VBox gestionLibrosRoot = loader.load();

            // Crear una nueva escena con la vista cargada y el tamaño deseado (600x400)
            Stage newStage = new Stage();
            newStage.setTitle("Gestión de Libros");
            newStage.setScene(new Scene(gestionLibrosRoot, 600, 400));  // Establecer el ancho y alto a 600x400
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Maneja el evento de almacenamiento.
     * Carga y muestra la pantalla de almacenamiento.
     *
     * @param event El evento de acción.
     */
    @FXML
    public void handleAlmacenamiento(ActionEvent event) {
        try {
            // Se corrigió la ruta para que coincida con la ubicación esperada del archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/proyectofinalprimero/almacenamiento.fxml")); // Ruta correcta
            Parent root = loader.load();

            // Crear una nueva escena con la vista cargada y el tamaño deseado (600x400)
            Scene scene = new Scene(root, 600, 400);  // Establecer el ancho y alto a 600x400
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Cambiar la escena actual
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Imprimir la traza de error para ayudar a depurar
        }
    }

    /**
     * Maneja el evento de salir.
     * Cierra la aplicación.
     */
    @FXML
    public void handleSalir() {
        System.exit(0);  // Salir de la aplicación
    }
}
