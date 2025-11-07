import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.Arrays;
import org.mindrot.jbcrypt.BCrypt;

public class SavePassword {
    // Lees DB-config uit omgevingsvariabelen (veiliger dan hardcoden)
    // Voorbeeld DB_URL: jdbc:postgresql://db-host:5432/mijn_db?sslmode=require
    private static final String DB_URL  = getenvOrThrow("DB_URL");
    private static final String DB_USER = getenvOrThrow("DB_USER");
    private static final String DB_PASS = getenvOrThrow("DB_PASS");

    public static void main(String[] args) {
        char[] password = null;
        try {
            // 1) Wachtwoord inlezen zonder echo
            if (System.console() != null) {
                password = System.console().readPassword("Voer uw wachtwoord in: ");
            } else {
                // Fallback voor IDEs die geen Console ondersteunen
                System.out.print("Voer uw wachtwoord in: ");
                password = new java.util.Scanner(System.in).nextLine().toCharArray();
            }

            if (password == null || password.length == 0) {
                System.err.println("Geen wachtwoord ingevoerd.");
                return;
            }

            // 2) Hashen met BCrypt (inclusief salt)
            String hashed = BCrypt.hashpw(new String(password), BCrypt.gensalt(12));

            // 3) Opslaan in database via beveiligde verbinding
            saveHash(hashed);

            // 4) Succesmelding
            System.out.println("Wachtwoord opgeslagen");

        } catch (Exception e) {
            System.err.println("Er ging iets mis: " + e.getMessage());
        } finally {
            // Wachtwoordbuffer overschrijven in het geheugen
            if (password != null) Arrays.fill(password, '\0');
        }
    }

    private static void saveHash(String passwordHash) throws SQLException {
        // Eenvoudige tabel met alleen een hash en timestamp
        String sql = "INSERT INTO passwords (password_hash, created_at) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, passwordHash);
            ps.setObject(2, OffsetDateTime.now()); // PostgreSQL timestamptz compatibel
            ps.executeUpdate();
        }
    }

    private static String getenvOrThrow(String key) {
        String v = System.getenv(key);
        if (v == null || v.isBlank()) {
            throw new IllegalStateException("Omgevingsvariabele ontbreekt: " + key);
        }
        return v;
    }
}

