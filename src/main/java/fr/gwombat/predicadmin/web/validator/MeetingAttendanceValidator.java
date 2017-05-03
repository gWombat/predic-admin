package fr.gwombat.predicadmin.web.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import fr.gwombat.predicadmin.web.form.MeetingAttendanceForm;

public class MeetingAttendanceValidator implements Validator {

    private static final String FORMAT_DATE = "format.date";

    private final MessageSource messageSource;

    public MeetingAttendanceValidator(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return MeetingAttendanceForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final MeetingAttendanceForm meetingAttendance = (MeetingAttendanceForm) target;

        final Locale locale = LocaleContextHolder.getLocale();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(messageSource.getMessage(FORMAT_DATE, null, locale));

        final LocalDate date = LocalDate.parse(meetingAttendance.getDate(), formatter);
        if (date != null) {
            if (date.isAfter(LocalDate.now()))
                errors.rejectValue("date", "validation.attendance.date");
        }

        final Integer attendance = meetingAttendance.getAttendance();
        if (attendance == null)
            errors.rejectValue("attendance", "validation.attendance.count.null");
        else if (attendance < 0)
            errors.rejectValue("attendance", "validation.attendance.count");
    }

}
