package com.example.chatapp_ui;

import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {

        if (Objects.equals(args[0],"123") && Objects.equals(args[1],"321"))  // """Username and password"""
        {
            com.example.chatapp_ui.Server server = new com.example.chatapp_ui.Server();
            server.run();
        }
        else
        {
            System.out.println("Invalid credentials. Can not start server");
        }
    }

}
