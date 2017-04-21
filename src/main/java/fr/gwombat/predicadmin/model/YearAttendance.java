package fr.gwombat.predicadmin.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class YearAttendance implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private final TheocraticYear year;
    private List<MonthAttendance> attendances;
    
    
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
    
    public void addAttendance(final MonthAttendance monthAttendance){
        if(attendances == null)
            attendances = new ArrayList<>(0);
        if(monthAttendance != null)
            attendances.add(monthAttendance);
    }

}
