package com.challenge.wazejob.services;

import com.challenge.wazejob.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

@Service
public class JwtTokenService {

    private final JwtProperties properties;
    private final SecretKey signingKey;

    public JwtTokenService(JwtProperties properties) {
        this.properties = properties;
        String secret = properties.getSecret();
        this.signingKey = StringUtils.hasText(secret)
                ? Keys.hmacShaKeyFor(secret.getBytes())
                : throwMissingSecret();
    }

    private SecretKey throwMissingSecret() {
        throw new IllegalStateException("security.jwt.secret must be configured");
    }

    public String generateToken(String subject, Map<String, Object> claims, Instant issuedAt) {
        Instant expiresAt = issuedAt.plus(properties.getExpirationHours(), ChronoUnit.HOURS);

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuer(properties.getIssuer())
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(expiresAt))
                .signWith(signingKey)
                .compact();
    }

    public String generateToken(String subject, Instant issuedAt) {
        return generateToken(subject, Collections.emptyMap(), issuedAt);
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public long calculateExpirationEpochSeconds(Instant issuedAt) {
        return issuedAt.plus(properties.getExpirationHours(), ChronoUnit.HOURS).getEpochSecond();
    }
}
