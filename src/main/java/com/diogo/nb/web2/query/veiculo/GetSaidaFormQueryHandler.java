package com.diogo.nb.web2.query.veiculo;

import com.diogo.nb.web2.model.StatusVeiculo;
import com.diogo.nb.web2.model.Veiculo;
import com.diogo.nb.web2.query.QueryHandler;
import com.diogo.nb.web2.repository.FuncionarioRepository;
import com.diogo.nb.web2.repository.VeiculoRepository;
import com.diogo.nb.web2.viewmodel.VeiculoSaidaViewModel;
import com.diogo.nb.web2.viewmodel.VeiculoSaidaViewModel.FuncionarioOption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetSaidaFormQueryHandler implements QueryHandler<GetSaidaFormQuery, VeiculoSaidaViewModel> {

    private final VeiculoRepository veiculoRepository;
    private final FuncionarioRepository funcionarioRepository;

    @Override
    public VeiculoSaidaViewModel execute(GetSaidaFormQuery query) {
        Veiculo v = veiculoRepository.findById(query.veiculoId()).orElseThrow();
        if (v.getStatus() != StatusVeiculo.DISPONIVEL) {
            throw new IllegalStateException("Veículo não está disponível para saída.");
        }

        List<FuncionarioOption> funcionarios = funcionarioRepository.findAll().stream()
                .map(f -> {
                    FuncionarioOption opt = new FuncionarioOption();
                    opt.setId(f.getId());
                    opt.setNome(f.getNome());
                    return opt;
                })
                .toList();

        VeiculoSaidaViewModel vm = new VeiculoSaidaViewModel();
        vm.setVeiculoId(v.getId());
        vm.setModelo(v.getModelo());
        vm.setPlaca(v.getPlaca());
        vm.setKm(v.getKm());
        vm.setFuncionarios(funcionarios);
        return vm;
    }
}
