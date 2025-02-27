package com.transfinan.transfinan.service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.transfinan.transfinan.dto.TransferenciaDTO;
import com.transfinan.transfinan.model.Transferencia;
import com.transfinan.transfinan.repository.TransferenciaRepository;

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

    /**
     * Método responsável por gerar um extrato de transferências em formato PDF.
     * @return byte[] contendo os dados binários do PDF gerado.
     */ 
    
    public byte[] gerarExtratoPdf() {
        logger.info("Iniciando geração do extrato em PDF...");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // Título centralizado
            Font tituloFonte = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Paragraph titulo = new Paragraph("Extrato de Transferências", tituloFonte);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            document.add(new Paragraph("\n")); // Espaço

            // Criando a tabela
            PdfPTable tabela = new PdfPTable(5); // 5 colunas
            tabela.setWidthPercentage(100);
            tabela.setSpacingBefore(10f);
            tabela.setSpacingAfter(10f);

            // Definição do tamanho das colunas
            tabela.setWidths(new float[]{2, 2, 2, 2, 2});

            // Criando o cabeçalho da tabela
            adicionarCelulaCabecalho(tabela, "Data");
            adicionarCelulaCabecalho(tabela, "Origem");
            adicionarCelulaCabecalho(tabela, "Destino");
            adicionarCelulaCabecalho(tabela, "Valor");
            adicionarCelulaCabecalho(tabela, "Taxa");

            // Obtendo as transferências reais do banco
            List<Transferencia> transferencias = transferenciaRepository.findAll();
            logger.info("Foram encontradas {} transferências para o extrato.", transferencias.size());

            // Formatador de data
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            // Populando a tabela com os dados reais
            for (Transferencia transferencia : transferencias) {
                adicionarCelulaDado(tabela, transferencia.getDataTransferencia().format(formatter));
                adicionarCelulaDado(tabela, transferencia.getContaOrigem());
                adicionarCelulaDado(tabela, transferencia.getContaDestino());
                adicionarCelulaDado(tabela, "R$" + String.format("%.2f", transferencia.getValor()));
                adicionarCelulaDado(tabela, "R$" + String.format("%.2f", 
                    transferencia.getTaxa() != null ? transferencia.getTaxa() : BigDecimal.ZERO));
            }

            document.add(tabela);

            // Rodapé com data e hora de geração
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String dataHoraGeracao = LocalDateTime.now().format(dateTimeFormatter);
            Paragraph rodape = new Paragraph("Extrato gerado em: " + dataHoraGeracao, new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC));
            rodape.setAlignment(Element.ALIGN_RIGHT);
            document.add(rodape);

            document.close();
            logger.info("PDF gerado com sucesso.");


        } catch (DocumentException e) {
            logger.error("Erro ao gerar o PDF: {}", e.getMessage(), e);
        }

        byte[] pdfBytes = outputStream.toByteArray();
        logger.info("Tamanho final do PDF: {} bytes", pdfBytes.length);

        return pdfBytes;
    }

    // Método auxiliar para adicionar células no cabeçalho da tabela
    private void adicionarCelulaCabecalho(PdfPTable tabela, String texto) {
        PdfPCell celula = new PdfPCell(new Phrase(texto, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
        celula.setHorizontalAlignment(Element.ALIGN_CENTER);
        celula.setBackgroundColor(BaseColor.LIGHT_GRAY);
        tabela.addCell(celula);
    }

    // Método auxiliar para adicionar células com dados
    private void adicionarCelulaDado(PdfPTable tabela, String texto) {
        PdfPCell celula = new PdfPCell(new Phrase(texto, new Font(Font.FontFamily.HELVETICA, 10)));
        celula.setHorizontalAlignment(Element.ALIGN_CENTER);
        tabela.addCell(celula);
    }
}
