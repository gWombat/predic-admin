package fr.gwombat.predicadmin.exception.upload;

public class DataMappingException extends UploadDataException {

    private static final String LABEL_CODE       = "data.upload.file.excel.invalid.data";

    private static final long   serialVersionUID = 1L;
    
    public DataMappingException(final String labelCode, final Object[] args) {
        super();
        this.setMessageCode(labelCode);
        this.setMessageArguments(args);
    }

    public DataMappingException(final String cellValue, final Throwable t) {
        super(t);
        this.setMessageCode(LABEL_CODE);
        this.setMessageArguments(new Object[] { cellValue });
    }

}
