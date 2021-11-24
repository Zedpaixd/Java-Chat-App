package com.company;

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


    @Override
    public void run()
    {
        try
        {
            client = new Socket("127.0.0.1",9999);  // --------------
            clientOutput = new PrintWriter(client.getOutputStream(),true);
            clientInput = new BufferedReader(new InputStreamReader(client.getInputStream()));

            InputHandler inHandler = new InputHandler();
            Thread clientThread = new Thread(inHandler);
            clientThread.start();

            String clientMessage;
            while ((clientMessage = clientInput.readLine()) != null)
            {
                System.out.println(clientMessage);
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

        @Override
        public void run(){
            try
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
            }
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }

}
