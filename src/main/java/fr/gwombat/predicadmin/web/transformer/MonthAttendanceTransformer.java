package fr.gwombat.predicadmin.web.transformer;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import fr.gwombat.predicadmin.model.MonthAttendance;
import fr.gwombat.predicadmin.model.entities.MeetingAttendance;
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
        final MonthAttendanceVoBuilder builder = MonthAttendanceVoBuilder.create()
                .period(entity.getPeriod());

        if (entity != null) {
            final List<MeetingAttendance> originalAttendances = entity.getAttendances();
            if (!CollectionUtils.isEmpty(originalAttendances)) {
                originalAttendances.forEach(attendance -> {
                    final MeetingAttendanceVO meetingAttendanceVo = meetingAttendanceTransformer.toViewObject(attendance);
                    builder.addAttendance(meetingAttendanceVo);
                });
            }

            builder.averageAttendance(entity.getAverageAttendance());

            final MeetingAttendanceVO maxAttendance = getMaxOrMinAttendance(builder.getAttendances(), MAX_ATTENDANCE);
            builder.maxAttendance(maxAttendance);

            final MeetingAttendanceVO minAttendance = getMaxOrMinAttendance(builder.getAttendances(), MIN_ATTENDANCE);
            builder.minAttendance(minAttendance);
        }
        attendanceVo = builder.build();
        return attendanceVo;
    }

    private static MeetingAttendanceVO getMaxOrMinAttendance(final List<MeetingAttendanceVO> attendances, int maxOrMinAttendance) {
        if (CollectionUtils.isEmpty(attendances))
            return null;

        Comparator<MeetingAttendanceVO> comparator = Comparator.comparing(MeetingAttendanceVO::getAttendance);
        if (maxOrMinAttendance == MAX_ATTENDANCE)
            comparator = comparator.reversed();

        Optional<MeetingAttendanceVO> maxMinAttendance = attendances.stream()
                .sorted(comparator)
                .findFirst();

        return maxMinAttendance.orElse(null);
    }

    @Autowired
    public void setMeetingAttendanceTransformer(MeetingAttendanceTransformer meetingAttendanceTransformer) {
        this.meetingAttendanceTransformer = meetingAttendanceTransformer;
    }

}
