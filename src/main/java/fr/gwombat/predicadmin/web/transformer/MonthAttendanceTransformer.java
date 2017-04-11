package fr.gwombat.predicadmin.web.transformer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.gwombat.predicadmin.model.MeetingAttendance;
import fr.gwombat.predicadmin.model.MonthAttendance;
import fr.gwombat.predicadmin.web.vo.MeetingAttendanceVO;
import fr.gwombat.predicadmin.web.vo.MonthAttendanceVO;
import fr.gwombat.predicadmin.web.vo.builder.MonthAttendanceVoBuilder;

@Component
public class MonthAttendanceTransformer implements ViewTransformer<MonthAttendance, MonthAttendanceVO> {

    private MeetingAttendanceTransformer meetingAttendanceTransformer;

    @Autowired
    public MonthAttendanceTransformer(final MeetingAttendanceTransformer meetingAttendanceTransformer) {
        this.meetingAttendanceTransformer = meetingAttendanceTransformer;
    }

    @Override
    public MonthAttendanceVO toViewObject(MonthAttendance entity) {
        MonthAttendanceVO attendanceVo = null;
        if (entity != null) {
            MonthAttendanceVoBuilder builder = MonthAttendanceVoBuilder.begin().period(entity.getPeriod());

            final List<MeetingAttendance> originalAttendances = entity.getAttendances();
            if (originalAttendances != null) {
                for (MeetingAttendance originalAttendance : originalAttendances) {
                    final MeetingAttendanceVO meetingAttendanceVo = meetingAttendanceTransformer.toViewObject(originalAttendance);
                    builder = builder.addAttendance(meetingAttendanceVo);
                }
            }

            final Integer averageAttendance = calculateAverageAttendance(builder.getAttendances());
            builder = builder.averageAttendance(averageAttendance);

            final MeetingAttendanceVO maxAttendance = evaluateMaxAttendance(builder.getAttendances());
            builder = builder.maxAttendance(maxAttendance);

            attendanceVo = builder.build();

        }
        return attendanceVo;
    }

    private static MeetingAttendanceVO evaluateMaxAttendance(final List<MeetingAttendanceVO> attendances) {
        MeetingAttendanceVO maxAttendance = null;
        if (attendances != null && !attendances.isEmpty()) {
            attendances.sort((o1, o2) -> o2.getAttendance() - o1.getAttendance());
            maxAttendance = attendances.get(0);
            attendances.sort((o1, o2) -> o1.getDate().compareTo(o2.getDate()));
        }
        return maxAttendance;
    }

    private static Integer calculateAverageAttendance(final List<MeetingAttendanceVO> attendances) {
        Integer result = null;
        if (attendances != null) {
            for (MeetingAttendanceVO attendance : attendances){
                if(attendance != null) {
                    if (result == null)
                        result = 0;
                    result += attendance.getAttendance();
                }
            }

            if(result != null)
                result = result / Math.max(attendances.size(), 1);
        }
        return result;
    }

}
