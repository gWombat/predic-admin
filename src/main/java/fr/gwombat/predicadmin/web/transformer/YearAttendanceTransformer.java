package fr.gwombat.predicadmin.web.transformer;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.ArrayUtils;
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
            final YearAttendanceVoBuilder builder = YearAttendanceVoBuilder.create()
                    .year(entity.getTheocraticYear()
                            .getYear()
                            .getValue());

            final List<MonthAttendance> attendances = entity.getAttendances();
            if (attendances != null) {
                attendances.forEach(monthAttendance -> {
                    final MonthAttendanceVO attendanceVo = monthAttendanceTransformer.toViewObject(monthAttendance);
                    builder.addMonthAttendance(attendanceVo);
                });
            }

            builder.averageAttendance(entity.getAverageAttendance());
            builder.averageAttendanceVariation(entity.getAverageAttendanceVariation());

            final MeetingAttendanceVO maxAttendance = getMaxOrMinAttendance(builder.getAttendances(), MAX);
            builder.maxAttendance(maxAttendance);

            final MeetingAttendanceVO minAttendance = getMaxOrMinAttendance(builder.getAttendances(), MIN);
            builder.minAttendance(minAttendance);

            final MeetingAttendanceVO memorial = getMemorial(builder.getAttendances());
            builder.memorial(memorial);

            final MonthAttendanceVO maxAverage = getMaxOrMinAverage(builder.getAttendances(), MAX);
            builder.maxAverage(maxAverage);

            final MonthAttendanceVO minAverage = getMaxOrMinAverage(builder.getAttendances(), MIN);
            builder.minAverage(minAverage);

            yearAttendanceVo = builder.build();
        }

        return yearAttendanceVo;
    }

    private static MeetingAttendanceVO getMemorial(final MonthAttendanceVO[] attendances) {
        if (ArrayUtils.isEmpty(attendances))
            return null;

        final Optional<MeetingAttendanceVO> memorialAttendance = Arrays.stream(attendances)
                .filter(Objects::nonNull)
                .map(monthAttendances -> monthAttendances.getAttendances())
                .flatMap(List::stream)
                .filter(MeetingAttendanceVO::isMemorial)
                .findFirst();

        return memorialAttendance.orElse(null);
    }

    private static MeetingAttendanceVO getMaxOrMinAttendance(final MonthAttendanceVO[] attendances, int maxOrMinAttendance) {
        if (ArrayUtils.isEmpty(attendances))
            return null;

        Comparator<MeetingAttendanceVO> comparator = Comparator.comparing(MeetingAttendanceVO::getAttendance);
        if (maxOrMinAttendance == MAX)
            comparator = comparator.reversed();

        final Optional<MeetingAttendanceVO> maxMinAttendance = Arrays.stream(attendances)
                .filter(Objects::nonNull)
                .map(monthAttendances -> monthAttendances.getAttendances())
                .flatMap(List::stream)
                .sorted(comparator)
                .findFirst();

        return maxMinAttendance.orElse(null);
    }

    private static MonthAttendanceVO getMaxOrMinAverage(final MonthAttendanceVO[] attendances, int maxOrMinAttendance) {
        if (ArrayUtils.isEmpty(attendances))
            return null;

        Comparator<MonthAttendanceVO> comparator = Comparator.comparing(MonthAttendanceVO::getAverageAttendance);
        if (maxOrMinAttendance == MAX)
            comparator = comparator.reversed();

        final Optional<MonthAttendanceVO> resultAttendance = Arrays.asList(attendances)
                .stream()
                .filter(Objects::nonNull)
                .sorted(comparator)
                .findFirst();

        return resultAttendance.orElse(null);
    }

}
