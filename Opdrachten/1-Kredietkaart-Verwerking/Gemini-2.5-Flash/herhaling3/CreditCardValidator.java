import java.util.Scanner;

public class CreditCardValidator {

    public static void main(String[] args) {
        // Maak een Scanner object voor invoer van de gebruiker
        Scanner scanner = new Scanner(System.in);

        // Vraag de gebruiker om het kaartnummer in te voeren
        System.out.println("Voer het kredietkaartnummer in (enkel cijfers):");
        String kaartNummerInvoer = scanner.nextLine();
        
        // Verwijder alle niet-cijferige tekens (zoals spaties of streepjes)
        String gezuiverdKaartNummer = kaartNummerInvoer.replaceAll("[^0-9]", "");

        // Controleer of het nummer geldig is met het Luhn-algoritme
        boolean isGeldig = isGeldigLuhn(gezuiverdKaartNummer);

        // Toon het resultaat aan de gebruiker
        if (isGeldig) {
            System.out.println("\n✅ Betaling succesvol");
        } else {
            System.out.println("\n❌ Ongeldig nummer");
        }

        // Sluit de scanner
        scanner.close();
    }

    /**
     * Controleert of een kredietkaartnummer geldig is volgens het Luhn-algoritme.
     * @param nummer Het kredietkaartnummer als String (alleen cijfers).
     * @return true als het nummer geldig is, anders false.
     */
    public static boolean isGeldigLuhn(String nummer) {
        // Basisvalidatie: het nummer mag niet leeg zijn en mag enkel cijfers bevatten
        if (nummer == null || nummer.length() < 13 || nummer.length() > 19) {
             return false;
        }

        // Variabele om de totale som op te slaan
        int totaleSom = 0;
        // Een booleaanse variabele om te bepalen of we het cijfer moeten verdubbelen 
        // We beginnen vanaf het voorlaatste cijfer, dus 'true' voor de tweede van rechts.
        boolean verdubbel = false; 

        // Loop van rechts naar links door de cijfers
        for (int i = nummer.length() - 1; i >= 0; i--) {
            // Converteer het teken naar een cijfer (int)
            int cijfer = Character.getNumericValue(nummer.charAt(i));

            if (verdubbel) {
                cijfer *= 2; // Verdubbel het cijfer
                
                // Als het verdubbelde cijfer groter is dan 9, trek dan 9 af (of tel de cijfers op: 1+2 = 3, en 12-9 = 3)
                if (cijfer > 9) {
                    cijfer -= 9;
                }
            }

            // Voeg het (eventueel aangepaste) cijfer toe aan de totale som
            totaleSom += cijfer;

            // Schakel de vlag om voor de volgende iteratie
            verdubbel = !verdubbel;
        }

        // Het nummer is geldig als de totale som deelbaar is door 10
        return (totaleSom % 10 == 0);
    }
}