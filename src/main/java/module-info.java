module com.example.course_work {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;
    requires java.sql;


    opens com.example.course_work to javafx.fxml;
    exports com.example.course_work;
}