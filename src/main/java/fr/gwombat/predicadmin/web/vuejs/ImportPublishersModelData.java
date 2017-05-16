package fr.gwombat.predicadmin.web.vuejs;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import fr.gwombat.predicadmin.upload.excel.SheetColumnUtils;
import fr.gwombat.predicadmin.upload.excel.UploadablePublisherFields;

public class ImportPublishersModelData {
    
    private static final Logger logger = LoggerFactory.getLogger(ImportPublishersModelData.class); 

    private List<String>                                optionColumns;
    private List<OptionItem<UploadablePublisherFields>> optionFields;

    public ImportPublishersModelData(final MessageSource messageSource) {
        optionColumns = SheetColumnUtils.getAllColumns();
        
        optionFields = new ArrayList<>(UploadablePublisherFields.values().length);
        for(UploadablePublisherFields field : UploadablePublisherFields.values()){
            String textLabel = messageSource.getMessage(field.getLabel(), null, LocaleContextHolder.getLocale());
            final OptionItem<UploadablePublisherFields> item = new OptionItem<UploadablePublisherFields>(textLabel, field);
            optionFields.add(item);
            logger.debug(item.toString());
        }
    }

    public List<String> getOptionColumns() {
        return optionColumns;
    }

    public List<OptionItem<UploadablePublisherFields>> getOptionFields() {
        return optionFields;
    }

}
