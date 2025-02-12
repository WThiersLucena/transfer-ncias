package com.transfinan.transfinan.service;

import com.transfinan.transfinan.dto.TransferenciaDTO;
import com.transfinan.transfinan.model.Transferencia;
import com.transfinan.transfinan.repository.TransferenciaRepository;
import com.transfinan.transfinan.util.TransferenciaMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransferenciaServiceTest {

    @InjectMocks
    private TransferenciaService transferenciaService;

    @Mock
    private TransferenciaRepository transferenciaRepository;

    @Mock
    private TaxaService taxaService;

    @Mock
    private TransferenciaMapper transferenciaMapper;

    private TransferenciaDTO transferenciaDTO;

    @BeforeEach
    void setUp() {
        // Preparar dados de teste
        transferenciaDTO = new TransferenciaDTO();
        transferenciaDTO.setValor(new BigDecimal("1000"));
        transferenciaDTO.setDataTransferencia(LocalDate.now().plusDays(5)); // 5 dias para frente
    }

    @Test
    void agendarTransferencia_DeveSalvarTransferenciaComTaxaCalculada() {
        // Arrange
        BigDecimal taxaCalculada = new BigDecimal("100.0");
        Transferencia transferencia = new Transferencia();

        // Mock do comportamento do TaxaService
        when(taxaService.calcularTaxa(transferenciaDTO.getValor(), 5)).thenReturn(taxaCalculada);
        
        // Mock do TransferenciaMapper
        when(transferenciaMapper.toEntity(transferenciaDTO, taxaCalculada)).thenReturn(transferencia);
        
        // Mock do TransferenciaRepository
        when(transferenciaRepository.save(transferencia)).thenReturn(transferencia);

        // Act
        Transferencia result = transferenciaService.agendarTransferencia(transferenciaDTO);

        // Assert
        assertNotNull(result);
        verify(taxaService, times(1)).calcularTaxa(transferenciaDTO.getValor(), 5); // Verifica se o método foi chamado
        verify(transferenciaRepository, times(1)).save(transferencia); // Verifica se o save foi chamado
    }

    @Test
    void agendarTransferencia_DeveLancarExcecaoSeTaxaForNull() {
        // Arrange
        when(taxaService.calcularTaxa(transferenciaDTO.getValor(), 5)).thenReturn(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            transferenciaService.agendarTransferencia(transferenciaDTO);
        });

        assertEquals("Nenhuma taxa aplicável. Transferência não permitida.", exception.getMessage());
    }

    @Test
    void listarTransferencias_DeveRetornarListaDeTransferencias() {
        // Arrange
        Transferencia transferencia1 = new Transferencia();
        Transferencia transferencia2 = new Transferencia();
        List<Transferencia> transferencias = List.of(transferencia1, transferencia2);

        // Mock do comportamento do TransferenciaRepository
        when(transferenciaRepository.findAll()).thenReturn(transferencias);

        // Act
        List<Transferencia> result = transferenciaService.listarTransferencias();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size()); // Verifica se a lista contém 2 transferências
    }
}
