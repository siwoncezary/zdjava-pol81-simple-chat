package pl.sda.chat.client;

import pl.sda.chat.server.ChatServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Klasa reprezentuje połączenie z klientem i jego dane
 */
public class ChatClient implements Runnable {
    private final Socket clientSocket;
    private PrintWriter output;
    private Scanner input;
    private final ChatServer server;
    private String username;
    private boolean isLogged;
    private static final Logger logger = Logger.getLogger(ChatClient.class.getName());

    public ChatClient(Socket clientSocket, ChatServer server) throws IOException {
        this.clientSocket = clientSocket;
        output = new PrintWriter(clientSocket.getOutputStream(), true);
        input = new Scanner(clientSocket.getInputStream());
        this.server = server;
    }

    /**
     * Metoda działająca w osobnym wątku. Odbiera surowe wiadomości (POLECENIE <parametry>)
     * i przekazuje je do serwera wywołując metodę process
     */
    @Override
    public void run() {
        while (input.hasNextLine()) {
            String line = input.nextLine();
            server.process(line, this);
        }
    }

    /**
     * Metoda wysyła wiadomość do klienta
     * @param message łańcuch wiadomości wysyłany do klienta
     */
    public void send(String message){
        output.println("Server echo: " + message);
        logger.info("Message: " + message + ", send to: " + clientSocket.getInetAddress() +":" + clientSocket.getPort());
    }

    /**
     * Metoda zwraca gniazdo klienta
     * @return gniazdo klienta
     */
    public Socket getClientSocket() {
        return clientSocket;
    }

    /**
     * Zwraca informację, czy klient się zalogował
     * @return true, jeśli zalogowany
     */
    public boolean isLogged() {
        return isLogged;
    }

    /**
     * Metoda wywoływana przez serwer, gdy klient zostanie zalogowany
     * @param logged true lub false
     */
    public void setLogged(boolean logged) {
        isLogged = logged;
    }

    /**
     * Zwraca nazwę użytkownika
     * @return nazwa użytkownika, nick
     */
    public String getUsername() {
        return username;
    }

    /**
     * Serwer ustawia nazwę użytkownika po zalogowaniu
     * @param username nazwa użytkownika
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Zwraca opis gniazda klienta
     * @return łańcuch z ip i portem
     */
    public String getClientDescription(){
        return clientSocket.getInetAddress()+":"+clientSocket.getPort();
    }
}
