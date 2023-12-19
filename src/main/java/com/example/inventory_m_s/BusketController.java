package com.example.inventory_m_s;

import com.example.inventory_m_s.entities.Goods;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public class BusketController {

    private DbFunctions dbFunctions;
    private Connection conn;

    @FXML
    private Text logo_email;

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
        loadPageMain("main-user-view.fxml");
    }

    @FXML
    void initialize() {
        dbFunctions = new DbFunctions();
        conn = dbFunctions.connect_to_db("testdb", "postgres", "123456");

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
        System.out.println("work01");
        List<Integer> ids = dbFunctions.selectUsersGoods(conn, RegisterController.getUserId());
        System.out.println("the ids of goods: " + ids.size());
        //System.out.println("id of good: "+ids.get(0));
        List<Goods> goodsList = dbFunctions.selectGoodsWithIds(conn,ids );
        System.out.println("size of list goods: "+goodsList.size());
        all_goods_table.getItems().addAll(goodsList);
    }






    public void setLogo_email(String email) {
        logo_email.setText(email);
    }

    public void loadPageMain(String page) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(page));
            Parent root = loader.load();

            MainUserController goodsController = loader.getController();
            goodsController.setLogo_email(logo_email.getText());

            Scene scene = new Scene(root);
            Stage stage = (Stage) logo_email.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPageLogin(String page) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(page));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) logo_email.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPage(String page) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(page));
            Parent root = loader.load();

            DeleteController goodsController = loader.getController();
            goodsController.setLogo_email(logo_email.getText());


            Scene scene = new Scene(root);
            Stage stage = (Stage) logo_email.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
