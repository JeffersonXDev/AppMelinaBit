package com.appmelinabit.controller;

import com.appmelinabit.model.Manejo;
import com.appmelinabit.service.ManejoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/colmeias")
public class ManejoApiController {

    @Autowired
    private ManejoService manejoService;

    // Endpoint chamado pelo AJAX para popular o SELECT de colmeias
    @GetMapping("/por-apiario/{apiarioId}")
    public ResponseEntity<List<Manejo>> getManejosPorApiario(@PathVariable Long apiarioId) {
        
        List<Manejo> todosManejos = manejoService.buscarManejosPorApiario(apiarioId);
        
        // Para a seleção de colmeia, geralmente você só precisa de uma lista de colmeias únicas 
        // em vez de todos os registros de manejo. Aqui, assumimos que 'Manejo' atua como 
        // a 'Colmeia' para fins de seleção, retornando todos os registros para simplificar.
        
        // Caso seu modelo 'Manejo' seja na verdade o registro histórico de uma 'Colmeia',
        // você precisaria de uma entidade 'Colmeia' separada para o SELECT, ou faria o seguinte:
        
        // Simplesmente retorna todos os registros de manejo (que incluem numeroColmeia e id_colmeia)
        return ResponseEntity.ok(todosManejos); 
    }
}	