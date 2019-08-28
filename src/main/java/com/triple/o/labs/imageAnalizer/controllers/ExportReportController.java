package com.triple.o.labs.imageAnalizer.controllers;

import com.itextpdf.text.DocumentException;
import com.triple.o.labs.imageAnalizer.services.CaseService;
import com.triple.o.labs.imageAnalizer.services.ExportReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping(value = "/report", headers = "Authorization")
public class ExportReportController {

    @Autowired
    private ExportReportService exportReportService;

    @Autowired
    private CaseService caseService;

    @RequestMapping(value = "/case/{id}", method = RequestMethod.GET, produces = "application/pdf")
    public byte[] getPdfReport(@PathVariable Long id) throws DocumentException, IOException, URISyntaxException {
        return exportReportService.exportReport(caseService.getCase(id));
    }
}
