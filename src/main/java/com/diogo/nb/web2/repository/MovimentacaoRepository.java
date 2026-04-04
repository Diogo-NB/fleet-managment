package com.diogo.nb.web2.repository;

import com.diogo.nb.web2.model.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    Optional<Movimentacao> findByVeiculoIdAndVoltaIsNull(Long veiculoId);
    List<Movimentacao> findByVeiculoIdOrderBySaidaDesc(Long veiculoId);
}
