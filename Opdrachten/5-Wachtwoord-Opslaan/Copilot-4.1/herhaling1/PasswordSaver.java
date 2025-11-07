import java.sql.*;
import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordSaver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Geef wachtwoord: ");
        String password = scanner.nextLine();

        String hashedPassword = hashPassword(password);

        if (hashedPassword == null) {
            System.out.println("Fout bij het hashen van het wachtwoord.");
            return;
        }

        // Maak een verbinding met een SQLite-database
        String url = "jdbc:sqlite:passwords.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            createTableIfNotExists(conn);

            String sql = "INSERT INTO wachtwoorden (password_hash) VALUES (?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, hashedPassword);
                pstmt.executeUpdate();
                System.out.println("Wachtwoord opgeslagen");
            }
        } catch (SQLException e) {
            System.out.println("Databasefout: " + e.getMessage());
        }
    }

    // Hash het wachtwoord met SHA-256
    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    // Maak de tabel aan als hij nog niet bestaat
    private static void createTableIfNotExists(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS wachtwoorden (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                     "password_hash TEXT NOT NULL" +
                     ")";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }
}