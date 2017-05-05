package fr.gwombat.predicadmin.web.vo;

import java.util.List;

import fr.gwombat.predicadmin.web.vo.builder.GlobalAttendanceVoBuilder;

public class GlobalAttendanceVO {

    private final int                    averageAttendance;
    private final int                    nbReports;
    private final List<YearAttendanceVO> attendances;

    public GlobalAttendanceVO(final GlobalAttendanceVoBuilder builder) {
        attendances = builder.getAttendances();
        averageAttendance = builder.getAverageAttendance();
        nbReports = builder.getNbReports();
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
