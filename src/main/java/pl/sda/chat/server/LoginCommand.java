package pl.sda.chat.server;
import pl.sda.chat.client.ChatClient;

/**
 * LOGIN username password
 * Przykład poprawnego polecenia
 * LOGIN ewa 1234
 * Przykład błędnego polecenia
 * LOGIN ewa
 */
public class LoginCommand implements Command{
    public static final String PREFIX_LOGIN = "LOGIN";
    private static final String LOGIN_ERROR_INVALID_PARAMETERS = "Invalid number of parameters!";
    private static final String LOGIN_ERROR_INVALID_LOGIN_DATA = "Invalid username or password. Can,t login!";
    private ChatServer server;
    private ChatClient client;
    private String rawMessage;

    public LoginCommand(ChatServer server, ChatClient client, String rawMessage) {
        this.server = server;
        this.client = client;
        this.rawMessage = rawMessage;
    }

    @Override
    public void execute() {
        String[] tokens = rawMessage.split(" ");
        if (tokens.length != 3){
            client.send(LOGIN_ERROR_INVALID_PARAMETERS);
            server.disconnectClientAferDelay(client);
            return;
        }
        String username = tokens[1];
        String password = tokens[2];
        if (server.login(username, password, client)){
            client.send("Welcome " + username + "!");
        } else {
            client.send(LOGIN_ERROR_INVALID_LOGIN_DATA);
        }
    }
}
