package com.diogo.nb.web2.repository;

import com.diogo.nb.web2.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
}
