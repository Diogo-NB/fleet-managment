package com.diogo.nb.web2.controller;

import com.diogo.nb.web2.query.veiculo.GetCreateVeiculoFormQuery;
import com.diogo.nb.web2.query.veiculo.GetCreateVeiculoFormQueryHandler;
import com.diogo.nb.web2.query.veiculo.GetEditVeiculoFormQuery;
import com.diogo.nb.web2.query.veiculo.GetEditVeiculoFormQueryHandler;
import com.diogo.nb.web2.query.veiculo.GetVeiculoDetailsQuery;
import com.diogo.nb.web2.query.veiculo.GetVeiculoDetailsQueryHandler;
import com.diogo.nb.web2.query.veiculo.GetVeiculoListQuery;
import com.diogo.nb.web2.query.veiculo.GetVeiculoListQueryHandler;
import com.diogo.nb.web2.query.veiculo.GetVeiculoRelatorioQuery;
import com.diogo.nb.web2.query.veiculo.GetVeiculoRelatorioQueryHandler;
import com.diogo.nb.web2.usecase.veiculo.CreateVeiculoUseCase;
import com.diogo.nb.web2.usecase.veiculo.DeleteVeiculoCommand;
import com.diogo.nb.web2.usecase.veiculo.DeleteVeiculoUseCase;
import com.diogo.nb.web2.usecase.veiculo.RegisterSaidaCommand;
import com.diogo.nb.web2.usecase.veiculo.RegisterSaidaUseCase;
import com.diogo.nb.web2.usecase.veiculo.RegisterVoltaCommand;
import com.diogo.nb.web2.usecase.veiculo.RegisterVoltaUseCase;
import com.diogo.nb.web2.usecase.veiculo.UpdateVeiculoCommand;
import com.diogo.nb.web2.usecase.veiculo.UpdateVeiculoUseCase;
import com.diogo.nb.web2.viewmodel.VeiculoFormViewModel;
import com.diogo.nb.web2.viewmodel.VeiculoRelatorioViewModel;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.TemplateSpec;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

@Controller
@RequestMapping("/veiculos")
@RequiredArgsConstructor
public class VeiculoController {

    private final GetVeiculoListQueryHandler getVeiculoListQueryHandler;
    private final GetCreateVeiculoFormQueryHandler getCreateVeiculoFormQueryHandler;
    private final GetEditVeiculoFormQueryHandler getEditVeiculoFormQueryHandler;
    private final GetVeiculoDetailsQueryHandler getVeiculoDetailsQueryHandler;
    private final GetVeiculoRelatorioQueryHandler getVeiculoRelatorioQueryHandler;
    private final SpringTemplateEngine templateEngine;
    private final CreateVeiculoUseCase createVeiculoUseCase;
    private final UpdateVeiculoUseCase updateVeiculoUseCase;
    private final DeleteVeiculoUseCase deleteVeiculoUseCase;
    private final RegisterSaidaUseCase registerSaidaUseCase;
    private final RegisterVoltaUseCase registerVoltaUseCase;

    @GetMapping
    public String list(Model model) {
        var vm = getVeiculoListQueryHandler.execute(new GetVeiculoListQuery());
        model.addAttribute("vm", vm);
        return "veiculo/list";
    }

    @GetMapping("/novo")
    public String createForm(Model model) {
        var vm = getCreateVeiculoFormQueryHandler.execute(new GetCreateVeiculoFormQuery());
        model.addAttribute("vm", vm);
        return "veiculo/form";
    }

    @PostMapping("/novo")
    public String create(@ModelAttribute("vm") VeiculoFormViewModel form) {
        createVeiculoUseCase.execute(form);
        return "redirect:/veiculos";
    }

    @GetMapping("/{id}/editar")
    public String editForm(@PathVariable Long id, Model model) {
        var vm = getEditVeiculoFormQueryHandler.execute(new GetEditVeiculoFormQuery(id));
        model.addAttribute("vm", vm);
        return "veiculo/form";
    }

    @PostMapping("/{id}/editar")
    public String update(@PathVariable Long id, @ModelAttribute("vm") VeiculoFormViewModel form) {
        updateVeiculoUseCase.execute(new UpdateVeiculoCommand(id, form));
        return "redirect:/veiculos/" + id + "/detalhes";
    }

    @PostMapping("/{id}/excluir")
    public String delete(@PathVariable Long id) {
        deleteVeiculoUseCase.execute(new DeleteVeiculoCommand(id));
        return "redirect:/veiculos";
    }

    @GetMapping("/{id}/detalhes")
    public String details(@PathVariable Long id, Model model) {
        var vm = getVeiculoDetailsQueryHandler.execute(new GetVeiculoDetailsQuery(id));
        model.addAttribute("vm", vm);
        return "veiculo/detalhes";
    }

    @GetMapping("/{id}/saida-form")
    public String saidaForm(@PathVariable Long id, Model model) {
        var vm = getVeiculoDetailsQueryHandler.execute(new GetVeiculoDetailsQuery(id));
        model.addAttribute("vm", vm);
        return "veiculo/fragments :: saida-form";
    }

    @GetMapping("/{id}/volta-form")
    public String voltaForm(@PathVariable Long id, Model model) {
        var vm = getVeiculoDetailsQueryHandler.execute(new GetVeiculoDetailsQuery(id));
        model.addAttribute("vm", vm);
        return "veiculo/fragments :: volta-form";
    }

    @PostMapping("/{id}/saida")
    public String registrarSaida(@PathVariable Long id,
            @RequestParam Long funcSaidaId,
            Model model) {
        registerSaidaUseCase.execute(new RegisterSaidaCommand(id, funcSaidaId));
        var vm = getVeiculoDetailsQueryHandler.execute(new GetVeiculoDetailsQuery(id));
        model.addAttribute("vm", vm);
        return "veiculo/fragments :: details-content";
    }

    @PostMapping("/{id}/volta")
    public String registrarVolta(@PathVariable Long id,
            @RequestParam Long funcVoltaId,
            @RequestParam Double kmPercorrido,
            Model model) {
        registerVoltaUseCase.execute(new RegisterVoltaCommand(id, funcVoltaId, kmPercorrido));
        var vm = getVeiculoDetailsQueryHandler.execute(new GetVeiculoDetailsQuery(id));
        model.addAttribute("vm", vm);
        return "veiculo/fragments :: details-content";
    }

    @GetMapping("/relatorio")
    public void relatorio(HttpServletResponse response) throws Exception {
        VeiculoRelatorioViewModel vm = getVeiculoRelatorioQueryHandler.execute(new GetVeiculoRelatorioQuery());

        Context ctx = new Context();
        ctx.setVariable("vm", vm);
        String html = templateEngine.process(new TemplateSpec("veiculo/relatorio", TemplateMode.XML), ctx);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        new PdfRendererBuilder()
                .withHtmlContent(html, null)
                .toStream(baos)
                .run();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"relatorio-movimentacoes.pdf\"");
        response.setContentLength(baos.size());

        try (OutputStream os = response.getOutputStream()) {
            baos.writeTo(os);
        }
    }
}
