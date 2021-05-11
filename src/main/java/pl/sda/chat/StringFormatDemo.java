package pl.sda.chat;

import java.util.Scanner;

public class StringFormatDemo {
    public static void main(String[] args) {
        //%[flaga][szerokość.liczba-cyfr-po-przecinku][znak-typu]
        //- wyrównanie do lewej
        //+ wyświetla zawsze znak liczby
        //, stosowanie lokalnych znaków separacji grup
        //znak-typu
        //d -liczba całkowita
        //x, X - w kodzie hex
        //o - liczba w kodzie ósemkowym
        //f - liczba zmiennoprzecinkowa
        //g, G - zapis dobierany automatycznie
        //e, E - zapis naukowy
        //b, B - typ logiczny
        //c - znak
        //s - łańcuch
        String outputString = String.format("Wartość: %,.2f", 11231321230.2345d);
        System.out.println(outputString);
        outputString = String.format("Wartość z liczby %,.2f z całości równej %,.2f wynosi %+3d %%", 1456.56d, 2500.25d, (int)(100 * 1456.56/2500.25));
        System.out.println(outputString);
        outputString = String.format("%10s %-10d%c %B", "ADAM",10, 'A', true);
        System.out.println(outputString);
        outputString = String.format("%10s %-10d%c %B", "EWA",145, 'E', false);
        System.out.println(outputString);
        //1. Napisz program drukujący łańcuch na podstawie dwóch wczytanych liczb i ich sumy:
        //      1234
        //        34
        //__________
        //      1268
        //2. Wydrukuj na podstawie wczytanej liczby zmiennoprzecinkowej x od 0 do 100
        //|################                              |
        //0%                 x%                       100%
        Scanner scanner = new Scanner(System.in);
        System.out.print("Wprowadź pierwszą liczbę: ");
        int a = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Wprowadź drugą liczbę: ");
        int b = scanner.nextInt();
        scanner.nextLine();
        String result = String.format("%10d\n%10d\n%10s\n%10d", a, b, "-".repeat(10), a + b);
        System.out.println(result);
        //2. Wydrukuj na podstawie wczytanej liczby zmiennoprzecinkowej od 0 do 100
        //|#################                       |
        //0%                  x%                100%
        System.out.print("Wprowadź procent: ");
        double percent = scanner.nextDouble();
        scanner.nextLine();
        String percentBar = String.format("|%-100s|", "#".repeat((int)percent));
        System.out.println(percentBar);
        String percentSymbols = String.format("0%%%49d%%%50s", (int)percent, "100%");
        System.out.println(percentSymbols);

    }
}
