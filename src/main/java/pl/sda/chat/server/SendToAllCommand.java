package pl.sda.chat.server;

import pl.sda.chat.client.ChatClient;

public class SendToAllCommand implements Command{
    public static final String PREFIX_SEND_TO_ALL = "SEND-TO-ALL";
    private final ChatServer server;
    private final ChatClient client;
    private final String rawMessage;

    public SendToAllCommand(ChatServer server, ChatClient client, String message) {
        this.server = server;
        this.client = client;
        this.rawMessage = message;
    }

    @Override
    public void execute() {
        if (!client.isLogged()){
            client.send("You can't send message to all. Your are not logged");
            return ;
        }
        String message = rawMessage.substring(PREFIX_SEND_TO_ALL.length());
        server.getClients().stream().filter(c -> c != client).forEach(c ->
                c.send(client.getUsername() + ": " + message));
    }
}
