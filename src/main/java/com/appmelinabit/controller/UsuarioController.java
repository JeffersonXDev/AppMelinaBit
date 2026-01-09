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

    @PostMapping
    public ResponseEntity<?> criarUsuario(@RequestBody Usuario usuario) {
        if (usuario.getLgpdAceito() == null || !usuario.getLgpdAceito()) {
            return new ResponseEntity<>("O aceite dos termos da LGPD é obrigatório para o cadastro.", HttpStatus.BAD_REQUEST);
        }
        Usuario novoUsuario = usuarioService.salvarUsuario(usuario);
        return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Usuario>> listarTodosUsuarios() {
        List<Usuario> usuarios = usuarioService.listarTodosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    // AJUSTE: MUDADO DE Long PARA Integer no {id} e @PathVariable
    @PutMapping("/admin/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Usuario> alterarStatusUsuario(@PathVariable Integer id, @RequestBody UsuarioStatusUpdateRequest request) {
        Usuario usuarioAtualizado = usuarioService.alterarStatusConta(id, request.getStatus());
        return ResponseEntity.ok(usuarioAtualizado);
    }

    // AJUSTE: MUDADO DE Long PARA Integer no {id} e @PathVariable
    @DeleteMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluirUsuario(@PathVariable Integer id) {
        usuarioService.excluirUsuario(id);
        return ResponseEntity.noContent().build();
    }
}

// Classe auxiliar mantida no mesmo arquivo conforme seu código original
class UsuarioStatusUpdateRequest {
    private String status;
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}