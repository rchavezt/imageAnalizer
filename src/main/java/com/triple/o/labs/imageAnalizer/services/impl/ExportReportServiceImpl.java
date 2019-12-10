package com.triple.o.labs.imageAnalizer.services.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.triple.o.labs.imageAnalizer.entities.MedicalCase;
import com.triple.o.labs.imageAnalizer.services.ExportReportService;
import com.triple.o.labs.imageAnalizer.services.report.Footer;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

@Service
public class ExportReportServiceImpl implements ExportReportService {
    @Override
    public byte[] exportReport(MedicalCase medicalCase) throws IOException, URISyntaxException, DocumentException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document();
        Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA, 16, BaseColor.BLACK);
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
        PdfWriter writer = PdfWriter.getInstance(document, byteArrayOutputStream);
        Footer footer = new Footer();
        writer.setPageEvent(footer);
        document.open();

        Phrase header = new Phrase(
                "Triple O International" + "\n" +
                        "Orthodontic Laboratory & Supply Company" + "\n" +
                        "PO BOX 1419" + "\n" +
                        "Gainesville, Tx 76240" + "\n" +
                        "Int Cell: 940-465-0711" + "\n" +
                        "Skip Truitt BS DDS" + "\n" +
                        "skip@cfoo.com"
        , fontHeader);

        Paragraph headerPara = new Paragraph();
        headerPara.add(header);
        headerPara.setAlignment(Element.ALIGN_CENTER);

        document.add(headerPara);

        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);

        Phrase observations = new Phrase(medicalCase.getObservations(), font);

        document.add(observations);

        document.newPage();

        Phrase simpleHeader = new Phrase(
                "www.tripleolab.com" + "\n" +
                        "skip@cfoo.com"
        );

        Paragraph simpleHeaderPara = new Paragraph();
        simpleHeaderPara.add(simpleHeader);
        simpleHeaderPara.setAlignment(Element.ALIGN_CENTER);

        document.add(simpleHeaderPara);

        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);

        Phrase phrase = new Phrase("Doctor: " + medicalCase.getUser().getName() + "\n" +
                "Patient: " + medicalCase.getPatient().getFirstName() + " " + medicalCase.getPatient().getLastName() + "\n" +
                "\n" +
                "\n" +
                "Special Instructions" +
                "\n" +
                "\n" +
                "Upper Appliance:" + "\n     " + medicalCase.getUpperAppliance() + "\n" +
                "Lower Appliance:" + "\n     " + medicalCase.getLowerAppliance() + "\n" +
                "\n" +
                "\n" +
                "Schwarz-Korkhaus Elite Arch Analysis Rx:\n", font);
        document.add(phrase);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);

        Image analyzedModel = Image.getInstance(medicalCase.getSnapshotImageAnalyzed().getBase64file());
        analyzedModel.scaleAbsolute(200,400);

        Image originalModel = Image.getInstance(medicalCase.getMedicalCaseImage().getBase64file());
        originalModel.scaleAbsolute(200,400);

        table.addCell(getCell(analyzedModel, PdfPCell.ALIGN_LEFT));
        table.addCell(getCell(originalModel, PdfPCell.ALIGN_RIGHT));

        document.add(table);

        document.close();
        return byteArrayOutputStream.toByteArray();
    }

    private PdfPCell getCell(Image image, int alignment) {
        PdfPCell cell = new PdfPCell(image);
        cell.setPadding(0);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }
}
