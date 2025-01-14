module com.example.proyectofinalprimero {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.proyectofinalprimero to javafx.fxml;
    exports com.example.proyectofinalprimero;
    exports com.example.proyectofinalprimero.controller;
    opens com.example.proyectofinalprimero.controller to javafx.fxml;
    exports com.example.proyectofinalprimero.model.repository.xml;
    opens com.example.proyectofinalprimero.model.repository.xml to javafx.fxml;
    exports com.example.proyectofinalprimero.model.repository.bd;
    opens com.example.proyectofinalprimero.model.repository.bd to javafx.fxml;
    exports com.example.proyectofinalprimero.model.entity;
    opens com.example.proyectofinalprimero.model.entity to javafx.fxml;
}