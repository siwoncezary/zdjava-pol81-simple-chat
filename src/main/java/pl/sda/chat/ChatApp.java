package pl.sda.chat;
import pl.sda.chat.configuration.Configuration;
import pl.sda.chat.server.ChatServer;
import java.io.IOException;

/**
 * Przykład aplikacji prostego chata, działającego na gniazdach z przesyłaniem
 * komunikatów otwartym tekstem.
 * Zadania do samodzielnego wykonania
 * 1. Popraw klasę LoginCommand, aby nie wykonywać logowania, jeśli klient jest już zalogowany.
 * 2. Zdefiniuj klasę interfejsu Command, która realizuje polecenie wylogowania: LOGOUT
 *    Po wylogowaniu serwer zrywa połączenie i usuwa obiekt połączenia z klientem z kolekcji clients
 * 3. Zdefiniuj klasę interfejsu Command, która realizuje polecenie prywatnej wiadomości: SEND-MD username message,
 *    które wysyła message tylko do użytkownika o nazwie username.
 * 4. Dodaj tworzenie obiektów nowym poleceń w metodzie buildCommand klasy CommandFactory
 * 5. Na podstawie przykładowego programu DigestDemo:
 *    - wygeneruj klasą DigestMessage skróty dla haseł trzech użytkowników z pliku configuration.yaml,
 *    - umieść skróty w pliku konfiguracyjnym zamiast haseł,
 *    - zmień metodę login w klasie ChatServer, aby otrzymane hasła od logujących się użytkowników
 *      zamieniać na skróty i porównywać je ze skrótem w mapie users.
 * 6. Dodaj funkcje kanałów:
 *    - zalogowany użytkownik trafia do domyślnego kanału np. general
 *    - wysyłane wiadomości docierają tylko do użytkowników danego kanału
 *    - zmiana kanału odbywa się nowym poleceniem np. CHANNEL channelName,
 *    - w przypadku podania nazwy nieistniejącego kanału, użytkownik powinien dostać informację o błędzie
 *    - użytkownik może zapytać o listę kanałów np. poleceniem CHANNEL-LIST
 *    - lista dostępnych kanałów znajduje się w pliku konfiguracyjnym configuration.yaml a klasa ChatServer
 *      powinna ją odczytywać z klasy konfiguracyjnej.
 *
 */
public class ChatApp {
    public static void main(String[] args) throws IOException {
        new ChatServer(Configuration.SERVER.port(), Configuration.SERVER.maxClients(), Configuration.SERVER.users()).start();
    }
}
