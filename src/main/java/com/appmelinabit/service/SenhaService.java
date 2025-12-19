package com.appmelinabit.service;

import com.appmelinabit.model.PasswordResetToken;
import com.appmelinabit.model.Usuario;
import com.appmelinabit.repository.PasswordResetTokenRepository;
import com.appmelinabit.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class SenhaService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;

//---------------------------------------------------------

    @Transactional
    public void iniciarProcessoRedefinicao(String email) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            // CORREÇÃO: Exclusão direta na transação principal. 
            // Isso garante que o token antigo seja excluído antes de tentar inserir o novo.
            tokenRepository.deleteByUsuario(usuario); 

            PasswordResetToken token = new PasswordResetToken();
            token.setToken(UUID.randomUUID().toString());
            token.setUsuario(usuario);
            token.setExpiryDate(LocalDateTime.now().plusMinutes(15));
            
            // O token só é salvo se o e-mail for enviado com sucesso
            try {
                enviarEmailRedefinicao(usuario.getEmail(), token.getToken());
                tokenRepository.save(token); // Salva APÓS o envio
            } catch (MessagingException e) {
                System.err.println("Falha no envio do e-mail (Token NÃO SALVO). Causa: " + e.getMessage());
                // Lança RuntimeException para forçar o ROLLBACK
                throw new RuntimeException("Falha ao enviar e-mail de redefinição. Erro SMTP.", e); 
            }
        }
    }

    // MÉTODO REMOVIDO/COMENTADO - A lógica de exclusão foi movida para 'iniciarProcessoRedefinicao'
    /*
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void excluirTokenExistente(Usuario usuario) {
        tokenRepository.findByUsuario(usuario)
                .ifPresent(tokenRepository::delete);
    }
    */
//---------------------------------------------------------
    
    private void enviarEmailRedefinicao(String email, String token) throws MessagingException {
        
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        String urlRedefinicao = "http://localhost:8080/redefinir-senha?token=" + token;

        String htmlMsg = "<h3>Redefinição de Senha</h3>"
                       + "<p>Olá,</p>"
                       + "<p>Clique no link abaixo para redefinir sua senha:</p>"
                       + "<a href=\"" + urlRedefinicao + "\">Redefinir Senha</a>"
                       + "<p>Este link irá expirar em 15 minutos.</p>";

        helper.setFrom("melinabit.suporte@gmail.com"); 
        helper.setTo(email);
        helper.setSubject("Redefinição de Senha");
        helper.setText(htmlMsg, true);

        mailSender.send(message);
        System.out.println("E-mail de redefinição enviado para: " + email);
    }

    public Optional<PasswordResetToken> getTokenValido(String token) {
        Optional<PasswordResetToken> resetToken = tokenRepository.findByToken(token);
        
        if (resetToken.isPresent() && resetToken.get().getExpiryDate().isAfter(LocalDateTime.now())) {
            return resetToken;
        }
        return Optional.empty();
    }

    @Transactional
    public Optional<Usuario> redefinirSenha(String token, String novaSenha) {
        Optional<PasswordResetToken> resetToken = getTokenValido(token);
        
        if (resetToken.isPresent()) {
            Usuario usuario = resetToken.get().getUsuario();
            usuario.setSenha(passwordEncoder.encode(novaSenha));
            usuarioRepository.save(usuario);
            tokenRepository.delete(resetToken.get());
            
            return Optional.of(usuario);
        }
        return Optional.empty();
    }
}