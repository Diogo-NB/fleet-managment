package com.diogo.nb.web2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.sql.Connection;

@Controller
@RequiredArgsConstructor
public class AppController {

    private final DataSource dataSource;

    @GetMapping("/health")
    @ResponseBody
    public String health() {
        try (Connection connection = dataSource.getConnection()) {
            return connection.isValid(2) ? "ok" : "db unavailable";
        } catch (Exception e) {
            return "db unavailable";
        }
    }

    @GetMapping("/login")
    public String login(
            @RequestParam(required = false) String error,
            @RequestParam(required = false) String logout,
            Model model) {
        if (error != null) model.addAttribute("errorMessage", "Usuário ou senha incorretos.");
        if (logout != null) model.addAttribute("logoutMessage", "Sessão encerrada com sucesso.");
        return "auth/login";
    }
}
