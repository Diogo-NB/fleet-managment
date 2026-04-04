package com.diogo.nb.web2.model;

public enum StatusVeiculo {
    DISPONIVEL("Disponível"),
    INDISPONIVEL("Indisponível"),
    CONSERTO("Conserto");

    private final String descricao;

    StatusVeiculo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
