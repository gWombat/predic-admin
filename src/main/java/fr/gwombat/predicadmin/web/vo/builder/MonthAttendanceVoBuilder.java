package fr.gwombat.predicadmin.web.vo.builder;

import java.util.ArrayList;
import java.util.List;

import fr.gwombat.predicadmin.support.period.Period;
import fr.gwombat.predicadmin.web.vo.MeetingAttendanceVO;
import fr.gwombat.predicadmin.web.vo.MonthAttendanceVO;

/**
 * Created by gWombat.
 *
 * @since 05/04/2017
 */
public class MonthAttendanceVoBuilder {

    private Period                    period;
    private MeetingAttendanceVO       maxAttendance;
    private List<MeetingAttendanceVO> attendances;

    private MonthAttendanceVoBuilder() {
        attendances = new ArrayList<>(0);
    }

    public static MonthAttendanceVoBuilder begin() {
        return new MonthAttendanceVoBuilder();
    }

    public MonthAttendanceVoBuilder period(final Period period) {
        this.period = period;
        return this;
    }

    public MonthAttendanceVoBuilder addAttendance(MeetingAttendanceVO attendance) {
        if (attendance != null) {
            if (maxAttendance == null || attendance.getAttendance() > maxAttendance.getAttendance()) {
                attendance.setMaxOfMonth(true);
                maxAttendance = attendance;
            }
            attendances.add(attendance);
        }

        return this;
    }

    public MonthAttendanceVO build() {
        return new MonthAttendanceVO(this);
    }

    public Period getPeriod() {
        return period;
    }

    public MeetingAttendanceVO getMaxAttendance() {
        return maxAttendance;
    }

    public List<MeetingAttendanceVO> getAttendances() {
        return attendances;
    }
}
