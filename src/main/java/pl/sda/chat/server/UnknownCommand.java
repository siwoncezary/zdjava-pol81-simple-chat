package pl.sda.chat.server;

import pl.sda.chat.client.ChatClient;

/**
 * Klasa reprezentuje wszystkie nierozpoznane przez nasz serwer polecenia
 */
public class UnknownCommand implements Command{
    private final ChatServer server;
    private final ChatClient client;

    public UnknownCommand(ChatServer server, ChatClient client) {
        this.server = server;
        this.client = client;
    }

    @Override
    public void execute() {
        client.send("You send unknown command!");
        if (!client.isLogged()){
            server.disconnectClientAferDelay(client);
        }
    }
}

