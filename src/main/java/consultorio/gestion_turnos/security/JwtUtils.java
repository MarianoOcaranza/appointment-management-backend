package consultorio.gestion_turnos.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    @SuppressWarnings("unused")
    private final String jwtSecret;
    private final Long jwtExpiration;
    private final SecretKey key;

    public JwtUtils(
        @Value("${jwt.secret}") String jwtSecret,
        @Value("${jwt.expiration}") Long jwtExpiration) {
            this.jwtSecret = jwtSecret;
            this.jwtExpiration = jwtExpiration;
            this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("role",  userDetails.getAuthorities().iterator().next().getAuthority())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(key)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String getRoleFromToken(String token) {
    return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .get("role", String.class);
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload();
            return true;
        } catch (JwtException e) {
            throw new Error(e.getMessage());
        }
         
    }
}