package fr.gwombat.predicadmin.service;

import java.util.List;

import fr.gwombat.predicadmin.model.Congregation;
import fr.gwombat.predicadmin.model.MonthAttendance;
import fr.gwombat.predicadmin.support.period.Period;

public interface MonthAttendanceService {
    
    MonthAttendance getByPeriod(Congregation congregation, Period period);
    
    List<MonthAttendance> getAttendancesBetween(Congregation congregation, Period startPeriod, Period endperiod);

}
