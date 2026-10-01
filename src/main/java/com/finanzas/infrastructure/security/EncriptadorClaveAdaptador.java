package com.finanzas.infrastructure.security;

import com.finanzas.application.port.out.EncriptadorClavePuertoSalida;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EncriptadorClaveAdaptador implements EncriptadorClavePuertoSalida {

    private final PasswordEncoder passwordEncoder;

    @Override
    public String encriptar(String clavePlana) {
        return passwordEncoder.encode(clavePlana);
    }

    @Override
    public boolean coincide(String clavePlana, String claveEncriptada) {
        return passwordEncoder.matches(clavePlana, claveEncriptada);
    }
}
