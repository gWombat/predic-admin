package fr.gwombat.predicadmin.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.gwombat.predicadmin.model.Congregation;
import fr.gwombat.predicadmin.model.MeetingAttendance;
import fr.gwombat.predicadmin.repository.MeetingAttendanceRepository;
import fr.gwombat.predicadmin.service.MeetingAttendanceService;
import fr.gwombat.predicadmin.support.period.Period;

@Service
@Transactional
public class MeetingAttendanceServiceImpl implements MeetingAttendanceService {
    
    private MeetingAttendanceRepository attendanceRepository;

    @Autowired
    public MeetingAttendanceServiceImpl(final MeetingAttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    public MeetingAttendance getByIdentifier(final String identifier) {
        return attendanceRepository.findByIdentifier(identifier);
    }

    @Override
    public MeetingAttendance save(MeetingAttendance meetingAttendance) {
        return attendanceRepository.save(meetingAttendance);
    }

    @Override
    public List<MeetingAttendance> getByCongregation(final Congregation congregation) {
        return attendanceRepository.findByCongregation(congregation);
    }

    @Override
    public List<MeetingAttendance> getAttendancesBetween(final Congregation congregation, final LocalDate startDate, final LocalDate endDate) {
        return attendanceRepository.findByCongregationAndDateBetweenOrderByDateAsc(congregation, startDate, endDate);
    }
    
    @Override
    public List<MeetingAttendance> getAttendancesForPeriod(final Congregation congregation, final Period period) {
        if (congregation == null || period == null)
            return null;

        final LocalDate startDate = period.getStart().toLocalDate();
        final LocalDate endDate = period.getEnd().toLocalDate();
        return getAttendancesBetween(congregation, startDate, endDate);
    }

    @Override
    public void deleteByIdentifier(final String identifier) {
        final MeetingAttendance attendance = attendanceRepository.findByIdentifier(identifier);
        if(attendance != null)
            attendanceRepository.delete(attendance);
    }

}
