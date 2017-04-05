package fr.gwombat.predicadmin.web.form;

import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.Min;

public class MeetingAttendanceForm {

    private String  date;
    @Min(0)
    private Integer attendance;
    private String  identifier;

    public boolean isAllFieldsNull() {
        if(!StringUtils.isBlank(date))
            return false;
        if(attendance != null)
            return false;
        return true;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getAttendance() {
        return attendance;
    }

    public void setAttendance(Integer attendance) {
        this.attendance = attendance;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
