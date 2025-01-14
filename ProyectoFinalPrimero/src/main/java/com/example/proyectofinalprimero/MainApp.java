package com.example.proyectofinalprimero;

import com.example.proyectofinalprimero.controller.HelloController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Clase principal de la aplicación.
 * Inicia y configura la escena principal de la aplicación.
 *
 * @version 1.0
 * @autor oscarruiz-code
 */
public class MainApp extends Application {

    /**
     * Método de inicio de la aplicación.
     * Carga la vista del menú principal y establece la escena principal.
     *
     * @param primaryStage El escenario principal de la aplicación.
     * @throws Exception Si ocurre un error al cargar la vista del menú principal.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu-principal.fxml"));
        Parent root = loader.load();

        // Obtener el controlador y establecer el primaryStage
        HelloController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);

        primaryStage.setTitle("Biblioteca oscarruiz-code");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    /**
     * Método principal de la aplicación.
     * Lanza la aplicación.
     *
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
