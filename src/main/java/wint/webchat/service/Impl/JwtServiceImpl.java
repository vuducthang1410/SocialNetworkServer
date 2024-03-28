package wint.webchat.service.Impl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import wint.webchat.security.CustomUserDetail;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;


@Service
public class JwtServiceImpl {
    @Value("${jwt.secret}")
    private String secret_key;
    @Value("${jwt.accessTokenExpirationMs}")
    private int accessTokenExpirationMs;
    @Value("${jwt.refreshTokenExpirationMs}")
    private int  refreshTokenExpirationMs;

    public String generateJwtToken(CustomUserDetail customUserDetail){
        return generateRefreshToken(customUserDetail.getUsername());
    }

    private String generateRefreshToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime()+accessTokenExpirationMs))
                .signWith(getKey())
                .compact();
    }
    public String generateAccessToken(HashMap<String,Object> claims,CustomUserDetail customUserDetail){
        return Jwts.builder()
                .claims(claims)
                .subject(customUserDetail.getUsername())
                .claim("role",customUserDetail.getAuthorities())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+refreshTokenExpirationMs))
                .signWith(getKey())
                .compact();
    }
    public String getUsernameFromToken(String token){
        return extractClaims(token,Claims::getSubject);
    }
    public <T> T extractClaims(String token,Function<Claims,T> claimsTFunction){
        return claimsTFunction.apply(Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload());
    }
    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(Jwts.SIG.HS256.key().build().getEncoded());
    }
    public boolean isTokenValid(String token,CustomUserDetail customUserDetail){
        final String username=getUsernameFromToken(token);
        return (username.equals(customUserDetail.getUsername()))&&!isTokenExpiration(token);
    }

    private boolean isTokenExpiration(String token) {
        return extractClaims(token,Claims::getExpiration).before(new Date());
    }

}
