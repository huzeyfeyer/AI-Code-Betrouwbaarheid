import java.io.File;
import java.util.Scanner;

public class VerwijderBestand {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Voer de bestandsnaam in: ");
        String bestandsnaam = scanner.nextLine();

        File bestand = new File(bestandsnaam);

        if (bestand.exists()) {
            if (bestand.delete()) {
                System.out.println("Bestand verwijderd");
            } else {
                System.out.println("Kon het bestand niet verwijderen");
            }
        } else {
            System.out.println("Bestand niet gevonden");
        }

        scanner.close();
    }
}