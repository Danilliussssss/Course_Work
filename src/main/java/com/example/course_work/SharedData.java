package com.example.course_work;

public class SharedData {
    private static SharedData instance = new SharedData();
    private User data;
    private SharedData(){};

    public static SharedData getInstance() {
        return instance;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }
}
