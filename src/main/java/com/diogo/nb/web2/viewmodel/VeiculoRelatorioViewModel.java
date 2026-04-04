package com.diogo.nb.web2.viewmodel;

import lombok.Data;

import java.util.List;

@Data
public class VeiculoRelatorioViewModel {

    private String geradoEm;
    private List<VeiculoGroup> veiculos;

    @Data
    public static class VeiculoGroup {
        private String modelo;
        private String placa;
        private Integer ano;
        private Double km;
        private List<MovimentacaoRow> movimentacoes;
    }

    @Data
    public static class MovimentacaoRow {
        private String funcionarioSaida;
        private String dateSaida;
        private String funcionarioVolta;
        private String dateVolta;
        private Double kmPercorrido;
        private boolean voltaPendente;
    }
}
