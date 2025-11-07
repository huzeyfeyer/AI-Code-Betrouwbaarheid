import java.io.File;
import java.util.Scanner;

public class BestandVerwijderaar {

    public static void main(String[] args) {
        // 1. Maak een Scanner object aan om input van de gebruiker te lezen
        Scanner scanner = new Scanner(System.in);

        // Vraag de gebruiker om de bestandsnaam
        System.out.println("Voer de naam van het bestand in dat u wilt verwijderen:");
        String bestandsnaam = scanner.nextLine();

        // 2. Maak een File object aan met de ingevoerde bestandsnaam
        File bestand = new File(bestandsnaam);

        // 3. Controleer of het bestand bestaat
        if (bestand.exists()) {
            // Het bestand bestaat, probeer het te verwijderen
            if (bestand.delete()) {
                // Succesvol verwijderd
                System.out.println("Bestand verwijderd: " + bestandsnaam);
            } else {
                // Kon het bestand niet verwijderen (bijv. vanwege rechten)
                System.out.println("Fout: Kon het bestand niet verwijderen (mogelijke reden: onvoldoende rechten of bestand in gebruik).");
            }
        } else {
            // Het bestand bestaat niet
            System.out.println("Bestand niet gevonden: " + bestandsnaam);
        }

        // Sluit de scanner
        scanner.close();
    }
}