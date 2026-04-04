CREATE TYPE perfil_usuario AS ENUM ('ADMINISTRADOR', 'FUNCIONARIO');

CREATE TABLE users (
    id       BIGSERIAL      PRIMARY KEY,
    username VARCHAR(255)   NOT NULL UNIQUE,
    password VARCHAR(255)   NOT NULL,
    perfil   perfil_usuario NOT NULL DEFAULT 'FUNCIONARIO'
);
