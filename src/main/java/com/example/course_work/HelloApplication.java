package com.example.course_work;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.java_websocket.client.WebSocketClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HelloApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException, URISyntaxException, InterruptedException {


        FXMLLoader fxmlLoader  =  new FXMLLoader(HelloApplication.class.getResource("SignUp.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 600, 400);

        stage.setTitle("Вход");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}