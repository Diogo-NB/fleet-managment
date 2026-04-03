package com.diogo.nb.web2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "veiculo_id", insertable = false, updatable = false)
    private Long veiculoId;

    @Column(name = "func_saida_id", nullable = false)
    private Long funcSaidaId;

    @Column(name = "func_volta_id")
    private Long funcVoltaId;

    @Column(nullable = false)
    private LocalDateTime saida;

    private LocalDateTime volta;

    private Double kmPercorrido;

    public void registrarVolta(Long funcVoltaId, Double kmPercorrido) {
        this.funcVoltaId = funcVoltaId;
        this.volta = LocalDateTime.now();
        this.kmPercorrido = kmPercorrido;
    }
}
