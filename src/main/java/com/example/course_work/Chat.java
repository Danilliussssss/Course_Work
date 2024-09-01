package com.example.course_work;

import com.google.firebase.database.DatabaseReference;

public class Chat {

private String ChatID;
private String hashCode;
private Firebase database;
private String YourName;
private String AnotherUserName;

public Chat(User anotherUser){

    YourName = SharedData.getInstance().getData().getName();
    AnotherUserName =  anotherUser.getName();
    hashCode =SharedData.getInstance().getData().getName()+ anotherUser.getName();
    DatabaseReference MessageRef = Firebase.getInstance().getDatabase().child("/0").child("Chats").push();
    MessageRef.setValueAsync(this);



}

    public String getYourName() {
        return YourName;
    }

    public void setYourName(String yourName) {
        YourName = yourName;
    }

    public String getAnotherUserName() {
        return AnotherUserName;
    }

    public void setAnotherUserName(String anotherUserName) {
        AnotherUserName = anotherUserName;
    }

    public String getChatID() {
        return ChatID;
    }

    public void setChatID(String chatID) {
        ChatID = chatID;
    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }
}
