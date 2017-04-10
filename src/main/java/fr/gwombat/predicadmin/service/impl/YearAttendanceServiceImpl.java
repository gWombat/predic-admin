package fr.gwombat.predicadmin.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.gwombat.predicadmin.model.Congregation;
import fr.gwombat.predicadmin.model.MonthAttendance;
import fr.gwombat.predicadmin.model.TheocraticYear;
import fr.gwombat.predicadmin.model.YearAttendance;
import fr.gwombat.predicadmin.service.MonthAttendanceService;
import fr.gwombat.predicadmin.service.YearAttendanceService;
import fr.gwombat.predicadmin.support.period.Period;

@Service
@Transactional
public class YearAttendanceServiceImpl implements YearAttendanceService {

    private final MonthAttendanceService monthAttendanceService;

    @Autowired
    public YearAttendanceServiceImpl(final MonthAttendanceService monthAttendanceService) {
        this.monthAttendanceService = monthAttendanceService;
    }

    @Override
    public YearAttendance getAttendanceForYear(final Congregation congregation, final TheocraticYear year) {
        if (year != null) {
            final Map<Period, MonthAttendance> attendances = new HashMap<>(12);
            Period currentPeriod = year.getStart();
            for(int i = 0; i < 12; i++){
                currentPeriod = Period.shiftPeriode(currentPeriod, 1);
                attendances.put(currentPeriod, new MonthAttendance(currentPeriod, null));
            }
            
            final List<MonthAttendance> attendancesByMonth = monthAttendanceService.getAttendancesBetween(congregation, year.getStart(), year.getEnd());
            for(MonthAttendance attendance : attendancesByMonth)
                if(attendance != null)
                    attendances.put(attendance.getPeriod(), attendance);
            final List<MonthAttendance> finalAttendances = attendances.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
            if(finalAttendances != null)
                finalAttendances.sort((month1, month2) -> month1.getPeriod().compareTo(month2.getPeriod()));
            
            return new YearAttendance(year, finalAttendances);
        }

        return null;
    }

}
