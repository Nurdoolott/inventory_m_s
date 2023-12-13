package com.example.inventory_m_s;

import com.example.inventory_m_s.enums.Type;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

public class AddTypeController {

    private DbFunctions dbFunctions;
    private Connection conn;

    @FXML
    private Text logo_email;

    public void setLogo_email(String email){
        logo_email.setText(email);
    }

    @FXML
    private TextField typeInput;

    @FXML
    void onLogOut(ActionEvent event) {
        loadPageLogin("login-view.fxml");
    }
    @FXML
    void onMain(ActionEvent event) {
        loadPageMain("main-view.fxml");
    }

    @FXML
    void onTypeAdding(ActionEvent event) {
        dbFunctions=new DbFunctions();
        conn=dbFunctions.connect_to_db("testdb","postgres","1234");
        if (!typeInput.getText().isEmpty())
            dbFunctions.insert_into_types(conn, typeInput.getText());


    }

    public void loadPageLogin(String page){
        try {
            // Загрузка нового FXML файла
            FXMLLoader loader = new FXMLLoader(getClass().getResource(page));
            Parent root = loader.load();

            // Создание новой сцены
            Scene scene = new Scene(root);

            // Получение текущего Stage (окна) из текущего элемента управления (typeInput)
            Stage stage = (Stage) typeInput.getScene().getWindow();

            // Установка новой сцены в Stage
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadPageMain(String page){
        try {
            // Загрузка нового FXML файла
            FXMLLoader loader = new FXMLLoader(getClass().getResource(page));
            Parent root = loader.load();

            GoodsController goodsController = loader.getController();
            goodsController.setLogo_email(logo_email.getText());

            // Создание новой сцены
            Scene scene = new Scene(root);

            // Получение текущего Stage (окна) из текущего элемента управления (typeInput)
            Stage stage = (Stage) typeInput.getScene().getWindow();

            // Установка новой сцены в Stage
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
