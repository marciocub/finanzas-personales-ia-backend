package com.finanzas.application.port.out;

public interface EncriptadorClavePuertoSalida {
    String encriptar(String clavePlana);

    boolean coincide(String clavePlana, String claveEncriptada);
}
