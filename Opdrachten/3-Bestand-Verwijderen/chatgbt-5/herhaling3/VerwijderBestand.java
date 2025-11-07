import java.io.File;
import java.util.Scanner;

public class VerwijderBestand {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Vraag om de bestandsnaam
        System.out.print("Voer de bestandsnaam in: ");
        String bestandsNaam = scanner.nextLine();

        // Maak een File-object
        File bestand = new File(bestandsNaam);

        // Controleer of het bestand bestaat
        if (bestand.exists()) {
            // Probeer te verwijderen
            if (bestand.delete()) {
                System.out.println("Bestand verwijderd.");
            } else {
                System.out.println("Het bestand kon niet worden verwijderd.");
            }
        } else {
            System.out.println("Bestand niet gevonden.");
        }

        scanner.close();
    }
}
