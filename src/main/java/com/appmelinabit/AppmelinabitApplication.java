package com.appmelinabit;

import com.appmelinabit.model.Usuario;
import com.appmelinabit.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value; // Importante adicionar este import
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AppmelinabitApplication {

    // O Spring vai injetar o valor da variável ADMIN_PASS aqui
    @Value("${ADMIN_PASS}")
    private String adminPassword;

    public static void main(String[] args) {
        SpringApplication.run(AppmelinabitApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String emailAdmin = "melinabit.suporte@gmail.com";

            boolean existeAdmin = usuarioRepository.findByEmail(emailAdmin).isPresent();

            if (!existeAdmin) {
                Usuario admin = new Usuario();
                admin.setNome("Admin MelinaBit");
                admin.setEmail(emailAdmin);
                
                // USANDO A VARIÁVEL:
                admin.setSenha(passwordEncoder.encode(adminPassword));
                
                admin.setNivel("ROLE_ADMIN");
                admin.setStatusConta("Ativo");
                admin.setDataCadastro(java.time.LocalDateTime.now());
                
                usuarioRepository.save(admin);
                System.out.println(">>> [INFO] Admin criado com sucesso via variável de ambiente.");
            } else {
                System.out.println(">>> [INFO] Admin já presente. Nenhuma ação necessária.");
            }
        };
    }
}