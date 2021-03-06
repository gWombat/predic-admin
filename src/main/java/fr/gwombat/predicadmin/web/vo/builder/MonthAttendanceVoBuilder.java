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
    private MeetingAttendanceVO       minAttendance;
    private MeetingAttendanceVO       memorialAttendance;
    private Integer                   averageAttendance;
    private List<MeetingAttendanceVO> attendances;

    private MonthAttendanceVoBuilder() {
        attendances = new ArrayList<>(0);
    }

    public static MonthAttendanceVoBuilder create() {
        return new MonthAttendanceVoBuilder();
    }

    public MonthAttendanceVoBuilder period(final Period period) {
        this.period = period;
        return this;
    }

    public MonthAttendanceVoBuilder addAttendance(MeetingAttendanceVO attendance) {
        if (attendance != null){
            attendances.add(attendance);
            if(attendance.isMemorial())
                memorialAttendance = attendance;
        }
        return this;
    }

    public MonthAttendanceVoBuilder maxAttendance(MeetingAttendanceVO maxAttendance) {
        this.maxAttendance = maxAttendance;
        if (this.maxAttendance != null)
            this.maxAttendance.setMaxOfMonth(true);
        return this;
    }

    public MonthAttendanceVoBuilder minAttendance(MeetingAttendanceVO minAttendance) {
        this.minAttendance = minAttendance;
        if(this.minAttendance != null)
            this.minAttendance.setMinOfMonth(true);
        return this;
    }

    public MonthAttendanceVoBuilder memorialAttendance(MeetingAttendanceVO memorialAttendance) {
        this.memorialAttendance = memorialAttendance;
        return this;
    }

    public MonthAttendanceVoBuilder averageAttendance(Integer averageAttendance) {
        this.averageAttendance = averageAttendance;
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

    public Integer getAverageAttendance() {
        return averageAttendance;
    }

    public MeetingAttendanceVO getMinAttendance() {
        return minAttendance;
    }

    public MeetingAttendanceVO getMemorialAttendance() {
        return memorialAttendance;
    }
}
