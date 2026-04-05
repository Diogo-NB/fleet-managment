package com.diogo.nb.web2.query.veiculo;

import com.diogo.nb.web2.model.Funcionario;
import com.diogo.nb.web2.model.Movimentacao;
import com.diogo.nb.web2.model.StatusVeiculo;
import com.diogo.nb.web2.model.Veiculo;
import com.diogo.nb.web2.query.DateFormatters;
import com.diogo.nb.web2.query.QueryHandler;
import com.diogo.nb.web2.repository.FuncionarioRepository;
import com.diogo.nb.web2.repository.MovimentacaoRepository;
import com.diogo.nb.web2.repository.VeiculoRepository;
import com.diogo.nb.web2.viewmodel.VeiculoDetailsViewModel;
import com.diogo.nb.web2.viewmodel.VeiculoDetailsViewModel.FuncionarioOption;
import com.diogo.nb.web2.viewmodel.VeiculoDetailsViewModel.MovimentacaoRow;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetVeiculoDetailsQueryHandler implements QueryHandler<GetVeiculoDetailsQuery, VeiculoDetailsViewModel> {

    private final VeiculoRepository veiculoRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final MovimentacaoRepository movimentacaoRepository;

    @Override
    public VeiculoDetailsViewModel execute(GetVeiculoDetailsQuery query) {
        Veiculo v = veiculoRepository.findById(query.id()).orElseThrow();
        Map<Long, Funcionario> funcionariosById = funcionarioRepository.getAllMapped();

        VeiculoDetailsViewModel vm = new VeiculoDetailsViewModel();

        vm.setId(v.getId());
        vm.setAno(v.getAno());
        vm.setKm(v.getKm());
        vm.setModelo(v.getModelo());
        vm.setPlaca(v.getPlaca());
        vm.setStatus(v.getStatus());

        List<MovimentacaoRow> movimentacaoRows = movimentacaoRepository.findByVeiculoIdOrderBySaidaDesc(v.getId())
                .stream()
                .map(m -> createMovimentacaoRow(m, funcionariosById))
                .toList();
        vm.setMovimentacoes(movimentacaoRows);

        List<FuncionarioOption> funcionarios = funcionariosById.values().stream()
                .map(f -> {
                    FuncionarioOption opt = new FuncionarioOption();
                    opt.setId(f.getId());
                    opt.setNome(f.getNome());
                    return opt;
                })
                .toList();
        vm.setFuncionarios(funcionarios);

        movimentacaoRepository.findByVeiculoIdAndVoltaIsNull(v.getId()).ifPresentOrElse(
                m -> vm.setSaidaPendente(m.getSaida().format(DateFormatters.DATE_TIME)),
                () -> vm.setPodeRegistrarSaida(v.getStatus() == StatusVeiculo.DISPONIVEL));

        return vm;
    }

    private MovimentacaoRow createMovimentacaoRow(Movimentacao m, Map<Long, Funcionario> funcionariosById) {
        MovimentacaoRow row = new MovimentacaoRow();

        row.setId(m.getId());
        row.setVoltaPendente(m.isVoltaPendente());
        row.setKmPercorrido(m.getKmPercorrido());

        row.setFuncionarioSaida(getNome(funcionariosById, m.getFuncSaidaId()));
        row.setDateSaida(m.getSaida().format(DateFormatters.DATE_TIME));

        if (m.isVoltaPendente()) {
            row.setFuncionarioVolta("--");
            row.setDateVolta("--");
        } else {
            row.setFuncionarioVolta(getNome(funcionariosById, m.getFuncVoltaId()));
            row.setDateVolta(m.getVolta().format(DateFormatters.DATE_TIME));
        }

        return row;
    }

    private String getNome(Map<Long, Funcionario> funcionariosById, Long id) {
        Funcionario f = funcionariosById.get(id);
        return f != null ? f.getNome() : "--";
    }
}
