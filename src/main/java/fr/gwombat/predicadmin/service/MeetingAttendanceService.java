package fr.gwombat.predicadmin.service;

import java.util.List;

import fr.gwombat.predicadmin.model.Congregation;
import fr.gwombat.predicadmin.model.MeetingAttendance;

public interface MeetingAttendanceService {
    
    List<MeetingAttendance> getByCongregation(Congregation congregation);

}
