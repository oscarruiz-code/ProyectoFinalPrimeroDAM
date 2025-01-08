package com.example.proyectofinalprimero;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
public class AlmacenamientoController {

    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnCargar;
    @FXML
    private Button btnVolver;
    @FXML
    private Label txtMensaje;
    @FXML
    private Label txtOutput;

    private AlmacenamientoXML almacenamientoXML;

    public AlmacenamientoController() {
        almacenamientoXML = new AlmacenamientoXML();
    }

    @FXML
    public void onGuardar() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos de texto", "*.txt"));
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            // Llamamos al método guardarEnArchivo de la clase AlmacenamientoXML
            almacenamientoXML.guardarEnArchivo(file.getAbsolutePath());
            txtMensaje.setText("Datos guardados correctamente.");
        } else {
            txtMensaje.setText("No se seleccionó ningún archivo.");
        }
    }

    @FXML
    public void onCargar() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos de texto", "*.txt"));
        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {
            // Llamamos al método cargarDesdeArchivo de la clase AlmacenamientoXML
            almacenamientoXML.cargarDesdeArchivo(file.getAbsolutePath());
            txtMensaje.setText("Datos cargados correctamente.");
        } else {
            txtMensaje.setText("No se seleccionó ningún archivo.");
        }
    }

    @FXML
    public void onVolver() throws IOException {
        // Cambiar la escena a la vista del menú principal sin depender de txtOutput
        Parent root = FXMLLoader.load(getClass().getResource("menu-principal.fxml"));
        Stage stage = (Stage) btnVolver.getScene().getWindow();  // Obtenemos la ventana desde el botón Volver
        stage.setScene(new Scene(root, 600, 400));  // Ajusta el tamaño de la ventana si es necesario
        stage.show();
    }

}
