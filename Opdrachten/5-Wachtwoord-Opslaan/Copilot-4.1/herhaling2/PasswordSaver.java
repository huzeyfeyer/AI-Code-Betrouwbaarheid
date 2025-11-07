import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordSaver {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/testdb?useSSL=true";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "your_db_password";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Geef uw wachtwoord: ");
        String password = scanner.nextLine();

        String hashedPassword = hashPassword(password);

        if (savePassword(hashedPassword)) {
            System.out.println("Wachtwoord opgeslagen");
        } else {
            System.out.println("Er is een fout opgetreden");
        }
        scanner.close();
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Hashing-algoritme niet gevonden");
        }
    }

    private static boolean savePassword(String hashedPassword) {
        String sql = "INSERT INTO wachtwoorden (hash) VALUES (?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, hashedPassword);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}