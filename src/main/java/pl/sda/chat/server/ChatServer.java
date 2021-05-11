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
import java.util.logging.Logger;

public class ChatServer {
    private final ServerSocket serverSocket;
    private final ExecutorService service;
    private final List<ChatClient> clients;
    private Logger logger = Logger.getLogger(ChatServer.class.getName());
    public ChatServer(int port) throws IOException {
        serverSocket = new ServerSocket(5555);
        logger.info("Socket server at " + serverSocket.getInetAddress() +":" + serverSocket.getLocalPort());
        service = Executors.newFixedThreadPool(20);
        clients = Collections.synchronizedList(new ArrayList<>());
    }
    public void start(){
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                //log o połączonym kliencie
                ChatClient client = new ChatClient(clientSocket, this);
                clients.add(client);
                service.execute(client);
            } catch (IOException e) {
                logger.warning("Can't connect to client: " + e.getMessage());
            }
        }
    }
    //Dodać wzorzec command aby nasz obsługiwał polecenie:
    //login: username, password
    //send-to-all: message
    //send-to: username, message
    public void process(String rawMessage, ChatClient origin){
        //log o przetwarzanej wiadomości od klienta
        clients.forEach(client ->{
            if(client == origin){
                return;
            }
            client.send(rawMessage);
        });
    }
}
