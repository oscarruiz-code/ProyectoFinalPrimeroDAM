package com.example.proyectofinalprimero;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("menu-principal.fxml"));
        Parent root = loader.load();

        // Obtener el controlador y establecer el primaryStage
        HelloController controller = loader.getController();
        controller.setPrimaryStage(primaryStage);

        primaryStage.setTitle("Biblioteca Pix4ke");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
