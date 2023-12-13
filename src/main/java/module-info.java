module com.example.inventory_m_s {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.inventory_m_s to javafx.fxml;
    exports com.example.inventory_m_s;
    exports com.example.inventory_m_s.entities;



}