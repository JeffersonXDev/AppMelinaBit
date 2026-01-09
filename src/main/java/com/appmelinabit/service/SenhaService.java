package com.appmelinabit.service;

import com.appmelinabit.model.PasswordResetToken;
import com.appmelinabit.model.Usuario;
import com.appmelinabit.repository.PasswordResetTokenRepository;
import com.appmelinabit.repository.UsuarioRepository;
import jakarta.servlet.http.HttpServletRequest;
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

    @Transactional
    public void iniciarProcessoRedefinicao(String email, HttpServletRequest request) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            tokenRepository.deleteByUsuario(usuario);

            PasswordResetToken token = new PasswordResetToken();
            token.setToken(UUID.randomUUID().toString());
            token.setUsuario(usuario);
            token.setExpiryDate(LocalDateTime.now().plusMinutes(15));

            try {
                String urlBase = System.getenv("APP_URL");
                if (urlBase == null || urlBase.isEmpty()) {
                    urlBase = request.getScheme() + "://" + request.getServerName();
                    if (request.getServerPort() != 80 && request.getServerPort() != 443) {
                        urlBase += ":" + request.getServerPort();
                    }
                }

                String linkCompleto = urlBase + "/redefinir-senha?token=" + token.getToken();
                enviarEmailRedefinicao(usuario.getEmail(), linkCompleto);
                tokenRepository.save(token);
            } catch (MessagingException e) {
                throw new RuntimeException("Falha ao enviar e-mail de redefinição.", e);
            }
        }
    }

    private void enviarEmailRedefinicao(String email, String urlRedefinicao) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

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