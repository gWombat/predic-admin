package fr.gwombat.predicadmin.upload.excel;

public class ColumnMappingItem {

    private String          columnValue;
    private UploadablePublisherFields mappedToValue;

    public ColumnMappingItem() {
    }

    public ColumnMappingItem(String columnValue, UploadablePublisherFields mappedToValue) {
        super();
        this.columnValue = columnValue;
        this.mappedToValue = mappedToValue;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

    public UploadablePublisherFields getMappedToValue() {
        return mappedToValue;
    }

    public void setMappedToValue(UploadablePublisherFields mappedToValue) {
        this.mappedToValue = mappedToValue;
    }

    public int getColumnIndex() {
        return SheetColumnUtils.getValueOfColumn(columnValue);
    }

    @Override
    public String toString() {
        return columnValue + "(" + getColumnIndex() + ") --> " + mappedToValue;
    }

}
