package pl.sda.chat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatApp {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5555);
        ExecutorService service = Executors.newFixedThreadPool(20);
        while(true) {
            Socket clientSocket = serverSocket.accept();
            service.execute(() -> {
                try (
                        InputStream inputStream = clientSocket.getInputStream();
                        OutputStream outputStream = clientSocket.getOutputStream();
                        PrintWriter output = new PrintWriter(outputStream, true);
                        Scanner input = new Scanner(inputStream);
                ) {
                    while (input.hasNextLine()) {
                        String line = input.nextLine();
                        System.out.println(line);
                        output.println("Server echo: " + line);
                    }
                    System.out.println("Klient zakończył połączenie!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
