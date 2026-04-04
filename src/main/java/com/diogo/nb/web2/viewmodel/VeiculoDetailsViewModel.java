package com.diogo.nb.web2.viewmodel;

import com.diogo.nb.web2.model.StatusVeiculo;
import lombok.Data;

import java.util.List;

@Data
public class VeiculoDetailsViewModel {

    private Long id;
    private String modelo;
    private String placa;
    private Integer ano;
    private Double km;
    private StatusVeiculo status;
    private List<MovimentacaoRow> movimentacoes;
    private List<FuncionarioOption> funcionarios;
    private String saidaPendente;

    @Data
    public static class MovimentacaoRow {
        private Long id;
        private String funcionarioSaida;
        private String funcionarioVolta;
        private String dateSaida;
        private String dateVolta;
        private Double kmPercorrido;
        private boolean isVoltaPendente;
    }

    @Data
    public static class FuncionarioOption {
        private Long id;
        private String nome;
    }
}
