package fr.gwombat.predicadmin.web.vo.builder;

import java.util.ArrayList;
import java.util.List;

import fr.gwombat.predicadmin.web.vo.GlobalAttendanceVO;
import fr.gwombat.predicadmin.web.vo.YearAttendanceVO;

public class GlobalAttendanceVoBuilder {

    private int                    averageAttendance;
    private int                    nbReports;
    private List<YearAttendanceVO> attendances;

    private GlobalAttendanceVoBuilder() {
        attendances = new ArrayList<>(0);
    }

    public static GlobalAttendanceVoBuilder create() {
        return new GlobalAttendanceVoBuilder();
    }
    
    public GlobalAttendanceVoBuilder attendances(final List<YearAttendanceVO> attendances){
        this.attendances = attendances;
        return this;
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

    public GlobalAttendanceVoBuilder nbReports(final int nbReports) {
        this.nbReports = nbReports;
        return this;
    }

    public GlobalAttendanceVO build() {
        return new GlobalAttendanceVO(this);
    }

    public List<YearAttendanceVO> getAttendances() {
        return attendances;
    }

    public int getAverageAttendance() {
        return averageAttendance;
    }

    public int getNbReports() {
        return nbReports;
    }

}
