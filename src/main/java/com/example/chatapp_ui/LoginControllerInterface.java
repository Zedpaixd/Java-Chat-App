package com.example.chatapp_ui;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.SQLException;

public interface LoginControllerInterface {
    public void nicknamePrompt(ActionEvent event) throws IOException;
    public void show(ActionEvent event) throws IOException;
    public void login(ActionEvent event) throws SQLException;
}
