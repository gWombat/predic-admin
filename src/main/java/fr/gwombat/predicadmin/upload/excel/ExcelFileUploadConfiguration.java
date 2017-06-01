package fr.gwombat.predicadmin.upload.excel;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ExcelFileUploadConfiguration {

    private MultipartFile           file;
    private String                  sheetName;
    private boolean                 useHeader;
    private boolean                 useActiveSheet;
    private List<ColumnMappingItem> mappings;

    public ExcelFileUploadConfiguration() {
        mappings = new ArrayList<>(0);
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public List<ColumnMappingItem> getMappings() {
        return mappings;
    }

    public void setMappings(List<ColumnMappingItem> mappings) {
        this.mappings = mappings;
    }

    public void addMappingItem(ColumnMappingItem item) {
        if (item != null)
            mappings.add(item);
    }

    public boolean isUseHeader() {
        return useHeader;
    }

    public void setUseHeader(boolean useHeader) {
        this.useHeader = useHeader;
    }

    public UploadablePublisherFields getEntityFieldForindex(int columnIndex) {
        return mappings.stream()
                .filter(item -> columnIndex == item.getColumnIndex())
                .map(ColumnMappingItem::getMappedToValue)
                .findFirst()
                .get();
    }

    public boolean isUseActiveSheet() {
        return useActiveSheet;
    }

    public void setUseActiveSheet(boolean useActiveSheet) {
        this.useActiveSheet = useActiveSheet;
    }

}
