package com.diogo.nb.web2.query.veiculo;

import com.diogo.nb.web2.model.Movimentacao;
import com.diogo.nb.web2.model.Veiculo;
import com.diogo.nb.web2.query.QueryHandler;
import com.diogo.nb.web2.repository.FuncionarioRepository;
import com.diogo.nb.web2.repository.VeiculoRepository;
import com.diogo.nb.web2.viewmodel.VeiculoRelatorioViewModel;
import com.diogo.nb.web2.viewmodel.VeiculoRelatorioViewModel.MovimentacaoRow;
import com.diogo.nb.web2.viewmodel.VeiculoRelatorioViewModel.VeiculoGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetVeiculoRelatorioQueryHandler implements QueryHandler<GetVeiculoRelatorioQuery, VeiculoRelatorioViewModel> {

    private final VeiculoRepository veiculoRepository;
    private final FuncionarioRepository funcionarioRepository;

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private final DateTimeFormatter geradoEmFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");

    @Override
    public VeiculoRelatorioViewModel execute(GetVeiculoRelatorioQuery query) {
        List<VeiculoGroup> groups = veiculoRepository.findAll().stream()
                .map(this::toGroup)
                .toList();

        VeiculoRelatorioViewModel vm = new VeiculoRelatorioViewModel();
        vm.setGeradoEm(LocalDateTime.now().format(geradoEmFormatter));
        vm.setVeiculos(groups);
        return vm;
    }

    private VeiculoGroup toGroup(Veiculo v) {
        List<MovimentacaoRow> rows = v.getMovimentacoes().stream()
                .sorted(Comparator.comparing(Movimentacao::getSaida).reversed())
                .map(this::toRow)
                .toList();

        VeiculoGroup group = new VeiculoGroup();
        group.setModelo(v.getModelo());
        group.setPlaca(v.getPlaca());
        group.setAno(v.getAno());
        group.setKm(v.getKm());
        group.setMovimentacoes(rows);
        return group;
    }

    private MovimentacaoRow toRow(Movimentacao m) {
        MovimentacaoRow row = new MovimentacaoRow();
        row.setVoltaPendente(m.isVoltaPendente());
        row.setKmPercorrido(m.getKmPercorrido());
        row.setDateSaida(m.getSaida().format(dateFormatter));
        row.setFuncionarioSaida(
                funcionarioRepository.findById(m.getFuncSaidaId()).map(f -> f.getNome()).orElse("--"));

        if (m.isVoltaPendente()) {
            row.setFuncionarioVolta("--");
            row.setDateVolta("--");
        } else {
            row.setFuncionarioVolta(
                    funcionarioRepository.findById(m.getFuncVoltaId()).map(f -> f.getNome()).orElse("--"));
            row.setDateVolta(m.getVolta().format(dateFormatter));
        }

        return row;
    }
}
