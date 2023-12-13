package com.example.inventory_m_s;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Authenticate {

    private DbFunctions dbFunctions;
    private Connection conn;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button login;

    @FXML
    private Button register;

    @FXML
    void OnLogin(ActionEvent event) {
      loadPage("login-view.fxml");
    }

    @FXML
    void onRegister(ActionEvent event) {
        loadPage("register-view.fxml");
    }

    @FXML
    void initialize() {
        dbFunctions=new DbFunctions();
        conn=dbFunctions.connect_to_db("testdb","postgres","1234");

        dbFunctions.createTableUser(conn, "users");
       // dbFunctions.insert_users_row(conn,"surname", "lastname", "email", "password", "phone", "address");


    }
    public void loadPage(String page){
        try {
            // Загрузка нового FXML файла
            FXMLLoader loader = new FXMLLoader(getClass().getResource(page));
            Parent root = loader.load();

            // Создание новой сцены
            Scene scene = new Scene(root);

            // Получение текущего Stage (окна)
            Stage stage = (Stage) login.getScene().getWindow();

            // Установка новой сцены в Stage
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
