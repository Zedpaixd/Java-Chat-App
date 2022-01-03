package com.example.chatapp_ui;

public class CredentialsTooShortException extends Exception{
    public CredentialsTooShortException(String errorMessage)
    {
        super(errorMessage);
    }
}
