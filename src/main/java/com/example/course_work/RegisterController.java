package com.example.course_work;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    private Button ExitButton;
    @FXML
    void initialize() {

       Firebase firebase = Firebase.getInstance();
        DatabaseReference database = firebase.getDatabase();


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


                CompletableFuture<DataSnapshot> exist = firebase.CheckUserData(NameField.getText().trim(),"0/Users/","name");
                exist.thenAccept(result->{
                      if(result!=null) {
                          System.out.println("Имя занято");
                          Platform.runLater(()->{Alert RegError = new Alert(Alert.AlertType.ERROR);
                          RegError.setContentText("Имя занято");
                          RegError.setTitle("Сообщение о регистрации");
                          RegError.showAndWait();


                      });
                      }
                      else {

                            System.out.println("Имя свободно");
                          Platform.runLater(()->{Alert RegComplete = new Alert(Alert.AlertType.CONFIRMATION);
                          RegComplete.setContentText("Успешная регистрация");
                          RegComplete.setTitle("Сообщение о регистрации");
                          RegComplete.showAndWait();
                          });

                          User user = new User(NameField.getText(),PasswordField.getText());
                          firebase.sendUser(user);
                          System.out.println(user.getKey());


                        }
                });






            }


        });
        ExitButton.setOnAction(actionEvent -> {
            ExitButton.getScene().getWindow().hide();
            FXMLLoader Loader = new FXMLLoader(HelloController.class.getResource("SignUp.fxml"));
            try {
                Scene scene = new Scene(Loader.load(),600,400);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Вход");
                stage.show();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });


    }
}
