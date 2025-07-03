package com.students.studmanagement.service;

import com.students.studmanagement.exeptionhandling.InvelidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JWTService {
    @Autowired
    private UserDetailsService userDetailsService;
    String secretKey = "q9Ys7bKi1VrDd0bT7FvEQqX6vUqjZs6uUlsFq9QZUcA=";

    public JWTService(){
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey secKey = keyGen.generateKey();
            System.out.println(Base64.getEncoder().encodeToString(secKey.getEncoded()));
            System.out.println("key generated "+secretKey);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public String getToken(String useremail){
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(useremail);
            if(userDetails != null) {
                Map<String, Object> claims = new HashMap<>();
                claims.put("roles", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

                return Jwts.builder()
                        .setClaims(claims)
                        .setSubject(useremail)
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + 5 * 60 * 60 * 1000)) //5 hour
                        .signWith(getKeySet())
                        .compact();
            }
            else {
                return "user not found";
            }
        } catch (Exception e) {
            System.out.println("token not created : "+e.toString());
            return "token not created";
        }
    }


    private SecretKey getKeySet() {
        byte[] decodedKey = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(decodedKey);
    }

    public String extractUserEmail(String token) {
        String userEmail = extractClaim(token, Claims::getSubject);
        if(userEmail == null){
            return null;
        }
        return userEmail;
    }

    private <T> T extractClaim(java.lang.String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        if(claims == null){
            return null;
        }
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
    //                .setAllowedClockSkewSeconds(300)
                    .verifyWith(getKeySet())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userEmail = extractUserEmail(token);
        if(userEmail.equals(userDetails.getUsername()) && !isTokenExpired(token)){
            return true;
        }
        throw new InvelidTokenException("invelid token");
    }

    public boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public Date extractExpiration(String token) {
        try {
            return extractClaim(token, Claims::getExpiration);
        } catch (Exception e) {
            throw new InvelidTokenException("invelid token");
        }
    }
}
