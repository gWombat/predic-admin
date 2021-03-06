package fr.gwombat.predicadmin.web.vo;

import java.time.Year;

import org.apache.commons.lang3.ArrayUtils;

import fr.gwombat.predicadmin.web.vo.builder.YearAttendanceVoBuilder;
import fr.gwombat.predicadmin.web.vo.element.NumberElementVO;
import fr.gwombat.predicadmin.web.vo.element.PercentageNumberElementVO;

public class YearAttendanceVO {

    private final Year                year;
    private final MeetingAttendanceVO maxAttendance;
    private final MeetingAttendanceVO minAttendance;
    private final MeetingAttendanceVO memorial;
    private final MonthAttendanceVO   maxAverage;
    private final MonthAttendanceVO   minAverage;
    private final int                 averageAttendance;
    private MonthAttendanceVO[]       attendances;
    private NumberElementVO<Double>   averageAttendanceVariation;

    public YearAttendanceVO(YearAttendanceVoBuilder builder) {
        this.attendances = builder.getAttendances();
        this.averageAttendance = builder.getAverageAttendance();
        this.maxAttendance = builder.getMaxAttendance();
        this.year = builder.getYear();
        this.minAttendance = builder.getMinAttendance();
        this.maxAverage = builder.getMaxAverage();
        this.minAverage = builder.getMinAverage();
        this.memorial = builder.getMemorial();

        final Double variation = builder.getAverageAttendanceVariation();
        if (variation != null)
            this.averageAttendanceVariation = new PercentageNumberElementVO(variation);
    }

    @Override
    public String toString() {
        final int yearValue = year.getValue();
        return String.format("%s-%s", yearValue - 1, yearValue);
    }

    public boolean isShowable() {
        return !isAllAttendancesEmptyOrNull() && year != null && maxAverage != null && minAverage != null;
    }

    public boolean isAllAttendancesEmptyOrNull() {
        if (ArrayUtils.isEmpty(attendances))
            return true;

        for (MonthAttendanceVO monthAttendance : attendances)
            if (monthAttendance != null)
                return false;
        return true;
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

    public MonthAttendanceVO[] getAttendances() {
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

    public NumberElementVO<Double> getAverageAttendanceVariation() {
        return averageAttendanceVariation;
    }

}
