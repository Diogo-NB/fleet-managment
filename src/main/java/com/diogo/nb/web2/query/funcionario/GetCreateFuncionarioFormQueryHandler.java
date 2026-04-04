package com.diogo.nb.web2.query.funcionario;

import com.diogo.nb.web2.query.QueryHandler;
import com.diogo.nb.web2.viewmodel.FuncionarioFormViewModel;
import org.springframework.stereotype.Service;

@Service
public class GetCreateFuncionarioFormQueryHandler implements QueryHandler<GetCreateFuncionarioFormQuery, FuncionarioFormViewModel> {

    @Override
    public FuncionarioFormViewModel execute(GetCreateFuncionarioFormQuery query) {
        return new FuncionarioFormViewModel();
    }
}
