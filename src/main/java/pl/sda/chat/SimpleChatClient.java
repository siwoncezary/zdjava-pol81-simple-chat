package pl.sda.chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SimpleChatClient {
    public static void main(String[] args) throws IOException {
        Socket client = new Socket("127.0.0.1",5555);
        Scanner input = new Scanner(client.getInputStream());
        Scanner scanner = new Scanner(System.in);
        PrintWriter output = new PrintWriter(client.getOutputStream(), true);
        while(true){
            String line = scanner.nextLine();
            if (line.equals("Q")){
                client.close();
                return;
            }
            output.println(line);
            if(input.hasNextLine()){
                System.out.println(input.nextLine());
            }
        }
    }
}
