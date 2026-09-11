package cinema_backend.api_movie_system.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private final JwtProperties jwtProperties;
    private final Key signingKey;

    public JwtUtil(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        // HMAC-SHA key derived from the configured secret; jjwt requires >= 256 bits for HS256
        this.signingKey = Keys.hmacShaKeyFor(
            jwtProperties.getSecret().getBytes(java.nio.charset.StandardCharsets.UTF_8)
        );
    }

    // Builds a signed JWT containing the user id (subject), email and role code as claims
    public String generateAccessToken(Integer userId, String email, String roleCode) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtProperties.getExpirationMs());

        return Jwts.builder()
            .subject(String.valueOf(userId))
            .claim("email", email)
            .claim("role", roleCode)
            .issuedAt(now)
            .expiration(expiry)
            .signWith((SecretKey) signingKey)
            .compact();
    }

    // Opaque refresh token; kept separate from the access token so it can be long-lived
    public String generateRefreshToken(Integer userId) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtProperties.getRefreshExpirationMs());

        return Jwts.builder()
            .subject(String.valueOf(userId))
            .claim("type", "refresh")
            .issuedAt(now)
            .expiration(expiry)
            .signWith((SecretKey) signingKey)
            .compact();
    }

    // Parses and validates the token signature/expiry, throwing if either check fails
    public Claims parseClaims(String token) {
        return Jwts.parser()
            .verifyWith((SecretKey) signingKey)
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
}
