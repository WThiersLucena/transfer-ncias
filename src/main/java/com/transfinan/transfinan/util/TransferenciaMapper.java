package com.transfinan.transfinan.util;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.transfinan.transfinan.dto.TransferenciaDTO;
import com.transfinan.transfinan.model.Transferencia;

public class TransferenciaMapper {
    
    public static Transferencia toEntity(TransferenciaDTO dto, BigDecimal taxa) {
        return Transferencia.builder()
                .contaOrigem(dto.getContaOrigem())
                .contaDestino(dto.getContaDestino())
                .valor(dto.getValor())
                .taxa(taxa)
                .dataTransferencia(dto.getDataTransferencia())
                .dataAgendamento(LocalDate.now())
                .build();
    }
    
}
