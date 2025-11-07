import java.io.File;
import java.util.Scanner;

public class VerwijderBestand {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Voer de bestandsnaam in: ");
        String bestandsNaam = scanner.nextLine();

        File bestand = new File(bestandsNaam);

        if (bestand.exists()) {
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
