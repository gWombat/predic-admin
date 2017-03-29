package fr.gwombat.predicadmin.web.transformer;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import fr.gwombat.predicadmin.model.Publisher;
import fr.gwombat.predicadmin.web.form.PublisherForm;
import fr.gwombat.predicadmin.web.vo.AddressVO;
import fr.gwombat.predicadmin.web.vo.ContactDetailVO;
import fr.gwombat.predicadmin.web.vo.PublisherVO;
import fr.gwombat.predicadmin.web.vo.builder.PublisherVoBuilder;

@Component
public class PublisherTransformer extends AbstractEntityTransformer<Publisher, PublisherForm, PublisherVO> {

    private static final Logger      logger                 = LoggerFactory.getLogger(PublisherTransformer.class);

    private static final String      DATE_FORMAT_CODE       = "format.date";
    private static final String      DATE_FORMAT_LARGE_CODE = "format.date.l";
    private static final String      DEFAULT_DATE_VALUE     = "N/A";

    private MessageSource            messageSource;
    private AddressTransformer       addressTransformer;
    private ContactDetailTransformer contactDetailTransformer;

    @Autowired
    public PublisherTransformer(final MessageSource messageSource) {
        this.messageSource = messageSource;
        this.addressTransformer = new AddressTransformer();
        this.contactDetailTransformer = new ContactDetailTransformer();
    }

    @Override
    public Publisher toEntity(PublisherForm publisherForm, Publisher publisher) {
        if (publisher == null)
            publisher = new Publisher();

        if (publisherForm != null) {
            publisher.setName(StringUtils.upperCase(publisherForm.getName()));
            publisher.setFirstName(WordUtils.capitalizeFully(publisherForm.getFirstName()));
            publisher.setGender(publisherForm.getGender());

            publisher.setBaptismDate(formatDate(publisherForm.getBaptismDate()));
            publisher.setBirthDate(formatDate(publisherForm.getBirthDate()));

            publisher.setAddress(addressTransformer.toEntity(publisherForm.getAddress(), publisher.getAddress()));
            publisher.setContactDetail(contactDetailTransformer.toEntity(publisherForm.getContactDetail(), publisher.getContactDetail()));
        }

        return publisher;
    }

    @Override
    public PublisherForm toFormObject(Publisher publisher) {
        if (publisher != null) {
            final PublisherForm publisherForm = new PublisherForm();

            publisherForm.setBaptismDate(formatDate(publisher.getBaptismDate()));
            publisherForm.setBirthDate(formatDate(publisher.getBirthDate()));

            publisherForm.setFirstName(publisher.getFirstName());
            publisherForm.setName(publisher.getName());
            publisherForm.setGender(publisher.getGender());
            publisherForm.setIdentifier(publisher.getIdentifier());
            publisherForm.setFullName(publisher.getFullName());

            publisherForm.setAddress(addressTransformer.toFormObject(publisher.getAddress()));
            publisherForm.setContactDetail(contactDetailTransformer.toFormObject(publisher.getContactDetail()));

            return publisherForm;
        }
        return null;
    }

    @Override
    public PublisherVO toViewObject(Publisher publisher) {
        if (publisher != null) {
            final AddressVO addressVo = addressTransformer.toViewObject(publisher.getAddress());
            final ContactDetailVO contactDetailVo = contactDetailTransformer.toViewObject(publisher.getContactDetail());
            
            final PublisherVoBuilder builder = PublisherVoBuilder.begin(contactDetailVo, addressVo)
                    .fullName(publisher.getFullName())
                    .identifier(publisher.getIdentifier())
                    .birthDate(formatDateLarge(publisher.getBirthDate()))
                    .name(publisher.getName())
                    .firstName(publisher.getFirstName())
                    .baptismDate(formatDateLarge(publisher.getBaptismDate()));
            
            return builder.build();
        }
        return null;
    }
    
    private String formatDateLarge(LocalDate date) {
        return formatDateToString(date, DATE_FORMAT_LARGE_CODE);
    }

    private String formatDate(LocalDate date) {
        return formatDateToString(date, DATE_FORMAT_CODE);
    }
    
    private String formatDateToString(LocalDate date, String format){
        String result = DEFAULT_DATE_VALUE;
        if (date != null) {
            final String dateFormat = messageSource.getMessage(format, null, LocaleContextHolder.getLocale());
            final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
            try {
                result = dateTimeFormatter.format(date);
            } catch (DateTimeException e) {
                logger.warn("", e);
            }
        }
        return result;
    }

    private LocalDate formatDate(String parsedDate) {
        LocalDate date = null;
        if (!StringUtils.isBlank(parsedDate)) {
            final String dateFormat = messageSource.getMessage(DATE_FORMAT_CODE, null, LocaleContextHolder.getLocale());
            final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
            try {
                date = LocalDate.parse(parsedDate, dateTimeFormatter);
            } catch (DateTimeException e) {
                logger.warn("", e);
            }
        }
        return date;
    }

}
