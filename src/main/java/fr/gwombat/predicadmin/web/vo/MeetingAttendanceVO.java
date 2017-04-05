package fr.gwombat.predicadmin.web.vo;

import fr.gwombat.predicadmin.web.vo.builder.MeetingAttendanceVoBuilder;

import java.time.LocalDate;

public class MeetingAttendanceVO {

    private final LocalDate date;
    private final int       attendance;
    private final String    identifier;
    private       boolean   maxOfMonth;

    public MeetingAttendanceVO(MeetingAttendanceVoBuilder builder) {
        this.date = builder.getDate();
        this.attendance = builder.getAttendance();
        this.identifier = builder.getIdentifier();
    }

    public LocalDate getDate() {
        return date;
    }

    public int getAttendance() {
        return attendance;
    }

    public boolean isMaxOfMonth() {
        return maxOfMonth;
    }

    public void setMaxOfMonth(boolean maxOfMonth) {
        this.maxOfMonth = maxOfMonth;
    }

    public String getIdentifier() {
        return identifier;
    }
}
