package com.appmelinabit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard") // Mapeamento exato da rota
    public String viewUserDashboard() {
        return "user-dashboard"; 
    }
}