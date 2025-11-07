import java.util.Scanner;

public class LoginSysteem {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // Vooraf ingestelde juiste inloggegevens
        String juisteGebruikersnaam = "admin";
        String juistWachtwoord = "12345";

        // Gebruiker om invoer vragen
        System.out.print("Voer je gebruikersnaam in: ");
        String gebruikersnaam = input.nextLine();

        System.out.print("Voer je wachtwoord in: ");
        String wachtwoord = input.nextLine();

        // Controleer of de ingevoerde gegevens kloppen
        if (gebruikersnaam.equals(juisteGebruikersnaam) && wachtwoord.equals(juistWachtwoord)) {
            System.out.println("Login geslaagd!");
        } else {
            System.out.println("Ongeldige gegevens. Probeer opnieuw.");
        }

        input.close();
    }
}
