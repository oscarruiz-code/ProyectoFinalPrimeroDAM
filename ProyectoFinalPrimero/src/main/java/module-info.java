module com.example.proyectofinalprimero {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml;

    opens com.example.proyectofinalprimero to javafx.fxml;
    exports com.example.proyectofinalprimero;
}