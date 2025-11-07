import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import org.mindrot.jbcrypt.BCrypt;

// Simuleert een klasse voor veilige wachtwoordverwerking
class PasswordUtil {
    // Hasht een wachtwoord met BCrypt
    public static String hashPassword(String password) {
        // Genereert een 'salt' en hashet het wachtwoord in één stap
        // De work factor van 12 is een goede standaard
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }
}

// Simuleert een klasse om de database-interactie af te handelen
class DatabaseManager {
    // Database URL (gebruik een in-memory DB zoals H2 voor het voorbeeld)
    private static final String DB_URL = "jdbc:h2:mem:testdb;INIT=RUNSCRIPT FROM 'classpath:init.sql'";
    private static final String USER = "sa";
    private static final String PASS = "";
    
    // SQL-statement om een gehasht wachtwoord in te voegen
    // Gebruik altijd PreparedStatement om SQL-injectie te voorkomen
    private static final String SQL_INSERT = "INSERT INTO users (username, password_hash) VALUES (?, ?)";

    public static Connection getConnection() throws SQLException {
        // Zorg ervoor dat de database en tabel bestaan voor dit voorbeeld
        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", USER, PASS);
             java.sql.Statement stmt = conn.createStatement()) {
            
            // Creëer de tabel indien deze niet bestaat
            // In een echte applicatie doe je dit via migraties
            String createTableSQL = "CREATE TABLE IF NOT EXISTS users ("
                                  + "id INT AUTO_INCREMENT PRIMARY KEY,"
                                  + "username VARCHAR(255) UNIQUE NOT NULL,"
                                  + "password_hash VARCHAR(60) NOT NULL)"; // BCrypt hashes zijn 60 karakters lang
            stmt.executeUpdate(createTableSQL);
            return DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", USER, PASS);
        } catch (SQLException e) {
            System.err.println("Fout bij het maken van de databaseverbinding of tabel: " + e.getMessage());
            throw e;
        }
    }

    public static boolean saveHashedPassword(String username, String hashedPassword) {
        // Gebruik try-with-resources voor automatische sluiting van Connection en PreparedStatement
        try (Connection conn = getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SQL_INSERT)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashedPassword);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            // In een echte app, log deze fout
            System.err.println("Fout bij het opslaan van het wachtwoord: " + e.getMessage());
            return false;
        }
    }
}

public class PasswordSaver {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            
            // 1. Vraag het wachtwoord
            System.out.print("Voer een gebruikersnaam in: ");
            String username = scanner.nextLine();
            
            // Wachtwoorden moeten bij voorkeur via console verborgen invoer worden gevraagd,
            // maar voor dit eenvoudige voorbeeld gebruiken we de Scanner.
            System.out.print("Voer het wachtwoord in (wordt gehasht): ");
            String rawPassword = scanner.nextLine();
            
            if (rawPassword.isEmpty()) {
                System.out.println("❌ Wachtwoord kan niet leeg zijn.");
                return;
            }

            // 2. Hash het wachtwoord veilig (Salt + BCrypt)
            String hashedPassword = PasswordUtil.hashPassword(rawPassword);
            System.out.println("\n✅ Wachtwoord gehasht (niet-omkeerbaar): " + hashedPassword);

            // 3. Sla de gehashte waarde op in de (gesimuleerde) database
            // De verbinding is beveiligd met PreparedStatement
            boolean success = DatabaseManager.saveHashedPassword(username, hashedPassword);

            // 4. Toon melding
            if (success) {
                System.out.println("\n🎉 Wachtwoord succesvol en veilig opgeslagen!");
            } else {
                System.out.println("\n❌ Fout bij het opslaan van het wachtwoord in de database.");
            }
            
        } catch (Exception e) {
            System.err.println("Er is een onverwachte fout opgetreden: " + e.getMessage());
        }
    }
}

