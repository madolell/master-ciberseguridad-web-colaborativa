package helpers;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class HashUtils {

    private static final int SALT_LENGTH = 16;
    private static final int ITERATIONS = 150_000;

    //Generamos un salt seguro para los hash
    public static String generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    //Ahora hasheamos la contraseña utilizando SHA-256 + salt
    public static String hashPassword(String password, String salt) {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = (salt + password).getBytes(StandardCharsets.UTF_8);

                for (int i = 0; i < ITERATIONS; i++) {
                    hash = digest.digest(hash);
                }

                return Base64.getEncoder().encodeToString(hash);
            } catch (Exception e) {
                throw new IllegalStateException("Error hashing password", e);
            }
        }

    // Verifica la contraseña en login
    public static boolean verifyPassword(
            String inputPassword,
            String storedHash,
            String storedSalt) {

        String inputHash = hashPassword(inputPassword, storedSalt);
        return constantTimeEquals(storedHash, inputHash);
    }

    // Comparación en tiempo constante
    private static boolean constantTimeEquals(String a, String b) {
        if (a.length() != b.length()) return false;
        int result = 0;
        for (int i = 0; i < a.length(); i++) {
            result |= a.charAt(i) ^ b.charAt(i);
        }
        return result == 0;
    }
}

@Deprecated
public static String getMd5(String s) {
    throw new UnsupportedOperationException(
        "Insecure method removed. Use hashPassword() instead."
    );
}