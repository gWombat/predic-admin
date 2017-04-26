package fr.gwombat.predicadmin.web.vo.builder;

import java.util.ArrayList;
import java.util.List;

import fr.gwombat.predicadmin.web.vo.GlobalAttendanceVO;
import fr.gwombat.predicadmin.web.vo.YearAttendanceVO;

public class GlobalAttendanceVoBuilder {

    private int                    averageAttendance;
    private List<YearAttendanceVO> attendances;

    private GlobalAttendanceVoBuilder() {
        attendances = new ArrayList<>(0);
    }

    public static GlobalAttendanceVoBuilder create() {
        return new GlobalAttendanceVoBuilder();
    }

    public GlobalAttendanceVoBuilder addAttendance(final YearAttendanceVO yearAttendance) {
        if (yearAttendance != null)
            attendances.add(yearAttendance);
        return this;
    }

    public GlobalAttendanceVoBuilder averageAttendance(final int averageAttendance) {
        this.averageAttendance = averageAttendance;
        return this;
    }
    
    public GlobalAttendanceVO build(){
        return new GlobalAttendanceVO(this);
    }

    public List<YearAttendanceVO> getAttendances() {
        return attendances;
    }

    public int getAverageAttendance() {
        return averageAttendance;
    }

}
