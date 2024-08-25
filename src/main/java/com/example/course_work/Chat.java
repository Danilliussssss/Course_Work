package com.example.course_work;

import com.google.firebase.database.DatabaseReference;

public class Chat {
private User AnotherUser;
private User user;
private String ChatID;
private Firebase database;

public Chat(User anotherUser){
    user = User.getInstance();
    AnotherUser = anotherUser;
    DatabaseReference MessageRef = Firebase.getInstance().getDatabase().child("/0").child("Chats").push();
    MessageRef.setValueAsync(this);


}
public void SendMessage(String text){

}

    public User getAnotherUser() {
        return AnotherUser;
    }

    public void setAnotherUser(User anotherUser) {
        AnotherUser = anotherUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getChatID() {
        return ChatID;
    }

    public void setChatID(String chatID) {
        ChatID = chatID;
    }
}
