package com.diogo.nb.web2.viewmodel;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class VeiculoVoltaViewModel {

    private Long veiculoId;
    private String modelo;
    private String placa;
    private Double kmAtual;
    private LocalDateTime saidaEm;
    private String nomeFuncSaida;
    private Long funcVoltaId;
    private Double kmPercorrido;
    private List<FuncionarioOption> funcionarios;

    @Data
    public static class FuncionarioOption {
        private Long id;
        private String nome;
    }
}
