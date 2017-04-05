package fr.gwombat.predicadmin.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import fr.gwombat.predicadmin.support.period.Period;

public class MonthAttendance implements Serializable {

    private static final long                 serialVersionUID = 1L;

    private final Period                      period;
    private MeetingAttendance                 maxAttendance;
    private Map<LocalDate, MeetingAttendance> attendances;

    public MonthAttendance(final Period period, final List<MeetingAttendance> attendances) {
        this.period = period;
        initAttendances(attendances);
    }

    private void initAttendances(final List<MeetingAttendance> attendances) {
        if (attendances != null) {
            for (MeetingAttendance attendance : attendances) {
                if (attendance != null) {
                    this.attendances.put(attendance.getDate(), attendance);
                    if (maxAttendance == null || attendance.getAttendance() > maxAttendance.getAttendance())
                        maxAttendance = attendance;
                }
            }
        }
    }

    public Period getPeriod() {
        return period;
    }

    public MeetingAttendance getMaxAttendance() {
        return maxAttendance;
    }

    public Map<LocalDate, MeetingAttendance> getAttendances() {
        return attendances;
    }
    
}
