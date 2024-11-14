package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Authorization {
    DatabaseHandler db;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button ButtLogin;

    @FXML
    private TextField LoginField;

    @FXML
    private PasswordField PassField;

    @FXML
    void checkauth(ActionEvent actionEvent) throws SQLException, IOException,ClassNotFoundException{
        String login = LoginField.getText().trim();
        String password = PassField.getText().trim();
        if ((login !=null || !login.isEmpty()) && (password !=null || !password.isEmpty())){
            try{
                int id  = db.checkUser(login,password);
                if(id != -1){

                    db.loadUser(id);
                    if (InfoUser.getUserRole() == 1){
                        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("info.fxml"));
                        // fxmlLoader.setLocation(getClass().getResource(".fxml"));
                        Stage stage = new Stage();
                        Scene scene = new Scene(fxmlLoader.load(),747,557);
                        stage.setResizable(false);
                        stage.setTitle("Information");
                        stage.setScene(scene);
                        ButtLogin.getScene().getWindow().hide();
                        stage.show();

                    }
                    if (InfoUser.getUserRole()==2){
                        InputStream stream = getClass().getResourceAsStream("admin-view.fxml");
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        // fxmlLoader.setLocation(getClass().getResource("/mt/.fxml"));
                        Stage stage = new Stage();
                        Scene scene = new Scene(fxmlLoader.load(stream),747,557);
                        stage.setResizable(false);
                        stage.setTitle("Information");
                        stage.setScene(scene);
                        ButtLogin.getScene().getWindow().hide();
                        stage.show();
                    }
                }
                else {
                    Alert loginError = new Alert(Alert.AlertType.ERROR);
                    loginError.setTitle("Error");
                    loginError.setHeaderText("Ошибка авторизации");
                    loginError.setContentText("Что-то пошло не так");
                    loginError.show();

                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        db = new DatabaseHandler();
        db.getDbconnection();

    }
}
