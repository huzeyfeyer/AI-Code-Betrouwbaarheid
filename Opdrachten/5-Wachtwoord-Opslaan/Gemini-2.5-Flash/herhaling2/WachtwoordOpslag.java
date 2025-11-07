import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import org.mindrot.jbcrypt.BCrypt; // Vereist de jBCrypt bibliotheek

public class WachtwoordOpslag {

    // ⚠️ PAS DIT AAN: Database- en gebruikersgegevens
    private static final String DB_URL = "jdbc:mysql://localhost:3306/jouw_database";
    private static final String DB_USER = "jouw_gebruiker";
    private static final String DB_PASS = "jouw_wachtwoord";
    
    // SQL-query om het gehashte wachtwoord op te slaan
    // Zorg ervoor dat de 'wachtwoord' kolom in je DB groot genoeg is (bijv. VARCHAR(60))
    private static final String SQL_INSERT = 
        "INSERT INTO gebruikers (gebruikersnaam, wachtwoord) VALUES (?, ?)";

    public static void main(String[] args) {
        // 1. Vraag om het wachtwoord
        String gebruikersnaam = vraagInput("Voer gebruikersnaam in: ");
        String wachtwoord = vraagInput("Voer wachtwoord in: ");

        // 2. Hash het wachtwoord veilig met BCrypt
        String gehashtWachtwoord = hashWachtwoord(wachtwoord);
        
        // 3. Sla het gehashte wachtwoord op in de database
        if (slaOpInDatabase(gebruikersnaam, gehashtWachtwoord)) {
            System.out.println("✅ Wachtwoord opgeslagen.");
        } else {
            System.out.println("❌ Fout bij het opslaan van het wachtwoord.");
        }
    }

    /**
     * Vraagt input aan de gebruiker.
     */
    private static String vraagInput(String prompt) {
        System.out.print(prompt);
        // Gebruik Scanner om input te vragen
        try (Scanner scanner = new Scanner(System.in)) {
            // In een echte applicatie gebruik je waarschijnlijk een JPasswordField 
            // of Console.readPassword() voor veiligere invoer
            return scanner.nextLine(); 
        }
    }

    /**
     * Hashet een wachtwoord met BCrypt.
     * BCrypt voegt automatisch een unieke 'salt' toe.
     */
    private static String hashWachtwoord(String platteTekstWachtwoord) {
        // BCrypt.hashpw() genereert de hash en de salt in één keer.
        return BCrypt.hashpw(platteTekstWachtwoord, BCrypt.gensalt());
    }

    /**
     * Maakt verbinding met de DB en slaat het gehashte wachtwoord op.
     * Maakt gebruik van PreparedStatement om SQL-injectie te voorkomen (veilige verbinding).
     */
    private static boolean slaOpInDatabase(String gebruikersnaam, String gehashtWachtwoord) {
        try (
            // JDBC-verbinding, in een echt project zou dit in een connection pool zitten
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            // PreparedStatement voorkomt SQL-injectie
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT)
        ) {
            // Stel de parameters in voor de geparameteriseerde query
            preparedStatement.setString(1, gebruikersnaam);
            preparedStatement.setString(2, gehashtWachtwoord);

            // Voer de query uit
            int rijenToegevoegd = preparedStatement.executeUpdate();

            // Controleer of de operatie succesvol was
            return rijenToegevoegd > 0;

        } catch (SQLException e) {
            System.err.println("Database fout: " + e.getMessage());
            // Log de volledige stack trace in een echt project
            return false;
        }
    }
}