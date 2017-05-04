package fr.gwombat.predicadmin.service.impl;

import fr.gwombat.predicadmin.model.MonthAttendance;
import fr.gwombat.predicadmin.model.TheocraticYear;
import fr.gwombat.predicadmin.model.YearAttendance;
import fr.gwombat.predicadmin.model.entities.Congregation;
import fr.gwombat.predicadmin.service.MonthAttendanceService;
import fr.gwombat.predicadmin.service.YearAttendanceService;
import fr.gwombat.predicadmin.support.util.TheocraticYearUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class YearAttendanceServiceImpl implements YearAttendanceService {

    private MonthAttendanceService monthAttendanceService;

    @Override
    public YearAttendance getAttendanceForYear(final Congregation congregation, final TheocraticYear year) {
        if(year != null) {
            final TheocraticYear previousYear = TheocraticYearUtils.getPreviousYear(year);
            final List<MonthAttendance> attendancesByMonth = monthAttendanceService.getAttendancesBetween(congregation, previousYear
                    .getStart(), year.getEnd());
            if(attendancesByMonth != null)
                attendancesByMonth.sort(Comparator.comparing(MonthAttendance::getPeriod));

            final List<YearAttendance> resultYears = buildYearsAttendances(attendancesByMonth);
            if(!CollectionUtils.isEmpty(resultYears)) {
                resultYears.sort(Comparator.comparing(YearAttendance::getTheocraticYear).reversed());
                return resultYears.get(0);
            } else
                return new YearAttendance(year, null);
        }

        return null;
    }

    @Override
    public List<YearAttendance> getAttendancesForCongregation(Congregation congregation) {
        final List<MonthAttendance> attendancesByMonth = monthAttendanceService.getAttendances(congregation);
        if(attendancesByMonth != null)
            attendancesByMonth.sort(Comparator.comparing(MonthAttendance::getPeriod));

        final List<YearAttendance> resultYears = buildYearsAttendances(attendancesByMonth);
        if(!CollectionUtils.isEmpty(resultYears))
            resultYears.sort(Comparator.comparing(YearAttendance::getTheocraticYear));

        return resultYears;
    }

    private static List<YearAttendance> buildYearsAttendances(final List<MonthAttendance> monthsAttendances) {
        if(!CollectionUtils.isEmpty(monthsAttendances)) {
            final Map<TheocraticYear, YearAttendance> mapYearsAttendances = new HashMap<>(0);
            for(MonthAttendance monthAttendance : monthsAttendances) {
                if(monthAttendance != null) {
                    final TheocraticYear year = new TheocraticYear(monthAttendance.getPeriod());
                    YearAttendance yearAttendance = mapYearsAttendances.get(year);
                    if(yearAttendance == null) {
                        final List<MonthAttendance> attendances = new ArrayList<>(1);
                        attendances.add(monthAttendance);
                        yearAttendance = new YearAttendance(year, attendances);
                        mapYearsAttendances.put(year, yearAttendance);
                    } else
                        yearAttendance.addAttendance(monthAttendance);
                }
            }
            final List<YearAttendance> attendances = mapYearsAttendances.entrySet()
                                                                        .stream()
                                                                        .map(Map.Entry::getValue)
                                                                        .collect(Collectors.toList());
            calculateVariation(attendances);

            return attendances;
        }
        return null;
    }

    private static void calculateVariation(final List<YearAttendance> attendances) {
        if(!CollectionUtils.isEmpty(attendances)) {
            attendances.sort(Comparator.comparing(YearAttendance::getTheocraticYear));
            for(int i = 1; i < attendances.size(); i++) {
                final YearAttendance yearAttendance = attendances.get(i);
                final YearAttendance previousYearAttendance = attendances.get(i - 1);
                final Double variation = getAverageAttendanceVariation(previousYearAttendance, yearAttendance);
                yearAttendance.setAverageAttendanceVariation(variation);
            }
        }
    }

    private static Double getAverageAttendanceVariation(final YearAttendance oldestYearAttendance, final YearAttendance newestYearAttendance) {
        Double variation = null;

        if(oldestYearAttendance != null && newestYearAttendance != null) {
            double va = newestYearAttendance.getAverageAttendance();
            double vd = oldestYearAttendance.getAverageAttendance();

            variation = (va - vd) / vd;
        }

        return variation;
    }

    @Autowired
    public void setMonthAttendanceService(MonthAttendanceService monthAttendanceService) {
        this.monthAttendanceService = monthAttendanceService;
    }

}
