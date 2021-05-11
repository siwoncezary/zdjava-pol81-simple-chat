package pl.sda.chat;
import pl.sda.chat.server.ChatServer;
import java.io.IOException;

public class ChatApp {
    public static void main(String[] args) throws IOException {
        new ChatServer(5555).start();
    }
}
