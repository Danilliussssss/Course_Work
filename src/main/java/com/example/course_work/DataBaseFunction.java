package com.example.course_work;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.ResultSet;
public class DataBaseFunction extends DatabaseConfig
{
    Connection DBConnect;
    public Connection getDBConnect() throws
            ClassNotFoundException,SQLException {
        String connectionString = "jdbc:mysql://" + hostname + ":"
                + port +"/" +DBname;
        Class.forName("com.mysql.cj.jdbc.Driver");
        DBConnect = DriverManager.getConnection(connectionString,username,password);
        return DBConnect;
    }
    public boolean WriteToDB(User user){
String insert = "INSERT INTO "+Const.USER_TABLE+"("+Const.USERS_NAME+","+Const.PASSWORD+")"+"VALUES(?,?)";

        try {
            PreparedStatement PrepStat = getDBConnect().prepareStatement(insert);
            PrepStat.setString(1,user.getName());
            PrepStat.setString(2,user.getPassword());
            PrepStat.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;

        } catch (ClassNotFoundException e) {
           return false;
        }
    }

    public  ResultSet getUserData(String name,String password) {
ResultSet ResSet = null;
        String select = "SELECT * FROM "+Const.USER_TABLE+" WHERE "+Const.USERS_NAME+"=? AND "+Const.PASSWORD+"=? AND "+ Const.ID_USERS;
        PreparedStatement PrepStat = null;
        try {
            PrepStat = getDBConnect().prepareStatement(select);
            PrepStat.setString(1,name);
            PrepStat.setString(2,password);
            ResSet =  PrepStat.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResSet;
    }
    public  ResultSet getUserID(String name,String password) {
        ResultSet ResSet = null;
        String select = "SELECT * FROM "+Const.USER_TABLE+" WHERE "+Const.USERS_NAME+"=? AND "+Const.PASSWORD+"=?";
        PreparedStatement PrepStat = null;
        try {
            PrepStat = getDBConnect().prepareStatement(select);
            PrepStat.setString(1,name);
            PrepStat.setString(2,password);
            ResSet =  PrepStat.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResSet;
    }

    public  ResultSet getUserLogin(String name) {
        ResultSet ResSet = null;
        String select = "SELECT * FROM "+Const.USER_TABLE+" WHERE "+Const.USERS_NAME+"=?";
        PreparedStatement PrepStat = null;
        try {
            PrepStat = getDBConnect().prepareStatement(select);
            PrepStat.setString(1,name);

            ResSet =  PrepStat.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ResSet;
    }

}