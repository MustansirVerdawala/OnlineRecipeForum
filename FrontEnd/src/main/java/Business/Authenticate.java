package Business;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.AbstractMap;
import java.util.Date;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class Authenticate {

    private SignatureAlgorithm signatureAlgorithm;
    private Key signingKey;  // Secret key for signing JWT

    public Authenticate() {
        // Setting the algorithm to use for signing the JWT
        signatureAlgorithm = SignatureAlgorithm.HS256;

        // Generate a strong 256-bit secret key
        signingKey = generateKey();
    }

    // Method to generate a secure 256-bit key for signing JWT
    private Key generateKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            keyGenerator.init(256);  // 256-bit key
            return keyGenerator.generateKey();  // Generates a secure key
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate a secure key", e);
        }
    }

    public String createJWT(String issuer, String subject, long ttlMillis) {
        // The JWT signature algorithm we will be using to sign the token
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // Setting JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signingKey);  // Use the generated secret key

        // Add expiration if specified
        if (ttlMillis > 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        // Build the JWT and return the compact string
        String jwt = builder.compact();
        System.out.println("Generated JWT: " + jwt);
        return jwt;
    }

    public Entry<Boolean, String> verify(String jwt) {
        Jws<Claims> jws = null;
        String username = "";

        System.out.println("Verifying JWT: " + jwt);
        try {
            // Parse the JWT using the signing key
            jws = Jwts.parserBuilder()
                    .setSigningKey(signingKey)  // Use the same generated key for verification
                    .build()
                    .parseClaimsJws(jwt);  // Parse the JWT

            // If no exception, JWT is valid
            System.out.println("JWT is valid. Can trust it.");
            username = jws.getBody().getSubject();  // Extract the subject (username)
            System.out.println("Extracted username: " + username);

        } catch (JwtException ex) {
            // Catch any JWT exception and log it
            System.out.println("Error during JWT verification: " + ex.getMessage());
            Entry entry = new AbstractMap.SimpleEntry<>(false, "Invalid JWT");
            return entry;
        }

        // Check for expiration date
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        if (jws.getBody().getExpiration().before(now)) {
            System.out.println("JWT has expired.");
            Entry entry = new AbstractMap.SimpleEntry<>(false, "JWT expired");
            return entry;
        }

        // JWT is valid and not expired
        Entry entry = new AbstractMap.SimpleEntry<>(true, username);
        return entry;
    }
    
    public boolean validateJWT(HttpServletRequest request, String authenticationCookieName) {
        Cookie[] cookies = request.getCookies();
        String token = "";

        // Check if authentication token in cookies (customer already in session)
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(authenticationCookieName)) {
                    token = cookie.getValue();
                }
            }
        }

        // If a token was found, try to verify it to make sure we can trust the token
        if (!token.isEmpty()) {
            try {
                // Make sure cookie is verified
                if (this.verify(token).getKey()) {
                    return true; // Token is valid
                }
            } catch (Exception e) {
                // Log and handle exceptions if token verification fails
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, "Token verification failed", e);
            }
        }

        // Return false if no valid token found or verification fails
        return false;
    }
}
