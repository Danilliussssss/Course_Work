package com.example.course_work;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

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


    @FXML
    void initialize() {
       ApplyUser.setOnAction(actionEvent ->{
           String Name = Add_UserName.getText().trim();

           CompletableFuture<DataSnapshot> exist = firebase.CheckUserData(Name,"0/Users/","name");
           exist.thenAccept(result-> {
                       if (result!=null)
                       {
                           for(DataSnapshot snapshot: result.getChildren()) {
                               User user = snapshot.getValue(User.class);
                                user.setKey(snapshot.getKey());
                               System.out.println(user.getKey());


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
