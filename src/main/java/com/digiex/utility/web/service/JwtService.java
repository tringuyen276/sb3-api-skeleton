package com.digiex.utility.web.service;

import com.digiex.utility.web.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.sql.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;

public class JwtService {
  @Value("${jwt.secretKey}")
  private String base64SecretKey;

  @Value("${jwt.expirationTime}")
  private Long expirationTime;

  private SecretKey secretKey;

  public JwtService() {
    this.secretKey = Keys.hmacShaKeyFor(base64SecretKey.getBytes());
  }

  public String generateToken(User user) {
    Claims claims = Jwts.claims().setSubject(user.getId().toString());

    claims.put("user", user);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
        .signWith(secretKey)
        .compact();
  }

  public Claims extractAllClaims(String token) {
    return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
  }

  public String extractUserId(String Token) {
    return extractAllClaims(Token).getId();
  }
}
