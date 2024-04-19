package com.example.emmisiontracker.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class JwtService {

    private final static String ENCRYPTION_KEY = "e23c74e541a53eefa4d1860f95bb6962d38fd4ec63476ed720fbacfd9b8de51b";

    private final UserDetailsService userDetailsService;

    public Map<String, String> generate(String username) {
        UserDetails user = userDetailsService.loadUserByUsername(username);
        return generateJwt(user);
    }

    private Map<String, String> generateJwt(UserDetails userDetails) {
        final Map<String, String> claims = Map.of(
                "username", userDetails.getUsername()
        );

        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime + 30 * 60 * 1000;

        String bearer = Jwts.builder()
                .issuedAt(new Date(currentTime))
                .expiration(new Date(expirationTime))
                .subject(userDetails.getUsername())
                .claims(claims)
                .signWith(getKey())
                .compact();

        return Map.of("token", bearer);
    }

    private SecretKey getKey() {
        byte[] decode = Decoders.BASE64.decode(ENCRYPTION_KEY);
        return Keys.hmacShaKeyFor(decode);
    }

    public String extractUsername(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = getClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }

    private <T> T getClaim(String token, Function<Claims, T> claim) {
        Claims claims = getAllClaims(token);
        return claim.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
