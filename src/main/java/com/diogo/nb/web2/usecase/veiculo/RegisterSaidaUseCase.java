package com.diogo.nb.web2.usecase.veiculo;

import com.diogo.nb.web2.model.Veiculo;
import com.diogo.nb.web2.repository.VeiculoRepository;
import com.diogo.nb.web2.usecase.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RegisterSaidaUseCase implements UseCase<RegisterSaidaCommand> {

    private final VeiculoRepository veiculoRepository;

    @Override
    public void execute(RegisterSaidaCommand command) {
        log.info("Registering saida: veiculoId={}, funcSaidaId={}", command.veiculoId(), command.funcSaidaId());
        Veiculo v = veiculoRepository.findById(command.veiculoId()).orElseThrow();
        v.registrarSaida(command.funcSaidaId());
        veiculoRepository.save(v);
    }
}
