package com.diogo.nb.web2.viewmodel;

import lombok.Data;

import java.util.List;

@Data
public class VeiculoSaidaViewModel {

    private Long veiculoId;
    private String modelo;
    private String placa;
    private Double km;
    private Long funcSaidaId;
    private List<FuncionarioOption> funcionarios;

    @Data
    public static class FuncionarioOption {
        private Long id;
        private String nome;
    }
}
