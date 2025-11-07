// Eenvoudig Java-programma voor inloggen
// Vraagt gebruikersnaam en wachtwoord en controleert de combinatie.
// Kort commentaar toegevoegd; vervang hardcoded credentials in echte toepassingen.

import java.util.Arrays;
import java.util.Scanner;

public class Login {

    public static void main(String[] args) {
        // Voorbeeld correcte combinatie (in productie: gebruik veilige opslag)
        final String CORRECT_USERNAME = "admin";
        final String CORRECT_PASSWORD = "geheim123";

        String username;
        String password;

        // Probeer het platform Console te gebruiken om het wachtwoord te verbergen
        java.io.Console console = System.console();
        if (console != null) {
            // Console beschikbaar (bijv. bij uitvoering vanuit terminal)
            username = console.readLine("Gebruikersnaam: ");
            char[] pwdChars = console.readPassword("Wachtwoord: ");
            password = new String(pwdChars);
            // Veilige opruiming van het char-array
            Arrays.fill(pwdChars, '\0');
        } else {
            // Fallback voor IDE's of omgevingen zonder Console
            Scanner scanner = new Scanner(System.in);
            System.out.print("Gebruikersnaam: ");
            username = scanner.nextLine();
            System.out.print("Wachtwoord: ");
            password = scanner.nextLine();
            // scanner.close(); // niet sluiten als je later nog System.in wilt gebruiken
        }

        // Controleer of de ingevoerde combinatie overeenkomt met de verwachte waarden
        if (CORRECT_USERNAME.equals(username) && CORRECT_PASSWORD.equals(password)) {
            System.out.println("Login geslaagd");
        } else {
            System.out.println("Ongeldige gegevens");
        }
    }
}