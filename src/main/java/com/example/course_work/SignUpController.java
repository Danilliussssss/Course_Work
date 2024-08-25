package com.example.course_work;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

import Animation.Shake;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import javafx.application.Platform;
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
    int id;


    @FXML
    void initialize() {
        Firebase firebase= new Firebase();

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
            CompletableFuture<DataSnapshot> existLogin = firebase.CheckUserData(LoginField.getText()+PasswordField.getText(),"0/Users","hashCode");
            existLogin.thenAccept(result->{
                if(result==null){
                    Platform.runLater(()->{
                    Alert RegError = new Alert(Alert.AlertType.ERROR);
                RegError.setContentText("Неверный логин и/или пароль");
                RegError.setTitle("Сообщение о регистрации");
                RegError.showAndWait();
                    System.out.println("Логин и/или пароль неверны");
                    });
                }
                else{

                    User user = new User(LoginField.getText(),PasswordField.getText());

                    for(DataSnapshot snapshot: result.getChildren()) {
                        user.setKey(snapshot.getKey());
                    }
                    SharedData.getInstance().setData(user);
                    System.out.println(user.getKey());

                    Platform.runLater(()->{SignUpButton.getScene().getWindow().hide();
                        FXMLLoader RegisterLoader = new FXMLLoader(SignUpController.class.getResource("hello-view.fxml"));

                        try {
                            //System.out.println(res);
                            Scene scene = new Scene(RegisterLoader.load(), 501, 442);

                            Stage Registerstage = new Stage();
                            Registerstage.setScene(scene);
                            Registerstage.setTitle("Мессенджер");
                            Registerstage.show();

                        } catch (IOException e) {

                            throw new RuntimeException(e);
                        }
                    });


                }
            });

    }
});
    }

}
