package com.example.course_work;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;

public class MessageService {
    BlockingQueue<String> messageQueue;
    CompletableFuture<String> Querymessage = new CompletableFuture<>();

    List<String> MessageList = new ArrayList<>();
    private boolean test;
    MessageService(){
        Querymessage = new CompletableFuture<>();
        test= true;
        messageQueue = new LinkedBlockingQueue<>();

    }

    public String getMessage() throws InterruptedException {

        System.out.println("Сработала функция getMessage");
        return messageQueue.take();
    }

    public void setMessage(String message) {

             System.out.println("Сработала функция setMessage");
    messageQueue.offer(message);

    }
    public void reset(){
        test = true;
    }
}
