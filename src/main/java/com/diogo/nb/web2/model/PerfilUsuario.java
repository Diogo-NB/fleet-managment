package com.diogo.nb.web2.model;

public enum PerfilUsuario {
    ADMINISTRADOR("ROLE_ADMINISTRADOR"),
    FUNCIONARIO("ROLE_FUNCIONARIO");

    private final String authority;

    PerfilUsuario(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }
}
