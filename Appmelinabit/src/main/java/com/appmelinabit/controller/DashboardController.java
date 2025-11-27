package com.appmelinabit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    // Lida com a URL padrão para usuários comuns (e também admins)
    @GetMapping("/dashboard")
    public String viewDashboard() {
        return "dashboard"; // Retorna o arquivo HTML: dashboard.html
    }
}