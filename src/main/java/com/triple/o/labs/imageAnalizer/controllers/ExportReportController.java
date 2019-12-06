package com.triple.o.labs.imageAnalizer.controllers;

import com.itextpdf.text.DocumentException;
import com.triple.o.labs.imageAnalizer.config.UserPrincipal;
import com.triple.o.labs.imageAnalizer.config.security.CurrentUser;
import com.triple.o.labs.imageAnalizer.entities.MedicalCase;
import com.triple.o.labs.imageAnalizer.entities.User;
import com.triple.o.labs.imageAnalizer.enums.UserType;
import com.triple.o.labs.imageAnalizer.exceptions.BadRequestException;
import com.triple.o.labs.imageAnalizer.services.CaseService;
import com.triple.o.labs.imageAnalizer.services.ExportReportService;
import com.triple.o.labs.imageAnalizer.services.UserService;
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

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/case/{id}", method = RequestMethod.GET, produces = "application/pdf")
    public byte[] getPdfReport(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long id) throws DocumentException, IOException, URISyntaxException {
        User user = userService.getUser(userPrincipal.getId());
        if (user.getUserType() != UserType.LAB && user.getUserType() != UserType.DOCTOR) {
            throw new BadRequestException("User must be Doctor or Laboratory");
        }

        MedicalCase medicalCase = caseService.getCase(id);

        if (user.getUserType() == UserType.DOCTOR && medicalCase.getUser() != user){
            throw new BadRequestException("The report of the case you request belongs to another Doctor");
        }

        return exportReportService.exportReport(medicalCase);
    }
}
