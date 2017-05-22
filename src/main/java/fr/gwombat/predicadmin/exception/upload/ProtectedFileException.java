package fr.gwombat.predicadmin.exception.upload;

public class ProtectedFileException extends UploadDataException {

    private static final String LABEL_CODE       = "data.upload.file.encrypted";

    private static final long   serialVersionUID = 1L;

    public ProtectedFileException(Throwable throwable) {
        super(LABEL_CODE, throwable);
    }

}
