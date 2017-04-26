package fr.gwombat.predicadmin.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import fr.gwombat.predicadmin.model.entities.MeetingAttendance;
import fr.gwombat.predicadmin.support.period.Period;

public class MonthAttendance implements Serializable {

    private static final long       serialVersionUID = 1L;

    private final Period            period;
    private List<MeetingAttendance> attendances;

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

    public void addAttendance(final MeetingAttendance meetingAttendance) {
        if (attendances == null)
            attendances = new ArrayList<>(0);

        if (meetingAttendance != null)
            attendances.add(meetingAttendance);
    }

}
