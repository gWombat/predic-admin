package fr.gwombat.predicadmin.web.vo.builder;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import fr.gwombat.predicadmin.web.vo.MeetingAttendanceVO;
import fr.gwombat.predicadmin.web.vo.MonthAttendanceVO;
import fr.gwombat.predicadmin.web.vo.YearAttendanceVO;

public class YearAttendanceVoBuilder {

    private int                     intYear;
    private Year                    year;
    private MeetingAttendanceVO     maxAttendance;
    private int                     averageAttendance;
    private List<MonthAttendanceVO> attendances;

    private YearAttendanceVoBuilder() {
        attendances = new ArrayList<>(0);
    }

    public static YearAttendanceVoBuilder begin() {
        return new YearAttendanceVoBuilder();
    }

    public YearAttendanceVoBuilder year(int year) {
        this.intYear = year;
        return this;
    }

    public YearAttendanceVoBuilder maxAttendance(final MeetingAttendanceVO maxAttendance) {
        this.maxAttendance = maxAttendance;
        return this;
    }

    public YearAttendanceVoBuilder averageAttendance(final int averageAttendance) {
        this.averageAttendance = averageAttendance;
        return this;
    }

    public YearAttendanceVoBuilder addMonthAttendance(final MonthAttendanceVO monthAttendance) {
        if (monthAttendance != null)
            attendances.add(monthAttendance);
        return this;
    }

    public YearAttendanceVO build() {
        year = Year.of(intYear);
        return new YearAttendanceVO(this);
    }

    public Year getYear() {
        return year;
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
