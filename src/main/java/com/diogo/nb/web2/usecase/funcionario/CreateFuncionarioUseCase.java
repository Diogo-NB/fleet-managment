package com.diogo.nb.web2.usecase.funcionario;

import com.diogo.nb.web2.model.Funcionario;
import com.diogo.nb.web2.repository.FuncionarioRepository;
import com.diogo.nb.web2.usecase.UseCase;
import com.diogo.nb.web2.viewmodel.FuncionarioFormViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CreateFuncionarioUseCase implements UseCase<FuncionarioFormViewModel> {

    private final FuncionarioRepository funcionarioRepository;

    @Override
    public void execute(FuncionarioFormViewModel form) {
        Funcionario f = new Funcionario();
        f.setNome(form.getNome());
        f.setContato(form.getContato());
        funcionarioRepository.save(f);
    }
}
