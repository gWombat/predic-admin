package fr.gwombat.predicadmin.web.transformer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import fr.gwombat.predicadmin.model.MonthAttendance;
import fr.gwombat.predicadmin.model.YearAttendance;
import fr.gwombat.predicadmin.web.vo.MeetingAttendanceVO;
import fr.gwombat.predicadmin.web.vo.MonthAttendanceVO;
import fr.gwombat.predicadmin.web.vo.YearAttendanceVO;
import fr.gwombat.predicadmin.web.vo.builder.YearAttendanceVoBuilder;

@Component
public class YearAttendanceTransformer implements ViewTransformer<YearAttendance, YearAttendanceVO> {

    private static final int           MAX = 0;
    private static final int           MIN = 1;

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

            final MeetingAttendanceVO maxAttendance = getMaxOrMinAttendance(builder.getAttendances(), MAX);
            builder = builder.maxAttendance(maxAttendance);

            final MeetingAttendanceVO minAttendance = getMaxOrMinAttendance(builder.getAttendances(), MIN);
            builder = builder.minAttendance(minAttendance);
            
            final MeetingAttendanceVO memorial = getMemorial(builder.getAttendances());
            builder = builder.memorial(memorial);

            final MonthAttendanceVO maxAverage = getMaxOrMinAverage(builder.getAttendances(), MAX);
            builder = builder.maxAverage(maxAverage);

            final MonthAttendanceVO minAverage = getMaxOrMinAverage(builder.getAttendances(), MIN);
            builder = builder.minAverage(minAverage);

            yearAttendanceVo = builder.build();
        }

        return yearAttendanceVo;
    }
    
    private static MeetingAttendanceVO getMemorial(final List<MonthAttendanceVO> attendances){
        if (CollectionUtils.isEmpty(attendances))
            return null;

        final List<MeetingAttendanceVO> allAttendances = new ArrayList<>(0);
        for (MonthAttendanceVO currentAttendance : attendances)
            allAttendances.addAll(currentAttendance.getAttendances());
        
        final List<MeetingAttendanceVO> memorailAttendances = allAttendances.stream().filter(MeetingAttendanceVO::isMemorial).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(memorailAttendances))
            return memorailAttendances.get(0);
        
        return null;
    }

    private static MeetingAttendanceVO getMaxOrMinAttendance(final List<MonthAttendanceVO> attendances, int maxOrMinAttendance) {
        if (CollectionUtils.isEmpty(attendances))
            return null;

        MeetingAttendanceVO result = null;

        final List<MeetingAttendanceVO> allAttendances = new ArrayList<>(0);
        for (MonthAttendanceVO currentAttendance : attendances)
            allAttendances.addAll(currentAttendance.getAttendances());

        Comparator<MeetingAttendanceVO> comparator = Comparator.comparing(MeetingAttendanceVO::getAttendance);
        if (maxOrMinAttendance == MAX)
            comparator = comparator.reversed();

        allAttendances.sort(comparator);
        result = allAttendances.get(0);
        allAttendances.sort(Comparator.comparing(MeetingAttendanceVO::getDate));
        return result;
    }

    private static MonthAttendanceVO getMaxOrMinAverage(final List<MonthAttendanceVO> attendances, int maxOrMinAttendance) {
        if (CollectionUtils.isEmpty(attendances))
            return null;

        MonthAttendanceVO result = null;

        Comparator<MonthAttendanceVO> comparator = Comparator.comparing(MonthAttendanceVO::getAverageAttendance);
        if (maxOrMinAttendance == MAX)
            comparator = comparator.reversed();

        attendances.sort(comparator);
        result = attendances.get(0);
        attendances.sort(Comparator.comparing(MonthAttendanceVO::getPeriod));
        return result;
    }

    private static Integer calculateAverageAttendance(final List<MonthAttendanceVO> attendances) {
        Integer result = null;
        int nbMonthsPassed = 0;
        if (attendances != null) {
            for (MonthAttendanceVO attendance : attendances) {
                if (attendance != null) {
                    if (attendance.getPeriod().isBeforeNow())
                        nbMonthsPassed++;
                    if (result == null)
                        result = 0;
                    if (attendance.getAverageAttendance() != null)
                        result += attendance.getAverageAttendance();
                }
            }
            if (result != null)
                result = result / Math.max(nbMonthsPassed, 1);
        }
        return result;
    }

}
