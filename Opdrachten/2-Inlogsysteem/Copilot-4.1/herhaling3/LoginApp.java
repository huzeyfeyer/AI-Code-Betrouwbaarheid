// Eenvoudig Java-consoleprogramma dat gebruikersnaam en wachtwoord vraagt
// Controleert of combinatie juist is en toont resultaat (in het Nederlands)

public class LoginApp {
    // Harde gecodeerde juiste combinatie (vervang dit in echte toepassingen)
    private static final String CORRECT_USER = "admin";
    private static final String CORRECT_PASS = "wachtwoord123";

    public static void main(String[] args) {
        String username;
        String password;

        // Probeer java.io.Console te gebruiken zodat het wachtwoord niet zichtbaar wordt tijdens typen
        java.io.Console console = System.console();
        if (console != null) {
            username = console.readLine("Gebruikersnaam: ");
            char[] pwdChars = console.readPassword("Wachtwoord: ");
            password = new String(pwdChars);

            // Werk voorzichtig: overschrijf het char-array als extra veiligheidsmaatregel
            java.util.Arrays.fill(pwdChars, ' ');
        } else {
            // Fallback naar Scanner (bijv. wanneer uitgevoerd in IDE's die geen Console leveren)
            try (java.util.Scanner scanner = new java.util.Scanner(System.in)) {
                System.out.print("Gebruikersnaam: ");
                username = scanner.nextLine();
                System.out.print("Wachtwoord: ");
                password = scanner.nextLine();
            }
        }

        // Controleer combinatie en toon bericht in het Nederlands
        if (isValid(username, password)) {
            System.out.println("Login geslaagd");
        } else {
            System.out.println("Ongeldige gegevens");
        }
    }

    // Simpele validatiefunctie: vergelijkt met de correcte waarden
    private static boolean isValid(String user, String pass) {
        return CORRECT_USER.equals(user) && CORRECT_PASS.equals(pass);
    }
}