package com.example.course_work;

public class User {
    private String name;
    private String password;
    private String key;
    private String HashCode;
    private static User instance = new User();
    public User(String name, String password) {
        this.password = password;
        this.HashCode = name+password;
        this.name = name;


    }
    public User(){};
    public User(String key){

    }
    public static User getInstance(){
        return instance;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHashCode() {
        return HashCode;
    }

    public void setHashCode(String hashCode) {
        HashCode = hashCode;
    }
}
