module com.example.course_work {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires java.sql;
    requires com.google.protobuf;
    requires com.google.api.apicommon;
    requires google.cloud.firestore;
    requires firebase.admin;
    requires com.google.auth.oauth2;
    requires com.google.auth;
    requires proto.google.cloud.firestore.v1;
    requires google.cloud.core;
    requires opencensus.api;
    requires javax.websocket.api;
    requires java.net.http;

    requires org.java_websocket;
    requires com.fasterxml.jackson.core;
    requires jackson.mapper.asl;
    requires jackson.core.asl;


    opens com.example.course_work to javafx.fxml, firebase.admin;
    exports com.example.course_work;
}