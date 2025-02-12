package com.transfinan.transfinan.controller;


import java.util.List;

import com.transfinan.transfinan.dto.TransferenciaDTO;
import com.transfinan.transfinan.model.Transferencia;
import com.transfinan.transfinan.service.TransferenciaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/transferencias")
public class TransferenciaController {
    
    @Autowired
    private  TransferenciaService transferenciaService;

    // Endpoint para agendar uma nova transferência
    @PostMapping("/agendar")
    public ResponseEntity<Transferencia> agendarTransferencia(@Valid @RequestBody TransferenciaDTO dto) {
        Transferencia transferencia = transferenciaService.agendarTransferencia(dto);
        return ResponseEntity.ok(transferencia);
    }

    // Endpoint para listar todas as transferências agendadas
    @GetMapping("/extrato")
    public ResponseEntity<List<Transferencia>> listarTransferencias() {
        List<Transferencia> transferencias = transferenciaService.listarTransferencias();
        return ResponseEntity.ok(transferencias);
    }
}
