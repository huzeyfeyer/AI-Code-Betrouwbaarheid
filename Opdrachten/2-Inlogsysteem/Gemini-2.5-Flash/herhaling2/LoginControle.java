import java.util.Scanner; // Importeer de Scanner klasse voor het lezen van invoer

public class LoginControle {

    public static void main(String[] args) {
        // Stel de correcte inloggegevens in (voor dit eenvoudige voorbeeld)
        final String CORRECTE_GEBRUIKERSNAAM = "admin";
        final String CORRECT_WACHTWOORD = "Welkom123";

        // Maak een Scanner object om invoer van de console te lezen
        Scanner scanner = new Scanner(System.in);

        // --- Vraag om Gebruikersnaam ---
        System.out.print("Voer uw gebruikersnaam in: ");
        // Lees de ingevoerde gebruikersnaam
        String ingevoerdeGebruikersnaam = scanner.nextLine();

        // --- Vraag om Wachtwoord ---
        System.out.print("Voer uw wachtwoord in: ");
        // Lees het ingevoerde wachtwoord
        String ingevoerdWachtwoord = scanner.nextLine();

        // Sluit de scanner. Dit is goede praktijk om bronnen vrij te geven.
        scanner.close();

        // --- Controleer de Inloggegevens ---
        // Gebruik .equals() om de inhoud van de strings te vergelijken
        if (ingevoerdeGebruikersnaam.equals(CORRECTE_GEBRUIKERSNAAM) && ingevoerdWachtwoord.equals(CORRECT_WACHTWOORD)) {
            // Combinatie is juist
            System.out.println("\nLogin geslaagd! ✅");
        } else {
            // Combinatie is onjuist
            System.out.println("\nOngeldige gegevens. ❌");
        }
    }
}