package fr.gwombat.predicadmin.web.transformer;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.Assert;

/**
 * Transformer base class used to switch between an entity, his form
 * representation and his view representation
 *
 * @param <T>
 *            The Entity
 * @param <U>
 *            The Form object corresponding to the entity
 * @param <V>
 *            The View object corresponding to the entity
 * @author gWombat
 */
abstract class AbstractEntityTransformer<T, U, V> implements ViewTransformer<T, V>, FormTransformer<T, U> {

    private static final Logger logger             = LoggerFactory.getLogger(AbstractEntityTransformer.class);

    private static final String DATE_FORMAT_CODE   = "format.date";
    private static final String DEFAULT_DATE_VALUE = "N/A";

    private final MessageSource messageSource;

    public AbstractEntityTransformer(MessageSource messageSource) {
        Assert.notNull(messageSource, "The messageSource must not be null");
        this.messageSource = messageSource;
    }

    protected String formatDate(final LocalDate date) {
        return formatDateToString(date, DATE_FORMAT_CODE);
    }

    protected String formatDateToString(final LocalDate date, final String formatCode) {
        String result = DEFAULT_DATE_VALUE;
        if (date != null) {
            final String dateFormat = messageSource.getMessage(formatCode, null, LocaleContextHolder.getLocale());
            final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
            try {
                result = dateTimeFormatter.format(date);
            } catch (DateTimeException e) {
                logger.warn(e.getMessage());
            }
        }
        return result;
    }

    protected LocalDate formatDate(String parsedDate) {
        LocalDate date = null;
        if (!StringUtils.isBlank(parsedDate)) {
            final String dateFormat = messageSource.getMessage(DATE_FORMAT_CODE, null, LocaleContextHolder.getLocale());
            final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
            try {
                date = LocalDate.parse(parsedDate, dateTimeFormatter);
            } catch (DateTimeException e) {
                logger.warn(e.getMessage());
            }
        }
        return date;
    }

}
