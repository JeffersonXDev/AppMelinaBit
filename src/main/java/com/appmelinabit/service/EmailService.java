package com.appmelinabit.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    // Agora o método recebe o link pronto, vindo do Controller
    public void enviarEmailRedefinicao(String email, String linkRedefinicao) {
        System.out.println("Enviando e-mail de redefinicao para " + email);
        System.out.println("Link de redefinicao: " + linkRedefinicao);

        // Aqui você futuramente colocaria a lógica real de envio (JavaMailSender)
    }
}