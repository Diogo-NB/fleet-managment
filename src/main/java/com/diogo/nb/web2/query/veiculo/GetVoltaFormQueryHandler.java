package com.diogo.nb.web2.query.veiculo;

import com.diogo.nb.web2.model.Funcionario;
import com.diogo.nb.web2.model.Movimentacao;
import com.diogo.nb.web2.model.Veiculo;
import com.diogo.nb.web2.query.QueryHandler;
import com.diogo.nb.web2.repository.FuncionarioRepository;
import com.diogo.nb.web2.repository.MovimentacaoRepository;
import com.diogo.nb.web2.repository.VeiculoRepository;
import com.diogo.nb.web2.viewmodel.VeiculoVoltaViewModel;
import com.diogo.nb.web2.viewmodel.VeiculoVoltaViewModel.FuncionarioOption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetVoltaFormQueryHandler implements QueryHandler<GetVoltaFormQuery, VeiculoVoltaViewModel> {

    private final VeiculoRepository veiculoRepository;
    private final MovimentacaoRepository movimentacaoRepository;
    private final FuncionarioRepository funcionarioRepository;

    @Override
    public VeiculoVoltaViewModel execute(GetVoltaFormQuery query) {
        Veiculo v = veiculoRepository.findById(query.veiculoId()).orElseThrow();
        Movimentacao mov = movimentacaoRepository.findByVeiculoIdAndVoltaIsNull(query.veiculoId())
                .orElseThrow(() -> new IllegalStateException("Nenhuma movimentação ativa encontrada."));

        String nomeFuncSaida = funcionarioRepository.findById(mov.getFuncSaidaId())
                .map(Funcionario::getNome)
                .orElse("Desconhecido");

        List<FuncionarioOption> funcionarios = funcionarioRepository.findAll().stream()
                .map(f -> {
                    FuncionarioOption opt = new FuncionarioOption();
                    opt.setId(f.getId());
                    opt.setNome(f.getNome());
                    return opt;
                })
                .toList();

        VeiculoVoltaViewModel vm = new VeiculoVoltaViewModel();
        vm.setVeiculoId(v.getId());
        vm.setModelo(v.getModelo());
        vm.setPlaca(v.getPlaca());
        vm.setKmAtual(v.getKm());
        vm.setSaidaEm(mov.getSaida());
        vm.setNomeFuncSaida(nomeFuncSaida);
        vm.setFuncionarios(funcionarios);
        return vm;
    }
}
