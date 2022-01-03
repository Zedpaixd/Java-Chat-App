package com.example.chatapp_ui.controllers;

import com.example.chatapp_ui.ChatAppEXE;
import com.example.chatapp_ui.UnmatchingCredentialsException;
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
import java.io.*;
import java.sql.*;
import java.util.Objects;
import java.util.Scanner;

public class LoginController {

    @FXML
    public TextField nickname;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Label errorIGuess;

    static Connection connection = null;
    static String DBName = "mjca";
    static String DBurl = "jdbc:mysql://localhost:3306/" + DBName;
    static String DBusername = "";
    static String DBpassword = "";

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

    public void login(ActionEvent event) {

        /*  F I L E   V E R S I O N
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
        }*/

        boolean match = false;

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
            PreparedStatement ps = connection.prepareStatement("select * from accounts");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (Objects.equals(username.getText(), rs.getString(2)) && Objects.equals(password.getText(), rs.getString(3)))
                    match = true;
            }
            if (match) {
                nicknamePrompt(event);
            }
            if (!match) {
                errorIGuess.setText("Wrong Username + Password combination.");
                match = false;
                throw new UnmatchingCredentialsException("Wrong username and password combination.");
            }
        }
        catch (IOException | ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException | UnmatchingCredentialsException e)
        {
            e.printStackTrace();
        }

    }
}

