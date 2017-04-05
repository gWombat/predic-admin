package fr.gwombat.predicadmin.web.vo.builder;

import java.time.LocalDate;

import fr.gwombat.predicadmin.web.vo.MeetingAttendanceVO;

public class MeetingAttendanceVoBuilder {

    private LocalDate date;
    private int       attendance;

    private MeetingAttendanceVoBuilder() {

    }

    public static MeetingAttendanceVoBuilder begin() {
        return new MeetingAttendanceVoBuilder();
    }

    public MeetingAttendanceVoBuilder date(LocalDate date) {
        this.date = date;
        return this;
    }

    public MeetingAttendanceVoBuilder attendance(int attendance) {
        this.attendance = attendance;
        return this;
    }
    
    public MeetingAttendanceVO build(){
        return new MeetingAttendanceVO(this);
    }

    public LocalDate getDate() {
        return date;
    }

    public int getAttendance() {
        return attendance;
    }

}
