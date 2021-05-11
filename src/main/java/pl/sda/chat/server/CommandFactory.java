package pl.sda.chat.server;

import pl.sda.chat.client.ChatClient;

public class CommandFactory {
    static final String LOGIN = "LOGIN:";
    static final String SEND_TO_ALL = "SEND:";
    static final String SEND_DM = "SEND_DM:";
    public static Command buildCommand(String rawMessage,
                                       ChatServer server,
                                       ChatClient client){
        if (rawMessage.startsWith(LOGIN)){
            return new LoginCommand(server, client, rawMessage);
        }
        if (rawMessage.startsWith(SEND_TO_ALL)){
            //dopisać klasę komendy SEND
            return null;
        }
        if(rawMessage.startsWith(SEND_DM)){
            //dopisać klasę komendy SEND_MD
            return null;
        }
        return null;
    }
}
