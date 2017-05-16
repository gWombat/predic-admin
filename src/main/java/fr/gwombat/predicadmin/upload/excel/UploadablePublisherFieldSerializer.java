package fr.gwombat.predicadmin.upload.excel;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

@Component
public class UploadablePublisherFieldSerializer extends StdSerializer<UploadablePublisherFields> {

    private MessageSource messageSource;
    
    public UploadablePublisherFieldSerializer() {
        super(UploadablePublisherFields.class);
    }

    protected UploadablePublisherFieldSerializer(Class<UploadablePublisherFields> t) {
        super(t);
    }

    private static final long serialVersionUID = 1L;

    @Override
    public void serialize(UploadablePublisherFields value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeFieldName("value");
        gen.writeString(value.name());
        gen.writeFieldName("label");
        gen.writeString(resolveTextFromLabel(value.getLabel()));
        gen.writeEndObject();
    }
    
    private String resolveTextFromLabel(final String label){
        return messageSource.getMessage(label, null, LocaleContextHolder.getLocale());
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

}
