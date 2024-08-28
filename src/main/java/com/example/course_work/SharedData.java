package com.example.course_work;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class SharedData {
    private static SharedData instance = new SharedData();
    private User data;
    private String message;
    private User GuestUser;
    private String ChatID;
    ObservableList<String> contacts = FXCollections.observableArrayList();
    private SharedData(){};

    public static SharedData getInstance() {
        return instance;
    }

    public User getData() {
        return data;
    }


    public void setData(User data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getGuestUser() {
        return GuestUser;
    }

    public void setGuestUser(User guestUser) {
        GuestUser = guestUser;
    }

    public ObservableList<String> getContacts() {
        return contacts;
    }

    public void setContacts(ObservableList<String> contacts) {
        this.contacts = contacts;
    }

    public String getChatID() {
        return ChatID;
    }

    public void setChatID(String chatID) {
        ChatID = chatID;
    }
}
