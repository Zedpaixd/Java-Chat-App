package com.example.chatapp_ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.io.*;

public class UI extends Application{

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UI.class.getResource("MainPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 250, 330);
        stage.setTitle("Simple java chat app!");
        stage.setScene(scene);
        stage.show();
    }



    public static void main(String[] args) {

        launch();
        System.exit(0);
    }
}


