package com.appmelinabit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    // Mapeia a requisição para a URL raiz ("/")
    @GetMapping("/")
    @ResponseBody // Indica que o retorno é o conteúdo, e não o nome de um template (como Thymeleaf)
    public String home() {
        return "<h1>A aplicação MelinaBIT iniciou com sucesso! O erro 404 foi corrigido.</h1>";
    }
}