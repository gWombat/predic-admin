package fr.gwombat.predicadmin.support.period;

import java.io.Serializable;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public final class Period implements Serializable, Comparable<Period> {

    private static final long              serialVersionUID = 1L;

    private static final DateTimeFormatter DATE_FORMAT      = DateTimeFormatter.ofPattern("yyyyMM");

    private final YearMonth                yearMonth;

    public static Period parseFromIntValue(int value) {
        final YearMonth yearMonth = YearMonth.parse(String.valueOf(value), DATE_FORMAT);
        final Period period = PeriodBuilder.init().yearMonth(yearMonth).build();
        return period;
    }

    public static Period shiftPeriode(Period period, int nbMonth) {
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

    public boolean isCurrentPeriode() {
        return this.toInt() == PeriodUtils.currentPeriodValue();
    }

    @Override
    public int compareTo(Period o) {
        if (yearMonth.isAfter(o.yearMonth))
            return 1;
        else if (yearMonth.isBefore(o.yearMonth))
            return -1;
        return 0;
    }

    @Override
    public String toString() {
        final DateTimeFormatter format = DateTimeFormatter.ofPattern("MMM yyyy");
        return String.format("%s (%s)", yearMonth.format(format), toInt());
    }

}
