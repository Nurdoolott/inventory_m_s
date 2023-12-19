package com.example.inventory_m_s;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.example.inventory_m_s.entities.User;
import com.example.inventory_m_s.enums.Role;
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

public class LoginController {

    private DbFunctions dbFunctions;
    private Connection conn;


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField email;

    @FXML
    private Button login;

    @FXML
    private PasswordField password;

    @FXML
    private Button register;

    @FXML
    private Text error_message;

    @FXML
    void onLogin(ActionEvent event) {
        User user = null;
        try {
            user = dbFunctions.users_search_by_email_and_password(conn,"users",email.getText());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (user.getEmail()!=null){
            if (!user.getPassword().equals(password.getText())){
                error_message.setText("incorrect password");
            }
            else {
                if (user.getRole().equals(Role.ADMIN)) {
                    System.out.println("the user id on login: "+user.getId());
                    RegisterController.setUserId(user.getId());
                    loadPage("main-view.fxml", true);
                }
                else {
                    System.out.println("the user id on login: "+user.getId());

                    RegisterController.setUserId(user.getId());

                    loadPageUser("main-user-view.fxml", true);

                }

            }
        }
        else {
            error_message.setText("gmail is not registered!");
        }

    }

    @FXML
    void onRegister(ActionEvent event) {
        System.out.println("opening register page");
        loadPage("register-view.fxml", false);
    }

    @FXML
    void initialize() {
        dbFunctions=new DbFunctions();
        conn=dbFunctions.connect_to_db("testdb","postgres","123456");

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
    public void loadPageUser(String page, Boolean isChecked){
        try {
            // Загрузка нового FXML файла
            FXMLLoader loader = new FXMLLoader(getClass().getResource(page));
            Parent root = loader.load();
            if (isChecked){
                MainUserController goodsController = loader.getController();
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

}
