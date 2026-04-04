package com.diogo.nb.web2.usecase.funcionario;

import com.diogo.nb.web2.model.Funcionario;
import com.diogo.nb.web2.repository.FuncionarioRepository;
import com.diogo.nb.web2.usecase.UseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UpdateFuncionarioUseCase implements UseCase<UpdateFuncionarioCommand> {

    private final FuncionarioRepository funcionarioRepository;

    @Override
    public void execute(UpdateFuncionarioCommand command) {
        Funcionario f = funcionarioRepository.findById(command.id()).orElseThrow();
        var form = command.form();
        f.setNome(form.getNome());
        f.setContato(form.getContato());
        funcionarioRepository.save(f);
    }
}
