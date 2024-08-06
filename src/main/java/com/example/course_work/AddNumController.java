package com.example.course_work;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;

public class AddNumController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField Add_Name;

    @FXML
    void initialize() {
        assert Add_Name != null : "fx:id=\"Add_Name\" was not injected: check your FXML file 'Add_Contact.fxml'.";

    }

}
