package com.example.inventory_m_s;

import com.example.inventory_m_s.entities.Goods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

public class AddGoodsController {
    private DbFunctions dbFunctions;
    private Connection conn;

    @FXML
    private ComboBox<String> variableComboBox;

    @FXML
    private Text logo_email;
    public void setLogo_email(String email){
        logo_email.setText(email);
    }

    @FXML
    private Text statusAdding;

    @FXML
    private ScrollPane scrollPane;


    @FXML
    private TextField prize;

    @FXML
    private TextField date;

    @FXML
    private TextField description;

    @FXML
    private TextField name;

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
        String type = variableComboBox.getValue();

        Goods goods = new Goods();
        goods.setType(type);
        goods.setDate(LocalDateTime.now().toString());
        goods.setName(name.getText());
        goods.setPrize(Integer.parseInt(prize.getText()));
        goods.setDescription(description.getText());

        dbFunctions.goods_insert(conn, goods);
        statusAdding.setText("added successfully!");

    }

    @FXML
    void initialize() {
        dbFunctions=new DbFunctions();
        conn=dbFunctions.connect_to_db("testdb","postgres","1234");

        List<String> types = dbFunctions.read_data_types(conn, "types");

            variableComboBox.getItems().addAll(types);
            variableComboBox.setValue("Types");
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
            Stage stage = (Stage) prize.getScene().getWindow();

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
            Stage stage = (Stage) prize.getScene().getWindow();

            // Установка новой сцены в Stage
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
