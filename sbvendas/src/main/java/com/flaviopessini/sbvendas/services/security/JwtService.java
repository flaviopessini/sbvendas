package com.flaviopessini.sbvendas.services.security;

import com.flaviopessini.sbvendas.domain.entities.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class JwtService {

    @Value("${security.jwt.expiration}")
    private String expiration;

    @Value("${security.jwt.key}")
    private String key;

    /**
     * Gera um novo token para o usuário.
     *
     * @param usuario
     * @return token
     */
    public String generateToken(Usuario usuario) {
        final long minutes = Long.valueOf(this.expiration);
        final var expiresIn = LocalDateTime.now().plusMinutes(minutes);
        final var date = Date.from(expiresIn.atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(usuario.getLogin())
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, this.key)
                .compact();
    }

    /**
     * Verifica a validade de token.
     *
     * @param token
     * @return
     */
    public boolean isValidToken(String token) {
        final var claims = getClaims(token);
        final var expiresIn = claims.getExpiration();
        final var date = expiresIn.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return !LocalDateTime.now().isAfter(date);
    }

    /**
     * Obtém o username a partir do token.
     * @param token
     * @return
     * @throws ExpiredJwtException
     */
    public String getUserLogin(String token) throws ExpiredJwtException {
        return getClaims(token).getSubject();
    }

    /**
     * Obter informações do token.
     * @param token
     * @return
     * @throws ExpiredJwtException
     */
    private Claims getClaims(String token) throws ExpiredJwtException {
        return Jwts.parser()
                .setSigningKey(this.key)
                .parseClaimsJws(token)
                .getBody();
    }
}
