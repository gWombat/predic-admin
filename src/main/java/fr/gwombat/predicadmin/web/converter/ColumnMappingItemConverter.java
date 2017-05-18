package fr.gwombat.predicadmin.web.converter;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.convert.converter.Converter;

import fr.gwombat.predicadmin.upload.excel.ColumnMappingItem;
import fr.gwombat.predicadmin.upload.excel.UploadablePublisherFields;

public class ColumnMappingItemConverter implements Converter<String, ColumnMappingItem> {

    private static final String SEPARATOR = ":";

    @Override
    public ColumnMappingItem convert(String source) {
        if (source == null)
            return null;

        final String[] elements = source.split(SEPARATOR);
        if (ArrayUtils.isEmpty(elements) || elements.length < 2)
            throw new IllegalArgumentException("The string" + source + " cannot be convert to " + ColumnMappingItem.class.getName());

        final ColumnMappingItem item = new ColumnMappingItem();
        item.setColumnValue(elements[0]);
        item.setMappedToValue(UploadablePublisherFields.valueOf(elements[1]));

        return item;
    }

}
