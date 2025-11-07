public class CreditCardValidator {

    /**
     * Eenvoudig Java-programma dat een kredietkaartnummer van de gebruiker vraagt,
     * valideert met de Luhn-algoritme en "Betaling succesvol" of "Ongeldig nummer" toont.
     *
     * Belangrijke punten:
     * - Spaties en streepjes worden toegestaan in de invoer (worden genegeerd).
     * - Alleen numerieke tekens na het normaliseren zijn toegestaan.
     * - Geldige lengte wordt gecontroleerd (algemene kaartlengtes: 13–19 cijfers).
     * - De Luhn-check controleert de checksum.
     */

    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        try {
            System.out.print("Voer een kredietkaartnummer in: ");
            String invoer = scanner.nextLine();

            // Normaliseer invoer: verwijder spaties en streepjes
            String genormaliseerdNummer = invoer.replaceAll("[\\s-]", "");

            // Controleer of het alleen cijfers bevat
            if (!genormaliseerdNummer.matches("\\d+")) {
                System.out.println("Ongeldig nummer");
                return;
            }

            // Controleer lengte (meestgebruikte kaartlengtes zijn tussen 13 en 19)
            final int MIN_LENGTE = 13;
            final int MAX_LENGTE = 19;
            int lengte = genormaliseerdNummer.length();
            if (lengte < MIN_LENGTE || lengte > MAX_LENGTE) {
                System.out.println("Ongeldig nummer");
                return;
            }

            // Uitvoeren van de Luhn-controle
            boolean geldig = luhnControle(genormaliseerdNummer);
            if (geldig) {
                System.out.println("Betaling succesvol");
            } else {
                System.out.println("Ongeldig nummer");
            }
        } finally {
            scanner.close();
        }
    }

    /**
     * Voert de Luhn-algoritme uit op het gegeven numerieke kaartnummer.
     *
     * @param nummer String bestaande uit alleen cijfers
     * @return true als het nummer de Luhn-check doorstaat, anders false
     */
    private static boolean luhnControle(String nummer) {
        int som = 0;
        boolean dubbel = false;

        // Loop van rechts naar links
        for (int i = nummer.length() - 1; i >= 0; i--) {
            int cijfer = nummer.charAt(i) - '0';
            if (dubbel) {
                cijfer *= 2;
                if (cijfer > 9) {
                    // Equivalent aan -9 (bijv. 12 -> 3)
                    cijfer -= 9;
                }
            }
            som += cijfer;
            dubbel = !dubbel; // alterneren: dubbel, niet dubbel, ...
        }

        return som % 10 == 0;
    }
}