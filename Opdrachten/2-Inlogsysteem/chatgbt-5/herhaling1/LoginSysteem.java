import java.util.Scanner;

public class LoginSysteem {
    public static void main(String[] args) {
        // Maak een Scanner-object om invoer van de gebruiker te lezen
        Scanner invoer = new Scanner(System.in);

        // Stel de juiste gebruikersnaam en wachtwoord in
        String juisteGebruikersnaam = "admin";
        String juistWachtwoord = "1234";

        // Vraag de gebruiker om in te loggen
        System.out.print("Gebruikersnaam: ");
        String gebruikersnaam = invoer.nextLine();

        System.out.print("Wachtwoord: ");
        String wachtwoord = invoer.nextLine();

        // Controleer of de ingevoerde gegevens kloppen
        if (gebruikersnaam.equals(juisteGebruikersnaam) && wachtwoord.equals(juistWachtwoord)) {
            System.out.println("Login geslaagd!");
        } else {
            System.out.println("Ongeldige gegevens.");
        }

        // Sluit de scanner
        invoer.close();
    }
}
