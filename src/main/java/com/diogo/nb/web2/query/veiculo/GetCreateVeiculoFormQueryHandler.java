package com.diogo.nb.web2.query.veiculo;

import com.diogo.nb.web2.model.StatusVeiculo;
import com.diogo.nb.web2.query.QueryHandler;
import com.diogo.nb.web2.viewmodel.VeiculoFormViewModel;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class GetCreateVeiculoFormQueryHandler implements QueryHandler<GetCreateVeiculoFormQuery, VeiculoFormViewModel> {

    @Override
    public VeiculoFormViewModel execute(GetCreateVeiculoFormQuery query) {
        VeiculoFormViewModel vm = new VeiculoFormViewModel();
        vm.setStatus(StatusVeiculo.DISPONIVEL);
        vm.setKm(0.0);
        vm.setStatusOptions(Arrays.asList(StatusVeiculo.values()));
        return vm;
    }
}
