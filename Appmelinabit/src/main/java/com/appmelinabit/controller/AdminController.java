package com.appmelinabit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin") // Todas as URLs neste Controller começam com /admin
public class AdminController {

    // Lida com a URL específica para administradores: /admin/dashboard
    @GetMapping("/dashboard")
    public String viewAdminDashboard() {
        // O SecurityConfig já garantiu que só ADMINs podem acessar /admin/**
        return "admin-dashboard"; // Retorna o arquivo HTML: admin-dashboard.html
    }
}