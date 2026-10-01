package com.finanzas.infrastructure.security;

import com.finanzas.application.port.out.GeneradorTokenPuertoSalida;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class ProveedorJwt implements GeneradorTokenPuertoSalida {

    private final SecretKey claveSecreta;
    private final long expiracionMs;

    public ProveedorJwt(
            @Value("${jwt.secreto}") String secreto,
            @Value("${jwt.expiracion-ms}") long expiracionMs
    ) {
        this.claveSecreta = Keys.hmacShaKeyFor(secreto.getBytes(StandardCharsets.UTF_8));
        this.expiracionMs = expiracionMs;
    }

    @Override
    public String generar(Long usuarioId, String correo) {
        Date ahora = new Date();
        return Jwts.builder()
                .subject(correo)
                .claim("usuarioId", usuarioId)
                .issuedAt(ahora)
                .expiration(new Date(ahora.getTime() + expiracionMs))
                .signWith(claveSecreta)
                .compact();
    }

    public UsuarioAutenticado extraerUsuario(String token) {
        Claims reclamos = Jwts.parser()
                .verifyWith(claveSecreta)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        Long usuarioId = reclamos.get("usuarioId", Number.class).longValue();
        return new UsuarioAutenticado(usuarioId, reclamos.getSubject());
    }
}
