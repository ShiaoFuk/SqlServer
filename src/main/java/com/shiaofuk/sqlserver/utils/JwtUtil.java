package com.shiaofuk.sqlserver.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey SECRET_KEY;

    @Autowired
    private JwtUtil(@Value("${JWT_KEY}") String SECRET_KEY) {
        this.SECRET_KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    // Token expiration time (e.g., 1 hour)
    private static final long EXPIRATION_TIME = 60 * 60 * 1000 * 24 * 7; // 1 hour in milliseconds

    /**
     * 生产token
     * @param userId token的唯一凭证
     * @return
     */
    public String generateToken(Integer userId) {
        return Jwts.builder().
                subject(String.valueOf(userId)).
                expiration(new Date(new Date().getTime() + EXPIRATION_TIME)).
                signWith(SECRET_KEY).
                compact();
    }

    /**
     * Parse and validate a JWT token.
     * @param token the JWT token to validate
     * @return userId if verified else null
     * @throws JwtException if the token is invalid
     */
    public Integer verifyToken(String token) throws JwtException {
        try {
            Jws<Claims> jwt = Jwts.parser()
                    .verifyWith(SECRET_KEY)
                    .build()
                    .parseSignedClaims(token);
            Claims claims = jwt.getPayload();
            Date expireTime = claims.getExpiration();
            if (expireTime.before(new Date())) {
                throw new JwtException("expired token");
            }
            return Integer.valueOf(claims.getSubject());
        } catch (JwtException ex) {
            // 验证失败
            System.out.println(ex.getMessage());
            return null;
        }
    }
}
