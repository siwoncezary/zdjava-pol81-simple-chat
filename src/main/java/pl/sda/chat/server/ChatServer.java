package pl.sda.chat.server;

import pl.sda.chat.client.ChatClient;
import pl.sda.chat.configuration.Configuration;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class ChatServer {
    private final ServerSocket serverSocket;
    private final ExecutorService service;
    private final List<ChatClient> clients;
    private Logger logger = Logger.getLogger(ChatServer.class.getName());
    private final Map<String, String> users = new HashMap<>(){
        {
            put("EWA","1234");
            put("KAROL", "ABCD");
            put("LENA", "abcd");
        }
    };
    public ChatServer(int port, int maxClients) throws IOException {
        serverSocket = new ServerSocket(port);
        logger.info("Socket server at " + serverSocket.getInetAddress() +":" + serverSocket.getLocalPort());
        service = Executors.newFixedThreadPool(maxClients);
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
    public void process(String rawMessage, ChatClient origin){
        //Dopisać obiekty interfejsu Command: SEND i SEND_MD, tak, aby niezalogowani użytkownicy nie dostawali wiadomości
        Command command = CommandFactory.buildCommand(rawMessage, this, origin);
        command.execute();
//        clients.forEach(client ->{
//            if(client == origin){
//                return;
//            }
//            client.send(rawMessage);
//        });
    }

    public boolean login(String username, String password){
        if (users.get(username) != null){
            return users.get(username).equals(password);
        }
        return false;
    }

    public void loginClient(ChatClient client){
        client.setLogged(true);
    }
}
