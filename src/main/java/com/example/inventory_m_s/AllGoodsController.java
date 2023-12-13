package com.example.inventory_m_s;

import com.example.inventory_m_s.entities.Goods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;


import java.util.List;

public class AllGoodsController {

    private DbFunctions dbFunctions;
    private Connection conn;

    @FXML
    private Text logo_email;

    public void setLogo_email(String email){
        logo_email.setText(email);
    }
    @FXML
    private TableView<Goods> all_goods_table;
    @FXML
    private TableColumn<Goods, Long> id;

    @FXML
    private TableColumn<Goods, String> description;

    @FXML
    private TableColumn<Goods, String> type;

    @FXML
    private TableColumn<Goods, Integer> size;

    @FXML
    private TableColumn<Goods, String> name;

    @FXML
    private TableColumn<Goods, String> date;

    @FXML
    void onLogOut(ActionEvent event) {
        loadPageLogin("login-view.fxml");
    }

    @FXML
    void onMain(ActionEvent event) {
        loadPageMain("main-view.fxml");
    }


    @FXML
    void initialize() {
        dbFunctions=new DbFunctions();
        conn=dbFunctions.connect_to_db("testdb","postgres","1234");

        id.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        description.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        type.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        size.setCellValueFactory(cellData -> cellData.getValue().sizeProperty().asObject());
        name.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        date.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        // Load and display goods data
        loadGoods(conn);
    }

    private void loadGoods(Connection conn) {
        DbFunctions dbFunctions = new DbFunctions();
        List<Goods> goodsList = dbFunctions.selectGoods(conn); // Replace with your actual method
        all_goods_table.getItems().addAll(goodsList);
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
            Stage stage = (Stage) logo_email.getScene().getWindow();

            // Установка новой сцены в Stage
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadPageLogin(String page){
        try {
            // Загрузка нового FXML файла
            FXMLLoader loader = new FXMLLoader(getClass().getResource(page));
            Parent root = loader.load();

            // Создание новой сцены
            Scene scene = new Scene(root);

            // Получение текущего Stage (окна) из текущего элемента управления (typeInput)
            Stage stage = (Stage) logo_email.getScene().getWindow();

            // Установка новой сцены в Stage
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}