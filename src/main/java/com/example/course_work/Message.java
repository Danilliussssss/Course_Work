package com.example.course_work;

import com.google.firebase.database.DatabaseReference;

public class Message {
    private String sender;
    private String message;

    Message(String sender,String message){
        this.sender = sender;
        this.message = message;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void sendMessage(){
        DatabaseReference MessageRef = Firebase.getInstance().getDatabase().child("/0").child("Chats").child(SharedData.getInstance().getChatID()).child("message").push();
   MessageRef.setValueAsync(this);
    }
}
