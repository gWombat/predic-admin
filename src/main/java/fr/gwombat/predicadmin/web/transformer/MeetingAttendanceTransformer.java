package fr.gwombat.predicadmin.web.transformer;

import fr.gwombat.predicadmin.model.MeetingAttendance;
import fr.gwombat.predicadmin.web.form.MeetingAttendanceForm;
import fr.gwombat.predicadmin.web.vo.MeetingAttendanceVO;
import fr.gwombat.predicadmin.web.vo.builder.MeetingAttendanceVoBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class MeetingAttendanceTransformer extends AbstractEntityTransformer<MeetingAttendance, MeetingAttendanceForm, MeetingAttendanceVO> {

    @Autowired
    public MeetingAttendanceTransformer(final MessageSource messageSource) {
        super(messageSource);
    }

    @Override
    public MeetingAttendance toEntity(final MeetingAttendanceForm formObject, MeetingAttendance existingEntity) {
        if(formObject != null) {
            if(formObject.isAllFieldsNull())
                existingEntity = null;
            else {
                if(existingEntity == null)
                    existingEntity = new MeetingAttendance();

                existingEntity.setAttendance(formObject.getAttendance());
                existingEntity.setDate(formatDate(formObject.getDate()));
            }
        }

        return existingEntity;
    }

    @Override
    public MeetingAttendanceForm toFormObject(final MeetingAttendance entity) {
        if(entity != null) {
            final MeetingAttendanceForm attendanceForm = new MeetingAttendanceForm();
            attendanceForm.setAttendance(entity.getAttendance());
            attendanceForm.setDate(super.formatDate(entity.getDate()));
            attendanceForm.setIdentifier(entity.getIdentifier());
        }
        return null;
    }

    @Override
    public MeetingAttendanceVO toViewObject(final MeetingAttendance entity) {
        MeetingAttendanceVoBuilder builder = MeetingAttendanceVoBuilder.begin();
        if(entity != null)
            builder = builder.attendance(entity.getAttendance()).date(entity.getDate());

        return builder.build();
    }

}
