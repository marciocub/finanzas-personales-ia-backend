CREATE DATABASE IF NOT EXISTS finanzas_personales
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE finanzas_personales;

CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT NOT NULL AUTO_INCREMENT,
    correo VARCHAR(180) NOT NULL,
    clave_encriptada VARCHAR(255) NOT NULL,
    moneda_principal VARCHAR(10) NOT NULL,
    fecha_creacion DATETIME NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_usuarios_correo (correo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS transacciones (
    id BIGINT NOT NULL AUTO_INCREMENT,
    usuario_id BIGINT NOT NULL,
    monto DECIMAL(19, 4) NOT NULL,
    moneda VARCHAR(10) NOT NULL,
    tipo VARCHAR(20) NOT NULL,
    categoria VARCHAR(40) NOT NULL,
    fecha DATETIME NOT NULL,
    descripcion VARCHAR(500) NULL,
    PRIMARY KEY (id),
    KEY idx_transacciones_usuario (usuario_id),
    KEY idx_transacciones_fecha (fecha),
    KEY idx_transacciones_categoria (categoria),
    CONSTRAINT fk_transacciones_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuarios (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS presupuestos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    usuario_id BIGINT NOT NULL,
    categoria VARCHAR(40) NOT NULL,
    monto_limite DECIMAL(19, 4) NOT NULL,
    moneda VARCHAR(10) NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    PRIMARY KEY (id),
    KEY idx_presupuestos_usuario (usuario_id),
    CONSTRAINT fk_presupuestos_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuarios (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
