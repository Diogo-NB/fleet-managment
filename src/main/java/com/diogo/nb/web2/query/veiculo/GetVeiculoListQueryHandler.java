package com.diogo.nb.web2.query.veiculo;

import com.diogo.nb.web2.query.QueryHandler;
import com.diogo.nb.web2.repository.VeiculoRepository;
import com.diogo.nb.web2.viewmodel.VeiculoListViewModel;
import com.diogo.nb.web2.viewmodel.VeiculoListViewModel.VeiculoRow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetVeiculoListQueryHandler implements QueryHandler<GetVeiculoListQuery, VeiculoListViewModel> {

    private final VeiculoRepository veiculoRepository;

    @Override
    public VeiculoListViewModel execute(GetVeiculoListQuery query) {
        List<VeiculoRow> rows = veiculoRepository.findAll().stream()
                .map(v -> {
                    VeiculoRow row = new VeiculoRow();
                    row.setId(v.getId());
                    row.setModelo(v.getModelo());
                    row.setPlaca(v.getPlaca());
                    row.setAno(v.getAno());
                    row.setKm(v.getKm());
                    row.setStatus(v.getStatus());
                    return row;
                })
                .toList();

        VeiculoListViewModel vm = new VeiculoListViewModel();
        vm.setVeiculos(rows);
        return vm;
    }
}
