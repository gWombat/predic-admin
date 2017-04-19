package fr.gwombat.predicadmin.web.vo;

import java.time.LocalDate;

import org.apache.commons.lang3.BooleanUtils;

import fr.gwombat.predicadmin.web.vo.builder.MeetingAttendanceVoBuilder;

public class MeetingAttendanceVO {

    private final LocalDate date;
    private final int       attendance;
    private final String    identifier;
    private final boolean   memorial;
    private final boolean   specialWeek;
    private boolean         maxOfMonth;

    public MeetingAttendanceVO(MeetingAttendanceVoBuilder builder) {
        this.date = builder.getDate();
        this.attendance = builder.getAttendance();
        this.identifier = builder.getIdentifier();
        this.memorial = BooleanUtils.toBoolean(builder.getMemorial());
        this.specialWeek = BooleanUtils.toBoolean(builder.getSpecialWeek());
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

    public boolean isMemorial() {
        return memorial;
    }

    public boolean isSpecialWeek() {
        return specialWeek;
    }
}
