package com.diogo.nb.web2.query.veiculo;

import com.diogo.nb.web2.model.Movimentacao;
import com.diogo.nb.web2.model.Veiculo;
import com.diogo.nb.web2.query.QueryHandler;
import com.diogo.nb.web2.repository.FuncionarioRepository;
import com.diogo.nb.web2.repository.MovimentacaoRepository;
import com.diogo.nb.web2.repository.VeiculoRepository;
import com.diogo.nb.web2.viewmodel.VeiculoDetailsViewModel;
import com.diogo.nb.web2.viewmodel.VeiculoDetailsViewModel.FuncionarioOption;
import com.diogo.nb.web2.viewmodel.VeiculoDetailsViewModel.MovimentacaoRow;

import lombok.RequiredArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetVeiculoDetailsQueryHandler implements QueryHandler<GetVeiculoDetailsQuery, VeiculoDetailsViewModel> {

    private final VeiculoRepository veiculoRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final MovimentacaoRepository movimentacaoRepository;

    private final DateTimeFormatter dateFormater = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Override
    public VeiculoDetailsViewModel execute(GetVeiculoDetailsQuery query) {
        Veiculo v = veiculoRepository.findById(query.id()).orElseThrow();
        VeiculoDetailsViewModel vm = new VeiculoDetailsViewModel();

        vm.setId(v.getId());
        vm.setAno(v.getAno());
        vm.setKm(v.getKm());
        vm.setModelo(v.getModelo());
        vm.setPlaca(v.getPlaca());
        vm.setStatus(v.getStatus());

        List<MovimentacaoRow> movimentacaoRows = movimentacaoRepository.findByVeiculoIdOrderBySaidaDesc(v.getId())
                .stream()
                .map(this::createMovimentacaoRow)
                .toList();
        vm.setMovimentacoes(movimentacaoRows);

        List<FuncionarioOption> funcionarios = funcionarioRepository.findAll().stream()
                .map(f -> {
                    FuncionarioOption opt = new FuncionarioOption();
                    opt.setId(f.getId());
                    opt.setNome(f.getNome());
                    return opt;
                })
                .toList();
        vm.setFuncionarios(funcionarios);

        movimentacaoRepository.findByVeiculoIdAndVoltaIsNull(v.getId())
                .ifPresent(m -> vm.setSaidaPendente(m.getSaida().format(dateFormater)));

        return vm;
    }

    private MovimentacaoRow createMovimentacaoRow(Movimentacao m) {
        MovimentacaoRow row = new MovimentacaoRow();

        row.setId(m.getId());
        row.setVoltaPendente(m.isVoltaPendente());
        row.setKmPercorrido(m.getKmPercorrido());

        String funcionarioSaida = funcionarioRepository.findById(
                m.getFuncSaidaId()).map(f -> f.getNome()).orElse("--");
        row.setFuncionarioSaida(funcionarioSaida);
        row.setDateSaida(m.getSaida().format(dateFormater));

        if (m.isVoltaPendente()) {
            row.setFuncionarioVolta("--");
            row.setDateVolta("--");
        } else {
            String funcionarioVolta = funcionarioRepository.findById(
                    m.getFuncVoltaId()).map(f -> f.getNome()).orElse("--");
            row.setFuncionarioVolta(funcionarioVolta);
            row.setDateVolta(m.getVolta().format(dateFormater));
        }

        return row;
    }
}
