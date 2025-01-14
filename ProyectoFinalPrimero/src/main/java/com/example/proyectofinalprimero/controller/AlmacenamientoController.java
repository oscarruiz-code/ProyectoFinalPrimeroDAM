package com.example.proyectofinalprimero.controller;

import com.example.proyectofinalprimero.model.repository.xml.AlmacenamientoXML;
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

/**
 * Controlador para la gestión del almacenamiento en la aplicación.
 * Permite guardar y cargar datos desde archivos de texto.
 * También proporciona la funcionalidad para volver al menú principal.
 *
 * @version 1.0
 * @author oscarruiz-code
 */
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

    /**
     * Constructor de la clase AlmacenamientoController.
     * Inicializa una nueva instancia de AlmacenamientoXML.
     */
    public AlmacenamientoController() {
        almacenamientoXML = new AlmacenamientoXML();
    }

    /**
     * Método llamado cuando se hace clic en el botón Guardar.
     * Abre un cuadro de diálogo para seleccionar el archivo donde se guardarán los datos.
     */
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

    /**
     * Método llamado cuando se hace clic en el botón Cargar.
     * Abre un cuadro de diálogo para seleccionar el archivo desde donde se cargarán los datos.
     */
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

    /**
     * Método llamado cuando se hace clic en el botón Volver.
     * Cambia la escena a la vista del menú principal.
     *
     * @throws IOException Si hay un error al cargar la vista del menú principal.
     */
    @FXML
    public void onVolver() throws IOException {
        // Cambiar la escena a la vista del menú principal sin depender de txtOutput
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/proyectofinalprimero/menu-principal.fxml"));
        Stage stage = (Stage) btnVolver.getScene().getWindow();  // Obtenemos la ventana desde el botón Volver
        stage.setScene(new Scene(root, 600, 400));  // Ajusta el tamaño de la ventana si es necesario
        stage.show();
    }

}
