package com.example.course_work;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;

import Animation.Fade;
import Animation.ScaleTransition;
import Animation.Shake;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import javax.websocket.DeploymentException;

public class HelloController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    @FXML
private TextArea Message;
    @FXML
    private Button SendButton;
    @FXML
    private Label MessageLabel;
    @FXML
    private Button AddNum;
    @FXML
    private Button MenuButton;
    @FXML
    private FlowPane PaneMessage;
    @FXML
    private ListView ListPanel;
@FXML
    private ListView<String> ListContact = new ListView<>();
    @FXML
    private Button ExitButton;
    private boolean position = false ;
    WebClient webClient  = null;
    MessageService messageService = new MessageService();
    String msg;
    ObjectMapper objectMapper = new ObjectMapper();
    String AccepterName;



    @FXML
    void initialize() {

        User UserData = SharedData.getInstance().getData();
        ListContact.setItems(SharedData.getInstance().getContacts());

        System.out.println(UserData.getName());
        try {

            webClient = new WebClient("ws://192.168.0.102:3500",messageService);
            webClient.connectBlocking();
            webClient.loginServer(UserData.getName());
           new Thread(this::handle).start();


        } catch (URISyntaxException | InterruptedException e) {
            throw new RuntimeException(e);
        }




      AddNum.setOnAction(actionEvent -> {
        AddNum.getScene().getWindow().hide();

          FXMLLoader Loader = new FXMLLoader(HelloController.class.getResource("Add_Contact.fxml"));
          try {
              Scene scene = new Scene(Loader.load(),501,442);
              Stage stage = new Stage();
              stage.setScene(scene);
              stage.setTitle("Добавить пользователя");
              stage.show();


          } catch (IOException e) {
              throw new RuntimeException(e);
          }

      });
      SendButton.setOnAction(actionEvent -> {
          if(AccepterName!=null) {


              MessageLabel = new Label();


              Label UserNameLabel = new Label("Вы");
              UserNameLabel.setWrapText(true);
              UserNameLabel.setStyle("-fx-text-fill: grey;-fx-font-family: 'Arial';-fx-font-style: italic;");
              MessageLabel.setWrapText(true);
              MessageLabel.setStyle("-fx-text-fill: white;");
              MessageLabel.setText(Message.getText());
              webClient.sendMessage(UserData.getName(), Message.getText(), AccepterName);
              DatabaseReference MessageRef = Firebase.getInstance().getDatabase().child("/0").child("Chats").child(UserData.getName()).push();
              MessageRef.setValueAsync(Message.getText());
              UserNameLabel.setPrefHeight(PaneMessage.getMaxHeight());
              UserNameLabel.setPrefWidth(PaneMessage.getPrefWidth() - 100);
              MessageLabel.setPrefWidth(PaneMessage.getPrefWidth() - 100);
              MessageLabel.setPrefHeight(PaneMessage.getMaxHeight());
              ScaleTransition scaleTransition = new ScaleTransition(MessageLabel, 0, -1, PaneMessage.getPrefHeight(), -PaneMessage.getMaxHeight());
              scaleTransition.Play();
              ScaleTransition scaleTransitionUserName = new ScaleTransition(UserNameLabel, 0, -1, PaneMessage.getPrefHeight(), -PaneMessage.getMaxHeight());
              scaleTransition.Play();
              scaleTransitionUserName.Play();
              FlowPane.setMargin(MessageLabel, new Insets(-10, 0, 0, 25));
              FlowPane.setMargin(UserNameLabel, new Insets(0, 0, 0, 25));
              PaneMessage.setVgap(10);
              PaneMessage.getChildren().addAll(UserNameLabel, MessageLabel);
              MessageLabel.setManaged(true);
              UserNameLabel.setManaged(true);

              MessageLabel.setVisible(true);
              UserNameLabel.setVisible(true);


          }
          else {
              Alert RegError = new Alert(Alert.AlertType.ERROR);
              RegError.setContentText("Выберите отправителя");
              RegError.setTitle("Ошибка");
              RegError.showAndWait();
          }
      });
      MenuButton.setOnAction(actionEvent -> {
          double Trans;
         if(position)
           Trans   = 120f;
         else Trans = -120f;

          ScaleTransition scaleTransitionPanel = new ScaleTransition(ListPanel,Trans,0,0,0);
          ScaleTransition scaleTransitionButton = new ScaleTransition(MenuButton,Trans,0,0,0);
          scaleTransitionPanel.Play();
          scaleTransitionButton.Play();
          position = !position;
          if(position) {

              ScaleTransition scaleTransition = new ScaleTransition(AddNum,-200f,0,0,0);
              scaleTransition.Play();
          }
          else{
              ScaleTransition scaleTransition = new ScaleTransition(AddNum,200f,0,0,0);
              scaleTransition.Play();
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
  ListContact.getSelectionModel().selectedItemProperty().addListener(((observableValue, OldValue, NewValue) ->{
      if(NewValue!=null) {

          System.out.println("Вы выбрали " + NewValue);
          AccepterName = NewValue;



            }
  } ));
    }
    void handle() {
        while (true) {
            try {
                msg = messageService.getMessage();
                System.out.println(msg);
                Platform.runLater(()->{
                    Label  MessageLabel =  new Label();




                    try {
                        JsonNode jsonNode = objectMapper.readTree(msg);
                        String type;
                            if(jsonNode.get("type").asText()=="message") {

                                String sender = jsonNode.get("sender").asText();
                                String message = jsonNode.get("message").asText();
                                Label UserNameLabel = new Label(sender);
                                UserNameLabel.setWrapText(true);
                                UserNameLabel.setStyle("-fx-text-fill: grey;-fx-font-family: 'Arial';-fx-font-style: italic;");
                                MessageLabel.setWrapText(true);
                                MessageLabel.setStyle("-fx-text-fill: white;");
                                ;
                                MessageLabel.setText(message);
                                UserNameLabel.setPrefHeight(PaneMessage.getMaxHeight());
                                UserNameLabel.setPrefWidth(PaneMessage.getPrefWidth() - 100);
                                MessageLabel.setPrefWidth(PaneMessage.getPrefWidth() - 100);
                                MessageLabel.setPrefHeight(PaneMessage.getMaxHeight());
                                ScaleTransition scaleTransition = new ScaleTransition(MessageLabel, 0, -1, PaneMessage.getPrefHeight(), -PaneMessage.getMaxHeight());
                                scaleTransition.Play();
                                ScaleTransition scaleTransitionUserName = new ScaleTransition(UserNameLabel, 0, -1, PaneMessage.getPrefHeight(), -PaneMessage.getMaxHeight());
                                scaleTransition.Play();
                                scaleTransitionUserName.Play();
                                FlowPane.setMargin(MessageLabel, new Insets(-10, 0, 0, 250));
                                FlowPane.setMargin(UserNameLabel, new Insets(0, 0, 0, 275));
                                PaneMessage.setVgap(10);
                                PaneMessage.getChildren().addAll(UserNameLabel, MessageLabel);
                                MessageLabel.setManaged(true);
                                UserNameLabel.setManaged(true);
                                MessageLabel.setVisible(true);
                                UserNameLabel.setVisible(true);
                            }
                            else if(jsonNode.get("type").asText()=="chat"){
                                String sender = jsonNode.get("first").asText();
                                SharedData.getInstance().getContacts().add(sender);
                                ListContact.setItems(SharedData.getInstance().getContacts());
                            }

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }



                });


            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(msg);
        }
    }


}