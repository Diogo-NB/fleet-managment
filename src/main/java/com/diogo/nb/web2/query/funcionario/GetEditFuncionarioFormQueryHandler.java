package com.diogo.nb.web2.query.funcionario;

import com.diogo.nb.web2.model.Funcionario;
import com.diogo.nb.web2.query.QueryHandler;
import com.diogo.nb.web2.repository.FuncionarioRepository;
import com.diogo.nb.web2.viewmodel.FuncionarioFormViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetEditFuncionarioFormQueryHandler implements QueryHandler<GetEditFuncionarioFormQuery, FuncionarioFormViewModel> {

    private final FuncionarioRepository funcionarioRepository;

    @Override
    public FuncionarioFormViewModel execute(GetEditFuncionarioFormQuery query) {
        Funcionario f = funcionarioRepository.findById(query.id()).orElseThrow();
        FuncionarioFormViewModel vm = new FuncionarioFormViewModel();
        vm.setId(f.getId());
        vm.setNome(f.getNome());
        vm.setContato(f.getContato());
        return vm;
    }
}
