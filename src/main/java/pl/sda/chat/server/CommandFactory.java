package pl.sda.chat.server;

import pl.sda.chat.client.ChatClient;

/**
 * Przykładowa klasa z metodą fabryczną, która zwraca obiekty interfejsu Command.
 */
public class CommandFactory {
    /**
     * Metoda statyczna budująca obiekty poleceń na podstawie prefiksu surowego łańcucha przesłanego do klienta
     * @param rawMessage wiadomość od klienta
     * @param server obiekt serwera
     * @param client obiekt połączenia z klientem, który przysłał łańcuch rawMessage
     * @return obiekt polecenia, który wykonuje odpowiednie operacje na obiektach serwera i klienta
     */
    public static Command buildCommand(String rawMessage,
                                       ChatServer server,
                                       ChatClient client){
        if (rawMessage.startsWith(LoginCommand.PREFIX_LOGIN)){
            return new LoginCommand(server, client, rawMessage);
        }
        if (rawMessage.startsWith(SendToAllCommand.PREFIX_SEND_TO_ALL)){
            return new SendToAllCommand(server, client, rawMessage);
        }
        return new UnknownCommand(server, client);
    }
}
