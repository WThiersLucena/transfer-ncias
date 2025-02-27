package com.transfinan.transfinan.controller;


import java.util.List;

import com.transfinan.transfinan.dto.TransferenciaDTO;
import com.transfinan.transfinan.model.Transferencia;
import com.transfinan.transfinan.service.TransferenciaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;



import jakarta.validation.Valid;

@RestController
@RequestMapping("/transferencias")
public class TransferenciaController {
    
    @Autowired
    private  TransferenciaService transferenciaService;

    private static Logger logger = LoggerFactory.getLogger(TransferenciaService.class);

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

    @GetMapping("/extrato/pdf")
    public ResponseEntity<byte[]> gerarExtratoPdf() {

        // Chama o serviço que gera o extrato em formato PDF e retorna os bytes do arquivo
        byte[] pdfBytes = transferenciaService.gerarExtratoPdf();
        
        // Logando o tamanho do arquivo gerado
        logger.info("PDF gerado com {} bytes", pdfBytes.length);

        // Cria um objeto HttpHeaders para configurar os cabeçalhos da resposta HTTP
        HttpHeaders headers = new HttpHeaders();

        // Define o tipo de conteúdo da resposta como "application/pdf"
        headers.setContentType(MediaType.APPLICATION_PDF);

        // Configura o cabeçalho Content-Disposition para indicar que o arquivo será baixado com o nome "extrato.pdf"
        headers.setContentDisposition(ContentDisposition.attachment().filename("extrato.pdf").build());

        // Retorna a resposta HTTP 200 (OK) com os cabeçalhos configurados e o corpo contendo os bytes do PDF        
        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }

}
