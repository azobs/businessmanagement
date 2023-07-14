package com.c2psi.businessmanagement.utils;

import com.c2psi.businessmanagement.models.auth.ExtendedUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {
    private String SECRET_KEY = "secret";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractIdEnterprise(String token){
        final Claims claims = extractAllClaims(token);
        return claims.get("idEnterprise", String.class);
    }

    public String extractIdPos(String token){
        final Claims claims = extractAllClaims(token);
        return claims.get("idPos", String.class);
    }

    public String extractUserBMType(String token){
        final Claims claims = extractAllClaims(token);
        return claims.get("userBMType", String.class);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /*public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }*/
    public String generateToken(ExtendedUser userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails);
    }

    /*private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .claim("idEnterprise", "")
                .claim("idPos", "")
                .claim("userBMType", "")
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }*/

    private String createToken(Map<String, Object> claims, ExtendedUser extendedUser) {

        return Jwts.builder().setClaims(claims).setSubject(extendedUser.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .claim("idEnterprise", extendedUser.getIdEnterprise().toString())
                .claim("idPos", extendedUser.getIdPos().toString())
                .claim("userBMType", extendedUser.getUserBMType().toString())
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
