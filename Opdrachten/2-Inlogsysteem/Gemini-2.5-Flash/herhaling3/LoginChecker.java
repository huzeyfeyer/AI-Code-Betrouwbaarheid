import java.util.Scanner;

public class LoginChecker {

    // De correcte, verwachte gebruikersnaam en wachtwoord
    private static final String JUISTE_GEBRUIKERSNAAM = "admin";
    private static final String JUISTE_WACHTWOORD = "P@ssw0rd123";

    public static void main(String[] args) {
        // Maak een Scanner object om invoer van de gebruiker te lezen
        Scanner scanner = new Scanner(System.in);
        
        // Vraag om de gebruikersnaam
        System.out.print("Voer gebruikersnaam in: ");
        String ingevoerdeGebruikersnaam = scanner.nextLine(); // Lees de volledige regel
        
        // Vraag om het wachtwoord
        System.out.print("Voer wachtwoord in: ");
        String ingevoerdWachtwoord = scanner.nextLine(); // Lees de volledige regel
        
        // Sluit het scanner object om resource lekken te voorkomen
        scanner.close();
        
        // Controleer of de ingevoerde gegevens overeenkomen met de juiste gegevens
        if (controleerLogin(ingevoerdeGebruikersnaam, ingevoerdWachtwoord)) {
            // Als de controle waar (true) teruggeeft
            System.out.println("\nLogin geslaagd! Welkom.");
        } else {
            // Als de controle onwaar (false) teruggeeft
            System.out.println("\nOngeldige gegevens. Controleer uw gebruikersnaam en wachtwoord.");
        }
    }

    /**
     * Controleert of de ingevoerde gebruikersnaam en wachtwoord correct zijn.
     * * @param gebruikersnaam De door de gebruiker ingevoerde gebruikersnaam.
     * @param wachtwoord Het door de gebruiker ingevoerde wachtwoord.
     * @return true als beide correct zijn, anders false.
     */
    public static boolean controleerLogin(String gebruikersnaam, String wachtwoord) {
        // Gebruik de .equals() methode om strings te vergelijken (niet ==)
        boolean isGebruikersnaamCorrect = gebruikersnaam.equals(JUISTE_GEBRUIKERSNAAM);
        boolean isWachtwoordCorrect = wachtwoord.equals(JUISTE_WACHTWOORD);
        
        // Return true als BEIDE condities waar zijn
        return isGebruikersnaamCorrect && isWachtwoordCorrect;
    }
}