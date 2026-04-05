package com.diogo.nb.web2.usecase.funcionario;

import com.diogo.nb.web2.model.Funcionario;
import com.diogo.nb.web2.repository.FuncionarioRepository;
import com.diogo.nb.web2.usecase.UseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DeleteFuncionarioUseCase implements UseCase<DeleteFuncionarioCommand> {

    private final FuncionarioRepository funcionarioRepository;

    @Override
    public void execute(DeleteFuncionarioCommand command) {
        log.info("Deleting funcionario id={}", command.id());
        Funcionario f = funcionarioRepository.findById(command.id()).orElseThrow();
        f.delete();
        funcionarioRepository.save(f);
    }
}
