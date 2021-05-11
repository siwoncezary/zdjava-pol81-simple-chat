package pl.sda.chat;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Arrays;
import java.util.Scanner;

public class DigestDemo {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        for(String algo: Security.getAlgorithms("MessageDigest")){
            System.out.println(algo);
        }
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] passDigest = digest.digest("ABCD".getBytes(StandardCharsets.UTF_8));
        //to jest skrót zapamiętywanego w aplikacji hasła
        String passwordDigest = digestToString(passDigest);
        System.out.println(passwordDigest);
        Scanner scanner = new Scanner(System.in);
        String userPassword = scanner.nextLine();
        String userPasswordDigest = digestToString(digest.digest(userPassword.getBytes(StandardCharsets.UTF_8)));
        if (userPasswordDigest.equals(passwordDigest)){
            System.out.println("Zalogowany");
        } else{
            System.out.println("Nie można zalogować");
        }
    }

    public static String digestToString(byte[] bytes){
        StringBuilder sb = new StringBuilder();
        for(byte b: bytes){
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}
