package com.triple.o.labs.imageAnalizer.services.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.triple.o.labs.imageAnalizer.entities.MedicalCase;
import com.triple.o.labs.imageAnalizer.services.ExportReportService;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ExportReportServiceImpl implements ExportReportService {
    @Override
    public byte[] exportReport(MedicalCase medicalCase) throws IOException, URISyntaxException, DocumentException {
        Path path = Paths.get(ResourceUtils.getURL("classpath:images/cfoo-logo.jpg").toURI());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        PdfWriter.getInstance(document, byteArrayOutputStream);
        document.open();
        Image img = Image.getInstance(path.toAbsolutePath().toString());
        img.setAlignment(Image.MIDDLE);
        document.add(img);

        Phrase phrase = new Phrase("Dr. " + medicalCase.getUser().getName() + "\n" +
                "Patient: " + medicalCase.getPatient().getFirstName() + " " + medicalCase.getPatient().getLastName() + "\n" +
                "Details: " + medicalCase.getDetail() + "\n" +
                "Original image:\n", font);
        document.add(phrase);

        Image model = Image.getInstance(medicalCase.getMedicalCaseImage().getBase64image());
        model.scaleAbsolute(200,400);
        model.setAlignment(Image.MIDDLE);
        document.add(model);

        document.close();
        return byteArrayOutputStream.toByteArray();
    }
}
