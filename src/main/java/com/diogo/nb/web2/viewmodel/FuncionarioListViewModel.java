package com.diogo.nb.web2.viewmodel;

import lombok.Data;

import java.util.List;

@Data
public class FuncionarioListViewModel {

    private List<FuncionarioRow> funcionarios;

    @Data
    public static class FuncionarioRow {
        private Long id;
        private String nome;
        private String contato;
    }
}
