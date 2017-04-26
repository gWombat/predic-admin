package fr.gwombat.predicadmin.service;

import java.time.LocalDate;
import java.util.List;

import fr.gwombat.predicadmin.model.entities.Congregation;
import fr.gwombat.predicadmin.model.entities.MeetingAttendance;
import fr.gwombat.predicadmin.support.period.Period;

public interface MeetingAttendanceService {
    
    MeetingAttendance getByIdentifier(String identifier);
    
    MeetingAttendance save(MeetingAttendance meetingAttendance);
    
    List<MeetingAttendance> getByCongregation(Congregation congregation);
    
    List<MeetingAttendance> getAttendancesBetween(Congregation congregation, LocalDate startDate, LocalDate endDate);
    
    List<MeetingAttendance> getAttendancesForPeriod(Congregation congregation, Period period);
    
    void deleteByIdentifier(String identifier);

}
