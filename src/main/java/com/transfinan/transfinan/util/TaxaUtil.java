package com.transfinan.transfinan.util;

import java.math.BigDecimal;
import java.util.NavigableMap;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaxaUtil {

    private static Logger logger = LoggerFactory.getLogger(TaxaUtil.class);

    // public static BigDecimal calcularTaxa(BigDecimal valor, long dias) {
    // if (dias == 0) {
    // BigDecimal taxa =
    // valor.multiply(BigDecimal.valueOf(0.025)).add(BigDecimal.valueOf(3.00));
    // logger.info("Taxa aplicada para transferência no mesmo dia: {}", taxa);
    // return taxa;
    // } else if (dias >= 1 && dias <= 10) {
    // logger.info("Taxa fixa aplicada para transferência entre 1 e 10 dias:
    // 12.00");
    // return BigDecimal.valueOf(12.00);
    // } else if (dias >= 11 && dias <= 20) {
    // BigDecimal taxa = valor.multiply(BigDecimal.valueOf(0.082));
    // logger.info("Taxa percentual aplicada (8.2%) para transferência entre 11 e 20
    // dias: {}", taxa);
    // return taxa;
    // } else if (dias >= 21 && dias <= 30) {
    // BigDecimal taxa = valor.multiply(BigDecimal.valueOf(0.069));
    // logger.info("Taxa percentual aplicada (6.9%) para transferência entre 21 e 30
    // dias: {}", taxa);
    // return taxa;
    // } else if (dias >= 31 && dias <= 40) {
    // BigDecimal taxa = valor.multiply(BigDecimal.valueOf(0.047));
    // logger.info("Taxa percentual aplicada (4.7%) para transferência entre 31 e 40
    // dias: {}", taxa);
    // return taxa;
    // } else if (dias >= 41 && dias <= 50) {
    // BigDecimal taxa = valor.multiply(BigDecimal.valueOf(0.017));
    // logger.info("Taxa percentual aplicada (1.7%) para transferência entre 41 e 50
    // dias: {}", taxa);
    // return taxa;
    // }

    // logger.warn("Nenhuma taxa aplicável encontrada para {} dias. Transferência
    // não permitida.", dias);
    // return null; // Retorna null para indicar erro
    // }

    // Estrutura de dados para armazenar regras de taxa
    private static final NavigableMap<Integer, Taxa> regrasTaxas = new TreeMap<>();

    static {
        regrasTaxas.put(0, new Taxa(BigDecimal.valueOf(3.00), BigDecimal.valueOf(0.025)));
        regrasTaxas.put(1, new Taxa(BigDecimal.valueOf(12.00), BigDecimal.ZERO));
        regrasTaxas.put(11, new Taxa(BigDecimal.ZERO, BigDecimal.valueOf(0.082)));
        regrasTaxas.put(21, new Taxa(BigDecimal.ZERO, BigDecimal.valueOf(0.069)));
        regrasTaxas.put(31, new Taxa(BigDecimal.ZERO, BigDecimal.valueOf(0.047)));
        regrasTaxas.put(41, new Taxa(BigDecimal.ZERO, BigDecimal.valueOf(0.017)));
    }

    public static BigDecimal calcularTaxa(BigDecimal valor, long dias) {
        Taxa taxa = regrasTaxas.floorEntry((int) dias).getValue();
        if (taxa == null) {
            logger.warn("Nenhuma taxa aplicável encontrada para {} dias. Transferência não permitida.", dias);
            return null;
        }

        BigDecimal taxaCalculada = taxa.taxaFixa.add(valor.multiply(taxa.taxaPercentual));
        logger.info("Taxa aplicada para {} dias: {}", dias, taxaCalculada);
        return taxaCalculada;
    }

    // Classe interna para armazenar os valores da taxa
    private static class Taxa {
        private final BigDecimal taxaFixa;
        private final BigDecimal taxaPercentual;

        public Taxa(BigDecimal taxaFixa, BigDecimal taxaPercentual) {
            this.taxaFixa = taxaFixa;
            this.taxaPercentual = taxaPercentual;
        }
    }

}
