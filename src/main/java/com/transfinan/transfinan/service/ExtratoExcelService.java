package com.transfinan.transfinan.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transfinan.transfinan.model.Transferencia;
import com.transfinan.transfinan.repository.TransferenciaRepository;

import org.apache.poi.ss.usermodel.Sheet;

import org.slf4j.LoggerFactory;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ExtratoExcelService {
    
    private static final Logger logger = LoggerFactory.getLogger(ExtratoExcelService.class);

    @Autowired
    private TransferenciaRepository transferenciaRepository;


    public byte[] gerarExtratoExcel() {
        logger.info("Iniciando geração do extrato em Excel...");

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Extrato de Transferências");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            // Criando cabeçalho da planilha
            Row headerRow = sheet.createRow(0);
            String[] colunas = {"Data", "Conta Origem", "Conta Destino", "Valor", "Taxa"};

            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            for (int i = 0; i < colunas.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(colunas[i]);
                cell.setCellStyle(headerStyle);
            }

            // Preenchendo os dados reais das transferências
            List<Transferencia> transferencias = transferenciaRepository.findAll();
            int rowNum = 1;

            for (Transferencia transferencia : transferencias) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(transferencia.getDataTransferencia().format(formatter));
                row.createCell(1).setCellValue(transferencia.getContaOrigem());
                row.createCell(2).setCellValue(transferencia.getContaDestino());
                row.createCell(3).setCellValue("R$ " + String.format("%.2f", transferencia.getValor()));

                BigDecimal taxa = transferencia.getTaxa() != null ? transferencia.getTaxa() : BigDecimal.ZERO;
                row.createCell(4).setCellValue("R$ " + String.format("%.2f", taxa));
            }

            // Ajustando largura das colunas automaticamente
            for (int i = 0; i < colunas.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Rodapé com data e hora de geração
            Row rodapeRow = sheet.createRow(rowNum + 2);
            Cell rodapeCell = rodapeRow.createCell(3);
            rodapeCell.setCellValue("Extrato gerado em: " + java.time.LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));

            // Escrevendo no OutputStream
            workbook.write(outputStream);
            logger.info("Excel gerado com sucesso.");

            return outputStream.toByteArray();
        } catch (IOException e) {
            logger.error("Erro ao gerar Excel: {}", e.getMessage(), e);
            return new byte[0];
        }
    }

}
