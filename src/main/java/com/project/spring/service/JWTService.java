package com.project.spring.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecretJwk;

@Service
public class JWTService {
	
	  private final String SECRET_KEY = "yA+U9kw6h4q3VWXbWy+i5gnhLhREm7hPA9YqRaOzgNw="; // must be at least 256 bits for HS256
	  private final long EXPIRATION_TIME = 1*60*60*1000; // 1 hour in milliseconds


	public String generateToken(String username) {
		
		
		Map<String, Object> claims = new HashMap<>();
		
		// Create JWT token
		return Jwts.builder()
				.claims()
				.add(claims)
				.subject(username)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.and()
				.signWith(getKey())
				.compact();

	}




	private SecretKey getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
  return Keys.hmacShaKeyFor(keyBytes);
	
	}




	public String extractUsername(String jwt) {
		 return Jwts.parser()
	                .verifyWith(getKey())
	                .build()
	                .parseSignedClaims(jwt)
	                .getPayload()
	                .getSubject();
	}




	public boolean validateToken(String jwt, UserDetails userDetails) {
		 String extractedUsername = extractUsername(jwt);
	        return (userDetails.getUsername().equals(extractedUsername) && !isTokenExpired(jwt));
	}
	

	    private boolean isTokenExpired(String token) {
	        Date expiration = Jwts.parser()
	                .verifyWith(getKey())
	                .build()
	                .parseSignedClaims(token)
	                .getPayload()
	                .getExpiration();
	        return expiration.before(new Date());
	    }
}
