package com.diogo.nb.web2.query.veiculo;

import com.diogo.nb.web2.model.StatusVeiculo;
import com.diogo.nb.web2.model.Veiculo;
import com.diogo.nb.web2.query.QueryHandler;
import com.diogo.nb.web2.repository.MovimentacaoRepository;
import com.diogo.nb.web2.repository.VeiculoRepository;
import com.diogo.nb.web2.viewmodel.VeiculoFormViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetEditVeiculoFormQueryHandler implements QueryHandler<GetEditVeiculoFormQuery, VeiculoFormViewModel> {

    private final VeiculoRepository veiculoRepository;
    private final MovimentacaoRepository movimentacaoRepository;

    @Override
    public VeiculoFormViewModel execute(GetEditVeiculoFormQuery query) {
        Veiculo v = veiculoRepository.findById(query.id()).orElseThrow();
        VeiculoFormViewModel vm = new VeiculoFormViewModel();
        vm.setId(v.getId());
        vm.setModelo(v.getModelo());
        vm.setPlaca(v.getPlaca());
        vm.setAno(v.getAno());
        vm.setKm(v.getKm());
        vm.setStatus(v.getStatus());
        vm.setStatusOptions(Arrays.asList(StatusVeiculo.values()));
        vm.setStatusBloqueado(movimentacaoRepository.findByVeiculoIdAndVoltaIsNull(v.getId()).isPresent());
        return vm;
    }
}
