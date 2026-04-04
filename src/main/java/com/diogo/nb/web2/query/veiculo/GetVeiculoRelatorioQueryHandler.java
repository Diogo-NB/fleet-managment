package com.diogo.nb.web2.query.veiculo;

import com.diogo.nb.web2.model.Funcionario;
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
import java.util.Map;

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
        Map<Long, Funcionario> funcionariosById = funcionarioRepository.getAllMapped();
        List<VeiculoGroup> groups = veiculoRepository.findAll().stream()
                .map(v -> toGroup(v, funcionariosById))
                .toList();

        VeiculoRelatorioViewModel vm = new VeiculoRelatorioViewModel();
        vm.setGeradoEm(LocalDateTime.now().format(geradoEmFormatter));
        vm.setVeiculos(groups);
        return vm;
    }

    private VeiculoGroup toGroup(Veiculo v, Map<Long, Funcionario> funcionariosById) {
        List<MovimentacaoRow> rows = v.getMovimentacoes().stream()
                .sorted(Comparator.comparing(Movimentacao::getSaida).reversed())
                .map(m -> toRow(m, funcionariosById))
                .toList();

        VeiculoGroup group = new VeiculoGroup();
        group.setModelo(v.getModelo());
        group.setPlaca(v.getPlaca());
        group.setAno(v.getAno());
        group.setKm(v.getKm());
        group.setMovimentacoes(rows);
        return group;
    }

    private MovimentacaoRow toRow(Movimentacao m, Map<Long, Funcionario> funcionariosById) {
        MovimentacaoRow row = new MovimentacaoRow();
        row.setVoltaPendente(m.isVoltaPendente());
        row.setKmPercorrido(m.getKmPercorrido());
        row.setDateSaida(m.getSaida().format(dateFormatter));
        row.setFuncionarioSaida(getNome(funcionariosById, m.getFuncSaidaId()));

        if (m.isVoltaPendente()) {
            row.setFuncionarioVolta("--");
            row.setDateVolta("--");
        } else {
            row.setFuncionarioVolta(getNome(funcionariosById, m.getFuncVoltaId()));
            row.setDateVolta(m.getVolta().format(dateFormatter));
        }

        return row;
    }

    private String getNome(Map<Long, Funcionario> funcionariosById, Long id) {
        Funcionario f = funcionariosById.get(id);
        return f != null ? f.getNome() : "--";
    }
}
