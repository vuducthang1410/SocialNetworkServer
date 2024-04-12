package wint.webchat.service.Impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import wint.webchat.security.CustomUserDetail;

import java.security.Key;
import java.util.*;
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

    public String generateAccessToken(HashMap<String, Object> claims, String username,
                                      Collection<GrantedAuthority> role) {
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .claim("role",role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpirationMs))
                .signWith(SignatureAlgorithm.HS256, getSignInKey())
                .compact();
    }
    public String getUsernameFromToken(String token) {
        return extractClaims(token, Claims::getSubject);
    }
    public Collection<GrantedAuthority> getAuthoritiesFromToken(String token) {
        Claims claims = extractAllClaims(token);
        List<Map<String, String>> roles = (List<Map<String, String>>) claims.get("role");
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Map<String, String> role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.get("authority")));
        }
        return authorities;
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }
    public boolean isTokenValidUserName(String token, CustomUserDetail customUserDetail) {
        final String username = getUsernameFromToken(token);
        return (username.equals(customUserDetail.getUsername())) ;
    }

    public boolean isTokenExpiration(String token) {
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
