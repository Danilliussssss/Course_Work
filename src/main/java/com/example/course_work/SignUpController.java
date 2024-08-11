package com.example.course_work;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Animation.Shake;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private Button SignUpButton;
    @FXML
    private Button RegisterButton;
    @FXML
    private TextField PasswordField;
    @FXML
    private TextField LoginField;
    private int LoginUser(String UserName,String UserPassword) throws SQLException {
DataBaseFunction DBFunction = new DataBaseFunction();
ResultSet resultSet = DBFunction.getUserData(UserName,UserPassword);
int counter = 0;
while(resultSet.next()){
counter++;
}
return counter;
    }
    @FXML
    void initialize() {

RegisterButton.setOnAction(actionEvent -> {
    RegisterButton.getScene().getWindow().hide();
    FXMLLoader SignUpLoader = new FXMLLoader(SignUpController.class.getResource("Register.fxml"));
    try {
        Scene scene = new Scene(SignUpLoader.load(),600,400);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Регистрация");
        stage.show();
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
});
SignUpButton.setOnAction(actionEvent -> {
    if (LoginField.getText().trim().isEmpty()) {

        Alert RegError = new Alert(Alert.AlertType.ERROR);
        RegError.setContentText("Введите логин для входа");
        RegError.setTitle("Сообщение о регистрации");
        RegError.showAndWait();
    }
    else if(PasswordField.getText().trim().isEmpty()) {

        Alert RegError = new Alert(Alert.AlertType.ERROR);

        RegError.setContentText("Введите пароль для входа");
        RegError.setTitle("Сообщение о регистрации");
        RegError.showAndWait();

    }

    else {
        int counter;
        try {
            counter =  LoginUser(LoginField.getText().trim(),PasswordField.getText().trim());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(counter>=1) {
            SignUpButton.getScene().getWindow().hide();
            FXMLLoader RegisterLoader = new FXMLLoader(SignUpController.class.getResource("hello-view.fxml"));
            try {
                Scene scene = new Scene(RegisterLoader.load(), 501, 442);
                Stage Registerstage = new Stage();
                Registerstage.setScene(scene);
                Registerstage.setTitle("Мессенджер");
                Registerstage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            Alert RegError = new Alert(Alert.AlertType.ERROR);
            RegError.setContentText("Неверный логин и/или пароль");
            RegError.setTitle("Сообщение о регистрации");
            RegError.showAndWait();

        }
    }
});
    }

}
