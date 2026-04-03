CREATE TYPE status_veiculo AS ENUM ('DISPONIVEL', 'INDISPONIVEL', 'CONSERTO');

CREATE TABLE veiculo (
    id            BIGSERIAL PRIMARY KEY,
    modelo        VARCHAR(255)   NOT NULL,
    placa         VARCHAR(20)    NOT NULL UNIQUE,
    ano           INTEGER        NOT NULL,
    km            DOUBLE PRECISION NOT NULL DEFAULT 0,
    status        status_veiculo NOT NULL DEFAULT 'DISPONIVEL',
    data_exclusao TIMESTAMP
);

CREATE TABLE funcionario (
    id            BIGSERIAL PRIMARY KEY,
    nome          VARCHAR(255) NOT NULL,
    contato       VARCHAR(255) NOT NULL,
    data_exclusao TIMESTAMP
);

CREATE TABLE movimentacao (
    id            BIGSERIAL PRIMARY KEY,
    veiculo_id    BIGINT NOT NULL REFERENCES veiculo(id),
    func_saida_id BIGINT NOT NULL REFERENCES funcionario(id),
    func_volta_id BIGINT REFERENCES funcionario(id),
    saida         TIMESTAMP        NOT NULL DEFAULT NOW(),
    volta         TIMESTAMP,
    km_percorrido DOUBLE PRECISION
);
