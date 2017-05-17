package fr.gwombat.predicadmin.upload.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public class ExcelFileUploadConfiguration {

    private MultipartFile                 file;
    private String                        sheetName;
    private boolean                       useHeader;
    private boolean                       useActiveSheet;
    private List<ColumnMappingItem>       mappings;
    private Map<Integer, UploadableField> mappedFieldsByColumnIndex;

    public ExcelFileUploadConfiguration() {
        mappings = new ArrayList<>(0);
        mappedFieldsByColumnIndex = new HashMap<>(0);
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
        for (ColumnMappingItem item : mappings)
            mappedFieldsByColumnIndex.put(item.getColumnIndex(), item.getMappedToValue());
    }

    public void addMappingItem(ColumnMappingItem item) {
        if (item != null) {
            mappings.add(item);
            mappedFieldsByColumnIndex.put(item.getColumnIndex(), item.getMappedToValue());
        }
    }

    public boolean isUseHeader() {
        return useHeader;
    }

    public void setUseHeader(boolean useHeader) {
        this.useHeader = useHeader;
    }

    public UploadablePublisherFields getEntityFieldForindex(int columnIndex) {
        return (UploadablePublisherFields) mappedFieldsByColumnIndex.get(columnIndex);
    }

    public boolean isUseActiveSheet() {
        return useActiveSheet;
    }

    public void setUseActiveSheet(boolean useActiveSheet) {
        this.useActiveSheet = useActiveSheet;
    }

}
