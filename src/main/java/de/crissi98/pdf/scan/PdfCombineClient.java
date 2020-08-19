package de.crissi98.pdf.scan;

import de.crissi98.pdf.file.FileType;
import de.crissi98.pdf.file.FileTypeException;
import de.crissi98.pdf.scan.model.PdfSizeException;
import de.crissi98.pdf.scan.model.ScannerDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@ApplicationScoped
public class PdfCombineClient {

    @Inject
    @ConfigProperty(name = "scan.path.base")
    String scanBasePath;

    public PDDocument openScanDoc(ScannerDocument docToScan) throws IOException, InputMismatchException, FileTypeException {

        if (docToScan.getFileType() != FileType.PDF) {
            throw new InputMismatchException(docToScan.getFileType().getSuffix() + " is not matching " + FileType.PDF);
        }
        File scanFile = this.openScanFile(docToScan);
        if (!scanFile.getPath().endsWith(FileType.PDF.getSuffix())) {
            throw new FileTypeException(FileType.PDF, scanFile.getPath());
        }
        return PDDocument.load(scanFile);
    }

    public PDDocument combineDocuments(PDDocument frontSidePdf, PDDocument backSidePdf) throws PdfSizeException {
        int frontSideSize = frontSidePdf.getNumberOfPages();
        int backSideSize = backSidePdf.getNumberOfPages();

        if (backSideSize < frontSideSize - 1 || backSideSize > frontSideSize) {
            throw new PdfSizeException();
        }

        List<PDPage> frontPages = readPages(frontSidePdf);
        List<PDPage> backPages = readPages(backSidePdf);

        PDDocument outputPdf = new PDDocument();
        Iterator<PDPage> backPageIterator = backPages.iterator();

        for (PDPage frontPage : frontPages) {
            outputPdf.addPage(frontPage);
            if (backPageIterator.hasNext()) {
                outputPdf.addPage(backPageIterator.next());
            }
        }

        return outputPdf;
    }

    public PDDocument appendDocuments(PDDocument firstDoc, PDDocument secondDoc) {
        List<PDPage> firstPages = readPages(firstDoc);
        List<PDPage> secondPages = readPages(secondDoc);

        PDDocument outputPdf = new PDDocument();

        this.addPagesToPdf(firstPages, outputPdf);
        this.addPagesToPdf(secondPages, outputPdf);

        return outputPdf;
    }

    public void saveToFile(PDDocument pdfDocumentToSave, String filePath) throws IOException {
        File saveFile = new File(filePath);
        if (saveFile.exists()) {
            saveFile.delete();
        }
        pdfDocumentToSave.save(saveFile);
    }

    private File openScanFile(ScannerDocument docToScan) throws FileNotFoundException {
        String filePath = this.getFilePath(docToScan);
        File result = new File(filePath);
        if (result.exists() && result.isFile()) {
            return result;
        } else {
            throw new FileNotFoundException(filePath + " is not a valid file.");
        }
    }

    public String getFilePath(ScannerDocument docToScan) {
        return scanBasePath + docToScan.getFileName() + docToScan.getFileType().getSuffix();
    }

    public String getScanBasePath() {
        return scanBasePath;
    }

    private void addPagesToPdf(List<PDPage> pages, PDDocument doc) {
        pages.forEach(doc::addPage);
    }

    private List<PDPage> readPages(PDDocument documentToRead) {
        return IntStream.range(0, documentToRead.getNumberOfPages())
                .mapToObj(documentToRead::getPage)
                .collect(Collectors.toList());
    }
}
