package com.diogo.nb.web2.viewmodel;

import com.diogo.nb.web2.model.StatusVeiculo;
import lombok.Data;

import java.util.List;

@Data
public class VeiculoFormViewModel {

    private Long id;
    private String modelo;
    private String placa;
    private Integer ano;
    private Double km;
    private StatusVeiculo status;
    private List<StatusVeiculo> statusOptions;
    private boolean statusBloqueado;
}
