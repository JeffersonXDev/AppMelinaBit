package com.appmelinabit;

import com.appmelinabit.model.Usuario;
import com.appmelinabit.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AppmelinabitApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppmelinabitApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
	    return args -> {
	        String emailAdmin = "melinabit.suporte@gmail.com";

	        // Busca apenas para verificar existência
	        boolean existeAdmin = usuarioRepository.findByEmail(emailAdmin).isPresent();

	        if (!existeAdmin) {
	            Usuario admin = new Usuario();
	            admin.setNome("Admin MelinaBit");
	            admin.setEmail(emailAdmin);
	            admin.setSenha(passwordEncoder.encode("Melina@2025"));
	            admin.setNivel("ROLE_ADMIN");
	            admin.setStatusConta("Ativo");
	            admin.setDataCadastro(java.time.LocalDateTime.now());
	            
	            usuarioRepository.save(admin);
	            System.out.println(">>> [INFO] Admin não encontrado. Criado com sucesso em 'usuarios'.");
	        } else {
	            // Se cair aqui, ele não toca em nada no banco
	            System.out.println(">>> [INFO] Admin já presente na tabela 'usuarios'. Nenhuma ação necessária.");
	        }
	    };
	}
}