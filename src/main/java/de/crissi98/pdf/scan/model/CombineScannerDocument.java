package de.crissi98.pdf.scan.model;

public class CombineScannerDocument {

    private ScannerDocument frontSideDocument;
    private ScannerDocument backSideDocument;
    private String outputFileName;

    public void setBackSideDocument(ScannerDocument backSideDocument) {
        this.backSideDocument = backSideDocument;
    }

    public void setFrontSideDocument(ScannerDocument frontSideDocument) {
        this.frontSideDocument = frontSideDocument;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public ScannerDocument getBackSideDocument() {
        return backSideDocument;
    }

    public ScannerDocument getFrontSideDocument() {
        return frontSideDocument;
    }

    public String getOutputFileName() {
        return outputFileName;
    }


}
