package com.example.chatapp_ui;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Server implements Runnable {

    private final ArrayList<ConnectionHandler> serverConnections;
    private ServerSocket serverHost;
    private boolean shutDown;
    private ExecutorService serverThreadPool;

    public Server() {
        serverConnections = new ArrayList<>();
        shutDown = false;
    }

    public static void main(String[] args) {
        com.example.chatapp_ui.Server server = new com.example.chatapp_ui.Server();
        server.run();
    }

    @Override
    public void run() {

        try {
            serverHost = new ServerSocket(9999);
            serverThreadPool = Executors.newCachedThreadPool();

            while (!shutDown) {
                Socket client = serverHost.accept();
                ConnectionHandler handler = new ConnectionHandler(client);

                serverConnections.add(handler);
                serverThreadPool.execute(handler);
            }

        } catch (Exception e) {
            e.printStackTrace();
            shutdown();
        }
    }

    public void sendToAllUsers(String message) {
        for (ConnectionHandler connection : serverConnections)
            if (connection != null)
                connection.sendMessage(message);
    }

    public void shutdown() {
        try {
            shutDown = true;
            serverThreadPool.shutdown();

            if (!serverHost.isClosed())
                serverHost.close();

            for (ConnectionHandler connection : serverConnections)
                connection.shutdown();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ConnectionHandler implements Runnable {

        private final Socket client;
        DateTimeFormatter dtf;
        LocalDateTime now;
        FileWriter fileWriter;
        BufferedWriter bufferedWriter;
        private BufferedReader inputReader;
        private PrintWriter outputReader;

        public ConnectionHandler(Socket client) throws IOException {
            this.client = client;
            dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            now = LocalDateTime.now();
            fileWriter = new FileWriter("logs.txt", true);
            bufferedWriter = new BufferedWriter(fileWriter);
        }


        @Override
        public void run() {
            try {
                outputReader = new PrintWriter(client.getOutputStream(), true);
                inputReader = new BufferedReader(new InputStreamReader(client.getInputStream()));


                String userName = inputReader.readLine();
                String userMessage;


                System.out.println(userName + " connected.");
                fileWriter.write(userName + " connected at " + dtf.format(now) + "\n");
                sendToAllUsers(userName + " joined the chat.");

                while ((userMessage = inputReader.readLine()) != null) {
                    if (userMessage.startsWith("/nick ")) {
                        String[] messageSplit = userMessage.split(" ", 2);
                        if (messageSplit.length == 2) {
                            sendToAllUsers(userName + " renamed to " + messageSplit[1]);
                            fileWriter.write(userName + " renamed to " + messageSplit[1] + " at " + dtf.format(now) + "\n");
                            userName = messageSplit[1];
                            outputReader.println("(( Successfully changed name to " + userName + " ))");
                        } else {
                            outputReader.println("No name provided.");
                        }
                    } else if (userMessage.startsWith("/quit")) {
                        sendToAllUsers(userName + " left the server.");
                        System.out.println(userName + " left the server.");
                        fileWriter.write(userName + " left the server at " + dtf.format(now) + "\n");

                        shutdown();
                    } else {
                        sendToAllUsers(userName + ": " + userMessage);
                    }
                }

            } catch (IOException e) {
                shutdown();

            }
        }

        public void sendMessage(String message) {
            outputReader.println(message);
        }


        public void shutdown() {
            try {
                inputReader.close();
                outputReader.close();
                fileWriter.close();
                bufferedWriter.close();

                if (!client.isClosed())
                    client.close();
            } catch (IOException ignored) {

            }

        }

    }

}
