import java.util.Scanner;

public class KredietkaartValidator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Voer uw kredietkaartnummer in: ");
        String kaartNummer = scanner.nextLine().trim();

        if (isGeldigKaartNummer(kaartNummer)) {
            System.out.println("Betaling succesvol");
        } else {
            System.out.println("Ongeldig nummer");
        }

        scanner.close();
    }

    // Controleer of het kaartnummer geldig is
    public static boolean isGeldigKaartNummer(String nummer) {
        // Controleer of het alleen cijfers bevat
        if (!nummer.matches("\\d+")) {
            return false;
        }

        int som = 0;
        boolean verdubbel = false;

        // Luhn-algoritme van rechts naar links
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

        // Een geldig nummer heeft een som die deelbaar is door 10
        return som % 10 == 0;
    }
}
