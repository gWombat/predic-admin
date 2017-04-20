package fr.gwombat.predicadmin.web.transformer;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import fr.gwombat.predicadmin.model.MeetingAttendance;
import fr.gwombat.predicadmin.model.MonthAttendance;
import fr.gwombat.predicadmin.web.vo.MeetingAttendanceVO;
import fr.gwombat.predicadmin.web.vo.MonthAttendanceVO;
import fr.gwombat.predicadmin.web.vo.builder.MonthAttendanceVoBuilder;

@Component
public class MonthAttendanceTransformer implements ViewTransformer<MonthAttendance, MonthAttendanceVO> {

    private static final int             MAX_ATTENDANCE = 0;
    private static final int             MIN_ATTENDANCE = 1;

    private MeetingAttendanceTransformer meetingAttendanceTransformer;

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

            final MeetingAttendanceVO maxAttendance = getMaxOrMinAttendance(builder.getAttendances(), MAX_ATTENDANCE);
            builder = builder.maxAttendance(maxAttendance);

            final MeetingAttendanceVO minAttendance = getMaxOrMinAttendance(builder.getAttendances(), MIN_ATTENDANCE);
            builder = builder.minAttendance(minAttendance);

            attendanceVo = builder.build();

        }
        return attendanceVo;
    }

    private static MeetingAttendanceVO getMaxOrMinAttendance(final List<MeetingAttendanceVO> attendances, int maxOrMinAttendance) {
        if (CollectionUtils.isEmpty(attendances))
            return null;

        MeetingAttendanceVO result = null;
        Comparator<MeetingAttendanceVO> comparator = Comparator.comparing(MeetingAttendanceVO::getAttendance);
        if (maxOrMinAttendance == MAX_ATTENDANCE)
            comparator = comparator.reversed();

        attendances.sort(comparator);
        result = attendances.get(0);
        attendances.sort(Comparator.comparing(MeetingAttendanceVO::getDate));
        return result;
    }

    private static int calculateAverageAttendance(final List<MeetingAttendanceVO> attendances) {
        int result = 0;
        if (attendances != null) {
            for (MeetingAttendanceVO attendance : attendances) {
                if (attendance != null)
                    result += attendance.getAttendance();
            }

            result = result / Math.max(attendances.size(), 1);
        }
        return result;
    }

    @Autowired
    public void setMeetingAttendanceTransformer(MeetingAttendanceTransformer meetingAttendanceTransformer) {
        this.meetingAttendanceTransformer = meetingAttendanceTransformer;
    }

}
