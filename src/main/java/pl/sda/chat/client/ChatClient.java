package pl.sda.chat.client;

import pl.sda.chat.server.ChatServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

public class ChatClient implements Runnable {
    private final Socket clientSocket;
    private PrintWriter output;
    private Scanner input;
    private final ChatServer server;
    private boolean isLogged;

    Logger logger = Logger.getLogger(ChatClient.class.getName());
    public ChatClient(Socket clientSocket, ChatServer server) throws IOException {
        this.clientSocket = clientSocket;
        output = new PrintWriter(clientSocket.getOutputStream(), true);
        input = new Scanner(clientSocket.getInputStream());
        this.server = server;
    }

    @Override
    public void run() {
        while (input.hasNextLine()) {
            String line = input.nextLine();
            server.process(line, this);
        }
    }

    public void send(String message){
        output.println("Server echo: " + message);
        logger.info("Message: " + message + ", send to: " + clientSocket.getInetAddress() +":" + clientSocket.getPort());
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean logged) {
        isLogged = logged;
    }
}
