package com.msevaluacionjava.security.jwt;

import com.msevaluacionjava.security.entity.LoginUserPrincipal;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class JwtProvider {

    @Value("${jwt.expiration.minutes}")
    private int expirationMinutes;

    public String generateToken(Authentication authentication) {
        LoginUserPrincipal usuarioPrincipal = (LoginUserPrincipal) authentication.getPrincipal();
        return Jwts.builder().setSubject(usuarioPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expirationMinutes * 60000L))
                .signWith(SignatureAlgorithm.HS512, "secret")
                .compact();
    }

    public String getNombreUsuarioFromToken(String token) {
        return Jwts.parser().setSigningKey("secret").parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey("secret").parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            log.error("malformed token");
        } catch (UnsupportedJwtException e) {
            log.error("token not supported");
        } catch (ExpiredJwtException e) {
            log.error("expired token");
        } catch (IllegalArgumentException e) {
            log.error("empty token");
        } catch (SignatureException e) {
            log.error("fail in signing");
        }
        return false;
    }
}