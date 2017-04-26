package fr.gwombat.predicadmin.web.vo.builder;

import java.time.Year;

import fr.gwombat.predicadmin.web.vo.MeetingAttendanceVO;
import fr.gwombat.predicadmin.web.vo.MonthAttendanceVO;
import fr.gwombat.predicadmin.web.vo.YearAttendanceVO;

public class YearAttendanceVoBuilder {

    private int                 intYear;
    private Year                year;
    private MeetingAttendanceVO maxAttendance;
    private MeetingAttendanceVO minAttendance;
    private MeetingAttendanceVO memorial;
    private int                 averageAttendance;
    private MonthAttendanceVO   maxAverage;
    private MonthAttendanceVO   minAverage;
    private MonthAttendanceVO[] attendances;

    private YearAttendanceVoBuilder() {
        attendances = new MonthAttendanceVO[12];
    }

    public static YearAttendanceVoBuilder create() {
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

    public YearAttendanceVoBuilder minAttendance(final MeetingAttendanceVO minAttendance) {
        this.minAttendance = minAttendance;
        return this;
    }

    public YearAttendanceVoBuilder memorial(final MeetingAttendanceVO memorial) {
        this.memorial = memorial;
        return this;
    }

    public YearAttendanceVoBuilder averageAttendance(final Integer averageAttendance) {
        if (averageAttendance != null)
            this.averageAttendance = averageAttendance;
        else
            this.averageAttendance = 0;
        return this;
    }

    public YearAttendanceVoBuilder addMonthAttendance(final MonthAttendanceVO monthAttendance) {
        if (monthAttendance != null)
            attendances[(monthAttendance.getPeriod().getMonth() + 3) % 12] = monthAttendance;
        return this;
    }

    public YearAttendanceVoBuilder maxAverage(final MonthAttendanceVO maxAverage) {
        this.maxAverage = maxAverage;
        return this;
    }

    public YearAttendanceVoBuilder minAverage(final MonthAttendanceVO minAverage) {
        this.minAverage = minAverage;
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

    public MonthAttendanceVO[] getAttendances() {
        return attendances;
    }

}
