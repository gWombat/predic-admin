package fr.gwombat.predicadmin.support.period;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public final class Period implements Serializable, Comparable<Period> {

    private static final long              serialVersionUID = 1L;

    private static final DateTimeFormatter DATE_FORMAT      = DateTimeFormatter.ofPattern("yyyyMM");

    private final YearMonth                yearMonth;

    public static Period parseFromIntValue(int value) {
        final YearMonth yearMonth = YearMonth.parse(String.valueOf(value), DATE_FORMAT);
        final Period period = PeriodBuilder.init().yearMonth(yearMonth).build();
        return period;
    }

    public static Period shiftPeriod(Period period, int nbMonth) {
        int newVal = PeriodUtils.shiftPeriod(period.yearMonth, nbMonth);
        return parseFromIntValue(newVal);
    }

    public Period(PeriodBuilder periodBuilder) {
        yearMonth = periodBuilder.getYearMonth();
    }

    public int toInt() {
        return PeriodUtils.convertPeriodToInt(yearMonth);
    }

    public int getPeriodValue() {
        return toInt();
    }

    public int getMonth() {
        return yearMonth.getMonthValue();
    }

    public int getYear() {
        return yearMonth.getYear();
    }

    public boolean isCurrentPeriod() {
        return this.toInt() == PeriodUtils.currentPeriodValue();
    }

    public LocalDateTime getStart() {
        return LocalDateTime.of(getYear(), getMonth(), 1, 0, 0, 0, 0);
    }

    public LocalDateTime getEnd() {
        final LocalDateTime date = LocalDateTime.of(getYear(), getMonth(), 15, 23, 59, 59, 999);
        return date.with(TemporalAdjusters.lastDayOfMonth());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj instanceof Period) {
            Period other = (Period) obj;
            return new EqualsBuilder().append(this.getPeriodValue(), other.getPeriodValue()).build();
        }

        return false;
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getPeriodValue()).build();
    }

    @Override
    public int compareTo(Period o) {
        return yearMonth.compareTo(o.yearMonth);
    }

    @Override
    public String toString() {
        final DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM yyyy");
        return String.format("%s (%s)", yearMonth.format(format), toInt());
    }

    public boolean isBefore(final Period otherPeriod){
        return compareTo(otherPeriod) < 0;
    }

    public boolean isBeforeNow(){
        return isBefore(PeriodBuilder.init().build());
    }

}
