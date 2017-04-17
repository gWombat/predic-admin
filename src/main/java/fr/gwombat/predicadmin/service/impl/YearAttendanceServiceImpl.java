package fr.gwombat.predicadmin.service.impl;

import fr.gwombat.predicadmin.model.Congregation;
import fr.gwombat.predicadmin.model.MonthAttendance;
import fr.gwombat.predicadmin.model.TheocraticYear;
import fr.gwombat.predicadmin.model.YearAttendance;
import fr.gwombat.predicadmin.service.MonthAttendanceService;
import fr.gwombat.predicadmin.service.YearAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

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
        if(year != null) {
            final List<MonthAttendance> attendancesByMonth = monthAttendanceService.getAttendancesBetween(congregation, year
                    .getStart(), year.getEnd());
            if(attendancesByMonth != null)
                attendancesByMonth.sort(Comparator.comparing(MonthAttendance::getPeriod));

            return new YearAttendance(year, attendancesByMonth);
        }

        return null;
    }

}
