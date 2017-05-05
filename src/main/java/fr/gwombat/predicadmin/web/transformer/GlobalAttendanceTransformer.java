package fr.gwombat.predicadmin.web.transformer;

import java.util.Comparator;
import java.util.List;

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

    public GlobalAttendanceVO toViewObject(List<YearAttendance> years) {
        GlobalAttendanceVO globalAttendance = null;

        if (!CollectionUtils.isEmpty(years)) {
            years.sort(Comparator.comparing(YearAttendance::getTheocraticYear).reversed());

            final GlobalAttendanceVoBuilder builder = GlobalAttendanceVoBuilder.create();

            for (YearAttendance year : years) {
                final YearAttendanceVO yearAttendanceVo = yearAttendanceTransformer.toViewObject(year);
                builder.addAttendance(yearAttendanceVo);
            }

            final int averageAttendance = calculateAverageAttendance(builder.getAttendances());
            final int nbReports = calculateNbReports(builder.getAttendances());

            builder.averageAttendance(averageAttendance).nbReports(nbReports);

            globalAttendance = builder.build();
        }

        return globalAttendance;
    }

    private int calculateAverageAttendance(final List<YearAttendanceVO> attendances) {
        int result = 0;
        if (CollectionUtils.isEmpty(attendances))
            return result;

        for (YearAttendanceVO yearAttendance : attendances)
            if (yearAttendance != null)
                result += yearAttendance.getAverageAttendance();
        result /= Math.max(1, attendances.size());
        return result;
    }

    private int calculateNbReports(final List<YearAttendanceVO> attendances) {
        int result = 0;
        if (!CollectionUtils.isEmpty(attendances))
            for (YearAttendanceVO yearAttendance : attendances)
                if (yearAttendance != null)
                    for (MonthAttendanceVO monthAttendance : yearAttendance.getAttendances())
                        if (monthAttendance != null)
                            result += monthAttendance.getAttendances().size();

        return result;
    }

    @Autowired
    public void setYearAttendanceTransformer(YearAttendanceTransformer yearAttendanceTransformer) {
        this.yearAttendanceTransformer = yearAttendanceTransformer;
    }

}
