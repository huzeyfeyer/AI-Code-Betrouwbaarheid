import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.Console;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.sql.*;
import java.time.Instant;
import java.util.Base64;
import java.util.Scanner;
import java.util.Arrays;

public class SavePassword {
    // PBKDF2-instellingen
    private static final String PBKDF2_ALG = "PBKDF2WithHmacSHA256";
    private static final int ITERATIONS = 210_000;     // modern, veilig tempo
    private static final int SALT_BYTES = 16;          // 128-bit salt
    private static final int KEY_LEN_BITS = 256;       // 256-bit hash

    public static void main(String[] args) {
        // 1) Wachtwoord inlezen zonder echo (val terug op Scanner indien nodig)
        char[] password = readPasswordSafely("Voer een wachtwoord in: ");

        if (password == null || password.length == 0) {
            System.err.println("Geen wachtwoord ingevoerd.");
            return;
        }

        // 2) Hash genereren
        String passwordHash = null;
        try {
            passwordHash = hashPassword(password);
        } catch (Exception e) {
            System.err.println("Kon wachtwoord niet hashen: " + e.getMessage());
            secureWipe(password);
            return;
        } finally {
            // wis het ruwe wachtwoord uit het geheugen
            secureWipe(password);
        }

        // 3) DB-verbinding (via SSL als je URL dat vereist) en opslag
        String url  = System.getenv("DB_URL");       // bijv. jdbc:postgresql://localhost:5432/demo?sslmode=require
        String user = System.getenv("DB_USER");
        String pass = System.getenv("DB_PASSWORD");

        if (url == null || user == null || pass == null) {
            System.err.println("Stel DB_URL, DB_USER en DB_PASSWORD als omgevingsvariabelen in.");
            return;
        }

        String createTableSql = ""
            + "CREATE TABLE IF NOT EXISTS users_passwords ("
            + "  id SERIAL PRIMARY KEY,"
            + "  password_hash TEXT NOT NULL,"
            + "  created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()"
            + ");";

        String insertSql = "INSERT INTO users_passwords (password_hash, created_at) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            // Optioneel: verifiëren dat de verbinding SSL gebruikt (voor Postgres)
            // System.out.println("SSL: " + conn.getClientInfo("ssl")); // driver-afhankelijk

            try (Statement st = conn.createStatement()) {
                st.execute(createTableSql);
            }

            try (PreparedStatement ps = conn.prepareStatement(insertSql)) {
                ps.setString(1, passwordHash);
                ps.setTimestamp(2, Timestamp.from(Instant.now()));
                ps.executeUpdate();
            }

            System.out.println("Wachtwoord opgeslagen");

        } catch (SQLException e) {
            System.err.println("Databasefout: " + e.getMessage());
        }
    }

    /** Leest een wachtwoord zonder echo; valt terug op Scanner als Console niet beschikbaar is. */
    private static char[] readPasswordSafely(String prompt) {
        Console console = System.console();
        if (console != null) {
            return console.readPassword(prompt);
        } else {
            System.out.print(prompt);
            Scanner sc = new Scanner(System.in, StandardCharsets.UTF_8);
            String line = sc.nextLine();
            return line != null ? line.toCharArray() : null;
        }
    }

    /** Maakt een PBKDF2-hash met random salt in het formaat:
     *  pbkdf2_sha256$<iteraties>$<base64(salt)>$<base64(hash)> */
    private static String hashPassword(char[] password) throws Exception {
        byte[] salt = new byte[SALT_BYTES];
        new SecureRandom().nextBytes(salt);

        PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LEN_BITS);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALG);
        byte[] hash = skf.generateSecret(spec).getEncoded();

        String saltB64 = Base64.getEncoder().encodeToString(salt);
        String hashB64 = Base64.getEncoder().encodeToString(hash);

        // Formaat is handig om later te verifiëren
        return String.format("pbkdf2_sha256$%d$%s$%s", ITERATIONS, saltB64, hashB64);
    }

    /** Veegt gevoelige data uit het geheugen. */
    private static void secureWipe(char[] data) {
        if (data != null) Arrays.fill(data, '\0');
    }
}
