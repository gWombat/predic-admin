package fr.gwombat.predicadmin.exception.upload;

public class SheetNotFoundException extends UploadDataException {

    private static final long   serialVersionUID = 1L;
    private static final String LABEL_CODE       = "data.upload.file.excel.sheet.not.found";

    private final String        sheetName;


    public SheetNotFoundException(String sheetName) {
        super();
        this.sheetName = sheetName;
        this.setMessageCode(LABEL_CODE);
        this.setMessageArguments(new Object[] { sheetName });
    }

    public String getSheetName() {
        return sheetName;
    }

}
