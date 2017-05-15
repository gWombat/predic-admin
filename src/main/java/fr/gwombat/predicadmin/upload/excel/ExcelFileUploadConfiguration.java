package fr.gwombat.predicadmin.upload.excel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;

public class ExcelFileUploadConfiguration {

    private InputStream             inputStream;
    private String                  sheetName;
    private boolean                 useHeader;
    private List<ColumnMappingItem> mappings;
    private Map<Integer, String>    mappedFieldsByColumnIndex;

    public ExcelFileUploadConfiguration() {
        mappings = new ArrayList<>(0);
        mappedFieldsByColumnIndex = new HashedMap<>(0);
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
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
        for(ColumnMappingItem item : mappings)
            mappedFieldsByColumnIndex.put(item.getColumnIndex(), item.getMappedToValue());
    }

    public void addMappingItem(ColumnMappingItem item) {
        if (item != null){
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
    
    public String getEntityFieldByindex(int columnIndex){
        return mappedFieldsByColumnIndex.get(columnIndex);
    }

}
