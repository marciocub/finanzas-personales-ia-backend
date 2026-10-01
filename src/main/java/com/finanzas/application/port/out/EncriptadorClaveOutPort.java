package com.finanzas.application.port.out;

public interface EncriptadorClaveOutPort {
    String encriptar(String clavePlana);

    boolean coincide(String clavePlana, String claveEncriptada);
}
