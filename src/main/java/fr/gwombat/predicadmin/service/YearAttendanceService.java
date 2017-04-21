package fr.gwombat.predicadmin.service;

import java.util.List;

import fr.gwombat.predicadmin.model.Congregation;
import fr.gwombat.predicadmin.model.TheocraticYear;
import fr.gwombat.predicadmin.model.YearAttendance;

public interface YearAttendanceService {

    YearAttendance getAttendanceForYear(Congregation congregation, TheocraticYear year);
    
    List<YearAttendance> getAttendancesForCongregation(Congregation congregation);

}
