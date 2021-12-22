package com.example.chatapp_ui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.*;
import java.util.EventObject;
import java.util.Objects;

public class UI_Controller {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label errorIGuess;
    @FXML
    private TextField nickname;
    @FXML
    private Button closeWindow;
    @FXML
    private TextField message;
    @FXML
    public TextArea chat;

    static Client client;
    static Client.InputHandler IH;

    private Runnable cht;

    public void registerScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Register.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void loginScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void nicknamePrompt(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("nickname prompt.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void MainScreen(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void register(ActionEvent event) throws IOException {

        boolean exists = false;

        FileWriter fw = new FileWriter("accounts.txt", true);

        BufferedReader fr = new BufferedReader(new FileReader("accounts.txt"));
        String line = fr.readLine();
        while (line != null) {
            String[] splitLine = line.trim().split("\\s+");

            if (Objects.equals(username.getText(), splitLine[0]))
                exists = true;

            line = fr.readLine();
        }
        if (!exists && username.getText() != "" && password.getText() != "") {
            fw.write(username.getText() + " " + password.getText() + "\n");
            MainScreen(event);
        }
        if (exists) {
            errorIGuess.setText("Username already in database.");
            exists = false;
        }

        fw.close();
        fr.close();
    }

    public void login(ActionEvent event) throws IOException {

        boolean match = false;

        BufferedReader fr = new BufferedReader(new FileReader("accounts.txt"));
        String line = fr.readLine();
        while (line != null) {
            String[] splitLine = line.trim().split("\\s+");

            if (Objects.equals(username.getText(), splitLine[0]) && Objects.equals(password.getText(), splitLine[1]))
                match = true;

            line = fr.readLine();
        }
        if (match) {
            nicknamePrompt(event);
        }
        if (!match) {
            errorIGuess.setText("Wrong Username + Password combination.");
            match = false;
        }

    }

    public void sendMessage(ActionEvent event) {
        IH.sendMessage(message.getText());
        appendText(message.getText());
        message.setText("");
    }

    public void closeWindow(ActionEvent event) {
        IH.sendMessage("/quit");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void appendText2(String str){
        cht = () -> appendText(str);
        Platform.runLater(cht);
    }

    public void appendText(String str) {
        if (chat != null) {
            chat.appendText(str + "\n");
        } else {
            System.err.println("CHAT IS NULL");
        }
    }

    public void ready(ActionEvent event) throws IOException {                    // From here below is where the issue begins to happen

        client = new Client(nickname.getText(), this::appendText2);
        IH = client.new InputHandler();

        Thread th = new Thread(client);
        th.start();

        Parent root = FXMLLoader.load(getClass().getResource("Chatting.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.show();

    }
}