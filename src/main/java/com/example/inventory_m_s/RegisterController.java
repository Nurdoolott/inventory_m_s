package com.example.inventory_m_s;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class RegisterController {

    private DbFunctions dbFunctions;
    private Connection conn;


    @FXML
    private TextField email;

    @FXML
    private Text error_message;

    @FXML
    private Button login;

    @FXML
    private PasswordField password;

    @FXML
    private Button register;

    @FXML
    void onLogin(ActionEvent event) {
        loadPage("login-view.fxml", false);
    }

    @FXML
    void onRegister(ActionEvent event) {
        dbFunctions.insert_users_register(conn, email.getText(), password.getText());
        loadPage("main-view.fxml", true);
    }

    public void loadPage(String page, Boolean isChecked){
        try {
            // Загрузка нового FXML файла
            FXMLLoader loader = new FXMLLoader(getClass().getResource(page));
            Parent root = loader.load();
            if (isChecked){
                GoodsController goodsController = loader.getController();
                goodsController.setLogo_email(email.getText());
            }

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
    @FXML
    void initialize() {
        dbFunctions=new DbFunctions();
        conn=dbFunctions.connect_to_db("testdb","postgres","123456");

    }
}
