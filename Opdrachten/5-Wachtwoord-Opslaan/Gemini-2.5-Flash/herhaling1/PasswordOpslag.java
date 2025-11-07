import org.mindrot.jbcrypt.BCrypt;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class PasswordOpslag {

    // 🔒 Database instellingen (PAS DIT AAN!)
    private static final String DB_URL = "jdbc:mysql://localhost:3306/jouw_database";
    private static final String DB_USER = "jouw_gebruiker";
    private static final String DB_PASSWORD = "jouw_wachtwoord";
    private static final String INSERT_SQL = "INSERT INTO gebruikers (gebruikersnaam, wachtwoord_hash) VALUES (?, ?)";

    /**
     * Hash een platte-tekst wachtwoord veilig met BCrypt.
     * BCrypt zorgt automatisch voor een unieke salt.
     */
    public static String hashWachtwoord(String platteTekstWw) {
        // De '12' is de cost factor (work factor). Hoger is veiliger, maar langzamer.
        return BCrypt.hashpw(platteTekstWw, BCrypt.gensalt(12));
    }

    /**
     * Slaat de wachtwoord-hash op in de database.
     * Gebruikt 'try-with-resources' voor een veilige afsluiting van de verbinding.
     */
    public static void slaWachtwoordOp(String gebruikersnaam, String wachtwoordHash) {
        // Gebruik 'try-with-resources' om Connection en PreparedStatement automatisch te sluiten
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(INSERT_SQL)) {

            // Voorkomt SQL-injectie door PreparedStatement
            pstmt.setString(1, gebruikersnaam); 
            pstmt.setString(2, wachtwoordHash);

            pstmt.executeUpdate();
            
            System.out.println("✅ Wachtwoord opgeslagen.");

        } catch (SQLException e) {
            System.err.println("❌ Fout bij het opslaan in de database: " + e.getMessage());
            // In een echte applicatie zou je hier loggen en/of een meer gebruikersvriendelijke fout geven
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Voer gebruikersnaam in:");
        String gebruikersnaam = scanner.nextLine();

        // ⚠️ Gebruik in een echte applicatie Console.readPassword() om de invoer te verbergen
        System.out.println("Voer het wachtwoord in (wordt gehasht opgeslagen):");
        String platteTekstWachtwoord = scanner.nextLine();

        if (platteTekstWachtwoord.isEmpty()) {
            System.out.println("Wachtwoord mag niet leeg zijn.");
            return;
        }

        // 1. Wachtwoord hashen met BCrypt
        String gehashteWachtwoord = hashWachtwoord(platteTekstWachtwoord);
        
        // Ter controle (dit zou NIET gelogd moeten worden in productie!)
        System.out.println("\n(Debug) Gehashte wachtwoord: " + gehashteWachtwoord);

        // 2. Hash veilig opslaan in de database
        slaWachtwoordOp(gebruikersnaam, gehashteWachtwoord);
    }
}