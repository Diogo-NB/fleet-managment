package com.diogo.nb.web2.controller;

import com.diogo.nb.web2.query.funcionario.GetCreateFuncionarioFormQuery;
import com.diogo.nb.web2.query.funcionario.GetCreateFuncionarioFormQueryHandler;
import com.diogo.nb.web2.query.funcionario.GetEditFuncionarioFormQuery;
import com.diogo.nb.web2.query.funcionario.GetEditFuncionarioFormQueryHandler;
import com.diogo.nb.web2.query.funcionario.GetFuncionarioListQuery;
import com.diogo.nb.web2.query.funcionario.GetFuncionarioListQueryHandler;
import com.diogo.nb.web2.usecase.funcionario.CreateFuncionarioUseCase;
import com.diogo.nb.web2.usecase.funcionario.DeleteFuncionarioCommand;
import com.diogo.nb.web2.usecase.funcionario.DeleteFuncionarioUseCase;
import com.diogo.nb.web2.usecase.funcionario.UpdateFuncionarioCommand;
import com.diogo.nb.web2.usecase.funcionario.UpdateFuncionarioUseCase;
import com.diogo.nb.web2.viewmodel.FuncionarioFormViewModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController {

    private final GetFuncionarioListQueryHandler getFuncionarioListQueryHandler;
    private final GetCreateFuncionarioFormQueryHandler getCreateFuncionarioFormQueryHandler;
    private final GetEditFuncionarioFormQueryHandler getEditFuncionarioFormQueryHandler;
    private final CreateFuncionarioUseCase createFuncionarioUseCase;
    private final UpdateFuncionarioUseCase updateFuncionarioUseCase;
    private final DeleteFuncionarioUseCase deleteFuncionarioUseCase;

    @GetMapping
    public String list(Model model) {
        var vm = getFuncionarioListQueryHandler.execute(new GetFuncionarioListQuery());
        model.addAttribute("vm", vm);
        return "funcionario/list";
    }

    @GetMapping("/novo")
    public String createForm(Model model) {
        var vm = getCreateFuncionarioFormQueryHandler.execute(new GetCreateFuncionarioFormQuery());
        model.addAttribute("vm", vm);
        return "funcionario/form";
    }

    @PostMapping("/novo")
    public String create(@ModelAttribute("vm") FuncionarioFormViewModel form) {
        createFuncionarioUseCase.execute(form);
        return "redirect:/funcionarios";
    }

    @GetMapping("/{id}/editar")
    public String editForm(@PathVariable Long id, Model model) {
        var vm = getEditFuncionarioFormQueryHandler.execute(new GetEditFuncionarioFormQuery(id));
        model.addAttribute("vm", vm);
        return "funcionario/form";
    }

    @PostMapping("/{id}/editar")
    public String update(@PathVariable Long id, @ModelAttribute("vm") FuncionarioFormViewModel form) {
        updateFuncionarioUseCase.execute(new UpdateFuncionarioCommand(id, form));
        return "redirect:/funcionarios";
    }

    @PostMapping("/{id}/excluir")
    public String delete(@PathVariable Long id) {
        deleteFuncionarioUseCase.execute(new DeleteFuncionarioCommand(id));
        return "redirect:/funcionarios";
    }
}
