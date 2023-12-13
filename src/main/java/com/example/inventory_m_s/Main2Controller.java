package com.example.inventory_m_s;

import java.io.IOException;
import java.net.ResponseCache;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

import com.example.inventory_m_s.enums.Type;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main2Controller {

    private DbFunctions dbFunctions;
    private Connection conn;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text logo_email;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    void onReturn(ActionEvent event) {
        loadPageMain("main-view.fxml", true);
    }

    @FXML
    void onAddType(ActionEvent event) {
        loadPage("add-type-view.fxml", true);
    }

    @FXML
    void onAllGoods(ActionEvent event) {
        loadPageAllGoods("all-goods-view.fxml");
    }
    @FXML
    void onUpdateGoods(ActionEvent event) {
        loadPageChange( "change-goods-view.fxml", true);
    }

    @FXML
    void onDeleteType(ActionEvent event) {
        dbFunctions.delete_type_by_type_name(conn, "types", comboBox.getValue());
        loadPageMain2("main2-view.fxml", true);
    }

    @FXML
    void onDeleteGoods(ActionEvent event) {
        loadPageDelete("delete-view.fxml");
    }

    @FXML
    void onLogOut(ActionEvent event) {
        loadPage("login-view.fxml", false);
    }

    @FXML
    void onYet(ActionEvent event) {

    }

    public void setLogo_email(String text){
        logo_email.setText(text);
    }

    @FXML
    void initialize() {
        dbFunctions=new DbFunctions();
        conn=dbFunctions.connect_to_db("testdb","postgres","1234");
        List<String> types = dbFunctions.read_data_types(conn, "types");

        comboBox.getItems().addAll(types);
        comboBox.setValue("Types");


        dbFunctions.createTableGoods(conn, "goods");
        dbFunctions.createTableType(conn, "types");
        if(dbFunctions.read_data_type(conn, "types") == 0){
            for (Type type: Type.values())
                dbFunctions.insert_into_types(conn, type.name());
        }


    }

    public void loadPage(String page, Boolean isChecked){
        try {
            // Загрузка нового FXML файла
            FXMLLoader loader = new FXMLLoader(getClass().getResource(page));
            Parent root = loader.load();
            if (isChecked){
                AddTypeController addTypeController = loader.getController();
                addTypeController.setLogo_email(logo_email.getText());
            }

            // Создание новой сцены
            Scene scene = new Scene(root);

            // Получение текущего Stage (окна) из любого элемента управления в вашем новом FXML
            Stage stage = (Stage) logo_email.getScene().getWindow();

            // Установка новой сцены в Stage
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadPageMain(String page, Boolean isChecked){
        try {
            // Загрузка нового FXML файла
            FXMLLoader loader = new FXMLLoader(getClass().getResource(page));
            Parent root = loader.load();
            if (isChecked){
                GoodsController addTypeController = loader.getController();
                addTypeController.setLogo_email(logo_email.getText());
            }

            // Создание новой сцены
            Scene scene = new Scene(root);

            // Получение текущего Stage (окна) из любого элемента управления в вашем новом FXML
            Stage stage = (Stage) logo_email.getScene().getWindow();

            // Установка новой сцены в Stage
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadPageChange(String page, Boolean isChecked){
        try {
            // Загрузка нового FXML файла
            FXMLLoader loader = new FXMLLoader(getClass().getResource(page));
            Parent root = loader.load();
            if (isChecked){
                ChangeController addTypeController = loader.getController();
                addTypeController.setLogo_email(logo_email.getText());
            }

            // Создание новой сцены
            Scene scene = new Scene(root);

            // Получение текущего Stage (окна) из любого элемента управления в вашем новом FXML
            Stage stage = (Stage) logo_email.getScene().getWindow();

            // Установка новой сцены в Stage
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadPageMain2(String page, Boolean isChecked){
        try {
            // Загрузка нового FXML файла
            FXMLLoader loader = new FXMLLoader(getClass().getResource(page));
            Parent root = loader.load();
            if (isChecked){
                Main2Controller addTypeController = loader.getController();
                addTypeController.setLogo_email(logo_email.getText());
            }

            // Создание новой сцены
            Scene scene = new Scene(root);

            // Получение текущего Stage (окна) из любого элемента управления в вашем новом FXML
            Stage stage = (Stage) logo_email.getScene().getWindow();

            // Установка новой сцены в Stage
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPageAddGoods(String page){
        try {
            // Загрузка нового FXML файла
            FXMLLoader loader = new FXMLLoader(getClass().getResource(page));
            Parent root = loader.load();

            AddGoodsController addTypeController = loader.getController();
            addTypeController.setLogo_email(logo_email.getText());
            // Создание новой сцены
            Scene scene = new Scene(root);

            // Получение текущего Stage (окна) из любого элемента управления в вашем новом FXML
            Stage stage = (Stage) logo_email.getScene().getWindow();

            // Установка новой сцены в Stage
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadPageAllGoods(String page){
        try {
            // Загрузка нового FXML файла
            FXMLLoader loader = new FXMLLoader(getClass().getResource(page));
            Parent root = loader.load();

            AllGoodsController addTypeController = loader.getController();
            addTypeController.setLogo_email(logo_email.getText());
            // Создание новой сцены
            Scene scene = new Scene(root);

            // Получение текущего Stage (окна) из любого элемента управления в вашем новом FXML
            Stage stage = (Stage) logo_email.getScene().getWindow();

            // Установка новой сцены в Stage
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadPageDelete(String page){
        try {
            // Загрузка нового FXML файла
            FXMLLoader loader = new FXMLLoader(getClass().getResource(page));
            Parent root = loader.load();

            DeleteController addTypeController = loader.getController();
            addTypeController.setLogo_email(logo_email.getText());
            // Создание новой сцены
            Scene scene = new Scene(root);

            // Получение текущего Stage (окна) из любого элемента управления в вашем новом FXML
            Stage stage = (Stage) logo_email.getScene().getWindow();

            // Установка новой сцены в Stage
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
