package de.crissi98.pdf.file;

public enum FileType {

    PDF(".pdf");

    private final String suffix;

    FileType(String suffix) {
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }
}
