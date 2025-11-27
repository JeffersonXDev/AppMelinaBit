package com.appmelinabit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.appmelinabit.model.Usuario;
import com.appmelinabit.repository.UsuarioRepository;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // A condição é importante para não duplicar o usuário
        if (usuarioRepository.count() == 0) {
            Usuario usuarioAdmin = new Usuario();
            usuarioAdmin.setNome("Admin");
            usuarioAdmin.setEmail("admin@melinabit.com");
            usuarioAdmin.setSenha(passwordEncoder.encode("123456")); 
            usuarioAdmin.setNivel("ROLE_ADMIN"); 

            usuarioRepository.save(usuarioAdmin);
            System.out.println("Usuário de teste 'Admin' inserido no banco de dados.");
        }
    }
}