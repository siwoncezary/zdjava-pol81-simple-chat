package pl.sda.chat;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import pl.sda.chat.configuration.Configuration;
import pl.sda.chat.server.ChatServer;
import java.io.IOException;
import java.io.InputStream;

public class ChatApp {
    static final String CONFIGURATION_FILE = "..\\..\\..\\configuration.yaml";
    static final String CONFIGURATION_FILE_MAC = "../../../configuration.yaml";
    public static void main(String[] args) throws IOException {
        YAMLMapper yamlMapper = new YAMLMapper();
        InputStream resourceAsStream = ChatApp.class.getResourceAsStream(CONFIGURATION_FILE);
        Configuration configuration = yamlMapper.readValue(resourceAsStream, Configuration.class);
        new ChatServer(configuration.getPort(), configuration.getMaxClients()).start();
    }
}
