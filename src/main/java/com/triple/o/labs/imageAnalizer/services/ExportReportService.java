package com.triple.o.labs.imageAnalizer.services;

import com.itextpdf.text.DocumentException;
import com.triple.o.labs.imageAnalizer.entities.MedicalCase;

import java.io.IOException;
import java.net.URISyntaxException;

public interface ExportReportService {
    byte[] exportReport(MedicalCase medicalCase) throws IOException, URISyntaxException, DocumentException;
}
