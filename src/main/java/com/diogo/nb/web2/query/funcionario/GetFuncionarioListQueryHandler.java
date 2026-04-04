package com.diogo.nb.web2.query.funcionario;

import com.diogo.nb.web2.query.QueryHandler;
import com.diogo.nb.web2.repository.FuncionarioRepository;
import com.diogo.nb.web2.viewmodel.FuncionarioListViewModel;
import com.diogo.nb.web2.viewmodel.FuncionarioListViewModel.FuncionarioRow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetFuncionarioListQueryHandler implements QueryHandler<GetFuncionarioListQuery, FuncionarioListViewModel> {

    private final FuncionarioRepository funcionarioRepository;

    @Override
    public FuncionarioListViewModel execute(GetFuncionarioListQuery query) {
        List<FuncionarioRow> rows = funcionarioRepository.findAll().stream()
                .map(f -> {
                    FuncionarioRow row = new FuncionarioRow();
                    row.setId(f.getId());
                    row.setNome(f.getNome());
                    row.setContato(f.getContato());
                    return row;
                })
                .toList();

        FuncionarioListViewModel vm = new FuncionarioListViewModel();
        vm.setFuncionarios(rows);
        return vm;
    }
}
