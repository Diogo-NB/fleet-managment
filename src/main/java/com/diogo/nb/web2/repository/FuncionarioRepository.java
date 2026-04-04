package com.diogo.nb.web2.repository;

import com.diogo.nb.web2.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;
import java.util.stream.Collectors;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    default Map<Long, Funcionario> getAllMapped() {
        return findAll().stream().collect(Collectors.toMap(Funcionario::getId, f -> f));
    }
}
