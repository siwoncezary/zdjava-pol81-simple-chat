package pl.sda.chat.configuration;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import pl.sda.chat.ChatApp;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static java.util.Map.entry;

/**
 * Przykład wzorca singleton w postaci enum.
 */
public enum Configuration {
    SERVER;
    //stała ze ścieżką do pliku konfiguracyjnego w katalogu zasobów (resources) aplikacji
    static final String CONFIGURATION_FILE = "..\\..\\..\\configuration.yaml";

    static final String CONFIGURATION_FILE_MAC = "../../../configuration.yaml";

    /**
     * obiekt klasy wewnętrznej z danymi odczytanymi z plik yaml
     */
    private ConfigurationData configurationData;

    /**
     * Konstruktor stałej enum'a, buduje obiekt klasy wewnętrznej, którego pola odpowiadają strukturze pliku
     * konfiguracyjnego yaml.
     */
    Configuration(){
        YAMLMapper yamlMapper = new YAMLMapper();
        InputStream resourceAsStream = ChatApp.class.getResourceAsStream(CONFIGURATION_FILE);
        try {
            configurationData = yamlMapper.readValue(resourceAsStream, ConfigurationData.class);
        } catch (IOException e) {
            //Tworzymy domyślną konfigurację, na wypadek gdyby nie było pliku konfiguracyjnego lub byłby niepoprawnie zbudowany
            configurationData = new ConfigurationData();
            configurationData.maxClients = 10;
            configurationData.port = 5000;
            configurationData.channels = new HashSet<>(List.of("general", "default"));
            configurationData.users = new HashMap<>(Map.ofEntries(entry("admin","1234")));
        }
    }

    /**
     * Klasa wewnętrzna z polami zgodnym z plikiem yaml
     * Klasa jest prywatna, aby nie można było tworzyć obiektów poza klasą Configuration
     * Mapper YAML potrzebuje konstruktora domyślnego i seterów.
     */
    private static class  ConfigurationData {
        private int port;
        private int maxClients;
        private Map<String, String> users;
        private Set<String> channels;

        public ConfigurationData() {
        }

        public void setPort(int port) {
            this.port = port;
        }
        public void setMaxClients(int maxClients) {
            this.maxClients = maxClients;
        }
        public void setUsers(Map<String, String> users) {
            this.users = users;
        }
        public void setChannels(Set<String> channels) {
            this.channels = channels;
        }
    }

    /**
     * Metody publiczne zwracające dane z pliku konfiguracyjnego.
     * Klasa zewnętrzna, czyli enum, ma dostęp do wszystkich składowych klasy wewnętrznej.
     */

    public int port(){
        return  configurationData.port;
    }

    public int maxClients(){
        return configurationData.maxClients;
    }

    public Map<String, String> users(){
        return configurationData.users;
    }

    public Set<String> channels(){
        return configurationData.channels;
    }
}
