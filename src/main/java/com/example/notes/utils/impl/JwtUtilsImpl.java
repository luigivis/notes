package com.example.notes.utils.impl;

import com.example.notes.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.xml.bind.DatatypeConverter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUtilsImpl implements JwtUtils {

  @Value("${jwt.key}")
  private String secretKeyString;

  @Value("${jwt.time.hours.expiration}")
  private Long timeHoursExpiration;

  @Override
  public SecretKey getKey() {
    final var apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKeyString);
    return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
  }

  @Override
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  @Override
  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  @Override
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  @Override
  public Claims extractAllClaims(String token) {
    return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
  }

  @Override
  public boolean isTokenExpired(String token) {
    var status = true;
    try {
      status = extractExpiration(token).before(new Date());
    } catch (Exception e) {
      log.error("Ocurrio un error {}", e.getMessage());
      return true;
    }
    return status;
  }

  @Override
  public String generateToken(String username) {
    final var claims = new HashMap<String, Object>();
    return createToken(claims, username);
  }

  @Override
  public String createToken(Map<String, Object> claims, String subject) {
    return Jwts.builder()
        .claims(claims)
        .subject(subject)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(timeHoursExpiration)))
        .signWith(getKey())
        .compact();
    
  }

  @Override
  public Boolean validateToken(String token, String user) {
    var username = extractUsername(token);
    return (username.equals(user) && !isTokenExpired(token));
  }
}
