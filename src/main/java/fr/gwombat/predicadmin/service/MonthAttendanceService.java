package fr.gwombat.predicadmin.service;

import fr.gwombat.predicadmin.model.Congregation;
import fr.gwombat.predicadmin.model.MonthAttendance;
import fr.gwombat.predicadmin.support.period.Period;

public interface MonthAttendanceService {
    
    MonthAttendance getByPeriod(Congregation congregation, Period period);

}
