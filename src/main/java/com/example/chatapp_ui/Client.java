package com.example.chatapp_ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable{


    private Socket client;
    private BufferedReader clientInput;
    private PrintWriter clientOutput;
    private boolean shutDown;
    String name;
    static String history = "";


    public Client(String str)
    {
        super();
        this.name = str;
    }

    public void appendToHistory(String a)
    {
        history.concat(a);
        history.concat("\n");
    }

    @Override
    public void run()
    {
        try
        {
            client = new Socket("127.0.0.1",9999);  // --------------
            clientOutput = new PrintWriter(client.getOutputStream(),true);
            clientInput = new BufferedReader(new InputStreamReader(client.getInputStream()));

            clientOutput.println(name);

            InputHandler inHandler = new InputHandler();
            Thread clientThread = new Thread(inHandler);
            clientThread.start();

            String clientMessage;

            while ((clientMessage = clientInput.readLine()) != null)
            {
                System.out.println(clientMessage);     // Server messages
                appendToHistory(clientMessage);
            }
        }
        catch (IOException e)
        {
            //e.printStackTrace();
            shutdown();
        }
    }

    public void shutdown()
    {
        shutDown = true;
        try
        {
            clientInput.close();
            clientOutput.close();
            if(!client.isClosed())
                client.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }


    class InputHandler implements Runnable {


        public void sendMessage(String userMessage) throws IOException
        {
            if (userMessage.equals("/quit"))
            {
                clientOutput.println(userMessage);
                shutdown();
            }
            else
            {
                clientOutput.println(userMessage);
            }
        }


        @Override
        public void run(){
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

    /*public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }*/

}
