package com.transfinan.transfinan.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferenciaDTO {
    
    @NotBlank(message = "A conta de origem é obrigatória.")
    @Pattern(regexp = "\\d{10}", message = "A conta de origem deve conter exatamente 10 dígitos.")
    private String contaOrigem;

    @NotBlank(message = "A conta de destino é obrigatória.")
    @Pattern(regexp = "\\d{10}", message = "A conta de destino deve conter exatamente 10 dígitos.")
    private String contaDestino;

    @NotNull(message = "O valor da transferência é obrigatório.")
    @DecimalMin(value = "0.01", message = "O valor da transferência deve ser maior que zero.")
    private BigDecimal valor;

    @NotNull(message = "A data da transferência é obrigatória.")
    @FutureOrPresent(message = "A data da transferência não pode estar no passado.")
    private LocalDate dataTransferencia;

}
