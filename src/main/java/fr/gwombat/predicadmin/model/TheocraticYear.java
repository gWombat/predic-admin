package fr.gwombat.predicadmin.model;

import java.io.Serializable;
import java.time.Year;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.util.Assert;

import fr.gwombat.predicadmin.support.period.Period;
import fr.gwombat.predicadmin.support.period.PeriodBuilder;

public class TheocraticYear implements Serializable, Comparable<TheocraticYear> {

    private static final long serialVersionUID = 1L;

    private Year              year;

    private Period            start;
    private Period            end;

    public TheocraticYear() {
    }

    public TheocraticYear(final Period period) {
        Assert.notNull(period, "The period must not be null");

        int intYear;
        if (period.getMonth() >= 9)
            intYear = period.getYear() + 1;
        else
            intYear = period.getYear();

        initDatesFromYear(intYear);
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj instanceof TheocraticYear) {
            TheocraticYear other = (TheocraticYear) obj;
            EqualsBuilder builder = new EqualsBuilder().append(this.year, other.year);
            return builder.build();
        }
        return false;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder().append(year);
        return builder.build();
    }
}
