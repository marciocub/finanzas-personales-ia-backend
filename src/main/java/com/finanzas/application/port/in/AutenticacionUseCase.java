package com.finanzas.application.port.in;

import com.finanzas.application.dto.ComandoInicioSesion;
import com.finanzas.application.dto.ComandoRegistro;
import com.finanzas.application.dto.ResultadoAutenticacion;

public interface AutenticacionUseCase {
    ResultadoAutenticacion registrar(ComandoRegistro comando);

    ResultadoAutenticacion iniciarSesion(ComandoInicioSesion comando);
}
