package fr.gwombat.predicadmin.model;

import java.io.Serializable;
import java.util.List;

import fr.gwombat.predicadmin.support.period.Period;

public class MonthAttendance implements Serializable {

    private static final long             serialVersionUID = 1L;

    private final Period                  period;
    private final List<MeetingAttendance> attendances;

    public MonthAttendance(final Period period, final List<MeetingAttendance> attendances) {
        this.period = period;
        this.attendances = attendances;
    }

    public Period getPeriod() {
        return period;
    }

    public List<MeetingAttendance> getAttendances() {
        return attendances;
    }

}
