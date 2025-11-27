package com.appmelinabit.controller;

import com.appmelinabit.model.Usuario;
import com.appmelinabit.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Endpoint para criar um novo usuário (Cadastro via API REST)
    @PostMapping
    public ResponseEntity<?> criarUsuario(@RequestBody Usuario usuario) {
        
        // --- VALIDAÇÃO LGPD ADICIONADA ---
        if (!usuario.isLgpdAceito()) {
            return new ResponseEntity<>("O aceite dos termos da LGPD é obrigatório para o cadastro.", HttpStatus.BAD_REQUEST);
        }
        // ----------------------------------
        
        Usuario novoUsuario = usuarioService.salvarUsuario(usuario);
        return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
    }
    
    // --- Endpoints para a Dashboard de Administração ---

    // 1. Endpoint para listar todos os usuários (necessário para a tabela)
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Usuario>> listarTodosUsuarios() {
        List<Usuario> usuarios = usuarioService.listarTodosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    // 2. Endpoint para bloquear/desbloquear um usuário
    @PutMapping("/admin/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Usuario> alterarStatusUsuario(@PathVariable Long id, @RequestBody UsuarioStatusUpdateRequest request) {
        Usuario usuarioAtualizado = usuarioService.alterarStatusConta(id, request.getStatus());
        return ResponseEntity.ok(usuarioAtualizado);
    }

    // 3. Endpoint para excluir um usuário
    @DeleteMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluirUsuario(@PathVariable Long id) {
        usuarioService.excluirUsuario(id);
        return ResponseEntity.noContent().build();
    }
}

// Classe auxiliar para receber o status de forma simples no corpo da requisição
class UsuarioStatusUpdateRequest {
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}