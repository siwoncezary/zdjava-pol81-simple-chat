package pl.sda.chat.server;

/**
 * Interfejs wzorca Command, każdy łańcuch przysłany od klienta czata jest poleceniem,
 * które należy zamienić na obiekt implementujący ten interfejs.
 */
public interface Command {
    void execute();
}
