package CSE360Project;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login {
	private String hashedUsername;
	
	
	public Login(String rawUser, String rawPassword) {
		// hash username
		try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hashedBytes = md.digest(rawUser.getBytes(StandardCharsets.UTF_8));
            hashedUsername = bytesToHex(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
	}
	
	public String getHashedUsername() {
		return hashedUsername;
	}
	
	public static String bytesToHex(byte[] bytes) {
        StringBuilder hexStringBuilder = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            hexStringBuilder.append(String.format("%02x", b)); // Convert byte to 2-digit hexadecimal representation
        }
        return hexStringBuilder.toString();
    }
}
