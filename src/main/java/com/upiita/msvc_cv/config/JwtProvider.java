package com.upiita.msvc_cv.config;

import com.upiita.msvc_cv.models.entity.CurriculumVitae;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {

	
	private static Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    private static final long TOKEN_VALIDITY = 24 * 60 * 60 * 1000;

    @Value("${jwt.secret}")
    private String secret;

    private SecretKey getSigningKey() {
        logger.info("Generating signing key from secret.");
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        if (keyBytes.length < 32) {
            throw new IllegalArgumentException("The secret key must be at least 256 bits (32 bytes) long.");
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(CurriculumVitae cv) {
        logger.info(String.format("Generating token for cvId %d", cv.getCvId()));
        Date now = new Date();
        Date exp = new Date(now.getTime() + TOKEN_VALIDITY);
        Map<String, Object> claims = buildClaims(cv,now, exp);
        Map<String, Object> headers = buildHeaders(cv);

        String token =  Jwts.builder()
                .setHeaderParams(headers)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
        logger.info("token: " + token);
        return token;
    }

    private Map<String, Object> buildClaims(CurriculumVitae cv, Date now, Date exp) {
        Map<String, Object> claims = Jwts.claims().setSubject(String.valueOf(cv.getCvId()));
        //dia en el que se creo
        claims.put("creationDate", now);
        //dia de expiracion
        claims.put("expirationDate", exp);
        return claims;
    }

    private Map<String, Object> buildHeaders(CurriculumVitae cv) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");
        headers.put("company", "PT");
        return headers;
    }

    public String getCVIdFromToken(String token) {
        try {
            String subject = Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody().getSubject();
            return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody().getSubject();
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid Token");
        }
    }

    public void validate(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            claims.keySet().forEach(c-> logger.info("key: " + c + " value: " + claims.get(c)));
        }catch (Exception e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

}
