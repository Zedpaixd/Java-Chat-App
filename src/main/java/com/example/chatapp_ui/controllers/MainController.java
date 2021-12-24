package com.example.chatapp_ui.controllers;

import com.example.chatapp_ui.ChatAppEXE;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    public void registerScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(ChatAppEXE.class.getResource("Register.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void loginScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(ChatAppEXE.class.getResource("Login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
