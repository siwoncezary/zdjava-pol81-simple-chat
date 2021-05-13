package pl.sda.chat.server;

import pl.sda.chat.client.ChatClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ChatServer {
    private final ServerSocket serverSocket;
    private final ExecutorService service;
    private final ScheduledExecutorService scheduler;
    private final List<ChatClient> clients;
    private static final Logger logger = Logger.getLogger(ChatServer.class.getName());
    private final Map<String, String> users;

    public ChatServer(int port, int maxClients, Map<String, String> users) throws IOException {
        serverSocket = new ServerSocket(port);
        this.users = users;
        service = Executors.newFixedThreadPool(maxClients);
        clients = Collections.synchronizedList(new ArrayList<>());
        scheduler = Executors.newSingleThreadScheduledExecutor();
        logger.info("Socket server at " + serverSocket.getInetAddress() + ":" + serverSocket.getLocalPort());
    }

    public void start() {
        while (true) {
            try {
                ChatClient client = new ChatClient(serverSocket.accept(), this);
                clients.add(client);
                service.execute(client);
            } catch (IOException e) {
                logger.warning("Can't connect to client: " + e.getMessage());
            }
        }
    }

    /**
     * Metoda wywoływana przez obiekty klasy ChatClient, którzy przekazują przysłane dane od klientów
     * @param rawMessage przesłany łańcuch, w którym zawarte są polecenia i ich parametry
     * @param origin obiekt reprezentujący połączenie z klientem, który nadesłał łańcuch rawMessage
     */
    public synchronized void process(String rawMessage, ChatClient origin) {
        //TODO Dopisać obiekty interfejsu Command: SEND i SEND_MD, tak, aby niezalogowani użytkownicy nie dostawali wiadomości
        //Metoda fabryczna klasy CommandFactory zwraca obiekty poleceń, na których wywołujemy metodę wykonania polecenia
        CommandFactory.buildCommand(rawMessage, this, origin).execute();
    }

    /**
     * Metoda loguje klienta na serwerze,
     *
     * @param username nazwa użytkownika
     * @param password hasło
     * @param client   obiekt klienta, który loguje się
     * @return true, jeśli klient został zalogowany
     */
    public boolean login(String username, String password, ChatClient client) {
        if (users.get(username) != null && users.get(username).equals(password)) {
            logger.info("Client " + username + " login ");
            client.setLogged(true);
            client.setUsername(username);
            return true;
        }
        return false;
    }

    /**
     * Metoda zamyka połączenie z klientem po 2 sekundach
     *
     * @param client obiekt klienta, którego gniazdo należy zamknąć
     */
    public void disconnectClientAferDelay(ChatClient client) {
        scheduler.schedule(() -> {
            try {
                client.getClientSocket().close();
                clients.remove(client);
            } catch (IOException e) {
                logger.warning("Can't close connection for  " + client.getClientDescription());
            }
        }, 2000, TimeUnit.MILLISECONDS);
    }

    public void disconnectClientNow(ChatClient client) {
        try {
            client.getClientSocket().close();
            clients.remove(client);
        } catch (IOException e) {
            logger.warning("Can't close connection for  " + client.getClientDescription());
        }
    }

    public String getServerDescription() {
        return serverSocket.getInetAddress() + ":" + serverSocket.getLocalPort();
    }

    /**
     * Metoda zwraca kolekcję z wszystkimi połączonymi klientami
     * @return
     */
    public List<ChatClient> getClients(){
        return clients;
    }
}
