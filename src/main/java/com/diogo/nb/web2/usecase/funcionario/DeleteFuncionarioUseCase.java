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
public class DeleteFuncionarioUseCase implements UseCase<DeleteFuncionarioCommand> {

    private final FuncionarioRepository funcionarioRepository;

    @Override
    public void execute(DeleteFuncionarioCommand command) {
        Funcionario f = funcionarioRepository.findById(command.id()).orElseThrow();
        f.delete();
        funcionarioRepository.save(f);
    }
}
