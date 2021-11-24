package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{

    private final ArrayList<ConnectionHandler> serverConnections;
    private ServerSocket serverHost;
    private boolean shutDown;
    private ExecutorService serverThreadPool;

    public Server()
    {
        serverConnections = new ArrayList<>();
        shutDown = false;
    }


    @Override
    public void run(){

        try {
            serverHost = new ServerSocket(9999);
            serverThreadPool = Executors.newCachedThreadPool();

            while (!shutDown)
            {
                Socket client = serverHost.accept();
                ConnectionHandler handler = new ConnectionHandler(client);

                serverConnections.add(handler);
                serverThreadPool.execute(handler);
            }


        }
        catch (Exception e) {
            e.printStackTrace();
            shutdown();
        }
    }

    public void sendToAllUsers(String message){
        for (ConnectionHandler connection: serverConnections)
            if (connection != null)
                connection.sendMessage(message);


    }

    public void shutdown()
    {
        try
        {
            shutDown = true;
            serverThreadPool.shutdown();

            if (!serverHost.isClosed())
                serverHost.close();

            for (ConnectionHandler connection : serverConnections)
                connection.shutdown();


        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }


    class ConnectionHandler implements Runnable{

        private final Socket client;
        private BufferedReader inputReader;
        private PrintWriter outputReader;

        public ConnectionHandler(Socket client)
        {
            this.client = client;
        }


        @Override
        public void run()
        {
            try
            {
                outputReader = new PrintWriter(client.getOutputStream(),true);
                inputReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                outputReader.println("Choose a name: ");

                String userName = inputReader.readLine();
                String userMessage;

                System.out.println(userName + " connected.");

                sendToAllUsers(userName + " joined the chat.");

                while ((userMessage = inputReader.readLine()) != null)
                {
                    if (userMessage.startsWith("/nick "))
                    {
                        String[] messageSplit = userMessage.split(" ", 2);
                        if (messageSplit.length == 2)
                        {
                            sendToAllUsers(userName + " renamed to " + messageSplit[1]);

                            userName = messageSplit[1];
                            outputReader.println("Successfully changed name to " + userName);
                        }
                        else
                        {
                            outputReader.println("No name provided.");
                        }
                    }
                    else if (userMessage.startsWith("/quit"))
                    {
                        sendToAllUsers(userName + " left the server.");
                        System.out.println(userName + " left the server.");

                        shutdown();
                    }
                    else
                    {
                        sendToAllUsers(userName + ": " + userMessage);
                    }
                }
            }
            catch (IOException e){
                //e.printStackTrace();
                shutdown();
            }
        }

        public void sendMessage(String message)
        {
            outputReader.println(message);
        }


        public void shutdown()
        {
            try
            {
                inputReader.close();
                outputReader.close();

                if (!client.isClosed())
                    client.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }

    }

    public static void main(String[] args)
    {

        Server server = new Server();
        server.run();

    }

}
