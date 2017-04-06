package fr.gwombat.predicadmin.web.vo;

import java.util.List;

import fr.gwombat.predicadmin.support.period.Period;
import fr.gwombat.predicadmin.web.vo.builder.MonthAttendanceVoBuilder;

public class MonthAttendanceVO {

    private final Period                    period;
    private final MeetingAttendanceVO       maxAttendance;
    private final List<MeetingAttendanceVO> attendances;

    public MonthAttendanceVO(MonthAttendanceVoBuilder builder) {
        this.period = builder.getPeriod();
        this.attendances = builder.getAttendances();
        this.maxAttendance = builder.getMaxAttendance();
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
