import java.util.Scanner; // Importeer de Scanner klasse om invoer te lezen

public class LoginControle {

    public static void main(String[] args) {
        // Definieer de correcte inloggegevens (vastgelegd voor dit voorbeeld)
        String correcteGebruikersnaam = "admin";
        String correctWachtwoord = "P@ssw0rd123";

        // Maak een Scanner object aan om invoer van de console te verwerken
        Scanner scanner = new Scanner(System.in);

        // Vraag om de gebruikersnaam
        System.out.print("Voer gebruikersnaam in: ");
        String ingevoerdeGebruikersnaam = scanner.nextLine(); // Lees de ingevoerde gebruikersnaam

        // Vraag om het wachtwoord
        System.out.print("Voer wachtwoord in: ");
        String ingevoerdWachtwoord = scanner.nextLine(); // Lees het ingevoerde wachtwoord

        // Sluit de scanner om resource lekkage te voorkomen
        scanner.close();

        // Controleer of de ingevoerde gegevens overeenkomen met de correcte gegevens
        // Gebruik .equals() om String-waarden te vergelijken
        if (ingevoerdeGebruikersnaam.equals(correcteGebruikersnaam) && 
            ingevoerdWachtwoord.equals(correctWachtwoord)) {
            
            // Beide gegevens zijn correct
            System.out.println("\nLogin geslaagd!");
        } else {
            // Minstens één van de gegevens is onjuist
            System.out.println("\nOngeldige gegevens. Probeer opnieuw.");
        }
    }
}