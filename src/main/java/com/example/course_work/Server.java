package com.example.course_work;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import javafx.concurrent.Service;
import com.google.cloud.firestore.WriteResult;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import javax.xml.*;


public class Server {
   URL url = new URL("http://192.168.0.102:3500/");
    HttpURLConnection connection;

    public Server() throws IOException {
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Content-Type","text/plain; charset=UTF-8");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setDoInput(true);

        connection.connect();
    }
    public void send_message(String message) throws IOException {

        byte[] msgbyte =  message.getBytes("UTF-8");
        OutputStream OS = connection.getOutputStream();
        OS.write(msgbyte, 0,msgbyte.length);
        OS.flush();
        int ResCode = connection.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while((inputLine = in.readLine())!=null)
            response.append(inputLine);
        in.close();
        System.out.println(response.toString());

    }
}
