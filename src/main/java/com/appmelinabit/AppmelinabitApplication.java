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
			if (usuarioRepository.count() == 0) {
				Usuario admin = new Usuario();
				admin.setNome("Admin");
				admin.setEmail("admin@melinabit.com");
				// Define a tua senha aqui - o encoder vai transformá-la em hash $2a$
				admin.setSenha(passwordEncoder.encode("Melinabit"));
				admin.setNivel("ROLE_ADMIN");
				admin.setStatusConta("Ativo");

				usuarioRepository.save(admin);
				System.out.println("-----------------------------------------");
				System.out.println(">>> BANCO VAZIO: Utilizador Admin criado!");
				System.out.println("-----------------------------------------");
			} else {
				System.out.println(">>> Banco de dados já contém utilizadores. Ignorando criação de Admin.");
			}
		};
	}
}