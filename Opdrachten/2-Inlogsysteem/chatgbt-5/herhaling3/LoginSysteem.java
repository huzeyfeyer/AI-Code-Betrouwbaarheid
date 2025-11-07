import java.util.Scanner;

public class LoginSysteem {
    public static void main(String[] args) {
        // Maak een Scanner-object om invoer van de gebruiker te lezen
        Scanner invoer = new Scanner(System.in);

        // Stel de juiste gebruikersnaam en wachtwoord in
        String juisteGebruikersnaam = "admin";
        String juistWachtwoord = "1234";

        // Vraag om gebruikersnaam
        System.out.print("Gebruikersnaam: ");
        String gebruikersnaam = invoer.nextLine();

        // Vraag om wachtwoord
        System.out.print("Wachtwoord: ");
        String wachtwoord = invoer.nextLine();

        // Controleer of beide overeenkomen
        if (gebruikersnaam.equals(juisteGebruikersnaam) && wachtwoord.equals(juistWachtwoord)) {
            System.out.println("Login geslaagd!");
        } else {
            System.out.println("Ongeldige gegevens.");
        }

        // Scanner sluiten
        invoer.close();
    }
}
