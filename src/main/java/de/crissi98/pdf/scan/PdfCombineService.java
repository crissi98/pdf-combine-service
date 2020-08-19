package de.crissi98.pdf.scan;

import de.crissi98.pdf.file.FileType;
import de.crissi98.pdf.file.FileTypeException;
import de.crissi98.pdf.scan.model.CombineScannerDocument;
import de.crissi98.pdf.scan.model.PdfSizeException;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;

@ApplicationScoped
public class PdfCombineService {

    @Inject
    PdfCombineClient pdfCombineClient;

    public void combine(CombineScannerDocument combineDoc) throws IOException, FileTypeException, PdfSizeException {
        PDDocument frontSideDoc = null;
        PDDocument backSideDoc = null;
        PDDocument outputDoc = null;
        try {
            frontSideDoc = pdfCombineClient.openScanDoc(combineDoc.getFrontSideDocument());
            backSideDoc = pdfCombineClient.openScanDoc(combineDoc.getBackSideDocument());
            outputDoc = pdfCombineClient.combineDocuments(frontSideDoc, backSideDoc);
            this.saveFile(outputDoc, combineDoc);
        } finally {
            if (frontSideDoc != null) {
                frontSideDoc.close();
            }
            if (backSideDoc != null) {
                backSideDoc.close();
            }
            if (outputDoc != null) {
                outputDoc.close();
            }
        }

    }

    public void append(CombineScannerDocument combineDoc) throws IOException, FileTypeException {
        PDDocument frontSideDoc = null;
        PDDocument backSideDoc = null;
        PDDocument outputDoc = null;
        try {
            frontSideDoc = pdfCombineClient.openScanDoc(combineDoc.getFrontSideDocument());
            backSideDoc = pdfCombineClient.openScanDoc(combineDoc.getBackSideDocument());
            outputDoc = pdfCombineClient.appendDocuments(frontSideDoc, backSideDoc);
            this.saveFile(outputDoc, combineDoc);
        } finally {
            if (frontSideDoc != null) {
                frontSideDoc.close();
            }
            if (backSideDoc != null) {
                backSideDoc.close();
            }
            if (outputDoc != null) {
                outputDoc.close();
            }
        }
    }

    private void saveFile(PDDocument outputDoc, CombineScannerDocument combineDoc) throws IOException {
        String saveFilePath;
        if (!combineDoc.getOutputFileName().equals("default")) {
            saveFilePath = pdfCombineClient.getScanBasePath() + combineDoc.getOutputFileName()
                    + FileType.PDF.getSuffix();
        } else {
            saveFilePath = pdfCombineClient.getScanBasePath() + combineDoc.getFrontSideDocument().getFileName()
                    + "_" + combineDoc.getBackSideDocument().getFileName() + FileType.PDF.getSuffix();

        }
        pdfCombineClient.saveToFile(outputDoc, saveFilePath);
    }
}
