package com.example.chatapp_ui.controllers;

import com.example.chatapp_ui.*;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class RegisterController implements RegisterControllerInterface {


    /*static Connection connection = null;
    static String DBName = "mjca";
    static String DBurl = "jdbc:mysql://localhost:3306/" + DBName;
    static String DBusername = "";
    static String DBpassword = "";*/

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

    public void register(ActionEvent event) throws SQLException, IOException {

        /*  F I L E   V E R S I O N
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
        fr.close();*/

        /* D A T A B A S E   C O N N E C T I O N

        try{
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
                if (Objects.equals(username.getText(), rs.getString(2)))
                    exists = true;
            }
            if (!exists) {
                if (username.getText().length() < 3 || password.getText().length() < 3)
                    throw new CredentialsTooShortException("Username and password must both be of 3 or more letters!");
                else
                {
                    ps = connection.prepareStatement("insert into accounts(username,password) values ('" + username.getText() + "','" + password.getText() + "');");
                    ps.executeUpdate();
                    MainScreen(event);
                }

            }
            if (exists) {
                errorIGuess.setText("Username already in database.");
                exists = false;
                throw new ExistingUsernameException("Username already in database");

            }
        }
        catch (IOException | ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException | ExistingUsernameException | CredentialsTooShortException e)
        {
            e.printStackTrace();
        }*/

        DBHandler inDB = new DBHandler();


        try
        {
            if (username.getText().length() < 3 || password.getText().length() < 3)

                throw new CredentialsTooShortException("Username and password must both be of 3 or more letters!");

            else
            {
                boolean addable = inDB.register(username.getText(), password.getText());

                if (addable)
                {
                    MainScreen(event);
                }
                else
                {
                    throw new ExistingUsernameException("Username already in database");
                }
            }
        }
        catch (CredentialsTooShortException e)
        {
            e.printStackTrace();
            errorIGuess.setText("User and Pass must be of at least 3 letters!");
        }
        catch (ExistingUsernameException e)
        {
            e.printStackTrace();
            errorIGuess.setText("Username already in database.");
        }



    }


}
