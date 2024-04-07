package wint.webchat.service.Impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import wint.webchat.security.CustomUserDetail;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;


@Service
public class JwtServiceImpl {
    @Value("${jwt.secret}")
    private String secret_key;
    @Value("${jwt.accessTokenExpirationMs}")
    private int accessTokenExpirationMs;
    @Value("${jwt.refreshTokenExpirationMs}")
    private int refreshTokenExpirationMs;


    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + refreshTokenExpirationMs))
                .signWith(SignatureAlgorithm.HS256, getSignInKey())
                .compact();
    }

    public String generateAccessToken(HashMap<String, Object> claims, CustomUserDetail customUserDetail) {
        return Jwts.builder()
                .claims(claims)
                .subject(customUserDetail.getUsername())
                .claim("role", customUserDetail.getAuthorities())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpirationMs))
                .signWith(SignatureAlgorithm.HS256, getSignInKey())
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }
    public boolean isTokenValid(String token, CustomUserDetail customUserDetail) {
        final String username = getUsernameFromToken(token);
        return (username.equals(customUserDetail.getUsername())) && !isTokenExpiration(token);
    }

    private boolean isTokenExpiration(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret_key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
