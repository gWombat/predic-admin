package fr.gwombat.predicadmin.web.transformer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.gwombat.predicadmin.model.MonthAttendance;
import fr.gwombat.predicadmin.model.YearAttendance;
import fr.gwombat.predicadmin.web.vo.MeetingAttendanceVO;
import fr.gwombat.predicadmin.web.vo.MonthAttendanceVO;
import fr.gwombat.predicadmin.web.vo.YearAttendanceVO;
import fr.gwombat.predicadmin.web.vo.builder.YearAttendanceVoBuilder;

@Component
public class YearAttendanceTransformer implements ViewTransformer<YearAttendance, YearAttendanceVO> {

    private MonthAttendanceTransformer monthAttendanceTransformer;

    @Autowired
    public YearAttendanceTransformer(final MonthAttendanceTransformer monthAttendanceTransformer) {
        this.monthAttendanceTransformer = monthAttendanceTransformer;
    }

    @Override
    public YearAttendanceVO toViewObject(YearAttendance entity) {
        YearAttendanceVO yearAttendanceVo = null;
        if (entity != null) {
            YearAttendanceVoBuilder builder = YearAttendanceVoBuilder.begin().year(entity.getTheocraticYear().getYear().getValue());

            final List<MonthAttendance> attendances = entity.getAttendances();
            if (attendances != null) {
                for (MonthAttendance monthAttendance : attendances) {
                    final MonthAttendanceVO attendanceVo = monthAttendanceTransformer.toViewObject(monthAttendance);
                    builder = builder.addMonthAttendance(attendanceVo);
                }
            }

            final Integer averageAttendance = calculateAverageAttendance(builder.getAttendances());
            builder = builder.averageAttendance(averageAttendance);

            final MeetingAttendanceVO maxAttendance = evaluateMaxAttendance(builder.getAttendances());
            builder = builder.maxAttendance(maxAttendance);

            yearAttendanceVo = builder.build();
        }

        return yearAttendanceVo;
    }

    private static MeetingAttendanceVO evaluateMaxAttendance(final List<MonthAttendanceVO> attendances) {
        MeetingAttendanceVO maxAttendance = null;

        if (attendances != null && !attendances.isEmpty()) {
            final List<MeetingAttendanceVO> allAttendances = new ArrayList<>(0);
            for (MonthAttendanceVO currentAttendance : attendances)
                allAttendances.addAll(currentAttendance.getAttendances());

            if (!allAttendances.isEmpty()) {
                allAttendances.sort((MeetingAttendanceVO o1, MeetingAttendanceVO o2) -> o2.getAttendance() - o1.getAttendance());
                maxAttendance = allAttendances.get(0);
            }
            allAttendances.sort((MeetingAttendanceVO o1, MeetingAttendanceVO o2) -> o1.getDate().compareTo(o2.getDate()));
        }

        return maxAttendance;
    }

    private static Integer calculateAverageAttendance(final List<MonthAttendanceVO> attendances) {
        Integer result = null;
        int nbMonthsPassed = 0;
        if (attendances != null) {
            for (MonthAttendanceVO attendance : attendances){
                if(attendance != null) {
                    if (attendance.getPeriod().isBeforeNow())
                        nbMonthsPassed++;
                    if (result == null)
                        result = 0;
                    if(attendance.getAverageAttendance() != null)
                        result += attendance.getAverageAttendance();
                }
            }
            if(result != null)
                result = result / Math.max(nbMonthsPassed, 1);
        }
        return result;
    }

}
