package com.example.course_work;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
    private int SearchUserLoginInDB(String UserName)throws SQLException {
        DataBaseFunction DBFunction = new DataBaseFunction();
        ResultSet resultSet = DBFunction.getUserLogin(UserName);
        int counter = 0;
        while (resultSet.next()) {
            counter++;
        }
        return counter;
    }

    @FXML
    void initialize() {
       ApplyUser.setOnAction(actionEvent ->{
           String Name = Add_UserName.getText().trim();
           int counter;
           try {
               counter =  SearchUserLoginInDB(Add_UserName.getText().trim());

           } catch (SQLException e) {
               throw new RuntimeException(e);
           }
           if(counter>=1)
           System.out.println(Name);
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
