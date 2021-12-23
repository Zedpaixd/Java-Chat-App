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
import java.io.IOException;
import java.util.Objects;

public class LoginController {

    @FXML
    public TextField nickname;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label errorIGuess;

    public TextField getNickname() {
        return nickname;
    }

    public void nicknamePrompt(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(ChatAppEXE.class.getResource("nickname prompt.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void show(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(ChatAppEXE.class.getResource("Chatting.fxml"));

        Parent root = loader.load();

        ChattingController controller = loader.getController();
        controller.start(nickname.getText());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
}
