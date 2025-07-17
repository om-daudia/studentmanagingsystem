package com.studentmanagement.service;

import com.studentmanagement.common.exceptionhandling.ApplicationException;
import com.studentmanagement.dto.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
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
//            System.out.println(Base64.getEncoder().encodeToString(secKey.getEncoded()));
//            System.out.println("key generated "+secretKey);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public String getToken(UserDTO userDTO){
        try {
            String userEmail = userDTO.getEmail();
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            if(userDetails != null) {
                List<String> rolesFromDb = userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList());
                if(!rolesFromDb.contains(userDTO.getRole())){
                    throw new ApplicationException("user not allow",HttpStatus.UNAUTHORIZED);
                }
                Map<String, Object> claims = new HashMap<>();
                claims.put("roles", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()));

                return Jwts.builder()
                        .setClaims(claims)
                        .setSubject(userEmail)
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000)) //1 hour
                        .signWith(getKeySet())
                        .compact();
            }
            else {
                throw new ApplicationException("user not exist", HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception ex){
            throw new RuntimeException("unknown error");
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
        throw new ApplicationException("invelid token", HttpStatus.NOT_FOUND);
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
            throw new ApplicationException("invelid token", HttpStatus.NOT_FOUND);
        }
    }

    public List<String> extractAllRoles(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("roles", List.class);
    }
}
