package com.example.todo_list_management.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
@RequiredArgsConstructor
public class JwtUtil {
    private static final String SECRET_KEY = "78214125442A472D4B6150645367566B59703373367639792F423F4528482B4D";
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }


    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /***
     * generateToken as name of method that it use for generate token when user authenticated
     */
    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    /***
     * generateToken that is the method overloading that use to generate token for user when
     * User has authenticate already
     * .setExpiration(new Date(System.currentTimeMillis() + 1000 *600 *24)) // It use for
     *  set time to our token to be expire during 24 day
     */
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 600 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
