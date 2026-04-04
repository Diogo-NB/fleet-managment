package com.diogo.nb.web2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@SQLRestriction("data_exclusao IS NULL")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String contato;

    private LocalDateTime dataExclusao;

    public void delete() {
        this.dataExclusao = LocalDateTime.now();
    }
}
