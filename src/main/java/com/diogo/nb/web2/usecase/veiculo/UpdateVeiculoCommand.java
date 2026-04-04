package com.diogo.nb.web2.usecase.veiculo;

import com.diogo.nb.web2.viewmodel.VeiculoFormViewModel;

public record UpdateVeiculoCommand(Long id, VeiculoFormViewModel form) {}
