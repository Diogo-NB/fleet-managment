package com.diogo.nb.web2.usecase.funcionario;

import com.diogo.nb.web2.model.Funcionario;
import com.diogo.nb.web2.repository.FuncionarioRepository;
import com.diogo.nb.web2.usecase.UseCase;
import com.diogo.nb.web2.viewmodel.FuncionarioFormViewModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CreateFuncionarioUseCase implements UseCase<FuncionarioFormViewModel> {

    private final FuncionarioRepository funcionarioRepository;

    @Override
    public void execute(FuncionarioFormViewModel form) {
        log.info("Creating funcionario: nome={}", form.getNome());
        Funcionario f = new Funcionario();
        f.setNome(form.getNome());
        f.setContato(form.getContato());
        funcionarioRepository.save(f);
    }
}
