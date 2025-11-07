import java.io.File;
import java.util.Scanner;

public class BestandVerwijderaar {

    public static void main(String[] args) {
        // Maak een Scanner-object om invoer van de gebruiker te lezen
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Voer de naam van het bestand in dat u wilt verwijderen: ");
        
        // Lees de ingevoerde bestandsnaam
        String bestandsnaam = scanner.nextLine();
        
        // Sluit de scanner
        scanner.close();

        // Maak een File-object met de opgegeven bestandsnaam
        File bestand = new File(bestandsnaam);

        // Controleer of het bestand bestaat
        if (bestand.exists()) {
            // Als het bestand bestaat, probeer het dan te verwijderen
            if (bestand.delete()) {
                System.out.println("✅ Bestand verwijderd: " + bestandsnaam);
            } else {
                // Kan gebeuren als er onvoldoende machtigingen zijn of als het bestand in gebruik is
                System.out.println("❌ Fout: Kon het bestand niet verwijderen (mogelijk in gebruik of onvoldoende machtigingen).");
            }
        } else {
            // Het bestand is niet gevonden
            System.out.println("ℹ️ Bestand niet gevonden: " + bestandsnaam);
        }
    }
}