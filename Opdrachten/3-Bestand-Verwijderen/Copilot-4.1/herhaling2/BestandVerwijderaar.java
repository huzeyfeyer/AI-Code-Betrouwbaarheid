import java.io.File;
import java.util.Scanner;

public class BestandVerwijderaar {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Voer de bestandsnaam in: ");
        String bestandsnaam = scanner.nextLine();

        File bestand = new File(bestandsnaam);

        if (bestand.exists()) {
            boolean verwijderd = bestand.delete();
            if (verwijderd) {
                System.out.println("Bestand verwijderd");
            } else {
                System.out.println("Kon bestand niet verwijderen");
            }
        } else {
            System.out.println("Bestand niet gevonden");
        }

        scanner.close();
    }
}