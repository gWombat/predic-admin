package fr.gwombat.predicadmin.web.vo;

import java.time.Year;
import java.util.List;

import fr.gwombat.predicadmin.web.vo.builder.YearAttendanceVoBuilder;

public class YearAttendanceVO {

    private final Year                    year;
    private final MeetingAttendanceVO     maxAttendance;
    private final MeetingAttendanceVO     minAttendance;
    private final MeetingAttendanceVO     memorial;
    private final MonthAttendanceVO       maxAverage;
    private final MonthAttendanceVO       minAverage;
    private final int                     averageAttendance;
    private final List<MonthAttendanceVO> attendances;

    public YearAttendanceVO(YearAttendanceVoBuilder builder) {
        this.attendances = builder.getAttendances();
        this.averageAttendance = builder.getAverageAttendance();
        this.maxAttendance = builder.getMaxAttendance();
        this.year = builder.getYear();
        this.minAttendance = builder.getMinAttendance();
        this.maxAverage = builder.getMaxAverage();
        this.minAverage = builder.getMinAverage();
        this.memorial = builder.getMemorial();
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

    public MeetingAttendanceVO getMinAttendance() {
        return minAttendance;
    }

    public MonthAttendanceVO getMaxAverage() {
        return maxAverage;
    }

    public MonthAttendanceVO getMinAverage() {
        return minAverage;
    }

    public MeetingAttendanceVO getMemorial() {
        return memorial;
    }

}
