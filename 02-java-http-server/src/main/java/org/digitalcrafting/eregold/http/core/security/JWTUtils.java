package org.digitalcrafting.eregold.http.core.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

import static java.lang.String.format;

@Slf4j
public class JWTUtils {
    /* TODO replace io.jsonwebtoken to get rid of jaxb dependency */

    private static final String JWT_SECRET = "zdtlD3JK56m6wTTgsNFhqzjqP";
    private static final String JWT_ISSUER = "digitalcrafting.org";

    private static final Algorithm ALGORITHM = Algorithm.HMAC512(JWT_SECRET);
    private static final JWTVerifier VERIFIER = JWT.require(ALGORITHM)
            .withIssuer(JWT_ISSUER)
            .build();

    public static String generateAccessToken(String userId) {
        return JWT.create()
                .withSubject(format("%s", userId))
                .withIssuer(JWT_ISSUER)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 1 week
                .sign(ALGORITHM);
    }

    public static String getUserId(String token) {
        return JWT.decode(token).getSubject();
    }

    public static Date getExpirationDate(String token) {
        return JWT.decode(token).getExpiresAt();
    }

    public static boolean validate(String token) {
        try {
            DecodedJWT jwt = VERIFIER.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            log.error(e.getMessage());
        }
        return false;
    }
}
