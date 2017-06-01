package fr.gwombat.predicadmin.web.vuejs;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import fr.gwombat.predicadmin.upload.excel.SheetColumnUtils;
import fr.gwombat.predicadmin.upload.excel.UploadablePublisherFields;

public class ImportPublishersModelData {

    private List<String>                                optionColumns;
    private List<OptionItem<UploadablePublisherFields>> optionFields;

    public ImportPublishersModelData(final MessageSource messageSource) {
        optionColumns = SheetColumnUtils.getAllColumns();

        optionFields = Arrays.stream(UploadablePublisherFields.values())
                .map(field -> {
                    final String textLabel = messageSource.getMessage(field.getLabel(), null, LocaleContextHolder.getLocale());
                    return new OptionItem<UploadablePublisherFields>(textLabel, field);
                })
                .collect(Collectors.toList());
    }

    public List<String> getOptionColumns() {
        return optionColumns;
    }

    public List<OptionItem<UploadablePublisherFields>> getOptionFields() {
        return optionFields;
    }

}
