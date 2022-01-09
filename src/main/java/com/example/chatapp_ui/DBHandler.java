package com.example.chatapp_ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.Scanner;



public class DBHandler {

    static Connection connection = null;
    static String DBName = "mjca";
    static String DBurl = "jdbc:mysql://localhost:3306/" + DBName;
    static String DBusername = "";
    static String DBpassword = "";
    public boolean match = false;

    public DBHandler(){
        try {
            File file = new File("Details.txt");
            Scanner fs = new Scanner(file);
            String data = fs.nextLine();
            String up[] = data.trim().split("\\s+");
            DBusername = up[0];
            DBpassword = up[1];
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(DBurl, DBusername, DBpassword);
        }
        catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException e)
        {
            e.printStackTrace();
        }


    }

    public boolean logIn(String username, String password) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from accounts");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            if (Objects.equals(username, rs.getString(2)) && Objects.equals(password, rs.getString(3)))
                match = true;
        }

        return match;

    }

    public boolean register(String username, String password) throws SQLException {
        boolean exists = false;
        PreparedStatement ps = connection.prepareStatement("select * from accounts");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            if (Objects.equals(username, rs.getString(2)))
                exists = true;
        }
        if (!exists) {
                ps = connection.prepareStatement("insert into accounts(username,password) values ('" + username + "','" + password + "');");
                ps.executeUpdate();
                return true;
        }
        if (exists) {
            return false;

        }
        return false;
    }


}
