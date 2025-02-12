package com.transfinan.transfinan.service;

import java.math.BigDecimal;

import com.transfinan.transfinan.config.TaxaConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TaxaService {
    
    private static  Logger logger = LoggerFactory.getLogger(TaxaService.class);

    public BigDecimal calcularTaxa(BigDecimal valor, long dias) {
        TaxaConfig.Taxa taxa = TaxaConfig.getTaxaParaDias((int) dias);
        if (taxa == null) {
            logger.warn("Nenhuma taxa aplicável encontrada para {} dias. Transferência não permitida.", dias);
            return null;
        }

        BigDecimal taxaCalculada = taxa.getTaxaFixa().add(valor.multiply(taxa.getTaxaPercentual()));
        logger.info("Taxa aplicada para {} dias: {}", dias, taxaCalculada);
        return taxaCalculada;
    }
}
