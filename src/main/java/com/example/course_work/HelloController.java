package com.example.course_work;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HelloController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button AddNum;

    @FXML
    void initialize() {
      AddNum.setOnAction(actionEvent -> {
         System.out.print("Работает");

      });

    }

}