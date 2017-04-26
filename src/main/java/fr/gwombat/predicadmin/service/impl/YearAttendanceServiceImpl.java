package fr.gwombat.predicadmin.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import fr.gwombat.predicadmin.model.MonthAttendance;
import fr.gwombat.predicadmin.model.TheocraticYear;
import fr.gwombat.predicadmin.model.YearAttendance;
import fr.gwombat.predicadmin.model.entities.Congregation;
import fr.gwombat.predicadmin.service.MonthAttendanceService;
import fr.gwombat.predicadmin.service.YearAttendanceService;

@Service
@Transactional
public class YearAttendanceServiceImpl implements YearAttendanceService {

    private MonthAttendanceService monthAttendanceService;

    @Override
    public YearAttendance getAttendanceForYear(final Congregation congregation, final TheocraticYear year) {
        if (year != null) {
            final List<MonthAttendance> attendancesByMonth = monthAttendanceService.getAttendancesBetween(congregation, year.getStart(), year.getEnd());
            if (attendancesByMonth != null)
                attendancesByMonth.sort(Comparator.comparing(MonthAttendance::getPeriod));

            return new YearAttendance(year, attendancesByMonth);
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
    
    private static List<YearAttendance> buildYearsAttendances(final List<MonthAttendance> monthsAttendances){
        if(!CollectionUtils.isEmpty(monthsAttendances)){
            final Map<TheocraticYear, YearAttendance> mapYearsAttendances = new HashMap<>(0);
            for(MonthAttendance monthAttendance : monthsAttendances){
                if(monthAttendance != null){
                    final TheocraticYear year = new TheocraticYear(monthAttendance.getPeriod());
                    YearAttendance yearAttendance = mapYearsAttendances.get(year);
                    if(yearAttendance == null){
                        final List<MonthAttendance> attendances = new ArrayList<>(1);
                        attendances.add(monthAttendance);
                        yearAttendance = new YearAttendance(year, attendances);
                        mapYearsAttendances.put(year, yearAttendance);
                    }
                    else
                        yearAttendance.addAttendance(monthAttendance);
                }
            }
            return mapYearsAttendances.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
        }
        return null;
    }

    @Autowired
    public void setMonthAttendanceService(MonthAttendanceService monthAttendanceService) {
        this.monthAttendanceService = monthAttendanceService;
    }

}
