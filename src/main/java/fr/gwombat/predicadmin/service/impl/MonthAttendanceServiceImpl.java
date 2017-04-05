package fr.gwombat.predicadmin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.gwombat.predicadmin.model.Congregation;
import fr.gwombat.predicadmin.model.MeetingAttendance;
import fr.gwombat.predicadmin.model.MonthAttendance;
import fr.gwombat.predicadmin.service.MeetingAttendanceService;
import fr.gwombat.predicadmin.service.MonthAttendanceService;
import fr.gwombat.predicadmin.support.period.Period;

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
        final List<MeetingAttendance> attendances = attendanceService.getAttendanceForPeriod(congregation, period);
        return new MonthAttendance(period, attendances);
    }

}
