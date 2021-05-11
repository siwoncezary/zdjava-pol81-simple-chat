package pl.sda.chat.client;

import pl.sda.chat.server.ChatServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient implements Runnable {
    private final Socket clientSocket;
    private PrintWriter output;
    private Scanner input;
    private final ChatServer server;

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
    }

    public Socket getClientSocket() {
        return clientSocket;
    }
}
