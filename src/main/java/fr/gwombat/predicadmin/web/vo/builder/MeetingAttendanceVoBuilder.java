package fr.gwombat.predicadmin.web.vo.builder;

import java.time.LocalDate;

import fr.gwombat.predicadmin.web.vo.MeetingAttendanceVO;

public class MeetingAttendanceVoBuilder {

    private LocalDate date;
    private int       attendance;
    private Boolean   memorial;
    private Boolean   specialWeek;
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

    public MeetingAttendanceVoBuilder memorial(final Boolean memorial) {
        this.memorial = memorial;
        return this;
    }

    public MeetingAttendanceVoBuilder specialWeek(final Boolean specialWeek) {
        this.specialWeek = specialWeek;
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

    public Boolean getMemorial() {
        return memorial;
    }

    public Boolean getSpecialWeek() {
        return specialWeek;
    }
}
