package com.diogo.nb.web2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@SQLRestriction("data_exclusao IS NULL")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false, unique = true)
    private String placa;

    @Column(nullable = false)
    private Integer ano;

    @Column(nullable = false)
    private Double km = 0.0;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(nullable = false, columnDefinition = "status_veiculo")
    private StatusVeiculo status = StatusVeiculo.DISPONIVEL;

    private LocalDateTime dataExclusao;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "veiculo_id", nullable = false)
    private List<Movimentacao> movimentacoes = new ArrayList<>();

    public void registrarSaida(Long funcSaidaId) {
        if (this.status != StatusVeiculo.DISPONIVEL) {
            throw new IllegalStateException("Veículo não está disponível para saída.");
        }
        Movimentacao mov = new Movimentacao();
        mov.setFuncSaidaId(funcSaidaId);
        mov.setSaida(LocalDateTime.now());
        this.movimentacoes.add(mov);
        this.status = StatusVeiculo.INDISPONIVEL;
    }

    public void registrarVolta(Long funcVoltaId, Double kmPercorrido) {
        Movimentacao ativa = this.movimentacoes.stream()
                .filter(m -> m.getVolta() == null)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Nenhuma movimentação ativa encontrada."));
        ativa.registrarVolta(funcVoltaId, kmPercorrido);
        this.km += kmPercorrido;
        this.status = StatusVeiculo.DISPONIVEL;
    }

    public void delete() {
        this.dataExclusao = LocalDateTime.now();
    }
}
