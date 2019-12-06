package com.triple.o.labs.imageAnalizer.services.report;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class Footer extends PdfPageEventHelper {

    public void onEndPage(PdfWriter writer, Document document) {
        PdfContentByte cb = writer.getDirectContent();

        Font font = FontFactory.getFont(FontFactory.HELVETICA, 8, BaseColor.BLACK);
        Phrase footer1 = new Phrase(
                "www.tripleolab.com"
                , font);

        Phrase footer2 = new Phrase("This information is suggestive only. Any diagnosis and prescription should be the decision " +
                "and sole responsibility of the doctor using this material.", font);


        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, footer1, (document.right() - document.left()) / 2 + document.leftMargin(), document.bottom() - 3, 0);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, footer2, (document.right() - document.left()) / 2 + document.leftMargin(), document.bottom() - 10, 0);
    }
}
