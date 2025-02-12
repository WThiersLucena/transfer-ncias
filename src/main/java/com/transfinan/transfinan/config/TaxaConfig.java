package com.transfinan.transfinan.config;

import java.math.BigDecimal;
import java.util.NavigableMap;
import java.util.TreeMap;

public class TaxaConfig {

    private static final NavigableMap<Integer, Taxa> regrasTaxas = new TreeMap<>();

    static {
        regrasTaxas.put(0, new Taxa(BigDecimal.valueOf(3.00), BigDecimal.valueOf(0.025)));
        regrasTaxas.put(1, new Taxa(BigDecimal.valueOf(12.00), BigDecimal.ZERO));
        regrasTaxas.put(11, new Taxa(BigDecimal.ZERO, BigDecimal.valueOf(0.082)));
        regrasTaxas.put(21, new Taxa(BigDecimal.ZERO, BigDecimal.valueOf(0.069)));
        regrasTaxas.put(31, new Taxa(BigDecimal.ZERO, BigDecimal.valueOf(0.047)));
        regrasTaxas.put(41, new Taxa(BigDecimal.ZERO, BigDecimal.valueOf(0.017)));
    }

    public static Taxa getTaxaParaDias(int dias) {
        return regrasTaxas.floorEntry(dias).getValue();
    }

    // Classe aninhada para representar as taxas
    public static class Taxa {
        private final BigDecimal taxaFixa;
        private final BigDecimal taxaPercentual;

        public Taxa(BigDecimal taxaFixa, BigDecimal taxaPercentual) {
            this.taxaFixa = taxaFixa;
            this.taxaPercentual = taxaPercentual;
        }

        public BigDecimal getTaxaFixa() {
            return taxaFixa;
        }

        public BigDecimal getTaxaPercentual() {
            return taxaPercentual;
        }
    }
}
