package pl.sda.chat.server;

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
    private final List<Socket> clients;
    public ChatServer(int port) throws IOException {
        serverSocket = new ServerSocket(5555);
        service = Executors.newFixedThreadPool(20);
        clients = Collections.synchronizedList(new ArrayList<>());
    }

    public void start(){
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                clients.add(clientSocket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
