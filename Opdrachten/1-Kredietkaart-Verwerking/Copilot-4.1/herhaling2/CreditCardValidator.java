// Eenvoudig Java-programma om een creditcardnummer te valideren met de Luhn-algoritme.
// Vraagt de gebruiker om een nummer, controleert het en toont "Betaling succesvol" of "Ongeldig nummer".

import java.util.Scanner;

public class CreditCardValidator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Voer uw kredietkaartnummer in (spaties of streepjes toegestaan): ");
        String invoer = scanner.nextLine();
        scanner.close();

        // Schoonmaken van invoer: verwijder spaties en streepjes
        String kaartNummer = sanitize(invoer);

        // Valideer lengte en cijfers, en controleer Luhn-algoritme
        if (isNumeric(kaartNummer) && isPlausibleLength(kaartNummer) && passesLuhn(kaartNummer)) {
            System.out.println("Betaling succesvol");
        } else {
            System.out.println("Ongeldig nummer");
        }
    }

    // Verwijdert spaties en streepjes uit de invoer
    private static String sanitize(String input) {
        if (input == null) return "";
        return input.replaceAll("[\\s-]", "");
    }

    // Controleer of de string alleen uit cijfers bestaat
    private static boolean isNumeric(String s) {
        return s.matches("\\d+");
    }

    // Basiscontrole op lengte: gangbare kredietkaartlengtes liggen tussen 13 en 19 cijfers
    private static boolean isPlausibleLength(String s) {
        int len = s.length();
        return len >= 13 && len <= 19;
    }

    // Luhn-algoritme implementatie
    private static boolean passesLuhn(String number) {
        int sum = 0;
        boolean shouldDouble = false;

        // Loop van rechts naar links
        for (int i = number.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(number.charAt(i));
            if (shouldDouble) {
                int doubled = digit * 2;
                // Als het resultaat twee cijfers heeft, tel de afzonderlijke cijfers (of trek 9 af)
                if (doubled > 9) {
                    doubled -= 9;
                }
                sum += doubled;
            } else {
                sum += digit;
            }
            shouldDouble = !shouldDouble;
        }

        return sum % 10 == 0;
    }
}