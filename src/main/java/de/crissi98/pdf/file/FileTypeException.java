package de.crissi98.pdf.file;

public class FileTypeException extends Exception {

    private FileType expectedType;
    private String actualPath;

    public FileTypeException(FileType expectedType, String actualPath) {
        this.expectedType = expectedType;
        this.actualPath = actualPath;
    }

    @Override
    public String getMessage() {
        return String.format("Wrong file Type. %s is not a %s-file", actualPath, expectedType.getSuffix());
    }
}
