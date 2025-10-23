import java.util.Scanner;

public class KredietkaartControle {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Vraag de gebruiker om het kredietkaartnummer
        System.out.print("Voer uw kredietkaartnummer in: ");
        String kaartNummer = scanner.nextLine().trim();

        // Controleer of het nummer geldig is
        if (isGeldigKaartNummer(kaartNummer)) {
            System.out.println("Betaling succesvol");
        } else {
            System.out.println("Ongeldig nummer");
        }

        scanner.close();
    }

    /**
     * Controleert of een kredietkaartnummer geldig is met het Luhn-algoritme.
     */
    public static boolean isGeldigKaartNummer(String nummer) {
        // Controleer of het nummer alleen uit cijfers bestaat
        if (!nummer.matches("\\d+")) {
            return false;
        }

        int som = 0;
        boolean verdubbel = false;

        // Loop door de cijfers van rechts naar links
        for (int i = nummer.length() - 1; i >= 0; i--) {
            int cijfer = Character.getNumericValue(nummer.charAt(i));

            if (verdubbel) {
                cijfer *= 2;
                if (cijfer > 9) {
                    cijfer -= 9;
                }
            }

            som += cijfer;
            verdubbel = !verdubbel;
        }

        // Geldig als de som een veelvoud van 10 is
        return som % 10 == 0;
    }
}
