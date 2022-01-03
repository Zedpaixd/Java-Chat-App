package com.example.chatapp_ui;

public class ExistingUsernameException extends Exception{
    public ExistingUsernameException(String errorMessage)
    {
        super(errorMessage);
    }
}
