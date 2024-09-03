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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;

import Animation.Fade;
import Animation.ScaleTransition;
import Animation.Shake;
import com.google.firebase.database.*;
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
    Firebase database = Firebase.getInstance();



    @FXML
    void initialize() {
       SharedData.getInstance().getContacts().clear();
        User UserData = SharedData.getInstance().getData();
        CompletableFuture<DataSnapshot> contacts = database.CheckUserData(UserData.getName(),"0/Chats/","yourName");
        contacts.thenAccept(result->{
           for(DataSnapshot snapshot: result.getChildren()){

               if(Objects.equals(snapshot.child("yourName").getValue(),UserData.getName())){
                   SharedData.getInstance().getContacts().add(snapshot.child("anotherUserName").getValue().toString());
               System.out.println("12345");
               }
           }
        });
        CompletableFuture<DataSnapshot> contacts2 = database.CheckUserData(UserData.getName(),"0/Chats/","anotherUserName");
        contacts2.thenAccept(result->{
            for(DataSnapshot snapshot: result.getChildren()){

                if(Objects.equals(snapshot.child("anotherUserName").getValue(),UserData.getName())&&!Objects.equals(snapshot.child("yourName").getValue(),snapshot.child("anotherUserName").getValue())){

                    SharedData.getInstance().getContacts().add(snapshot.child("yourName").getValue().toString());
                    System.out.println("76543");
                }
            }
        });
        ListContact.setItems(SharedData.getInstance().getContacts());


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
             Message message = new Message(UserData.getName(),Message.getText());
             message.sendMessage();
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
          ScaleTransition scaleTransitionListContact = new ScaleTransition(ListContact,Trans,0,0,0);
          ScaleTransition scaleTransitionExit = new ScaleTransition(ExitButton,Trans,0,0,0);
          scaleTransitionPanel.Play();
          scaleTransitionButton.Play();
          scaleTransitionListContact.Play();
          scaleTransitionExit.Play();
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

       PaneMessage.getChildren().clear();



          AccepterName = NewValue;
          CompletableFuture<DataSnapshot> contactsList = database.CheckUserData(NewValue,"0/Chats/","anotherUserName");
          contactsList.thenAccept(result->{
              for(DataSnapshot snapshot: result.getChildren()){

                  if(Objects.equals(snapshot.child("yourName").getValue(),UserData.getName())) {

                    SharedData.getInstance().setChatID(snapshot.getKey());
                          System.out.println(SharedData.getInstance().getChatID());


                      DatabaseReference messageRef = FirebaseDatabase.getInstance().getReference("0/Chats/"+SharedData.getInstance().getChatID()+"/message");
                      System.out.println(messageRef);
                      messageRef.addListenerForSingleValueEvent(new ValueEventListener() {
                          @Override
                          public void onDataChange(DataSnapshot snapshot) {
                              for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                                  System.out.println(dataSnapshot.getValue());
                                  Platform.runLater(()->{

                                      double t1,t2;
                                      MessageLabel = new Label();
                                      Label UserNameLabel;
                                      if(Objects.equals(dataSnapshot.child("sender").getValue().toString(),UserData.getName())) {
                                          UserNameLabel   = new Label("Вы");
                                          t1 = 25;
                                          t2 = 25;
                                      }
                                      else {
                                          UserNameLabel = new Label(dataSnapshot.child("sender").getValue().toString());
                                          t1 = 275;
                                          t2 = 250;
                                      }
                                      UserNameLabel.setWrapText(true);
                                      UserNameLabel.setStyle("-fx-text-fill: grey;-fx-font-family: 'Arial';-fx-font-style: italic;");
                                      MessageLabel.setWrapText(true);
                                      MessageLabel.setStyle("-fx-text-fill: white;");

                                      System.out.println(dataSnapshot.child("message").getValue().toString());
                                      MessageLabel.setText(dataSnapshot.child("message").getValue().toString());
                                      UserNameLabel.setPrefHeight(PaneMessage.getMaxHeight());
                                      UserNameLabel.setPrefWidth(PaneMessage.getPrefWidth() - 100);
                                      MessageLabel.setPrefWidth(PaneMessage.getPrefWidth() - 100);
                                      MessageLabel.setPrefHeight(PaneMessage.getMaxHeight());
                                      FlowPane.setMargin(UserNameLabel, new Insets(0, 0, 0, t1));
                                      FlowPane.setMargin(MessageLabel, new Insets(-10, 0, 0, t2));

                                      PaneMessage.setVgap(10);
                                      PaneMessage.getChildren().addAll(UserNameLabel, MessageLabel);
                                      MessageLabel.setManaged(true);
                                      UserNameLabel.setManaged(true);

                                      MessageLabel.setVisible(true);
                                      UserNameLabel.setVisible(true);
                                  });


                              }
                          }

                          @Override
                          public void onCancelled(DatabaseError error) {
                              System.err.println("Ошибка чтения данных: " + error.getMessage());
                          }
                      });
                  }


              }

          });





             // System.out.println(SharedData.getInstance().getChatID());
              CompletableFuture<DataSnapshot> contactsList2 = database.CheckUserData(NewValue, "0/Chats/", "yourName");
              contactsList2.thenAccept(result -> {
                  for (DataSnapshot snapshot : result.getChildren()) {
                       if(Objects.equals(snapshot.child("anotherUserName").getValue(),UserData.getName())) {
                           SharedData.getInstance().setChatID(snapshot.getKey());
                           System.out.println(SharedData.getInstance().getChatID());
                           DatabaseReference messageRef = FirebaseDatabase.getInstance().getReference("0/Chats/"+SharedData.getInstance().getChatID()+"/message");
                           messageRef.addListenerForSingleValueEvent(new ValueEventListener() {
                               @Override
                               public void onDataChange(DataSnapshot snapshot) {
                                   for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                                       System.out.println(dataSnapshot.getValue());
                                       Platform.runLater(()->{
                                           double t1,t2;
                                           MessageLabel = new Label();
                                           Label UserNameLabel;
                                           if(Objects.equals(dataSnapshot.child("sender").getValue().toString(),UserData.getName())) {
                                               UserNameLabel   = new Label("Вы");
                                                t1 = 25;
                                                t2 = 25;
                                           }
                                           else {
                                               UserNameLabel = new Label(dataSnapshot.child("sender").getValue().toString());
                                                t1 = 275;
                                                t2 = 250;
                                           }
                                           UserNameLabel.setWrapText(true);
                                           UserNameLabel.setStyle("-fx-text-fill: grey;-fx-font-family: 'Arial';-fx-font-style: italic;");
                                           MessageLabel.setWrapText(true);
                                           MessageLabel.setStyle("-fx-text-fill: white;");

                                           System.out.println(dataSnapshot.child("message").getValue().toString());
                                           MessageLabel.setText(dataSnapshot.child("message").getValue().toString());
                                           UserNameLabel.setPrefHeight(PaneMessage.getMaxHeight());
                                           UserNameLabel.setPrefWidth(PaneMessage.getPrefWidth() - 100);
                                           MessageLabel.setPrefWidth(PaneMessage.getPrefWidth() - 100);
                                           MessageLabel.setPrefHeight(PaneMessage.getMaxHeight());
                                           FlowPane.setMargin(UserNameLabel, new Insets(0, 0, 0, t1));
                                           FlowPane.setMargin(MessageLabel, new Insets(-10, 0, 0, t2));

                                           PaneMessage.setVgap(10);
                                           PaneMessage.getChildren().addAll(UserNameLabel, MessageLabel);
                                           MessageLabel.setManaged(true);
                                           UserNameLabel.setManaged(true);

                                           MessageLabel.setVisible(true);
                                           UserNameLabel.setVisible(true);
                                       });
                                   }
                               }

                               @Override
                               public void onCancelled(DatabaseError error) {
                                   System.err.println("Ошибка чтения данных: " + error.getMessage());
                               }
                           });

                       }



                  }
              });







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
                        System.out.println(jsonNode.get("type").asText());
                            if(Objects.equals(jsonNode.get("type").asText(), "message")) {

                                String accepter = jsonNode.get("accepter").asText();


                                String sender = jsonNode.get("sender").asText();
                                System.out.println(sender);
                                System.out.println(AccepterName);
                                if(Objects.equals(sender, AccepterName)){

                                String message = jsonNode.get("message").asText();
                                Label UserNameLabel = new Label(sender);
                                UserNameLabel.setWrapText(true);
                                UserNameLabel.setStyle("-fx-text-fill: grey;-fx-font-family: 'Arial';-fx-font-style: italic;");
                                MessageLabel.setWrapText(true);
                                MessageLabel.setStyle("-fx-text-fill: white;");

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
                            }
                            else if(Objects.equals(jsonNode.get("type").asText(), "chat")){


                                String sender = jsonNode.get("first").asText();

                                if(!Objects.equals(sender,SharedData.getInstance().getData().getName())) {
                                    SharedData.getInstance().setChatID( jsonNode.get("ChatID").asText());
                                    System.out.println("456");
                                    System.out.println(SharedData.getInstance().getChatID());
                                    SharedData.getInstance().getContacts().add(sender);
                                    ListContact.setItems(SharedData.getInstance().getContacts());
                                }
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