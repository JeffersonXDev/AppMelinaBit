package com.appmelinabit.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void enviarEmailRedefinicao(String email, String token) {
        // Implementar a lógica de envio de e-mail aqui
        // Exemplo:
        // Crie um link de redefinição: http://localhost:8080/redefinir-senha?token=<o-token-gerado>
        // Use uma biblioteca como JavaMailSender para enviar o e-mail
        System.out.println("Enviando e-mail de redefinicao para " + email);
        System.out.println("Link de redefinicao: http://localhost:8080/redefinir-senha?token=" + token);
    }
}