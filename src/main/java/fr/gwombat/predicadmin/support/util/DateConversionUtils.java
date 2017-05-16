package fr.gwombat.predicadmin.support.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/*
 * http://stackoverflow.com/questions/21242110/convert-java-util-date-to-java-time-localdate
 */
public class DateConversionUtils {

    private DateConversionUtils() {

    }

    /**
     * Calls {@link #asLocalDate(Date, ZoneId)} with the system default time
     * zone.
     */
    public static LocalDate asLocalDate(java.util.Date date) {
        return asLocalDate(date, ZoneId.systemDefault());
    }

    /**
     * Creates {@link LocalDate} from {@code java.util.Date} or it's subclasses.
     * Null-safe.
     */
    public static LocalDate asLocalDate(java.util.Date date, ZoneId zone) {
        if (date == null)
            return null;

        if (date instanceof java.sql.Date)
            return ((java.sql.Date) date).toLocalDate();
        else
            return Instant.ofEpochMilli(date.getTime()).atZone(zone).toLocalDate();
    }

    /**
     * Calls {@link #asUtilDate(Object, ZoneId)} with the system default time
     * zone.
     */
    public static java.util.Date asDate(Object date) {
        return asDate(date, ZoneId.systemDefault());
    }

    /**
     * Creates a {@link java.util.Date} from various date objects. Is null-safe.
     * Currently supports:
     * <ul>
     * <li>{@link java.util.Date}
     * <li>{@link java.sql.Date}
     * <li>{@link java.sql.Timestamp}
     * <li>{@link java.time.LocalDate}
     * <li>{@link java.time.LocalDateTime}
     * <li>{@link java.time.ZonedDateTime}
     * <li>{@link java.time.Instant}
     * </ul>
     * 
     * @param zone
     *            Time zone, used only if the input object is LocalDate or
     *            LocalDateTime.
     * 
     * @return {@link java.util.Date} (exactly this class, not a subclass, such
     *         as java.sql.Date)
     */
    public static java.util.Date asDate(Object date, ZoneId zone) {
        if (date == null)
            return null;

        if (date instanceof java.util.Date)
            return (java.util.Date) date;
        if (date instanceof java.sql.Date || date instanceof java.sql.Timestamp)
            return (java.util.Date) date;
        if (date instanceof LocalDate)
            return java.util.Date.from(((LocalDate) date).atStartOfDay(zone).toInstant());

        throw new UnsupportedOperationException("Enable to convert " + date.getClass().getName() + " as java.util.Date");
    }

}
