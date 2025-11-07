// Eenvoudig Java-programma om een gebruikersnaam/wachtwoord te controleren.
// Auteur: gegenereerd door Copilot op verzoek van gebruiker.

import java.io.Console;
import java.util.Scanner;

public class LoginApp {

    // Voorbeeldgeldige combinatie (in echte applicaties gebruik een veilige opslag)
    private static final String VALID_USERNAME = "admin";
    private static final String VALID_PASSWORD = "wachtwoord123";

    public static void main(String[] args) {
        String username;
        String password;

        // Probeer Console te gebruiken zodat wachtwoord niet zichtbaar is tijdens typen
        Console console = System.console();
        if (console != null) {
            username = console.readLine("Gebruikersnaam: ");
            char[] pwdChars = console.readPassword("Wachtwoord: ");
            password = new String(pwdChars);
        } else {
            // Console kan null zijn in sommige IDE's; gebruik dan Scanner
            try (Scanner scanner = new Scanner(System.in)) {
                System.out.print("Gebruikersnaam: ");
                username = scanner.nextLine();
                System.out.print("Wachtwoord: ");
                password = scanner.nextLine(); // Let op: zichtbaar tijdens typen
            }
        }

        // Vergelijk strings correct met equals
        if (VALID_USERNAME.equals(username) && VALID_PASSWORD.equals(password)) {
            System.out.println("Login geslaagd");
        } else {
            System.out.println("Ongeldige gegevens");
        }
    }
}