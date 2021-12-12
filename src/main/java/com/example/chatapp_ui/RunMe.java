package com.example.chatapp_ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.util.EventObject;
import java.util.Objects;

public class RunMe {

    @FXML private TextField username;
    @FXML private PasswordField password;
    @FXML private Label errorIGuess;
    @FXML private TextField nickname;
    @FXML private Button closeWindow;

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

    public void nicknamePrompt(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("nickname prompt.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void MainScreen(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void register(ActionEvent event) throws IOException{

        boolean exists = false;

        FileWriter fw = new FileWriter("accounts.txt", true);

        BufferedReader fr = new BufferedReader(new FileReader("accounts.txt"));
        String line = fr.readLine();
        while (line != null)
        {
            String[] splitLine = line.trim().split("\\s+");

            //System.out.println("." + username.getText() + ". | ." + splitLine[0] + ".\n");

            if (Objects.equals(username.getText(), splitLine[0]))
                exists = true;

            line = fr.readLine();
        }
        if (!exists)
        {
            fw.write(username.getText() + " " + password.getText() + "\n");
            MainScreen(event);
        }
        if (exists)
        {
            errorIGuess.setText("Username already in database.");
            exists = false;
        }

        fw.close();
        fr.close();
    }

    public void login(ActionEvent event) throws IOException{

        boolean match = false;

        BufferedReader fr = new BufferedReader(new FileReader("accounts.txt"));
        String line = fr.readLine();
        while (line != null)
        {
            String[] splitLine = line.trim().split("\\s+");

            //System.out.println("." + username.getText() + ". | ." + splitLine[0] + ".\n");

            if (Objects.equals(username.getText(), splitLine[0]) && Objects.equals(password.getText(), splitLine[1]))
                match = true;

            line = fr.readLine();
        }
        if (match)
        {
            nicknamePrompt(event);
        }
        if (!match)
        {
            errorIGuess.setText("Wrong Username + Password combination.");
            match = false;
        }

    }

    public void closeButtonAction(ActionEvent event){
        // get a handle to the stage
        Stage stage = (Stage) closeWindow.getScene().getWindow();
        // do what you have to do

        stage.close();
    }

    public void ready(ActionEvent event) throws IOException {

        Client client = new Client(nickname.getText());
        Thread th = new Thread(client);
        th.start();

        Parent root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }


}