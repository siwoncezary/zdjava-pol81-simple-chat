package pl.sda.chat.configuration;

import java.util.Map;
import java.util.Set;

public class Configuration {
    private int port;
    private int maxClients;
    private Map<String ,String> users;
    private Set<String> channels;

    public Configuration() {
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getMaxClients() {
        return maxClients;
    }

    public void setMaxClients(int maxClients) {
        this.maxClients = maxClients;
    }

    public Map<String, String> getUsers() {
        return users;
    }

    public void setUsers(Map<String, String> users) {
        this.users = users;
    }

    public Set<String> getChannels() {
        return channels;
    }

    public void setChannels(Set<String> channels) {
        this.channels = channels;
    }
}
