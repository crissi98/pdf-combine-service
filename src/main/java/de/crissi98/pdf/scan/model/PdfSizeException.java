package de.crissi98.pdf.scan.model;

public class PdfSizeException extends Exception {

    public PdfSizeException() {
    }

    @Override
    public String getMessage() {
        return "Document sizes do not match!";
    }
}
