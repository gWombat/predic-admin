package fr.gwombat.predicadmin.model;

import java.io.Serializable;
import java.time.Year;

import fr.gwombat.predicadmin.support.period.Period;
import fr.gwombat.predicadmin.support.period.PeriodBuilder;

public class TheocraticYear implements Serializable, Comparable<TheocraticYear> {

    private static final long serialVersionUID = 1L;

    private Year              year;

    private Period            start;
    private Period            end;

    public TheocraticYear() {
    }

    public TheocraticYear(int year) {
        initDatesFromYear(year);
    }

    private void initDatesFromYear(int yearValue) {
        year = Year.of(yearValue);
        start = PeriodBuilder.init().year(yearValue - 1).month(9).build();
        end = PeriodBuilder.init().year(yearValue).month(8).build();
    }

    @Override
    public String toString() {
        return String.format("%s-%s", year.getValue() - 1, year.getValue());
    }

    public Year getYear() {
        return year;
    }

    public void setYear(int yearValue) {
        initDatesFromYear(yearValue);
    }

    public Period getStart() {
        return start;
    }

    public Period getEnd() {
        return end;
    }

    @Override
    public int compareTo(TheocraticYear o) {
        return year.compareTo(o.year);
    }

}
