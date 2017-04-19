package fr.gwombat.predicadmin.web.vo;

import fr.gwombat.predicadmin.support.period.Period;
import fr.gwombat.predicadmin.web.vo.builder.MonthAttendanceVoBuilder;

import java.util.List;

public class MonthAttendanceVO {

    private final Period                    period;
    private final MeetingAttendanceVO       maxAttendance;
    private final MeetingAttendanceVO       minAttendance;
    private final Integer                   averageAttendance;
    private final List<MeetingAttendanceVO> attendances;

    public MonthAttendanceVO(MonthAttendanceVoBuilder builder) {
        this.period = builder.getPeriod();
        this.attendances = builder.getAttendances();
        this.maxAttendance = builder.getMaxAttendance();
        this.averageAttendance = builder.getAverageAttendance();
        this.minAttendance = builder.getMinAttendance();
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

    public Integer getAverageAttendance() {
        return averageAttendance;
    }

    public MeetingAttendanceVO getMinAttendance() {
        return minAttendance;
    }

}
