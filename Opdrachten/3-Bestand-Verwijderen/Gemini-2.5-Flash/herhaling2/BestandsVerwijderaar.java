import java.io.File;
import java.util.Scanner;

public class BestandsVerwijderaar {

    public static void main(String[] args) {
        // Maak een Scanner-object om invoer van de gebruiker te lezen
        Scanner scanner = new Scanner(System.in);
        String bestandsnaam;

        // Vraag de gebruiker om de bestandsnaam
        System.out.print("Voer de bestandsnaam in die u wilt verwijderen: ");
        bestandsnaam = scanner.nextLine();

        // Maak een File-object aan met de ingevoerde bestandsnaam
        File bestand = new File(bestandsnaam);

        // 1. Controleer of het bestand bestaat
        if (bestand.exists()) {
            // 2. Probeer het bestand te verwijderen
            if (bestand.delete()) {
                System.out.println("Bestand succesvol verwijderd: " + bestandsnaam);
            } else {
                // Kan voorkomen als er geen schrijfrechten zijn of als het bestand in gebruik is
                System.out.println("Fout: Het bestand kon niet worden verwijderd.");
            }
        } else {
            // 3. Geef een melding als het bestand niet is gevonden
            System.out.println("Bestand niet gevonden: " + bestandsnaam);
        }

        // Sluit de scanner
        scanner.close();
    }
}