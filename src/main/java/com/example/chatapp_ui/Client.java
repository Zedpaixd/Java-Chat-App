package com.example.chatapp_ui;

import com.example.chatapp_ui.controlles.ChattingController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Thread {


    private String name;
    private Socket client;
    private BufferedReader clientInput;
    private PrintWriter clientOutput;
    private ChattingController controller;
    private boolean shutDown;

    public Client(String str, ChattingController controller) {
        super();
        this.name = str;
        this.controller = controller;
    }

    public void writeHistory(BufferedReader clientInput) throws IOException {
        String clientMessage;

        while ((clientMessage = clientInput.readLine()) != null) {
            System.out.println(clientMessage);
        }
    }

    @Override
    public void run() {
        try {
            client = new Socket("127.0.0.1", 9999);  // --------------
            clientOutput = new PrintWriter(client.getOutputStream(), true);
            clientInput = new BufferedReader(new InputStreamReader(client.getInputStream()));

            clientOutput.println(name);

            InputHandler inHandler = new InputHandler();
            Thread clientThread = new Thread(inHandler);
            clientThread.start();

//            writeHistory(clientInput);
            String clientMessage;

            while ((clientMessage = clientInput.readLine()) != null) {
                controller.appendText(clientMessage);
            }
        } catch (IOException e) {
            shutdown();
        }
    }

    public void shutdown() {
        shutDown = true;
        try {
            clientInput.close();
            clientOutput.close();
            if (!client.isClosed())
                client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public class InputHandler implements Runnable {

        public void sendMessage(String userMessage) {
            if (userMessage.equals("/quit")) {
                clientOutput.println(userMessage);
                shutdown();
            } else {
                clientOutput.println(userMessage);
            }
        }

        @Override
        public void run() {

        }
    }
}
