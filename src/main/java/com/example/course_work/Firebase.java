package com.example.course_work;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.Credentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.FirebaseDatabase;
import io.opencensus.metrics.export.Summary;
import javafx.application.Platform;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class Firebase {
private DatabaseReference database;
private static Firebase instance;
FileInputStream PathToServiceFile;
public Firebase()  {
    try {
        PathToServiceFile = new FileInputStream("src/main/resources/com/example/course_work/messengerdb-cd487-firebase-adminsdk-dsxce-418e3e1564.json");
        try {
            if(FirebaseApp.getApps().isEmpty()) {
                FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(PathToServiceFile)).setDatabaseUrl("https://messengerdb-cd487-default-rtdb.firebaseio.com/").build();
                FirebaseApp.initializeApp(options);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
    }
    database = FirebaseDatabase.getInstance().getReference();
}

public CompletableFuture<DataSnapshot> CheckUserData(String param, String path, String key) {
   CompletableFuture<DataSnapshot> result = new CompletableFuture<>();
database.child(path).orderByChild(key).equalTo(param).addListenerForSingleValueEvent(new ValueEventListener() {

    @Override
    public void onDataChange(DataSnapshot snapshot) {

        if(snapshot.exists()) {

           result.complete(snapshot);
        }
        else {
            result.complete(null);
        }
    }

    @Override
    public void onCancelled(DatabaseError error) {
result.completeExceptionally(error.toException());
    }
});
    return result;
}
public static synchronized Firebase getInstance(){
    if(instance==null)
        instance = new Firebase();
    return instance;
}

    public DatabaseReference getDatabase() {
        return database;
    }





public void sendUser(User user) {
DatabaseReference MessageRef = database.child("/0").child("Users").push();
MessageRef.setValueAsync(user);
user.setKey(MessageRef.getKey());
System.out.println("Succesfully");
}
public void sendChat(Chat chat){
    DatabaseReference MessageRef = database.child("/0").child("Users").push();
    MessageRef.setValueAsync(chat);

    System.out.println("Succesfully");
}
}
