package com.example.course_work;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddNumController {

    @FXML
    private ResourceBundle resources;
    @FXML
    private Button ExitButton;
    @FXML
    private URL location;

    @FXML
    private TextField Add_UserName;
    @FXML
    private Button ApplyUser;
    Firebase firebase = Firebase.getInstance();
    DatabaseReference database = firebase.getDatabase();
WebClient webClient = null;
    MessageService messageService = new MessageService();


    @FXML
    void initialize() {


        try {
            webClient = new WebClient("ws://192.168.0.102:3500",messageService);
            webClient.connectBlocking();
        } catch (URISyntaxException | InterruptedException e) {
            throw new RuntimeException(e);
        }


        ApplyUser.setOnAction(actionEvent ->{
           String Name = Add_UserName.getText().trim();

           CompletableFuture<DataSnapshot> exist = firebase.CheckUserData(Name,"0/Users/","name");
           exist.thenAccept(result-> {
                       if (result!=null)
                       {
                           for(DataSnapshot snapshot: result.getChildren()) {
                               User user = snapshot.getValue(User.class);
                                user.setKey(snapshot.getKey());
                               System.out.println(user.getPassword());
                               SharedData.getInstance().setGuestUser(user);
                               Platform.runLater(()-> {
                               Chat chat = new Chat(user);
                              System.out.println(user.getName());

                              SharedData.getInstance().getContacts().add(user.getName());
                              webClient.sendChat(SharedData.getInstance().getData().getName(),user.getName());
                              webClient.close();
                               });

                           }

                       }
                       else{
                           Platform.runLater(()-> {
                               Alert RegError = new Alert(Alert.AlertType.ERROR);
                               RegError.setContentText("Пользователь не найден");
                               RegError.setTitle("Сообщение о поиске пользователя");
                               RegError.showAndWait();
                           });
                       }
                      // Chat chat = new Chat();
                   });
       });
        ExitButton.setOnAction(actionEvent -> {
            ExitButton.getScene().getWindow().hide();
            FXMLLoader Loader = new FXMLLoader(HelloController.class.getResource("hello-view.fxml"));
            try {
                Scene scene = new Scene(Loader.load(),501,442);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Мессенджер");
                stage.show();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });


    }


}
