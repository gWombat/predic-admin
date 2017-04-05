package fr.gwombat.predicadmin.web.vo.builder;

import fr.gwombat.predicadmin.web.vo.MeetingAttendanceVO;

import java.time.LocalDate;

public class MeetingAttendanceVoBuilder {

    private LocalDate date;
    private int       attendance;
    private String    identifier;

    private MeetingAttendanceVoBuilder() {

    }

    public static MeetingAttendanceVoBuilder begin() {
        return new MeetingAttendanceVoBuilder();
    }

    public MeetingAttendanceVoBuilder date(final LocalDate date) {
        this.date = date;
        return this;
    }

    public MeetingAttendanceVoBuilder attendance(final int attendance) {
        this.attendance = attendance;
        return this;
    }

    public MeetingAttendanceVoBuilder identifier(final String identifier) {
        this.identifier = identifier;
        return this;
    }

    public MeetingAttendanceVO build() {
        return new MeetingAttendanceVO(this);
    }

    public LocalDate getDate() {
        return date;
    }

    public int getAttendance() {
        return attendance;
    }

    public String getIdentifier() {
        return identifier;
    }
}
