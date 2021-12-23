package com.example.chatapp_ui.controlles;

import com.example.chatapp_ui.ChatAppEXE;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class RegisterController {


    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label errorIGuess;

    public void MainScreen(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(ChatAppEXE.class.getResource("MainPage.fxml"));
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
        if (!exists) {
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


}
