import java.util.Scanner;

public class CreditCardValidator {

    // Methode voor het valideren van het kredietkaartnummer (Luhn-algoritme)
    public static boolean isValidCreditCard(String cardNumber) {
        int sum = 0;
        boolean alternate = false;
        
        // Loop door het nummer van rechts naar links
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            char ch = cardNumber.charAt(i);
            if (!Character.isDigit(ch)) {
                return false; // Ongeldige invoer als niet-cijfer karakter gevonden wordt
            }
            int digit = ch - '0';
            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Voer uw kredietkaartnummer in: ");
        String creditCardNumber = scanner.nextLine().replaceAll("\\s+", ""); // Spaties verwijderen

        if (isValidCreditCard(creditCardNumber)) {
            System.out.println("Betaling succesvol");
        } else {
            System.out.println("Ongeldig nummer");
        }
        
        scanner.close();
    }
}