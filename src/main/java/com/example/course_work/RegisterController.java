package com.example.course_work;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RegisterController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
    private TextField NameField;

    @FXML
    private TextField PasswordField;

    @FXML
    private TextField PasswordRepeatField;
    @FXML
    private Button RegApplyButton;
    @FXML
    void initialize() {
        DataBaseFunction dataBaseFunction = new DataBaseFunction();


        RegApplyButton.setOnAction(event -> {
            if (PasswordField.getText().trim().isEmpty()) {
                Alert RegError = new Alert(Alert.AlertType.ERROR);
                RegError.setContentText("Введите пароль для продолжения регистрации");
                RegError.setTitle("Сообщение о регистрации");
                RegError.showAndWait();
            }
            else if(!PasswordField.getText().trim().equals(PasswordRepeatField.getText().trim())) {
                Alert RegError = new Alert(Alert.AlertType.ERROR);
                RegError.setContentText("Повторите пароль для продолжения регистрации");
                RegError.setTitle("Сообщение о регистрации");
                RegError.showAndWait();
            }
            else {
                boolean result = dataBaseFunction.WriteToDB(NameField.getText(), PasswordField.getText());
                if (result) {
                    Alert RegComplete = new Alert(Alert.AlertType.CONFIRMATION);
                    RegComplete.setContentText("Успешная регистрация");
                    RegComplete.setTitle("Сообщение о регистрации");
                    RegComplete.showAndWait();
                } else {
                    Alert RegError = new Alert(Alert.AlertType.ERROR);
                    RegError.setContentText("Имя занято");
                    RegError.setTitle("Сообщение о регистрации");
                    RegError.showAndWait();
                }
            }


        });

    }
}
