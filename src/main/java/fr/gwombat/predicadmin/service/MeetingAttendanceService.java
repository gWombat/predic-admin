package fr.gwombat.predicadmin.service;

import java.util.List;

import fr.gwombat.predicadmin.model.Congregation;
import fr.gwombat.predicadmin.model.MeetingAttendance;
import fr.gwombat.predicadmin.support.period.Period;

public interface MeetingAttendanceService {
    
    List<MeetingAttendance> getByCongregation(Congregation congregation);
    
    List<MeetingAttendance> getAttendanceForPeriod(Congregation congregation, Period period);

}
