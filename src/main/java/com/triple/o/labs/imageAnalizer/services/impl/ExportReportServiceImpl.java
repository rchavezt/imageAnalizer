package com.triple.o.labs.imageAnalizer.services.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.triple.o.labs.imageAnalizer.daos.CasesDao;
import com.triple.o.labs.imageAnalizer.entities.MedicalCase;
import com.triple.o.labs.imageAnalizer.enums.Status;
import com.triple.o.labs.imageAnalizer.services.CaseService;
import com.triple.o.labs.imageAnalizer.services.ExportReportService;
import com.triple.o.labs.imageAnalizer.services.report.Footer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

@Service
public class ExportReportServiceImpl implements ExportReportService {

    @Autowired
    private CasesDao casesDao;

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

        //Second page

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

        Image originalModel = Image.getInstance(medicalCase.getCanvas().getBase64file());
        originalModel.scaleAbsolute(200,400);

        table.addCell(getCell(analyzedModel, PdfPCell.ALIGN_LEFT));
        table.addCell(getCell(originalModel, PdfPCell.ALIGN_RIGHT));

        document.add(table);

        //Third page

        if (medicalCase.getBilmer() != null) {

            document.newPage();

            Phrase phraseTitle = new Phrase("Doctor: " + medicalCase.getUser().getName() + "\n" +
                    "Patient: " + medicalCase.getPatient().getFirstName() + " " + medicalCase.getPatient().getLastName(), font);

            document.add(phraseTitle);

            InputStream blimerBase64 = new ByteArrayInputStream(medicalCase.getBilmer().getBase64file());

            BufferedImage blimerBuffer = ImageIO.read(blimerBase64);
            blimerBuffer = blimerBuffer.getSubimage(0, 0, blimerBuffer.getWidth() / 2, blimerBuffer.getHeight());

            Graphics2D g2 = blimerBuffer.createGraphics();
            Color oldColor = g2.getColor();
            g2.fillRect(0, 0, blimerBuffer.getWidth() / 2, blimerBuffer.getHeight());
            g2.setColor(oldColor);
            g2.drawImage(blimerBuffer, null, 0, 0);
            g2.dispose();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            try {
                ImageIO.write(blimerBuffer, "png", bos);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Image blimer = Image.getInstance(bos.toByteArray());
            blimer.setAlignment(Element.ALIGN_CENTER);

            document.add(blimer);

            //Fourth page

            document.newPage();

            Image blimerOriginal = Image.getInstance(medicalCase.getBilmer().getBase64file());

            document.add(blimerOriginal);

        }

        //Extra pages

        for (com.triple.o.labs.imageAnalizer.entities.Image extra: medicalCase.getExtraImages()) {
            document.newPage();
            Image extraiText = Image.getInstance(extra.getBase64file());
            document.add(extraiText);
        }

        document.close();

        if (medicalCase.getStatus() != Status.COMPLETED) {
            medicalCase.setStatus(Status.COMPLETED);
            casesDao.save(medicalCase);
        }

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
