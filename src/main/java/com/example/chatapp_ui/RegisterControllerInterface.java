package com.example.chatapp_ui;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.SQLException;

public interface RegisterControllerInterface {
    public void register(ActionEvent event) throws SQLException, IOException;
    public void MainScreen(ActionEvent event) throws IOException;

}
