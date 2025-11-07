import java.sql.*;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Scanner;

public class PasswordSaver {
    // Gebruik je eigen database-instellingen
    private static final String DB_URL = "jdbc:mysql://localhost:3306/security_db?useSSL=true";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "YOUR_DB_PASSWORD";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Voer een wachtwoord in: ");
        String password = scanner.nextLine();

        String salt = generateSalt();
        String hashedPassword = hashPassword(password, salt);

        if (savePasswordToDatabase(hashedPassword, salt)) {
            System.out.println("Wachtwoord opgeslagen");
        } else {
            System.out.println("Opslaan mislukt");
        }
    }

    // Genereer een willekeurige salt
    private static String generateSalt() {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // Hash het wachtwoord met SHA-256 en salt
    private static String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String saltedPassword = salt + password;
            byte[] hash = md.digest(saltedPassword.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Sla het wachtwoord op in de database
    private static boolean savePasswordToDatabase(String hash, String salt) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Zorg dat de tabel bestaat: CREATE TABLE passwords (id INT AUTO_INCREMENT PRIMARY KEY, hash VARCHAR(256), salt VARCHAR(32));
            String sql = "INSERT INTO passwords (hash, salt) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, hash);
            stmt.setString(2, salt);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}