package com.example.course_work;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.net.URISyntaxException;
public class WebClient extends WebSocketClient{

Firebase firebase;
MessageService messageService;
    public WebClient(String serverUri,MessageService param) throws URISyntaxException {

        super(new URI(serverUri));
        firebase = Firebase.getInstance();
        this.messageService = param;




    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
     System.out.println("Подключено к серверу");
    }

    @Override
    public void onMessage(String message) {
      System.out.println("Получено: "+message);
      messageService.setMessage(message);

    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
System.out.println("Отключено от сервера");
    }

    @Override
    public void onError(Exception ex) {
        System.out.println("Ошибка: "+ex.getMessage());
    }
    public void sendMessage(String sender,String message,String accepter){
        String JsonMsg = String.format("{\"type\":\"message\",\"sender\":\"%s\",\"message\":\"%s\",\"accepter\":\"%s\"}",
                sender,message,accepter);
        send(JsonMsg);
        System.out.println("Сообщение отправлено!");
    }
    public void loginServer(String name){
        String JsonLgn  = String.format("{\"type\":\"login\",\"name\":\"%s\"}",
                name);
        send(JsonLgn);
        System.out.println("Вход выполнен");
    }
    public String getMessage(String message){
        return message;
    }
}
