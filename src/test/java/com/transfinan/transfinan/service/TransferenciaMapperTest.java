package com.transfinan.transfinan.service;

import com.transfinan.transfinan.dto.TransferenciaDTO;
import com.transfinan.transfinan.model.Transferencia;
import com.transfinan.transfinan.util.TransferenciaMapper;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TransferenciaMapperTest {

    @Test
    void toEntity_DeveMapearTransferenciaDTOParaTransferencia() {
        // Arrange
        TransferenciaDTO dto = new TransferenciaDTO();
        dto.setContaOrigem("12345");
        dto.setContaDestino("54321");
        dto.setValor(new BigDecimal("1500.00"));
        dto.setDataTransferencia(LocalDate.now().plusDays(3));

        BigDecimal taxa = new BigDecimal("50.00");

        // Act
        Transferencia transferencia = TransferenciaMapper.toEntity(dto, taxa);

        // Assert
        assertNotNull(transferencia);
        assertEquals(dto.getContaOrigem(), transferencia.getContaOrigem());
        assertEquals(dto.getContaDestino(), transferencia.getContaDestino());
        assertEquals(dto.getValor(), transferencia.getValor());
        assertEquals(taxa, transferencia.getTaxa());
        assertEquals(dto.getDataTransferencia(), transferencia.getDataTransferencia());
        assertEquals(LocalDate.now(), transferencia.getDataAgendamento());
    }
}