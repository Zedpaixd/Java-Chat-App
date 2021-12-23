package com.example.chatapp_ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;

public class ChatAppEXE extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ChatAppEXE.class.getResource("MainPage.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 250, 365);
        stage.setTitle("M J C P");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
        System.exit(0);
    }


}


