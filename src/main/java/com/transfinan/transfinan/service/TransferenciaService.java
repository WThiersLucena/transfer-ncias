package com.transfinan.transfinan.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.transfinan.transfinan.dto.TransferenciaDTO;
import com.transfinan.transfinan.model.Transferencia;
import com.transfinan.transfinan.repository.TransferenciaRepository;
import com.transfinan.transfinan.util.TaxaUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class TransferenciaService {

    @Autowired
    private TransferenciaRepository transferenciaRepository;

    @Autowired
    private TaxaService taxaService;

    private static Logger logger = LoggerFactory.getLogger(TransferenciaService.class);

    @Transactional
    public Transferencia agendarTransferencia(@Valid TransferenciaDTO dto) {
        LocalDate hoje = LocalDate.now();
        long diasDiferenca = ChronoUnit.DAYS.between(hoje, dto.getDataTransferencia());

        // Log para depuração
        BigDecimal taxa = taxaService.calcularTaxa(dto.getValor(), diasDiferenca);

        if (taxa == null) {
            throw new IllegalArgumentException("Nenhuma taxa aplicável. Transferência não permitida.");
        }

        Transferencia transferencia = Transferencia.builder()
                .contaOrigem(dto.getContaOrigem())
                .contaDestino(dto.getContaDestino())
                .valor(dto.getValor())
                .taxa(taxa)
                .dataTransferencia(dto.getDataTransferencia())
                .dataAgendamento(LocalDate.now())
                .build();

        return transferenciaRepository.save(transferencia);
    }

    public List<Transferencia> listarTransferencias() {
        return transferenciaRepository.findAll();
    }
}
