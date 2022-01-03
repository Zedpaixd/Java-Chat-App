package com.example.chatapp_ui;

public class UnmatchingCredentialsException extends Exception {
    public UnmatchingCredentialsException(String errorMessage)
    {
        super(errorMessage);
    }
}
