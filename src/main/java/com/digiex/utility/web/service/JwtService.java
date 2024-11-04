package com.digiex.utility.web.service;

import com.digiex.utility.web.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.sql.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
  @Value("${jwt.secretKey}")
  private String base64SecretKey;

  @Value("${jwt.expirationTime}")
  private Long expirationTime;

  private SecretKey secretKey;

  @PostConstruct
  public void init() {
    this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
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
