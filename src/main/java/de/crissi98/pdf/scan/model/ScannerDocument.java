package de.crissi98.pdf.scan.model;

import de.crissi98.pdf.file.FileType;

public class ScannerDocument {

    private String fileName;
    private FileType fileType;

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public FileType getFileType() {
        return fileType;
    }

    public String getFileName() {
        return fileName;
    }
}
