package fr.gwombat.predicadmin.exception.upload;

public class InvalidFileFormatException extends UploadDataException {

    private static final long   serialVersionUID = 1L;
    private static final String LABEL_CODE       = "data.upload.file.excel.invalid.format";
    
    public InvalidFileFormatException(final Throwable t) {
        super(t);
        this.setMessageCode(LABEL_CODE);
    }

}
