package fr.gwombat.predicadmin.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import fr.gwombat.predicadmin.model.MonthAttendance;
import fr.gwombat.predicadmin.model.entities.Congregation;
import fr.gwombat.predicadmin.model.entities.MeetingAttendance;
import fr.gwombat.predicadmin.service.MeetingAttendanceService;
import fr.gwombat.predicadmin.service.MonthAttendanceService;
import fr.gwombat.predicadmin.support.period.Period;
import fr.gwombat.predicadmin.support.period.PeriodBuilder;

@Service
@Transactional
public class MonthAttendanceServiceImpl implements MonthAttendanceService {

    private MeetingAttendanceService attendanceService;

    @Autowired
    public MonthAttendanceServiceImpl(final MeetingAttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @Override
    public MonthAttendance getByPeriod(final Congregation congregation, final Period period) {
        final List<MeetingAttendance> attendances = attendanceService.getAttendancesForPeriod(congregation, period);
        return new MonthAttendance(period, attendances);
    }

    @Override
    public List<MonthAttendance> getAttendancesBetween(final Congregation congregation, final Period startPeriod, Period endPeriod) {
        Assert.notNull(congregation, "The congregation must not be null");
        Assert.notNull(startPeriod, "The start period must not be null");

        if (endPeriod == null)
            endPeriod = PeriodBuilder.create().build();

        final LocalDate startDate = startPeriod.getStart().toLocalDate();
        final LocalDate endDate = endPeriod.getEnd().toLocalDate();

        final List<MeetingAttendance> attendances = attendanceService.getAttendancesBetween(congregation, startDate, endDate);
        final List<MonthAttendance> finalAttendances = buildMonthsAttendances(attendances);
        if (finalAttendances != null && !finalAttendances.isEmpty())
            finalAttendances.sort(Comparator.comparing(MonthAttendance::getPeriod));

        return finalAttendances;
    }

    @Override
    public List<MonthAttendance> getAttendances(Congregation congregation) {
        Assert.notNull(congregation, "The congregation must not be null");

        final List<MeetingAttendance> attendances = attendanceService.getByCongregation(congregation);
        final List<MonthAttendance> finalAttendances = buildMonthsAttendances(attendances);
        if (finalAttendances != null && !finalAttendances.isEmpty())
            finalAttendances.sort(Comparator.comparing(MonthAttendance::getPeriod));

        return finalAttendances;
    }

    private static List<MonthAttendance> buildMonthsAttendances(final List<MeetingAttendance> attendances) {
        if (attendances != null) {
            final Map<Period, MonthAttendance> mapAttendances = new HashMap<>(0);
            for (MeetingAttendance meetingAttendance : attendances) {
                final Period period = PeriodBuilder.create().month(meetingAttendance.getDate().getMonthValue()).year(meetingAttendance.getDate().getYear()).build();
                MonthAttendance monthAttendance = mapAttendances.get(period);
                if (monthAttendance == null) {
                    final List<MeetingAttendance> meetings = new ArrayList<>(1);
                    meetings.add(meetingAttendance);
                    monthAttendance = new MonthAttendance(period, meetings);
                    mapAttendances.put(period, monthAttendance);
                } else {
                    monthAttendance.addAttendance(meetingAttendance);
                }
            }

            return mapAttendances.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());

        }
        return null;
    }

}
