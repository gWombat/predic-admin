package fr.gwombat.predicadmin.web.transformer;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import fr.gwombat.predicadmin.model.MeetingAttendance;
import fr.gwombat.predicadmin.web.form.MeetingAttendanceForm;
import fr.gwombat.predicadmin.web.vo.MeetingAttendanceVO;
import fr.gwombat.predicadmin.web.vo.builder.MeetingAttendanceVoBuilder;

@Component
public class MeetingAttendanceTransformer extends AbstractEntityTransformer<MeetingAttendance, MeetingAttendanceForm, MeetingAttendanceVO> {

    private static final Logger logger             = LoggerFactory.getLogger(MeetingAttendanceTransformer.class);

    private static final String DATE_FORMAT_CODE   = "format.date";
    private static final String DEFAULT_DATE_VALUE = "N/A";

    private MessageSource       messageSource;

    @Autowired
    public MeetingAttendanceTransformer(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public MeetingAttendance toEntity(final MeetingAttendanceForm formObject, MeetingAttendance existingEntity) {
        if (formObject != null) {
            if (formObject.isAllFieldsNull())
                existingEntity = null;
            else {
                if (existingEntity == null)
                    existingEntity = new MeetingAttendance();

                existingEntity.setAttendance(formObject.getAttendance());
                existingEntity.setDate(existingEntity.getDate());
            }
        }

        return existingEntity;
    }

    @Override
    public MeetingAttendanceForm toFormObject(final MeetingAttendance entity) {
        if (entity != null) {
            final MeetingAttendanceForm attendanceForm = new MeetingAttendanceForm();
            attendanceForm.setAttendance(entity.getAttendance());
            attendanceForm.setDate(formatDate(entity.getDate()));
        }
        return null;
    }

    @Override
    public MeetingAttendanceVO toViewObject(final MeetingAttendance entity) {
        MeetingAttendanceVoBuilder builder = MeetingAttendanceVoBuilder.begin();
        if (entity != null)
            builder = builder.attendance(entity.getAttendance()).date(entity.getDate());

        return builder.build();
    }

    private String formatDate(LocalDate date) {
        return formatDateToString(date, DATE_FORMAT_CODE);
    }

    private String formatDateToString(LocalDate date, String format) {
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

}
