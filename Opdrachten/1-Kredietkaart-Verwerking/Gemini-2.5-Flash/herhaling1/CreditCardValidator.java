import java.util.Scanner;

public class CreditCardValidator {

    public static void main(String[] args) {
        // Stap 1: Vraag de gebruiker om invoer
        Scanner scanner = new Scanner(System.in);
        System.out.println("Voer het kredietkaartnummer in (zonder spaties of streepjes):");
        String kaartnummerInvoer = scanner.nextLine();
        scanner.close();

        // Verwijder niet-cijfertekens voor de validatie
        String gereinigdNummer = kaartnummerInvoer.replaceAll("[^0-9]", "");

        // Stap 2: Controleer of het nummer geldig is
        if (isGeldigLuhn(gereinigdNummer)) {
            System.out.println("Betaling succesvol");
        } else {
            System.out.println("Ongeldig nummer");
        }
    }

    /**
     * Controleert de geldigheid van een kredietkaartnummer met behulp van
     * het Luhn-algoritme (Modulus 10).
     *
     * @param nummer Het kredietkaartnummer als String.
     * @return true als het nummer geldig is volgens Luhn, anders false.
     */
    public static boolean isGeldigLuhn(String nummer) {
        int somVanCijfers = 0;
        boolean dubbelen = false; // Start met niet-dubbelen (vanaf de meest rechtse is dit de controle-cijfer)

        // Itereer van rechts naar links over het nummer
        for (int i = nummer.length() - 1; i >= 0; i--) {
            int cijfer = Character.getNumericValue(nummer.charAt(i));

            if (dubbelen) {
                cijfer *= 2;
                // Als het dubbele > 9 is, tel dan de afzonderlijke cijfers van het resultaat op
                if (cijfer > 9) {
                    cijfer = cijfer - 9; // Dit is equivalent aan (cijfer % 10) + (cijfer / 10)
                }
            }

            somVanCijfers += cijfer;
            dubbelen = !dubbelen; // Wissel de vlag voor de volgende iteratie
        }

        // Het nummer is geldig als de totale som deelbaar is door 10
        return (somVanCijfers % 10 == 0);
    }
}