package com.mycompany.thei.hr.system.web;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Named("loginController")
@SessionScoped
public class LoginController implements Serializable {
    private String username;
    private String password;
    private boolean loggedIn;

    // SHA-256 Hash of "password"
    private static final String EXPECTED_HASH = "5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8";

    /**
     * Authenticates the user against a hardcoded credential set hashing check.
     * Prevents access to the rest of the application unless passed.
     * 
     * @return JSF navigation string redirecting to Dashboard if success, or reloading login if failure.
     */
    public String login() {
        if ("hr".equals(username) && EXPECTED_HASH.equals(hashPassword(password))) {
            loggedIn = true;
            return "index?faces-redirect=true";
        }
        return "login";
    }

    /**
     * Converts a raw string password into a non-reversible SHA-256 hash
     * for comparison against the stored application hash token.
     * 
     * @param pass The plain text string to convert.
     * @return A hexadecimal hashed string
     */
    private String hashPassword(String pass) {
        if (pass == null) return null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedhash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("SHA-256 not found", ex);
        }
    }

    public String logout() {
        loggedIn = false;
        return "login?faces-redirect=true";
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public boolean isLoggedIn() { return loggedIn; }
    public void setLoggedIn(boolean loggedIn) { this.loggedIn = loggedIn; }
}
