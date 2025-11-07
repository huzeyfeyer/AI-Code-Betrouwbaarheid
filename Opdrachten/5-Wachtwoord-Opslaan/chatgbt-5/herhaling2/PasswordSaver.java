import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.Console;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Base64;

public class PasswordSaver {

    // PBKDF2 parameters (pas desnoods aan je performancedoelen aan)
    private static final String ALGO = "PBKDF2WithHmacSHA256";
    private static final int ITERATIONS = 210_000;
    private static final int SALT_BYTES = 16;     // 128-bit salt
    private static final int KEY_LENGTH = 256;    // 256-bit hash

    // Lees DB-gegevens uit omgevingsvariabelen voor veiligheid
    // Voorbeeld URL (PostgreSQL met SSL): jdbc:postgresql://db-host:5432/mijn_db?sslmode=require
    private static final String DB_URL  = System.getenv().getOrDefault("DB_URL",
            "jdbc:postgresql://localhost:5432/mijn_db?sslmode=require");
    private static final String DB_USER = System.getenv().getOrDefault("DB_USER", "postgres");
    private static final String DB_PASS = System.getenv().getOrDefault("DB_PASS", "postgres");

    public static void main(String[] args) {
        // 1) Wachtwoord veilig inlezen (zonder echo)
        Console console = System.console();
        if (console == null) {
            System.err.println("Geen Console beschikbaar. Start vanuit een echte terminal.");
            System.exit(1);
        }
        char[] password = console.readPassword("Voer wachtwoord in: ");

        // 2) Wachtwoord hashen (PBKDF2 + random salt)
        try {
            byte[] salt = generateSalt();
            byte[] hash = pbkdf2(password, salt, ITERATIONS, KEY_LENGTH);

            // Optioneel: wis het wachtwoord in geheugen
            java.util.Arrays.fill(password, '\0');

            // Bewaar in een compact formaat: algo$iteraties$saltB64$hashB64
            String record = String.format("pbkdf2-sha256$%d$%s$%s",
                    ITERATIONS,
                    Base64.getEncoder().encodeToString(salt),
                    Base64.getEncoder().encodeToString(hash)
            );

            // 3) Verbinden met DB via SSL en opslaan
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
                ensureTable(conn); // maakt tabel aan als die nog niet bestaat

                String sql = "INSERT INTO users (password_hash) VALUES (?)";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, record);
                    ps.executeUpdate();
                }

                System.out.println("Wachtwoord opgeslagen");
            }
        } catch (Exception e) {
            System.err.println("Er ging iets mis: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // -------- Helpers --------

    private static void ensureTable(Connection conn) throws SQLException {
        // Eenvoudige tabel met alleen de hash-kolom
        String ddl = "CREATE TABLE IF NOT EXISTS users ("
                + "id SERIAL PRIMARY KEY, "
                + "password_hash TEXT NOT NULL"
                + ")";
        try (Statement st = conn.createStatement()) {
            st.execute(ddl);
        }
    }

    private static byte[] generateSalt() {
        byte[] salt = new byte[SALT_BYTES];
        new SecureRandom().nextBytes(salt);
        return salt;
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int keyLength)
            throws Exception {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGO);
        return skf.generateSecret(spec).getEncoded();
    }
}
