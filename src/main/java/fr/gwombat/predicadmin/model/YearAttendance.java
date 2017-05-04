package fr.gwombat.predicadmin.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

public class YearAttendance implements Serializable {

    private static final long     serialVersionUID = 1L;

    private final TheocraticYear  year;
    private List<MonthAttendance> attendances;
    private int                   averageAttendance;
    private Double                averageAttendanceVariation;

    public YearAttendance(final TheocraticYear year, final List<MonthAttendance> attendances) {
        this.year = year;
        this.attendances = attendances;
        calculateAverageAttendance();
    }

    public TheocraticYear getTheocraticYear() {
        return year;
    }

    public List<MonthAttendance> getAttendances() {
        return attendances;
    }

    public void addAttendance(final MonthAttendance monthAttendance) {
        if (attendances == null)
            attendances = new ArrayList<>(0);
        if (monthAttendance != null)
            attendances.add(monthAttendance);
        calculateAverageAttendance();
    }

    private void calculateAverageAttendance() {
        int result = 0;
        if (!CollectionUtils.isEmpty(attendances)) {
            for (MonthAttendance monthAttendance : attendances)
                if (monthAttendance != null)
                    result += monthAttendance.getAverageAttendance();
            result = result / attendances.size();
        }
        averageAttendance = result;
    }

    public int getAverageAttendance() {
        return averageAttendance;
    }

    public Double getAverageAttendanceVariation() {
        return averageAttendanceVariation;
    }

    public void setAverageAttendanceVariation(Double averageAttendanceVariation) {
        this.averageAttendanceVariation = averageAttendanceVariation;
    }

}
