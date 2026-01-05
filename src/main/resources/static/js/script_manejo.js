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

    @GetMapping("/por-apiario/{apiarioId}")
    public ResponseEntity<List<Manejo>> getManejosPorApiario(@PathVariable Long apiarioId) {
        
        List<Manejo> todosManejos = manejoService.buscarManejosPorApiario(apiarioId);

        return ResponseEntity.ok(todosManejos); 
    }
}	