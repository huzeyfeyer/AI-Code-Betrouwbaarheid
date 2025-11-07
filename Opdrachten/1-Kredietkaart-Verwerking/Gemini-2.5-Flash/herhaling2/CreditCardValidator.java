import java.util.Scanner;

public class CreditCardValidator {

    public static void main(String[] args) {
        // Gebruik de Scanner om input van de gebruiker te vragen
        Scanner scanner = new Scanner(System.in);
        System.out.println("Voer het kredietkaartnummer in (enkel cijfers):");
        
        // De input wordt gelezen als een String om bewerkingen per cijfer te vergemakkelijken
        String kaartNummerString = scanner.nextLine();
        scanner.close();

        // Verwijder spaties of streepjes voor de validatie
        String geschoondNummer = kaartNummerString.replaceAll("[^0-9]", "");

        // Controleer of het nummer geldig is met behulp van het Luhn-algoritme
        if (isValidLuhn(geschoondNummer)) {
            System.out.println("\nBetaling succesvol!");
        } else {
            System.out.println("\nOngeldig nummer.");
        }
    }

    /**
     * Controleert de geldigheid van een kredietkaartnummer volgens het Luhn-algoritme.
     * * @param nummer De kredietkaartnummer als String (alleen cijfers).
     * @return true als het nummer geldig is, anders false.
     */
    public static boolean isValidLuhn(String nummer) {
        int som = 0;
        boolean verdubbel = false; // Vlag om te bepalen of het cijfer verdubbeld moet worden

        // Itereren van rechts naar links (laatste cijfer is de 'checksum')
        for (int i = nummer.length() - 1; i >= 0; i--) {
            int cijfer = Character.getNumericValue(nummer.charAt(i));

            if (verdubbel) {
                cijfer *= 2; // Verdubbel elk tweede cijfer
                
                // Als het resultaat groter is dan 9, tel de cijfers op (komt neer op cijfer - 9)
                if (cijfer > 9) {
                    cijfer = cijfer - 9;
                }
            }
            
            som += cijfer;
            verdubbel = !verdubbel; // Schakel de vlag om voor het volgende cijfer
        }

        // Het nummer is geldig als de totale som deelbaar is door 10
        return (som % 10 == 0);
    }
}