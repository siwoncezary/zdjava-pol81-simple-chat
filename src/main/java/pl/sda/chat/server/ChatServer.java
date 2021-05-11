package pl.sda.chat.server;

import pl.sda.chat.client.ChatClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
    private final ServerSocket serverSocket;
    private final ExecutorService service;
    private final List<ChatClient> clients;
    public ChatServer(int port) throws IOException {
        serverSocket = new ServerSocket(5555);
        service = Executors.newFixedThreadPool(20);
        clients = Collections.synchronizedList(new ArrayList<>());
    }
    public void start(){
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                ChatClient client = new ChatClient(clientSocket, this);
                clients.add(client);
                service.execute(client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void process(String rawMessage, ChatClient origin){

    }
}
