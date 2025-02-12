package com.transfinan.transfinan.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;

import com.transfinan.transfinan.config.TaxaConfig;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;

public class TaxaServiceTest {

    @Mock
    private Logger logger;

    @InjectMocks
    private TaxaService taxaService;

    @Test
    void testCalcularTaxa_ComTaxaValida() {
        // Configuração do cenário
        BigDecimal valor = new BigDecimal("1000.00");
        long dias = 5;

        // Mock do comportamento de TaxaConfig
        TaxaConfig.Taxa taxaMock = new TaxaConfig.Taxa(new BigDecimal("10.00"), new BigDecimal("0.05"));
        when(TaxaConfig.getTaxaParaDias((int) dias)).thenReturn(taxaMock);

        // Execução do método
        BigDecimal resultado = taxaService.calcularTaxa(valor, dias);

        // Verificações
        assertNotNull(resultado, "O resultado não deve ser nulo");
        assertEquals(new BigDecimal("60.00"), resultado, "O cálculo da taxa está incorreto");

        // Verifica se o logger foi chamado corretamente
        verify(logger).info("Taxa aplicada para {} dias: {}", dias, resultado);
    }

    @Test
    void testCalcularTaxa_ComTaxaNula() {
        // Configuração do cenário
        BigDecimal valor = new BigDecimal("1000.00");
        long dias = 100; // Dias fora do intervalo configurado

        // Mock do comportamento de TaxaConfig
        when(TaxaConfig.getTaxaParaDias((int) dias)).thenReturn(null);

        // Execução do método
        BigDecimal resultado = taxaService.calcularTaxa(valor, dias);

        // Verificações
        assertNull(resultado, "O resultado deve ser nulo quando não há taxa aplicável");

        // Verifica se o logger foi chamado corretamente
        verify(logger).warn("Nenhuma taxa aplicável encontrada para {} dias. Transferência não permitida.", dias);
    }

    @Test
    void testCalcularTaxa_ComValorNulo() {
        // Configuração do cenário
        BigDecimal valor = null;
        long dias = 5;

        // Execução e verificação de exceção
        assertThrows(NullPointerException.class, () -> {
            taxaService.calcularTaxa(valor, dias);
        }, "Deveria lançar NullPointerException quando o valor é nulo");
    }
}