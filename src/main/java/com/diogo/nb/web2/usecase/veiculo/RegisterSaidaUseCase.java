package com.diogo.nb.web2.usecase.veiculo;

import com.diogo.nb.web2.model.Veiculo;
import com.diogo.nb.web2.repository.VeiculoRepository;
import com.diogo.nb.web2.usecase.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RegisterSaidaUseCase implements UseCase<RegisterSaidaCommand> {

    private final VeiculoRepository veiculoRepository;

    @Override
    public void execute(RegisterSaidaCommand command) {
        Veiculo v = veiculoRepository.findById(command.veiculoId()).orElseThrow();
        v.registrarSaida(command.funcSaidaId());
        veiculoRepository.save(v);
    }
}
