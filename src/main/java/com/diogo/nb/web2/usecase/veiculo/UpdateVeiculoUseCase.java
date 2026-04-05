package com.diogo.nb.web2.usecase.veiculo;

import com.diogo.nb.web2.model.StatusVeiculo;
import com.diogo.nb.web2.model.Veiculo;
import com.diogo.nb.web2.repository.MovimentacaoRepository;
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
public class UpdateVeiculoUseCase implements UseCase<UpdateVeiculoCommand> {

    private final VeiculoRepository veiculoRepository;
    private final MovimentacaoRepository movimentacaoRepository;

    @Override
    public void execute(UpdateVeiculoCommand command) {
        log.info("Updating veiculo id={}", command.id());
        Veiculo v = veiculoRepository.findById(command.id()).orElseThrow();
        var form = command.form();
        boolean voltaPendente = movimentacaoRepository.findByVeiculoIdAndVoltaIsNull(v.getId()).isPresent();
        if (form.getStatus() == StatusVeiculo.DISPONIVEL && voltaPendente) {
            throw new IllegalStateException("Não é possível marcar o veículo como disponível enquanto há uma movimentação com volta pendente.");
        }
        v.setModelo(form.getModelo());
        v.setPlaca(form.getPlaca());
        v.setAno(form.getAno());
        v.setStatus(form.getStatus());
        veiculoRepository.save(v);
    }
}
