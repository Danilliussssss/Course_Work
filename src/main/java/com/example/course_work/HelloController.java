package com.example.course_work;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import Animation.Fade;
import Animation.ScaleTransition;
import Animation.Shake;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

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
    private boolean position = false ;
    @FXML
    void initialize() {

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

          MessageLabel =  new Label();
          Label UserNameLabel = new Label("Вы");
          UserNameLabel.setWrapText(true);
          UserNameLabel.setStyle("-fx-text-fill: grey;-fx-font-family: 'Arial';-fx-font-style: italic;");
          MessageLabel.setWrapText(true);
          MessageLabel.setStyle("-fx-text-fill: white;");

          MessageLabel.setText(Message.getText());
          UserNameLabel.setPrefHeight(PaneMessage.getMaxHeight());
         UserNameLabel.setPrefWidth(PaneMessage.getPrefWidth()-100);
         MessageLabel.setPrefWidth(PaneMessage.getPrefWidth()-100);
         MessageLabel.setPrefHeight(PaneMessage.getMaxHeight());
         ScaleTransition scaleTransition = new ScaleTransition(MessageLabel,0,-1,PaneMessage.getPrefHeight(),-PaneMessage.getMaxHeight());
          scaleTransition.Play();
          ScaleTransition scaleTransitionUserName = new ScaleTransition(UserNameLabel,0,-1,PaneMessage.getPrefHeight(),-PaneMessage.getMaxHeight());
          scaleTransition.Play();
          scaleTransitionUserName.Play();
         FlowPane.setMargin(MessageLabel,new Insets(-10,0,0,25) );
          FlowPane.setMargin(UserNameLabel,new Insets(0,0,0,25) );
         PaneMessage.setVgap(10);
        PaneMessage.getChildren().addAll(UserNameLabel,MessageLabel);
         MessageLabel.setManaged(true);
         UserNameLabel.setManaged(true);

         MessageLabel.setVisible(true);
          UserNameLabel.setVisible(true);
         System.out.println(MessageLabel.getText());
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

    }

}