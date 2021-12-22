package com.example.chatapp_ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.function.Consumer;

public class Client implements Runnable {


    private Socket client;
    private BufferedReader clientInput;
    private PrintWriter clientOutput;
    private boolean shutDown;
    private Consumer<String> chat;
    String name;


    public Client(String str, Consumer<String> chat) {
        super();
        this.chat = chat;
        this.name = str;
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

            System.out.println("Before while");
            while ((clientMessage = clientInput.readLine()) != null) {
                chat.accept(clientMessage);
                System.out.println("In while with message: " + clientMessage);
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


    class InputHandler implements Runnable {

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
            /*try
            {
                BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
                while (!shutDown)
                {
                    String userMessage = inputReader.readLine();
                    if (userMessage.equals("/quit"))
                    {
                        clientOutput.println(userMessage);
                        inputReader.close();
                        shutdown();
                    }
                    else
                    {
                        clientOutput.println(userMessage);

                    }
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
                shutdown();
            }*/
        }
    }
}
