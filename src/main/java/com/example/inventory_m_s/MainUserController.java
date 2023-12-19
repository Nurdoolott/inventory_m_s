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

public class MainUserController {

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
    private TableColumn<Goods, Void> deleteColumn;

    @FXML
    void onLogOut(ActionEvent event) {
        loadPageLogin("login-view.fxml");
    }

    @FXML
    void onMain(ActionEvent event) {
        loadPageMain("busket-view.fxml");
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

        initializeDeleteColumn();

        // Load and display goods data
        loadGoods(conn);
    }

    private void loadGoods(Connection conn) {

        List<Goods> goodsList = dbFunctions.selectGoods(conn); // Replace with your actual method
        all_goods_table.getItems().addAll(goodsList);
    }

    private void initializeDeleteColumn() {
        deleteColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("купить");

            {
                deleteButton.setOnAction(event -> {
                    Goods goods = getTableView().getItems().get(getIndex());
                    goods.setStatus("disable");
                    dbFunctions.setGoodStatus(conn,"disable", goods.getId());
                    loadPage("main-user-view.fxml");
                        deleteGoods(goods);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });
    }

    private void deleteGoods(Goods goods) {
        // Implement your delete logic here
        dbFunctions.setGoodsForUser(conn, RegisterController.getUserId(), goods);
        System.out.println("Deleting goods with id: " + goods.getId());
       // dbFunctions.delete_row_by_id(conn,"goods", Math.toIntExact(goods.getId()));
        loadPage("main-user-view.fxml");
    }
    public void deleteGoods(Connection conn, Goods goods) {
        try {
            String query = String.format("DELETE FROM goods WHERE id = %d;", goods.getId());
            Statement statement = conn.createStatement();
            statement.executeUpdate(query);
            System.out.println("Row Deleted");
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void setLogo_email(String email) {
        logo_email.setText(email);
    }

    public void loadPageMain(String page) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(page));
            Parent root = loader.load();

            BusketController goodsController = loader.getController();
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

            MainUserController goodsController = loader.getController();
            goodsController.setLogo_email(logo_email.getText());


            Scene scene = new Scene(root);
            Stage stage = (Stage) logo_email.getScene().getWindow();
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
