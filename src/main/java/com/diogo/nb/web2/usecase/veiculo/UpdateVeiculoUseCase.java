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
public class UpdateVeiculoUseCase implements UseCase<UpdateVeiculoCommand> {

    private final VeiculoRepository veiculoRepository;

    @Override
    public void execute(UpdateVeiculoCommand command) {
        Veiculo v = veiculoRepository.findById(command.id()).orElseThrow();
        v.setModelo(command.form().getModelo());
        v.setPlaca(command.form().getPlaca());
        v.setAno(command.form().getAno());
        v.setStatus(command.form().getStatus());
        veiculoRepository.save(v);
    }
}
