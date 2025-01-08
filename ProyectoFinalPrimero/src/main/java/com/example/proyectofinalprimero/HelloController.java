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

public class HelloController {

    // Referencia al Stage principal
    private Stage primaryStage;

    // Setter para el Stage principal
    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

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

    @FXML
    public void handleSalir() {
        System.exit(0);  // Salir de la aplicación
    }
}
