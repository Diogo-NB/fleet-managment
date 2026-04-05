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
public class DeleteVeiculoUseCase implements UseCase<DeleteVeiculoCommand> {

    private final VeiculoRepository veiculoRepository;

    @Override
    public void execute(DeleteVeiculoCommand command) {
        log.info("Deleting veiculo id={}", command.id());
        Veiculo v = veiculoRepository.findById(command.id()).orElseThrow();
        v.delete();
        veiculoRepository.save(v);
    }
}
