module com.example.proyectofinalprimero {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.proyectofinalprimero to javafx.fxml;
    exports com.example.proyectofinalprimero;
}