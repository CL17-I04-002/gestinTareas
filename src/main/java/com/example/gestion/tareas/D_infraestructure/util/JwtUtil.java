package com.example.gestion.tareas.D_infraestructure.util;

import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }
    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        try{
            final Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
        } catch (Exception e){
            System.out.println("Ocurrio un error: " + e.getMessage());
            return null;
        }
    }
    private Claims extractAllClaims(String token){
        try{
            return Jwts.parser().setSigningKey(FilesHandler.loadKey("application.properties", "server.key")).parseClaimsJws(token).getBody();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }
    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }
    public String createToken(Map<String, Object> claims, String subject){
        try{
            return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 *24))
                    .signWith(SignatureAlgorithm.HS256, FilesHandler.loadKey("application.properties", "server.key")).compact();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Boolean validateToken(String token){
        try{
            try {
                Jwts.parser().setSigningKey(FilesHandler.loadKey("application.properties", "server.key")).parseClaimsJws(token);
            } catch (IOException e){
                throw  new RuntimeException(e);
            }
            return true;
        } catch (JwtException | IllegalArgumentException e){
            e.printStackTrace();
            return false;
        }
    }
}
