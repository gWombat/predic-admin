package fr.gwombat.predicadmin.web.transformer;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import fr.gwombat.predicadmin.model.YearAttendance;
import fr.gwombat.predicadmin.web.vo.GlobalAttendanceVO;
import fr.gwombat.predicadmin.web.vo.MonthAttendanceVO;
import fr.gwombat.predicadmin.web.vo.YearAttendanceVO;
import fr.gwombat.predicadmin.web.vo.builder.GlobalAttendanceVoBuilder;

@Component
public class GlobalAttendanceTransformer implements ViewTransformer<List<YearAttendance>, GlobalAttendanceVO> {

    private YearAttendanceTransformer yearAttendanceTransformer;

    public GlobalAttendanceVO toViewObject(final List<YearAttendance> years) {
        GlobalAttendanceVO globalAttendance = null;

        if (!CollectionUtils.isEmpty(years)) {
            years.sort(Comparator.comparing(YearAttendance::getTheocraticYear)
                    .reversed());

            final List<YearAttendanceVO> yearsVo = years.stream()
                    .map(year -> yearAttendanceTransformer.toViewObject(year))
                    .collect(Collectors.toList());

            final GlobalAttendanceVoBuilder builder = GlobalAttendanceVoBuilder.create()
                    .attendances(yearsVo);

            final int averageAttendance = calculateAverageAttendance(builder.getAttendances());
            final int nbReports = calculateNbReports(builder.getAttendances());

            builder.averageAttendance(averageAttendance)
                    .nbReports(nbReports);

            globalAttendance = builder.build();
        }

        return globalAttendance;
    }

    private static int calculateAverageAttendance(final List<YearAttendanceVO> attendances) {
        int result = 0;
        if (CollectionUtils.isEmpty(attendances))
            return result;

        final OptionalDouble averageAttendance = attendances.stream()
                .filter(Objects::nonNull)
                .mapToInt(attendance -> attendance.getAverageAttendance())
                .average();

        if (averageAttendance.isPresent())
            result = (int) averageAttendance.getAsDouble();

        return result;
    }

    private static int calculateNbReports(final List<YearAttendanceVO> attendances) {
        int result = 0;

        if (!CollectionUtils.isEmpty(attendances)) {
            final Stream<MonthAttendanceVO> allMonthAttendances = attendances.stream()
                    .filter(Objects::nonNull)
                    .map(yearAttendances -> yearAttendances.getAttendances())
                    .flatMap(Arrays::stream)
                    .filter(Objects::nonNull);

            result = allMonthAttendances.map(monthAttendances -> monthAttendances.getAttendances())
                    .flatMapToInt(monthMeetings -> IntStream.of(monthMeetings.size()))
                    .sum();
        }

        return result;
    }

    @Autowired
    public void setYearAttendanceTransformer(YearAttendanceTransformer yearAttendanceTransformer) {
        this.yearAttendanceTransformer = yearAttendanceTransformer;
    }

}
