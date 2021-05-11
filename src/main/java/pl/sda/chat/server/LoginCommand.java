package pl.sda.chat.server;

import pl.sda.chat.client.ChatClient;
//Format protokołu naszego chat servera
//POLECENIE: parametry

/**
 * LOGIN: username password
 * LOGIN: ewa 1234
 * Błędne polecenie
 * LOGIN: ewa
 */
public class LoginCommand implements Command{
    private ChatServer server;
    private ChatClient client;
    private String rawMessage;

    public LoginCommand(ChatServer server, ChatClient client, String rawMessage) {
        this.server = server;
        this.client = client;
        this.rawMessage = rawMessage;
    }

    @Override
    public boolean execute() {
        String[] tokens = rawMessage.substring(6).split(" ");
        if (tokens.length != 2){
            return false;
        }
        String username = tokens[0];
        String password = tokens[1];
        if (server.login(username, password)){
            server.loginClient(client);
        }
        return true;
    }
}
