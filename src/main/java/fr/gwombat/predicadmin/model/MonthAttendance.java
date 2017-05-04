package fr.gwombat.predicadmin.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import fr.gwombat.predicadmin.model.entities.MeetingAttendance;
import fr.gwombat.predicadmin.support.period.Period;

public class MonthAttendance implements Serializable {

    private static final long       serialVersionUID = 1L;

    private final Period            period;
    private List<MeetingAttendance> attendances;
    private int                     averageAttendance;

    public MonthAttendance(final Period period, final List<MeetingAttendance> attendances) {
        this.period = period;
        this.attendances = attendances;
        calculateAverageAttendance();
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
        calculateAverageAttendance();
    }

    private void calculateAverageAttendance() {
        int result = 0;
        if (!CollectionUtils.isEmpty(attendances)) {
            for (MeetingAttendance attendance : attendances)
                if (attendance != null)
                    result += attendance.getAttendance();
            result = result / attendances.size();
        }

        averageAttendance = result;
    }

    public int getAverageAttendance() {
        return averageAttendance;
    }

}
