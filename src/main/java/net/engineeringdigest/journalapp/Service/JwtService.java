package net.engineeringdigest.journalapp.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class JwtService {

    private final String secretKey="l4aHs6r79GKnY5nhRZVlvhD09wd2xMfE1MHppBBUxCA";
    private SecretKey getSigningKey() {
        //byte[] decode = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
    public String generateToken(String userName) {
        HashMap<String, Object> claims = new HashMap<>();
        return createToken(claims,userName);
    }
    public String extractUsername(String token) {
        return function(extractAllClaims(token),Claims::getSubject);
    }
    public boolean isTokenExpired(String token) {
        return function(extractAllClaims(token),Claims::getExpiration).before(new Date());
    }
    public String createToken(HashMap<String,Object> claims,String userName) {
        return Jwts.builder()
                .claims(claims)
                .header().empty().add("typ","jwt").and()
                .subject(userName)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60))
                .issuer("admin of app")
                .signWith(getSigningKey())
                .compact();

    }
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }
    public <T> T function(Claims claims, Function<Claims,T> function) {
        return function.apply(claims);
    }
    public  boolean validateToken(String token) {
        return  !isTokenExpired(token);
    }
}
