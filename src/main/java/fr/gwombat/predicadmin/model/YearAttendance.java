package fr.gwombat.predicadmin.model;

import java.io.Serializable;
import java.util.List;

public class YearAttendance implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private final TheocraticYear year;
    private final List<MonthAttendance> attendances;
    
    
    public YearAttendance(final TheocraticYear year, final List<MonthAttendance> attendances) {
        this.year = year;
        this.attendances = attendances;
    }

    public TheocraticYear getTheocraticYear() {
        return year;
    }

    public List<MonthAttendance> getAttendances() {
        return attendances;
    }

}
