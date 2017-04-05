package fr.gwombat.predicadmin.web.vo;

import java.time.LocalDate;

import fr.gwombat.predicadmin.web.vo.builder.MeetingAttendanceVoBuilder;

public class MeetingAttendanceVO {

    private final LocalDate date;
    private final int       attendance;
    private boolean         maxOfmonth;

    public MeetingAttendanceVO(MeetingAttendanceVoBuilder builder) {
        this.date = builder.getDate();
        this.attendance = builder.getAttendance();
    }

    public LocalDate getDate() {
        return date;
    }

    public int getAttendance() {
        return attendance;
    }

    public boolean isMaxOfmonth() {
        return maxOfmonth;
    }

    public void setMaxOfmonth(boolean maxOfmonth) {
        this.maxOfmonth = maxOfmonth;
    }

}
