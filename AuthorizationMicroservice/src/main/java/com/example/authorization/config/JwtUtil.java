package com.example.authorization.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.authorization.model.Login;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {
	 private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);

	 private static final String SECRETKEY = "${jwt.secret}";


	 public String extractUsername(String token) {
	  return extractClaim(token, Claims::getSubject);
	 }

	 public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
	  LOGGER.info("START");

	  final Claims claims = extractAllClaims(token);
	  LOGGER.info("END");

	  return claimsResolver.apply(claims);

	 }


	 private Claims extractAllClaims(String token) {

	  return Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(token).getBody();

	 }


	 public String generateToken(Login login) {
	  

	  Map<String, Object> claims = new HashMap<>();
	  
	  return createToken(claims, login.getPassword());
	 }

	 private String createToken(Map<String, Object> claims, String subject) {
	  

	  String compact = Jwts.builder().setClaims(claims).setSubject(subject)
	    .setIssuedAt(new Date(System.currentTimeMillis()))
	    .setExpiration(new Date(System.currentTimeMillis() + (1000*60*15)))
	    .signWith(SignatureAlgorithm.HS256, SECRETKEY).compact();
	  LOGGER.info(compact);

	  return compact;
	 }
	 public Boolean validateToken(String token) {
	  LOGGER.info("START");

	  try {
	   Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(token).getBody();
	   LOGGER.info("END");

	   return true;
	  } catch (Exception e) {
	   LOGGER.info("EXCEPTION");
	   return false;
	  }

	 }
	}