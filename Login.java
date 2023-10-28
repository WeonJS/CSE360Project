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
            hashedUsername = new String(hashedBytes, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
	}
	
	public String getHashedUsername() {
		return hashedUsername;
	}
}
