package com.diogo.nb.web2.usecase.funcionario;

import com.diogo.nb.web2.viewmodel.FuncionarioFormViewModel;

public record UpdateFuncionarioCommand(Long id, FuncionarioFormViewModel form) {}
