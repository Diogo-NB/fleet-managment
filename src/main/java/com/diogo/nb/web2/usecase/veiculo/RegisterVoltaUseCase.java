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
public class RegisterVoltaUseCase implements UseCase<RegisterVoltaCommand> {

    private final VeiculoRepository veiculoRepository;

    @Override
    public void execute(RegisterVoltaCommand command) {
        log.info("Registering volta: veiculoId={}, funcVoltaId={}, kmPercorrido={}", command.veiculoId(), command.funcVoltaId(), command.kmPercorrido());
        Veiculo v = veiculoRepository.findById(command.veiculoId()).orElseThrow();
        v.registrarVolta(command.funcVoltaId(), command.kmPercorrido());
        veiculoRepository.save(v);
    }
}
