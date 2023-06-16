module com.example.platproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires gson;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires json.simple;

    opens com.example.platproject.dao.PlatDaoImpl to javafx.base, javafx.graphics, javafx.fxml;
    opens com.example.platproject.dao to javafx.base, javafx.graphics, javafx.fxml;
    opens com.example.platproject.entities to javafx.base, javafx.graphics, javafx.fxml, gson;
    opens com.example.platproject.service to javafx.base, javafx.graphics, javafx.fxml;
    opens com.example.platproject to javafx.base, javafx.graphics, javafx.fxml;
    opens com.example.platproject.controllers to javafx.base, javafx.graphics, javafx.fxml;


    exports com.example.platproject.dao.PlatDaoImpl;
    exports com.example.platproject.dao;
    exports com.example.platproject.entities;
    exports com.example.platproject.controllers;
    exports com.example.platproject;
}