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


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class Firebase {
private DatabaseReference database;
FileInputStream PathToServiceFile;
public Firebase()  {
    try {
        PathToServiceFile = new FileInputStream("src/main/resources/com/example/course_work/messengerdb-cd487-firebase-adminsdk-dsxce-418e3e1564.json");
        try {
            FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(PathToServiceFile)).setDatabaseUrl("https://messengerdb-cd487-default-rtdb.firebaseio.com/").build();
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
    }
    database = FirebaseDatabase.getInstance().getReference();
}

public CompletableFuture<Boolean> CheckUserName(User user) {
   CompletableFuture<Boolean> result = new CompletableFuture<>();
database.child("0").orderByChild("name").equalTo(user.getName()).addListenerForSingleValueEvent(new ValueEventListener() {

    @Override
    public void onDataChange(DataSnapshot snapshot) {

        if(snapshot.exists()) {
           result.complete(true);
        }
        else {
            result.complete(false);
        }
    }

    @Override
    public void onCancelled(DatabaseError error) {
result.completeExceptionally(error.toException());
    }
});
    return result;
}
public void sendUser(User user) {
DatabaseReference MessageRef = database.child("/0").push();
MessageRef.setValueAsync(user);
System.out.println("Succesfully");
}
}
