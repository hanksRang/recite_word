package com.myplus.engl.pdfprocess;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;

import java.io.IOException;

public class ITextWatermarkRemover {
    
    public static void removeWatermarkWithIText(String inputPath, String outputPath) throws IOException {
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(inputPath), new PdfWriter(outputPath));
        
        int numberOfPages = pdfDoc.getNumberOfPages();
        for (int i = 1; i <= numberOfPages; i++) {
            PdfCanvas canvas = new PdfCanvas(pdfDoc.getPage(i).newContentStreamBefore(),
                    pdfDoc.getPage(i).getResources(), pdfDoc);
            
            // 覆盖水印区域
            canvas.saveState()
                  .setFillColor(com.itextpdf.kernel.colors.ColorConstants.WHITE)
                  .rectangle(100, 100, 400, 100)
                  .fill()
                  .restoreState();
        }
        
        pdfDoc.close();
    }

    public static void main(String[] args) throws IOException {

    }
}