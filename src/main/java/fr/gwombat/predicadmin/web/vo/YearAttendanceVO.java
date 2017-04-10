package fr.gwombat.predicadmin.web.vo;

import java.time.Year;
import java.util.List;

import fr.gwombat.predicadmin.web.vo.builder.YearAttendanceVoBuilder;

public class YearAttendanceVO {

    private final Year                    year;
    private final MeetingAttendanceVO     maxAttendance;
    private final int                     averageAttendance;
    private final List<MonthAttendanceVO> attendances;

    public YearAttendanceVO(YearAttendanceVoBuilder builder) {
        this.attendances = builder.getAttendances();
        this.averageAttendance = builder.getAverageAttendance();
        this.maxAttendance = builder.getMaxAttendance();
        this.year = builder.getYear();
    }

    @Override
    public String toString() {
        final int yearValue = year.getValue();
        return String.format("%s-%s", yearValue - 1, yearValue);
    }

    public int getYear() {
        return year.getValue();
    }

    public MeetingAttendanceVO getMaxAttendance() {
        return maxAttendance;
    }

    public int getAverageAttendance() {
        return averageAttendance;
    }

    public List<MonthAttendanceVO> getAttendances() {
        return attendances;
    }

}
