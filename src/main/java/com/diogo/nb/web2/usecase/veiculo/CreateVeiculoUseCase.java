package com.diogo.nb.web2.usecase.veiculo;

import com.diogo.nb.web2.model.Veiculo;
import com.diogo.nb.web2.repository.VeiculoRepository;
import com.diogo.nb.web2.usecase.UseCase;
import com.diogo.nb.web2.viewmodel.VeiculoFormViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateVeiculoUseCase implements UseCase<VeiculoFormViewModel> {

    private final VeiculoRepository veiculoRepository;

    @Override
    public void execute(VeiculoFormViewModel form) {
        Veiculo v = new Veiculo();
        v.setModelo(form.getModelo());
        v.setPlaca(form.getPlaca());
        v.setAno(form.getAno());
        v.setKm(form.getKm());
        v.setStatus(form.getStatus());
        veiculoRepository.save(v);
    }
}
