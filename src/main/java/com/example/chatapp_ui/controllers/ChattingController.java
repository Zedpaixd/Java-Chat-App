package com.example.chatapp_ui.controllers;

import com.example.chatapp_ui.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ChattingController {

    @FXML
    private TextField message;
    @FXML
    private TextArea chat;

    private Client client;
    private Client.InputHandler inputHandler;

    public void sendMessage(ActionEvent event) throws IOException {
        inputHandler.sendMessage(message.getText());
        message.setText("");
    }

    public void closeWindow(ActionEvent event) throws IOException {
        inputHandler.sendMessage("/quit");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }


    public void appendText(String str) {
        if (chat != null) {
            chat.appendText(str + "\n");
        } else {
            System.err.println("CHAT IS NULL");
        }
    }

    public void start(String nickname) {
        client = new Client(nickname, this);
        inputHandler = client.new InputHandler();

        client.start();
    }

    @FXML
    void enterPress(KeyEvent event){
        if (event.getCode() == KeyCode.ENTER){
            inputHandler.sendMessage(message.getText());
            message.setText("");
        }
    }
}
