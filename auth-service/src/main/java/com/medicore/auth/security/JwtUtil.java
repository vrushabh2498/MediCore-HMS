package com.medicore.auth.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {
	
	@Value("${jwt.secret}")
	private String secret;

	 @Value("${jwt.expiration}")
	    private long expiration;
	 
	 private Key key;
	 
	 @PostConstruct
	    public void init() {
	        this.key = Keys.hmacShaKeyFor(secret.getBytes());
	    }
	 
	 public String generateToken(String email ,Long userId, List<String> roles) {
	     
		 Map<String, Object> claims = new HashMap<>();
		    claims.put("userId", userId);
		    claims.put("roles", roles);
		 
		 return Jwts.builder()
				 .setClaims(claims)
	                .setSubject(email)
	                .setIssuedAt(new Date())
	                .setExpiration(new Date(System.currentTimeMillis() + expiration))
	                .signWith(key, SignatureAlgorithm.HS256)
	                .compact();
	    }
	 private boolean isTokenExpired(String token) {
		    return extractClaims(token)
		            .getExpiration()
		            .before(new Date());
		} 
	 
	 public String extractEmail(String token) {
	        return extractClaims(token).getSubject();
	    }
	 
	 public boolean validateToken(String token, String email) {
	        final String extractedEmail = extractEmail(token);
	        return (extractedEmail.equals(email) && !isTokenExpired(token));
	    }
	 
	 private Claims extractClaims(String token) {
	        return Jwts.parserBuilder()
	                .setSigningKey(key)
	                .build()
	                .parseClaimsJws(token)
	                .getBody();
	    }
	 public Claims extractAllClaims(String token) {

		    return Jwts.parserBuilder()
		            .setSigningKey(getSigningKey())
		            .build()
		            .parseClaimsJws(token)
		            .getBody();
		}

	private SecretKey getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secret);
	    return Keys.hmacShaKeyFor(keyBytes);
	}
	

}
